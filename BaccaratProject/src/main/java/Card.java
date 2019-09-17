//Class for an individual playing card.
public class Card{

	//Card class data members
	private String suit;
	private int value; 
	private Boolean dealt;
	private String imageFileName;

	//Card class constructor. 
	//String input of card suit ("clubs", "diamonds", "hearts", "spades")
	//Interver input of card face value(1 = ace, 2, 3, 4,...,10, 11 = jack, 12 = queen, 13 = king)
	public Card(String theSuit, int theValue ){
		
		//Sets card's suit and rank
		suit = theSuit;
		value = theValue;
		//Sets that card has not been dealt.
		dealt = false;
		//Sets string face value to empty.
		String faceValueString = "";
		//If card value is 1, then string is ace
		if(value == 1)
			faceValueString = "ace";
		//if card face value is 10, then value is 0.
		else if(value == 10){
			faceValueString = Integer.toString(value);
			value = 0;
		}

		//If card face value is 11, then string is jack, and card valuie is 0.
		else if(value == 11){
			faceValueString = "jack";
			value = 0;
		}
		//If card face value is 12, then string is queen, and card value is 0.
		else if(value == 12){
			faceValueString = "queen";
			value = 0;
		}
		//If card face value is 13, then string is kin, and card value is 0.
		else if(value == 13){
			faceValueString = "king";
			value = 0;
		}
		//Otherwise, a cards value is the same as its face value. 
		else
			faceValueString = Integer.toString(value);
		
		//Generates the the card's standard image file name.
		imageFileName = faceValueString + "_of_" + suit + ".png"; 
	}

	//Method to return the card's suit name.
	public String getCardSuit(){
		return suit;
	}

	//Method to return the card's value.
	public int getCardValue(){
		return value;
	}

	//Method to return if the card has been dealt.
	public Boolean getIfDealt(){
		return dealt;
	}

	//Method to set if card has been dealt or not.
	public void setIfDealt(Boolean isDealt){
		dealt = isDealt;
	}

	//Method to return the card's image file name.
	public String getimageFileName(){
		return imageFileName;
	}
}
