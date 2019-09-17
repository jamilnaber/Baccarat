import java.util.*;
import java.util.Arrays;

public class BaccaratGameLogic{

	//Method that returns who won a hand based off the sums of each hand.
	public static String whoWon(ArrayList<Card> playerHand, ArrayList<Card> bankerHand){
		//Call to method handTotal to sum each player's and banker's hand.
		int playerSum = handTotal(playerHand);
		int bankerSum = handTotal(bankerHand);

		//If player's sum is greater than banker's sum, then player has won.
		if(playerSum > bankerSum)
			return "Player";
		//If banker's sum is greater than player's sume, then banker has won.
		else if(playerSum < bankerSum)
			return "Banker";
		//If banker and player has the same sum, then the game is a draw.
		else
			return "Draw";
	}

	//Checks if the hands compute to a natural
	public static String isNatural(ArrayList<Card> playerHand, ArrayList<Card> bankerHand ){

		//Call to method handTotal to sum the banker's hand and player's hand
		int playerSum = handTotal(playerHand);
		int bankerSum = handTotal(bankerHand);

		//Check if the player and banker hand size is 2.
		if(bankerHand.size() == 2 && playerHand.size() == 2){
			//If player sum and banker sum is 8, then it is a natural draw.
			if (playerSum == 8 && bankerSum == 8) {
				return "Draw";
			}
			//If player sum and banker sum is 9, then it is a natural draw.
			if (playerSum == 9 && bankerSum == 9){
				return "Draw";
			}
			//If player sum is 9, and banker sum is not 9, then player won.
			if(playerSum == 9){
				return "Player";
			}
			//If banker sum is 9, and player sum is not 9, then banker won.
			if (bankerSum == 9){
				return "Banker";
			}
			//If player sum is 8, and banker sum is less than 8, then player won. 
			if(playerSum == 8){
				return "Player";			
			}	
			//If banker sum is 8, and player sum is less than 8, then banker won.
			if(bankerSum == 8){
				return "Banker";
			}
		}
		//If none of the condtitions are met, then the hand is not a natural.
		return "Not natural";
	}

	//Method to sum a player's hand.
	public static int handTotal(ArrayList<Card> hand){
		//initialize hand total to 0.
		int handTotal = 0;
		//Iterate through the hand, and cound the card
		for(int i = 0; i < hand.size(); i++){
			handTotal = handTotal + hand.get(i).getCardValue();
		}
		//Hand total is modded by 10 and returned
		handTotal = handTotal%10;
		return handTotal;
	}	


	//Method to evaulate if the banker draws a third card.
	static boolean evaluateBankerDraw(ArrayList<Card> bankerHand, Card playerCard){

		//Logic table for if a third card is dealt. 
		boolean[][] thirdCardDrawTable = new boolean[][]{
			/* 0  	  1  	 2    	3  	   4  	  5  	 6  	7  	   8  	  9	*/
			{ true,  true,  true,  true,  true,  true,  true,  true,  true,  true }, //0	
			{ true,  true,  true,  true,  true,  true,  true,  true,  true,  true }, //1
			{ true,  true,  true,  true,  true,  true,  true,  true,  true,  true }, //2
			{ true,  true,  true,  true,  true,  true,  true,  true,  true,  true }, //3
			{ false, false, true,  true,  true,  true,  true,  true,  false, false}, //4
			{ false, false, false, false, true,  true,  true,  true,  false, false}, //5
			{ false, false, false, false, false, false, true,  true,  false, false}, //6
			{ false, false, false, false, false, false, false, false, false, false}, //7
			{ false, false, false, false, false, false, false, false, false, false}, //8
			{ false, false, false, false, false, false, false, false, false, false}, //9		
		};

		//Banker's current hand sum.
		int bankerHandTotal = handTotal(bankerHand);

		//If player's third card is null...
		if(playerCard == null){
			//if banker hand total is less than 6, then a third card is drawn.
			if(bankerHandTotal <= 5){
				return true;
			}
			//If the banker hand total is greater than 5, then a third card is not drawn.
			else{
				return false;
			}
		}
		
		//Check the logic table to determine if a third card is drawn.
		if(thirdCardDrawTable[bankerHandTotal][playerCard.getCardValue()]){
			return true;
		}
		//Return false if no conditions are met.
		return false;
	}

	//Method to evaluate if player draws a third card.
	static boolean evaluatePlayerDraw(ArrayList<Card> hand){
		
		//Sum the player's hand
		int handTotal = handTotal(hand);

		//If a 2 card size hand has a sum less than 6, then player is dealt a third card. 
		if(hand.size() == 2){
			if (handTotal >= 6)
				return false;
			return true;
		}
		//Return false if no conditions are met.
		return false;
	}
}