import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	Scene Intro,stateOfGame,thirdScene;
	BorderPane introPane,gamePane;
	TextField portNum, ipAddress;
	Button connect;



	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("CLIENT");

			introPane = new BorderPane();
			gamePane = new BorderPane();
			introPane.setStyle("-fx-font-family: SansSerif;-fx-background-color: blue;");
			gamePane.setStyle("-fx-font-family: SansSerif;-fx-background-color: blue;");


			Intro = new Scene(introPane, 600, 400);
			stateOfGame = new Scene(gamePane, 600, 400);

			portNum = new TextField("Enter port number");
			ipAddress = new TextField("Enter ip address");
			connect = new Button("Connect");
			VBox introBox = new VBox(portNum,ipAddress,connect);

			introPane.setCenter(introBox);
			introBox.setAlignment(Pos.CENTER);



			primaryStage.setScene(Intro);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}




