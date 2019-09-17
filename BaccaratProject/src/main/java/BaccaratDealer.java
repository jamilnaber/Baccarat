import java.util.*; // Java package that includes ArrayList

public class BaccaratDealer{
	//ArrayList deck of cards.
	ArrayList<Card> deck;
	//Array of suit names and card ranks.
	private String[] cardSuit = {"clubs","diamonds","hearts","spades"};
	private Integer[] cardRank = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
	//Current size of the deck. Number of viable cards that can be dealt.
	private static Integer deckCounter;
	
	//Dealer constructor. Creates and shuffles a deck. 
	BaccaratDealer(){
		deck = new ArrayList<Card>(52); 
		generateDeck();
		shuffleDeck();
		deckCounter = 52; 	
	}

	//Method that generates a 52 card deck with 13 cards per suit with 4 suits.
	public void generateDeck(){
		//Clears any previous deck list.
		deck.clear();
		//Card reference.
		Card newCard;
		//Creates 13 cards per 4 suits and adds them to the deck arraly list.
		for(int i = 0; i < cardSuit.length; i++){
			for(int j = 0; j < cardRank.length; j++){
				newCard = new Card(cardSuit[i], cardRank[j]);
				deck.add(newCard);
			}
		}
	}

	//Method shuffles the array list of 52 cards.
	public void shuffleDeck(){
		//Java library shuffle function. Linear time complexity. 
		Collections.shuffle(deck); 
		//Sets each card as available to be dealt.
		for(int i = 0; i < deck.size(); i++){
			deck.get(i).setIfDealt(false);
		}
		//Resets deck counter to 52.
		deckCounter = 52;
	}


	//Method that returns the current size of the deck.
	public int deckSize(){
		//deckCounter is decremented every time a card is dealt.
		//deckCounter is also reset to 52 everytime the deck is shuffled.
		return deckCounter;
	}

	//Method that returns a single card. 
	public Card drawOne(){

		//Checks if enough cards are in the deck to draw.
		if(deckCounter < 1){
			//If there are no cards left to draw, then shuffle deck.
			shuffleDeck();
		}

		//get the ith Card in teh deck. 
		//ith Card is the next card to be dealt.
		int ithCard = 52 - deckCounter; 
		//Save the ith card.
		Card drawnCard = deck.get(ithCard);
		//Set card as dealt.
		drawnCard.setIfDealt(true);
		//Decrement the current card count.
		deckCounter--;
		//Return the drawn card.
		return drawnCard;
	}	

	public  ArrayList<Card> dealHand(){
		//Initialize Array List for a hand of cards.
		ArrayList<Card> dealtHand = new ArrayList<Card>(2);
		//deal two cards and add to array list of cards.
		dealtHand.add(drawOne());
		dealtHand.add(drawOne());
		//return the array list of cards.
		return dealtHand;
	}
}