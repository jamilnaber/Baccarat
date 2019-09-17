import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;


class DealerTest{

	//Referance of BccaratDealer.
	BaccaratDealer dealer; 


	//Institializing Bccarat dealer.
	@BeforeEach
	void init(){
		dealer = new BaccaratDealer();
	}

	//Test to validate constructor.
	@Test
	void testConstructor() {
		assertEquals("BaccaratDealer", dealer.getClass().getName(), "did not initialize proper Card object");
	}

	//Test to validate GenerateDeck method by checking all 52.
	@Test
	void testGernerateDeckAllCards(){
		
		dealer.generateDeck();
		int deckSize = dealer.deck.size();
		assertEquals(52, dealer.deck.size(), "Deck not generated");
		for( int i = 0; i < dealer.deck.size(); i++){
			assertEquals("Card", dealer.deck.get(i).getClass().getName(), "Deck not generated");
		}
	}

	//Test to validate GenerateDeck method by checking the 39th card generated.
	@Test
	void testGernerateDeckAceOfSpades(){
		dealer.generateDeck();
		assertEquals("1 of spades",  Integer.toString(dealer.deck.get(39).getCardValue()) + " of " +   dealer.deck.get(39).getCardSuit(), "Deck genration is wrong");
	}

	//Test to validate GenerateDeck method by checking if the Queen of clubs is generated.
	@Test
	void testGernerateDeckKingOfClubs(){
		dealer.generateDeck();
		assertEquals("0 of clubs",  Integer.toString(dealer.deck.get(12).getCardValue()) + " of " +   dealer.deck.get(12).getCardSuit(), "Deck genration is wrong");
	}

	//Test to validate a dealtHand method returns an ArrayList of type card object.
	@Test 
	void testDealHand(){
		ArrayList<Card> dealtHand = dealer.dealHand();
		assertEquals("Card", dealtHand.get(0).getClass().getName(), "Hand not dealt properly");
		assertEquals("Card", dealtHand.get(1).getClass().getName(), "Hand not dealt properly");
	}


	//Test to validate the dealtHand method changes dealt status of the dealt cards.
	@Test
	void testDealtHandIsDealt(){
		ArrayList<Card> dealtHand = dealer.dealHand();
		assertTrue(dealtHand.get(0).getIfDealt());
		assertTrue(dealtHand.get(1).getIfDealt());
	}

	//Test to validate the drawOne method returns a card object.
	@Test
	void testDrawOne(){
		assertEquals("Card", dealer.drawOne().getClass().getName(), "Card not dealt properly");
	}

	//Test to validate the drawOne method changes the dealt status of the dealt card.
	@Test 
	void testDrawOneIsDealt(){
		assertTrue(dealer.drawOne().getIfDealt());
	}

	//Test to validate the shuffleDeck method shuffles a card from its generated order. 
	@Test 
	void testShuffleDeck(){
		//Generating a deck in Ace to King and Club to Spade order.
		BaccaratDealer newDeck = new BaccaratDealer();
		newDeck.generateDeck();
		//Generating a shuffled deck.
		ArrayList<Card> unshuffledDeck = newDeck.deck;
		ArrayList<Card> shuffledDeck = dealer.deck;
		//Initializing the number of cards.
		int matches = 0;
		//Counts how many cards are in the same position as before shuffling
		for(int i = 0; i < 52; i++){
			if(shuffledDeck.get(i).getimageFileName() == unshuffledDeck.get(i).getimageFileName()){
				matches++;
			}  
		}
		//Asserts that the number of cards in the same position cannot be 52. 
		assertNotEquals(52, matches, "Deck is not shuffled!");
	}

	//Test to validate the shuffling a deck resets the card count to 52.
	@Test 
	void testShuffleDeck2(){
		dealer.dealHand();
		dealer.dealHand();
		dealer.dealHand();
		dealer.dealHand();
		dealer.shuffleDeck();
		assertEquals(52,  dealer.deckSize(), "Shuffle did not re size deck");
	}

	//Test to validate deckSize method returns 52 on a new hand.
	@Test
	void testDeckSize(){
		assertEquals(52,  dealer.deckSize(), "Deck size is wrong");
	}

	//Test to validate deckSize method decrements to 51 if a card is drawn.
	@Test
	void testDeckSizeDrawOne(){
		dealer.drawOne();
		assertEquals(51,  dealer.deckSize(), "Deck size is wrong");
	}

	//Test to validate deckSize method returns 48 after 56 cards are dealt & a new deck is generated.
	@Test
	void testDeckSizeDraw52(){
		for(int i = 0; i < 28; i++)
			dealer.dealHand();

		assertEquals(48,  dealer.deckSize(), "Deck size is wrong");
	}
}