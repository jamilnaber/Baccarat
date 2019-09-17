import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class BaccaratGame extends Application {
	
	Text betConfirmTextBox, result;
	TextField bettingInput;
	Button b;
	PauseTransition pause = new PauseTransition(Duration.seconds(1));
	HashMap<String, Scene> sceneMap;
	GridPane grid;
	EventHandler<ActionEvent> dealButton, setBet, toGame, reset, exit;
	Timeline cardsDealt, player, playerBanker, banker, endRound;
	String userinput;
	private static DecimalFormat format = new DecimalFormat("#.##");
	
	int playerCurrentTotal = 0;
	int bankerCurrentTotal = 0;
	
	public static ArrayList<Card> playerHand;
	public static ArrayList<Card> bankerHand;
	public static BaccaratDealer theDealer;
	static double currentBet;
	static double totalWinnings;
	static String betOn___;

	
	public static void initialize(){

		currentBet = 0;
		totalWinnings = 0;
		playerHand = new ArrayList<Card>(3);
		bankerHand = new ArrayList<Card>(3);
		theDealer = new BaccaratDealer();	
		betOn___ = "";
	}

	//Method to handle whether a 3rd card should be dealt to the player and banker.
	static void thirdCardHandler(ArrayList<Card> playerHand1, ArrayList<Card> bankerHand1){
		//Set third card to null for both banker and player.
		Card playerThirdCard = null;
		Card bankerThirdCard = null;
		//First check if there is a natural winning/drawing hand.
		String nat = BaccaratGameLogic.isNatural(playerHand, bankerHand);
		//If there is a natural, then exit function.
		if(nat == "Player" || nat == "Banker")
		{
			return;
		}
		//Otherwise check if the player needs a third card. 
		if(BaccaratGameLogic.evaluatePlayerDraw(playerHand)){
			playerThirdCard = theDealer.drawOne();
			playerHand.add(playerThirdCard); //add third card if needed
		}
		//Check if the banker needs a third card which is dependent on the player's third card
		if(BaccaratGameLogic.evaluateBankerDraw(bankerHand1, playerThirdCard)){
			bankerThirdCard = theDealer.drawOne();
			bankerHand.add(bankerThirdCard); //Add a third card to the banker's hand.
		}
	}

	//Method to evaulate the betting outputs for the player.
	public double evaluateWinnings(){
		//Determine which hand is the winning hand.
		String winner = BaccaratGameLogic.whoWon(playerHand, bankerHand);
		//Check if the player's better is the winning bet
		if(betOn___ == winner){
			//If the player won betting on the player's hand, then return the winnings.
			if (winner == "Player"){
				totalWinnings = totalWinnings + currentBet;
				return currentBet;
			}
			//If the player won betting on the banker's hand, then return the winnings.
			if(winner == "Banker"){
				totalWinnings = totalWinnings + (.95) * currentBet;
				return currentBet*(.95);
			}
			//If the player won betting on a draw, then return the winnings
			if(winner == "Draw"){
				totalWinnings = totalWinnings - currentBet + 8.0 * currentBet;
				return  8.0 * currentBet; 
			}
		}
		//Otherwises, if player loses a bet, then return the losings. 
		totalWinnings = totalWinnings - currentBet;
		return (-1*currentBet);
	}
	
	public static void main(String[] args) {

		initialize();   //Initializes the variables that are in the backend of this program
		// TODO Auto-generated method stub
		launch(args);  //launch
	}//end of main
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		sceneMap = new HashMap<String,Scene>();  //create a scene map to store different scenes
		bettingInput = new TextField();          
		betConfirmTextBox = new Text();
		result = new Text();
		
		result.setStyle("-fx-font-size: 22;" + "-fx-fill: WHITE");
		betConfirmTextBox.setFill(Color.WHITE);
		
		timeLineArea(primaryStage);             //set all the TimeLines
		eventHandlerArea(primaryStage);         //set all the eventHandler
		
		sceneMap.put("homeScreen", createHomeScreen()); //create scene for the home screen
		
		primaryStage.setTitle("Lets Play Baccarat");      //set Title
		primaryStage.setScene(sceneMap.get("homeScreen"));
		primaryStage.sizeToScene();                       //set Scene to size
		primaryStage.setResizable(false);                 //Do not allow user to resize window
		primaryStage.show();                              //Present
	}//end of start
	
	//public void timeLineArea is a function that has the parameter of Stage
	//This function holds all of the setting code for any timeline used in this
	//program. 
	
	public void timeLineArea(Stage primaryStage) {
		//Timeline for when the round has ended
		endRound = new Timeline(
				//Frame will cause the message of who had won, reset betOn, and set a new scene
				new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						result.setText(BaccaratGameLogic.whoWon(playerHand, bankerHand) + " Has Won this Hand");
						betConfirmTextBox.setText("You Had Your Bet On " + b.getText());
						betOn___ = "";
						primaryStage.setScene(sceneMap.get("endRound"));
					}
					
				}
			)
		);
		//Timeline when there is only a third player card
		player = new Timeline(
				new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
					public void handle(ActionEvent t) {
						primaryStage.setScene(sceneMap.get("playerCard3"));
						endRound.play(); //end the round
					}
				}
		));
		//Timeline when there is a third player and banker card
		playerBanker = new Timeline(
				new KeyFrame(Duration.seconds(1), e->primaryStage.setScene(sceneMap.get("playerCard3"))),
				new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
					public void handle(ActionEvent t) {
						primaryStage.setScene(sceneMap.get("bankerCard3"));	
						endRound.play(); //end the round
					}
				}
		));
		//Timeline when there is only a third banker card
		banker = new Timeline(
				new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
					public void handle(ActionEvent t) {
						primaryStage.setScene(sceneMap.get("bankerCard3"));
						endRound.play(); //end the round
					}
				}
		));
		//TimeLine that will deal the first four cards at 1 second at a time, and will determine what cards will
		//proceed
		cardsDealt = new Timeline(
				//
				new KeyFrame(Duration.ZERO, e-> primaryStage.setScene(sceneMap.get("gameScene"))),
				new KeyFrame(Duration.seconds(1), e -> primaryStage.setScene(sceneMap.get("playerCard1"))),
				new KeyFrame(Duration.seconds(2), e -> primaryStage.setScene(sceneMap.get("bankerCard1"))),
				new KeyFrame(Duration.seconds(3), e -> primaryStage.setScene(sceneMap.get("playerCard2"))),
				new KeyFrame(Duration.seconds(4), new EventHandler<ActionEvent>() {
					public void handle(ActionEvent t) {
						primaryStage.setScene(sceneMap.get("bankerCard2"));	
						if(2 < playerHand.size() ) {                              //if the player has a third card
							if(2 < bankerHand.size()) {                           //also the banker
								playerBanker.play();
							}
							else{                                                 //banker does not
								player.play();
							}
						}
						else if(2 < bankerHand.size() ) {                         //only the banker has a third card
							banker.play();
						}
						else{
							endRound.play(); //end the round
						}
					}
				}
			)
		);
	}//end of timeLineArea()
	
	//public void eventHandlerArea is a function that has the parameter of Stage
	//This function holds all of the setting code for any eventHandlers used in this
	//program. 
	
	public void eventHandlerArea(Stage primaryStage) {
		
		//This event will deal the cards
		dealButton = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				result.setText("");                                               //reset result
				if(betOn___ == "") {                                              //check of there was a bet
					betConfirmTextBox.setText("Please Select A Betting Opition");
					return;
				}
				playerCurrentTotal = 0;                                           //reset the totals
				bankerCurrentTotal = 0;
				playerHand = theDealer.dealHand();                                //get the new hands
				bankerHand = theDealer.dealHand();
				thirdCardHandler(playerHand, bankerHand);                         //check/deal if a third card is needed for either
				sceneMapSetup();                                                  //setup the scenes
				cardsDealt.play();                                                //Play the timeline to display the cards
				evaluateWinnings();                                               //check/give who won
				sceneMap.put("endRound", endRound());                             //set the final scene
			}
		};
		//This event will get the betting amount and whom to bet upon from the user
		setBet = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				userinput = bettingInput.getText();                                                //grab data from the textfield
				b = (Button)event.getSource();                                                     //get the button name
				if (userinput != null && userinput.matches("[0-9]+") && userinput.length() > 0 && Integer.parseInt(userinput) != 0) {  //check if it is a number
					currentBet = Integer.parseInt(userinput);                                      //set current bet with userinput
					betOn___ = b.getText();                                                        //set betOn
					betConfirmTextBox.setText("Bet Placed On " + b.getText() + ".  Good Luck!");   //print confrimation
				}
				else {
					betConfirmTextBox.setText("Enter A Number, Then Select Who");                  //if any error
					return;
				}
			}
		};
		//This event will take you from the Homescreen to the gameScreen
		toGame = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				sceneMap.put("gameScene", createCardScene(0,false,false));
				primaryStage.setScene(sceneMap.get("gameScene"));
			}
		};
		//This event will reset the game 
		reset = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event) {
				cardsDealt.stop();                                         //if dealing cards, stop
				result.setText("");                                        //reset all text box's, variables and fields
				userinput = "";
				totalWinnings = 0;
				betOn___ = "";
				betConfirmTextBox.setText("");
				playerCurrentTotal = 0;
				bankerCurrentTotal = 0;
				bettingInput.clear();
				sceneMap.put("gameScene", createCardScene(0,false,false));//set stock game screen
				primaryStage.setScene(sceneMap.get("gameScene"));         //go to stock game screen
			}
		};
		//This event will exit the game
		exit = new EventHandler<ActionEvent>(){
			public void handle(ActionEvent event){
				primaryStage.close();
			}
		};
		
	}//end of eventHandlerArea()
	
	//endRound function job is to create the final screen with everything updated
	protected Scene endRound() {
		if(sceneMap.get("bankerCard3") != null)
			return createCardScene(4,true,true);
		else if(sceneMap.get("playerCard3") != null)
			return createCardScene(4,true,false);
		else
			return createCardScene(4,false,false);
	}//end of endRound()
/*------------------------------------------------------------*/
	// public createHomeScreen does not have any parameters, but has a return type of Scene. This function will
	// create the scene for the home screen
	private Scene createHomeScreen() {
		Button btnToGame = new Button();    //button to the game screen
		BorderPane home = new BorderPane(); //borderPane for layout
		btnToGame.setPrefSize(416, 50);     //Button size to fit
		btnToGame.setOnAction(toGame);      //set its action 
		btnToGame.setStyle("-fx-background-image: url(\"/0.png\");" + "-fx-focus-color: transparent;" + "-fx-border-color: transparent" );
		HBox hbox = new HBox(btnToGame);    //insert the button into an hbox
		hbox.setPadding(new Insets(30,0,30,25));
		home.setStyle("-fx-background-image: url(\"/homeScreen.png\");-fx-background-size: 960, 600;-fx-background-repeat: no-repeat;");
		home.setPrefSize(960, 540);
		home.setBottom(hbox);               //set the bottom of the borderpane to the hbox
		Scene scene = new Scene(home);      //create a scene
		return scene;                       //return the scene
	}//end of createHomeScreen()
	
	
	// public void sceneMapSetup take in no parameters. This function will set up all the scenes throughout the game
	// after the action of deal cards is called. All of the scenes are inserted to the sceneMap
	private void sceneMapSetup() {
		sceneMap.put("playerCard3", null); //reset 3rd cards, personal check
		sceneMap.put("bankerCard3", null);
		
		sceneMap.put("gameScene", createCardScene(0,false,false));                     //player card 1
		playerCurrentTotal = playerHand.get(0).getCardValue() + playerCurrentTotal;
		sceneMap.put("playerCard1", createCardScene(1,false,false));				   //banker card 1
		bankerCurrentTotal = bankerHand.get(0).getCardValue() + bankerCurrentTotal;
		sceneMap.put("bankerCard1", createCardScene(2,false,false));			       //player card 2
		playerCurrentTotal = playerHand.get(1).getCardValue() + playerCurrentTotal;
		sceneMap.put("playerCard2", createCardScene(3,false,false));				   //banker card 2
		bankerCurrentTotal = bankerHand.get(1).getCardValue() + bankerCurrentTotal;
		sceneMap.put("bankerCard2", createCardScene(4,false,false));
		if(2 < playerHand.size() ) {
			playerCurrentTotal = playerHand.get(2).getCardValue() + playerCurrentTotal; 
			sceneMap.put("playerCard3", createCardScene(4,true,false));                 //player card 3
		}
		if(2 < bankerHand.size()) {
			bankerCurrentTotal = bankerHand.get(2).getCardValue() + bankerCurrentTotal; 
			sceneMap.put("bankerCard3", createCardScene(4,true,true));                  //banker card 3
		}
	}//end of sceneMapSetup()

	
	// public createCardScene takes in 3 different parameters of int, and two booleans. The int will tell how far to 
	//build the first four card, and the booleans will determine the third card of both
	public Scene createCardScene(int firstFour, boolean playerThird, boolean bankerThird) {
		BorderPane border = stockLayout();                               //create a stock layout
		GridPane GridBox = gridAdder(firstFour,playerThird,bankerThird); //create a girdPane, but pass the variables
		border.setCenter(GridBox);                                       //set GridBox in the center
		Scene scene = new Scene(border);								 //new scene
		return scene;                                                    //return scene
	}//end of createCardScene()
	
	
	//gridAdder is a private function that takes in three parameters, one in and two boolean. The int will tell how far to 
	//build the first four card, and the booleans will determine the third card of both
	private GridPane gridAdder(int four, boolean playerThird, boolean bankerThird) {
		GridPane GridBox = createGridBox();
		
		if(four > 0) {
			ImageView PlayerCard1 =  imageSet(playerHand.get(0).getimageFileName()); //create player card 1 picture and set it to the grid
			GridBox.add(PlayerCard1, 0, 0, 1, 1);
		}
		if(four > 1) {
			ImageView BankerCard1 =  imageSet(bankerHand.get(0).getimageFileName()); //create banker card 1 picture and set it to the grid
			GridBox.add(BankerCard1, 0, 1, 1, 1);
		}
		if(four > 2) {
			ImageView PlayerCard2 =  imageSet(playerHand.get(1).getimageFileName()); //create player card 2 picture and set it to the grid
			GridBox.add(PlayerCard2, 1, 0, 1, 1);
		}
		if(four > 3) {
			ImageView BankerCard2 =  imageSet(bankerHand.get(1).getimageFileName()); //create banker card 2 picture and set it to the grid
			GridBox.add(BankerCard2, 1, 1, 1, 1);
		}
		if(playerThird == true && 2 < playerHand.size()) {
			ImageView PlayerCard3 =  imageSet(playerHand.get(2).getimageFileName()); //create player card 3 picture and set it to the grid
			GridBox.add(PlayerCard3, 2, 0, 1, 1);
		}
		if(bankerThird == true && 2 < bankerHand.size()) {
			ImageView BankerCard3 =  imageSet(bankerHand.get(2).getimageFileName()); //create banker card 3 picture and set it to the grid
			GridBox.add(BankerCard3, 2, 1, 1, 1);
		}
		GridBox.add(result,0,2,30,10);                                               //set result text
		return GridBox; //return GridBox
	}//end of gridAdder()
	
	
	//imageSet is a public function that will take in a a string with a file name and will return it of type
	//ImageView.
	private ImageView imageSet(String file) {
		ImageView card = new ImageView(new Image(file)); //create ImageView of picture
		card.setFitWidth(115);                           //set width and height
		card.setFitHeight(176);
		return card;                                     //return the card
	}//end of imageSet()
	
	//stockLayout is a public function that will return a borderPane. This function will create a template of the GUI
	//that will be used throughout the game
/*------------------------------------------------------------*/
	private BorderPane stockLayout() {
		BorderPane border = createBaseBorderPane(); //create the borderpane
	
		VBox topVbox = createVBoxtop();             //create the topVbox 
		HBox bottomHbox = createHboxBottom();       //create the bottomHbox 
		VBox leftVerticalBox = createVBoxLeft();    //create the leftVerticalBox 
		VBox rightVerticalBox = createVboxRight();  //create the rightVerticalBox 
		
		border.setTop(topVbox);           //set top with topVbox
	    border.setBottom(bottomHbox);     //set bottom with bottomHbox
		border.setLeft(leftVerticalBox);  //set left with leftVerticalBox
		border.setRight(rightVerticalBox);//set right with rightVerticalBox
	    
		return border;//return border (template)
	}//end of stockLayout()
	
	//createBaseBorderPane is a public function that will create/return a generic game screen BorderPane

	private BorderPane createBaseBorderPane() {
		BorderPane border = new BorderPane(); //create a new BorderPane
		border.setStyle("-fx-background-image: url(\"/green_felt.jpg\");-fx-background-size: 960, 600;-fx-background-repeat: no-repeat;");
		border.setPrefSize(960, 600);         //set it's size
		return border;//return it
	}//end of createBaseBorderPane()
	
	//createVBoxtop is a private function that will create/return a generic game screen VBox for the top
	
	private VBox createVBoxtop() {  
		VBox top = new VBox(); //create a new VBox
		top.getChildren().addAll(MenuCreate(),playerWinnings()); //call MenuCreate and playerWinnings and add them to top
		return top;  //return VBox
	}//end of createVBoxtop()
	
	//MenuCreate is a private function that will create/return a generic Hbox to hold the menu bar
	
	private HBox MenuCreate() {
		HBox hbox = new HBox();                     //create a new Hbox
		MenuBar menuBar = new MenuBar();            //create a menu bar
		Menu menuOne = new Menu("Options");         //create a menu
		MenuItem one = new MenuItem("Fresh Start"); //create a menu item's
		MenuItem two = new MenuItem("Exit");
		one.setOnAction(reset);                     //set there actions
		two.setOnAction(exit);
		menuOne.getItems().addAll(one,two);         //add menu
		menuBar.getMenus().addAll(menuOne);
		hbox.getChildren().addAll(menuBar);
		return hbox; //return HBox
	}//end of MenuCreate()
	
	//playerWinnings is a private function that will create/return a generic Hbox to hold the players winning textbox
	
	private HBox playerWinnings() {
		HBox hbox = new HBox();        //create a new HBox
		Text playerCount = new Text(); //create a new textbox
		playerCount.setStyle("-fx-font-size: 22;" + "-fx-fill: AZURE");
		playerCount.setText("Players Current Winnings: $" + format.format(totalWinnings));
		hbox.getChildren().add(playerCount);    //add the textbox to the HBox
		hbox.setPadding(new Insets(0,0,20,0));  //add some padding
		hbox.setAlignment(Pos.CENTER);			//center it
		return hbox;							//return the HBox
	}//end of playerWinnings()
	
	//createHboxBottom is a private function that will create/return a generic HBox for the bottom
	
	private HBox createHboxBottom() {
		HBox hbox = new HBox();            						//create a new Hbox
		hbox.setPadding(new Insets(0, 0, 0, 300));				//set the padding
	    Button playerBet = buttomBettingButtonsGen("Player");	//create the betting buttons by calling a generic function 
	    Button bankerBet = buttomBettingButtonsGen("Banker");
	    Button tieBet = buttomBettingButtonsGen("Draw");
	    bettingInput.setPrefSize(75, 20);                       //set size of the Textfeild
		hbox.setSpacing(10);                                    //set spacing
	    hbox.getChildren().addAll(playerBet, bankerBet, tieBet, bettingInput, betConfirmTextBox); //add everything in order to the hbox
		return hbox;											//return HBox
	}//end of createHboxBottom()
	
	//buttomBettingButtonsGen is a private function that will create/return a generic Button used for betting
	
	private Button buttomBettingButtonsGen(String label) {
		Button betButton = new Button();  //create a new button
		betButton.setPrefSize(75, 20);    //set size
		betButton.setOnAction(setBet);    //set its action
		betButton.setText(label);         //set its label
		betButton.setStyle("-fx-font-weight: bold;");
		return betButton;                 //return the button
	}//end of buttomBettingButtonsGen()
	
	//createVboxRight is a private function that will create/return a generic game screen VBox for the right
	
	private VBox createVboxRight() {
		Button gameButton1 = new Button();		//create a new button
		gameButton1.setPrefSize(90, 90);        //set its size
		gameButton1.setOnAction(dealButton);    //set its action
		gameButton1.setText("Deal Cards");      //set its text
		gameButton1.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 14;");
		VBox vbox = new VBox();                 //create a new VBox
		vbox.setAlignment(Pos.CENTER);			//center it
		vbox.getChildren().add(gameButton1);	//add the button
		vbox.setPadding(new Insets(0,0,0,0));   //make sure padding is 0
		return vbox;							//return vbox
	}//end of createVboxRight()
	
	//createVBoxLeft is a private function that will create/return a generic game screen VBox for the right
	
	private VBox createVBoxLeft() {
		playerCurrentTotal = playerCurrentTotal % 10;			//make sure the score gets reset above 10
		bankerCurrentTotal = bankerCurrentTotal % 10;
		Text playerCardCountTextBox = new Text("Player: " + Integer.toString(playerCurrentTotal));
		Text bankerCardCountTextBox = new Text("Banker: " + Integer.toString(bankerCurrentTotal));
		playerCardCountTextBox.setStyle("-fx-font-size: 22;" + "-fx-fill: WHITE");
		bankerCardCountTextBox.setStyle("-fx-font-size: 22;" + "-fx-fill: WHITE");
		VBox vbox = new VBox();									//create a VBox
		vbox.setAlignment(Pos.TOP_CENTER);						//Align it
		vbox.setSpacing(120);									//set its spacing
		vbox.setPadding(new Insets(80, 20, 0, 10));				//set the padding
		vbox.getChildren().addAll(playerCardCountTextBox,bankerCardCountTextBox); //add the node objects in order
		return vbox;											//return the VBox
	}//end of createVBoxLeft()
	
	//createGridBox is a private function that will create/return a generic game screen GridBox for the center

	private GridPane createGridBox() {
		GridPane GridBox = new GridPane();            //create a gridBox
		GridBox.setPadding(new Insets(0, 0, 0, 180)); //set its padding
		return GridBox;								  //return gridBox
	}//end of createGridBox()
}