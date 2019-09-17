import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

//Class to test the Card class.
class CardTest{

	//Referance of Card.
	Card theCard;

	//Initializing a card.
	@BeforeEach
	void init(){
		theCard = new Card("hearts", 10);
	}

	//Test to validate the Card constructor creates a Card object.
	@Test 
	void testConstructor(){
		assertEquals("Card", theCard.getClass().getName(), "did not initialize proper Card object");
	}

	//Test to validate the constructor creates a card with a heart suit.
	@Test 
	void testConstructorSuitInit(){
		assertEquals("hearts", theCard.getCardSuit(), "did not initialize card suit");
	}	

	//Test to validate the constructor creates a card with a value of 0.
	@Test 
	void testConstructorValueZeroInit(){
		assertEquals(0, theCard.getCardValue(), "did not initialize card's value");
	}	

	//Test to validate the card constructor creates a card with a value of 1.
	@Test 
	void testConstructorValueInit(){
		theCard = new Card("hearts", 1);
		assertEquals(1, theCard.getCardValue(), "did not initialize card's value");
	}	
}