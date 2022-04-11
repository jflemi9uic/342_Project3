import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;  
import java.io.FileInputStream; 
import javafx.geometry.*;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	Scene introScene,gameScene,thirdScene;
	BorderPane introPane,gamePane,thirdPane;
	TextField portNum, ipAddress;
	Button connect;
	Client clientConnection;
	private static int play;
	ListView<String> gameLog;
	Label playernumberlabel2, p1points, p2points; // LABEL FOR THE PLAYER NUMBER
	boolean firstmessage = true;
	int PlayerNumber;
	ImageView opImageView;
	TextField guessField = new TextField();
	Button sendData = new Button("Send");
	Button nextRound = new Button("Next Round"); //Button for next round
	Button finishGame = new Button("GG"); //Button to move to third scene

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("CLIENT");

			introPane = new BorderPane();
			gamePane = new BorderPane();
			thirdPane = new BorderPane();
			introPane.setStyle("-fx-font-family: SansSerif;-fx-background-color: lightblue;");
			gamePane.setStyle("-fx-font-family: SansSerif;-fx-background-color: lightblue;");
			thirdPane.setStyle("-fx-font-family: SansSerif;-fx-background-color: lightblue;");

			introScene = new Scene(introPane, 600, 400);
			gameScene = new Scene(gamePane, 600, 400);
			thirdScene = new Scene(thirdPane, 600, 400);

			// create intro scene
			portNum = new TextField();
			portNum.setPromptText("Enter port");
			ipAddress = new TextField();
			ipAddress.setPromptText("Enter ip address"); // assumer user enters a valid one
			connect = new Button("Connect");
			VBox introBox = new VBox(portNum,ipAddress,connect);
			introBox.setPrefWidth(200);

			introPane.setCenter(introBox);
			introBox.setAlignment(Pos.CENTER);

			primaryStage.setScene(introScene);
			primaryStage.show();

			connect.setOnAction(l -> {
				clientConnection = new Client(ipAddress.getText(), Integer.parseInt(portNum.getText()), 
					data -> {
						Platform.runLater( () -> {
							if (firstmessage) {
								// data is from the first call to callback.accpet() in while true
								MorraInfo temp = (MorraInfo) data;
								PlayerNumber = temp.playernumber;
								firstmessage = false;
								playernumberlabel2.setText(String.valueOf((PlayerNumber)));
							} else {
								MorraInfo temp = (MorraInfo) data;
								// if player is 1 -> output 2's data to the log
								if (temp.have2players) {
//######################################################
									if (PlayerNumber == 1) {
										//gameLog.getItems().add(String.valueOf(temp.getp2play()));
										//gameLog.getItems().add(String.valueOf(temp.getp2guess()));
										if (temp.getp2play() == 0){
											Image t = new Image("zero.png");
											opImageView.setImage(t);
										}
										else if (temp.getp2play() == 1) {
											Image t = new Image("one.png");
											opImageView.setImage(t);
										}
										else if (temp.getp2play() == 2) {
											Image t = new Image("two.png");
											opImageView.setImage(t);
										}
										else if (temp.getp2play() == 3) {
											Image t = new Image("three.png");
											opImageView.setImage(t);
										}
										else if (temp.getp2play() == 4) {
											Image t = new Image("four.png");
											opImageView.setImage(t);
										}
										else if (temp.getp2play() == 5) {
											Image t = new Image("five.png");
											opImageView.setImage(t);
										}
										else if (temp.getp2play() == 6) {
											Image t = new Image("six.png");
											opImageView.setImage(t);
										}
										gameLog.getItems().add("Correct Answer was: " + String.valueOf(temp.getp1play() + temp.getp2play()));
										temp.have2players = false;
										guessField.setDisable(false);
										sendData.setDisable(false);
									} else {
										if (temp.getp1play() == 0) {
											Image t = new Image("zero.png");
											opImageView.setImage(t);
										}
										else if (temp.getp1play() == 1) {
											Image t = new Image("one.png");
											opImageView.setImage(t);
										}
										else if (temp.getp1play() == 2) {
											Image t = new Image("two.png");
											opImageView.setImage(t);
										}
										else if (temp.getp1play() == 3) {
											Image t = new Image("three.png");
											opImageView.setImage(t);
										}
										else if (temp.getp1play() == 4) {
											Image t = new Image("four.png");
											opImageView.setImage(t);
										}
										else if (temp.getp1play() == 5) {
											Image t = new Image("five.png");
											opImageView.setImage(t);
										}
										else if (temp.getp1play() == 6) {
											Image t = new Image("six.png");
											opImageView.setImage(t);
										}
										gameLog.getItems().add("Correct Answer was: " + String.valueOf(temp.getp1play() + temp.getp2play()));
										temp.have2players = false;
										guessField.setDisable(false);
										sendData.setDisable(true);
									}
								}
							}
						});
					},
					data2 -> {
						Platform.runLater( () -> {
							MorraInfo temp = (MorraInfo) data2;
							p1points.setText(String.valueOf(temp.p1Points));
							p2points.setText(String.valueOf(temp.p2Points));

//							System.out.println("idk something here");
						});
					}
				);

				clientConnection.start();
				primaryStage.setScene(gameScene);
			});

			// create game scene
			gameLog = new ListView();
			Label opPlayedLabel = new Label("Opponent played: ");
			Image opImage = new Image("idk.jpg");
			opImageView = new ImageView(opImage);
			opImageView.setFitHeight(70); 
      		opImageView.setFitWidth(70);  

			Image i0 = new Image("zero.png");
			ImageView iv0 = new ImageView(i0);
			iv0.setFitHeight(70); 
      		iv0.setFitWidth(70);
			Button b0 = new Button();
			b0.setGraphic(iv0); 

			Image i1 = new Image("one.png");
			ImageView iv1 = new ImageView(i1);
			iv1.setFitHeight(70); 
      		iv1.setFitWidth(70);
			Button b1 = new Button();
			b1.setGraphic(iv1); 

			Image i2 = new Image("two.png");
			ImageView iv2 = new ImageView(i2);
			iv2.setFitHeight(70); 
      		iv2.setFitWidth(70);
			Button b2 = new Button();
			b2.setGraphic(iv2);  

			Image i3 = new Image("three.png");
			ImageView iv3 = new ImageView(i3);
			iv3.setFitHeight(70); 
      		iv3.setFitWidth(70); 
			Button b3 = new Button();
			b3.setGraphic(iv3); 

			Image i4 = new Image("four.png");
			ImageView iv4 = new ImageView(i4);
			iv4.setFitHeight(70); 
      		iv4.setFitWidth(70);
			Button b4 = new Button();
			b4.setGraphic(iv4);  

			Image i5 = new Image("five.png");
			ImageView iv5 = new ImageView(i5);
			iv5.setFitHeight(70); 
      		iv5.setFitWidth(70);
			Button b5 = new Button();
			b5.setGraphic(iv5); 

			Label guessLabel = new Label("Enter Guess:");

			guessField.setPromptText("(0-10)");

			Label playernumberlabel = new Label("You are player: ");
			playernumberlabel2 = new Label(""); // THIS IS THE PLAYER NUMBER
			HBox playerbox = new HBox(playernumberlabel, playernumberlabel2);

			Label p1pointsLabel = new Label("Player 1 points: ");
			p1points = new Label("0");
			Label p2pointsLabel = new Label("Player 2 points: ");
			p2points = new Label("0");
			HBox scores1 = new HBox(p1pointsLabel, p1points);
			HBox scores2 = new HBox(p2pointsLabel, p2points);
			VBox scores12 = new VBox(scores1, scores2);

			HBox opHBOX = new HBox(opPlayedLabel, opImageView);
			VBox opWplayer = new VBox(playerbox, opHBOX, scores12);
			HBox opWListview = new HBox(gameLog, opWplayer);
			HBox plays_0_5 = new HBox(b0,b1,b2,b3,b4,b5);
			HBox guess = new HBox(guessLabel, guessField, sendData,nextRound, finishGame);

			VBox overall = new VBox(opWListview, plays_0_5, guess);
			gamePane.setCenter(overall);
			overall.setPadding(new Insets(30));

			b0.setOnAction(l -> {
				play=0;
				b1.setDisable(true);
				b2.setDisable(true);
				b3.setDisable(true);
				b4.setDisable(true);
				b5.setDisable(true);
			});
			b1.setOnAction(l -> {
				play=1;
				b0.setDisable(true);
				b2.setDisable(true);
				b3.setDisable(true);
				b4.setDisable(true);
				b5.setDisable(true);
			});
			b2.setOnAction(l -> {
				play=2;
				b1.setDisable(true);
				b0.setDisable(true);
				b3.setDisable(true);
				b4.setDisable(true);
				b5.setDisable(true);
			});
			b3.setOnAction(l -> {
				play=3;
				b1.setDisable(true);
				b2.setDisable(true);
				b0.setDisable(true);
				b4.setDisable(true);
				b5.setDisable(true);
			});
			b4.setOnAction(l -> {
				play=4;
				b1.setDisable(true);
				b2.setDisable(true);
				b3.setDisable(true);
				b0.setDisable(true);
				b5.setDisable(true);
			});
			b5.setOnAction(l -> {
				play=5;
				b1.setDisable(true);
				b2.setDisable(true);
				b3.setDisable(true);
				b4.setDisable(true);
				b0.setDisable(true);
			});

			sendData.setOnAction(l -> {
				if (!(guessField.getText() == "")) {
					gameLog.getItems().add("Waiting for opponent...");
					sendData.setDisable(true);
					guessField.setDisable(true);

					MorraInfo asdf = new MorraInfo(play, Integer.parseInt(guessField.getText()), PlayerNumber);
					clientConnection.send(asdf);

				}
			});

			nextRound.setOnAction(l -> {
				// enable all buttons
				sendData.setDisable(false);
				guessField.setDisable(false);
				b1.setDisable(false);
				b2.setDisable(false);
				b3.setDisable(false);
				b4.setDisable(false);
				b5.setDisable(false);
				b0.setDisable(false);

			});


			// create a third scene
			Label finalMessage = new Label("You won or lost");
			thirdPane.setCenter(finalMessage);
			finalMessage.setAlignment(Pos.CENTER);

			finishGame.setOnAction(l -> {
				if (p1points.getText().equals("2") || p2points.getText().equals("2")) {
					primaryStage.setScene(thirdScene);
				}
			});

		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
