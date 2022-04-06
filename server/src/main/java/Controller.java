

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
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
    }

    public void startServer(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/logPage.fxml"));
        Parent root2 = loader.load();
        Controller myctr = loader.getController();
        root.getScene().setRoot(root2);
    }
}
