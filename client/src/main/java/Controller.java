import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.application.Platform;

public class Controller implements Initializable {

    Client clientConnection;

    @FXML private BorderPane root;
    @FXML private BorderPane root2;
    @FXML private TextField inputText;
    @FXML private Button sendButton;

	@Override
	public void initialize(URL location, ResourceBundle resources) { }

    public void startClient(ActionEvent e) throws IOException {
        // data is the parameter passed in to Client() constructor, so call
        clientConnection = new Client(
            data -> {
                Platform.runLater( () -> {
                    System.out.println(data.toString());
	                // listItems2.getItems().add(data.toString());
	            });
            }
        );
        clientConnection.start();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/gamePage.fxml"));
        Parent root2 = loader.load();
        Controller myctr = loader.getController();
        root.getScene().setRoot(root2);
    }

    public void sendMethod(ActionEvent e) throws IOException {
        System.out.println(" * entered sendMethod() -> inputText = " + inputText.getText());
        clientConnection.send(inputText.getText());
        System.out.println(" * passed send()");
        inputText.clear();
    }
}
