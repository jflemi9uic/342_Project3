import java.util.HashMap;
import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.geometry.*;
import javafx.application.Platform;
import javafx.scene.control.TextField;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	public boolean isNum(String num) {
        if (num == "") { return false; }

        try { int i = Integer.parseInt(num); } 
        catch (NumberFormatException e) { return false; }
        return true;
    }

	void decrementPlayers() {
		int curClients = Integer.parseInt(numClientsNum.getText());
		curClients--;
		numClientsNum.setText(String.valueOf(curClients));

	}
	void incrementPlayers() {
		int curClients = Integer.parseInt(numClientsNum.getText());
		curClients++;
		numClientsNum.setText(String.valueOf(curClients));
	}

	Scene startScene, logScene;
	BorderPane startPane, logPane;
	ListView serverLog;
	private int port;
	Server serverConnection;
	Label numClientsNum;
	int p1,p2;
	Label player1score;
	Label player2score;

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("SERVER");

		startPane = new BorderPane();
		logPane = new BorderPane();
		startPane.setStyle("-fx-font-family: SansSerif;-fx-background-color: #ff7f50;");
		logPane.setStyle("-fx-font-family: SansSerif;-fx-background-color: #ff7f50;");
		startScene = new Scene(startPane, 600, 400);
		logScene = new Scene(logPane, 600, 400);

		// set up the start page
		Label portLabel = new Label("Enter a port: ");
		TextField portTF = new TextField();
		Button portSend = new Button("Send");
		HBox starthbox = new HBox(portLabel, portTF, portSend);

		startPane.setCenter(starthbox);
		starthbox.setAlignment(Pos.CENTER);

		// set up the log page
		Label serverStatusLabel = new Label("Server status: ");
		Label serverStatusStatus = new Label("Online");
		HBox statushbox = new HBox(serverStatusLabel, serverStatusStatus);
		statushbox.setAlignment(Pos.CENTER);

		Label numClientsLabel = new Label("Number of clients: ");
		numClientsNum = new Label("0");
		HBox clientshbox = new HBox(numClientsLabel, numClientsNum);
		clientshbox.setAlignment(Pos.CENTER);

		VBox topInfo = new VBox(statushbox, clientshbox);

		Label logLabel = new Label("Server Log");
		serverLog = new ListView();
		VBox logvbox = new VBox(logLabel, serverLog);
		logvbox.setPadding(new Insets(10,10,10,10));

		// peoples scores inside of a vbox
		Label player1 = new Label("Player 1 (points): ");
		Label player1client = new Label("");
		player1score = new Label("0");
		HBox player1hbox = new HBox(player1, player1client, player1score);

		Label player2 = new Label("Player 2 (points): ");
		Label player2client = new Label("");
		player2score = new Label("0");
		HBox player2hbox = new HBox(player2, player2client, player2score);

		VBox players = new VBox(player1hbox, player2hbox);
		players.setAlignment(Pos.CENTER);

		HBox logAndPlayers = new HBox(logvbox, players);
		VBox overall = new VBox(topInfo, logAndPlayers);
		overall.setPadding(new Insets(30,30,30,30));

		logPane.setCenter(overall);

		primaryStage.setScene(startScene);
        primaryStage.show();

		// save the port and swtich scenes to the log scene
		portSend.setOnAction(l -> {
			// confirm that it is a number
			if (portTF.getText() != "" && isNum(portTF.getText())) {
				port = Integer.parseInt(portTF.getText());

				primaryStage.setScene(logScene);
				primaryStage.setTitle("Server log");

				serverConnection = new Server(port, 
					data -> {
						Platform.runLater( () -> {
							serverLog.getItems().add(data.toString());
						});
					}, 
					data2 -> {
						Platform.runLater( () -> {
							if (data2 == "1") { incrementPlayers(); }
							else { decrementPlayers(); }
						});
					},
					data3 -> {
						Platform.runLater( () -> {
							player1score.setText(data3.toString());
						});
					},
					data4 -> {
						Platform.runLater( () -> {
							player2score.setText(data4.toString());
						});
					}

					); // end of serverConnection
			}
		}); // end of setOnAction
	}
}