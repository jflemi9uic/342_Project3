import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class JavaFXTemplate extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/FXML/startPage.fxml"));
		
			primaryStage.setTitle("My Application");
			Scene s1 = new Scene(root, 700,400);
			s1.getStylesheets().add("/styles/style.css");
			primaryStage.setScene(s1);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}




