

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

public class Controller implements Initializable {
	
	@FXML private BorderPane root; // start page

    @FXML private BorderPane root2; // log page
    @FXML private TextField inputPort; // server decided port
    private static int chosenPort;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) { }

    public boolean isNum(String num) {
        if (num == "") { return false; }

        try { int i = Integer.parseInt(num); } 
        catch (NumberFormatException e) { return false; }
        return true;
    }

    public void startServer(ActionEvent e) throws IOException {
        // make sure it is a valid port
        if (isNum(inputPort.getText())) {
            chosenPort = Integer.parseInt(inputPort.getText());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/logPage.fxml"));
            Parent root2 = loader.load();
            Controller myctr = loader.getController();
            root.getScene().setRoot(root2);
        }
    }
}
