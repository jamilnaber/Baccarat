import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;

//Class to test the BaccaratGame class.
class GameTest{

	//Referance of BccaratDealer.
	BaccaratGame game;

	//Institializing BccaratGame class	
	@BeforeEach
	void init(){
		game = new BaccaratGame();
		game.totalWinnings = 10;
		game.currentBet = 2;
	}

	//Test to validate evaluateWinnings method when the 
	//player loses when betting on player's hand.
	void testEvaluateWinningsPlayerPlayerLoses(){
		game.betOn___ = "Player";
		game.bankerHand = new ArrayList<Card>(3);
		game.playerHand = new ArrayList<Card>(3);

		game.playerHand.add(new Card("clubs", 10));
		game.playerHand.add(new Card("clubs", 11));
		game.playerHand.add(new Card("clubs", 12));
		
		game.bankerHand.add(new Card("hearts", 4));
		game.bankerHand.add(new Card("hearts", 11));
		game.bankerHand.add(new Card("hearts", 13));

		assertEquals(-2, game.evaluateWinnings(), "Winnings are wrong");
	}


	//Test to validate evaluateWinnings method when the 
	//player wins when betting on player's hand.
	@Test 
	void testEvaluateWinningsPlayerPlayerWins(){
		game.betOn___ = "Player";
		game.bankerHand = new ArrayList<Card>(3);
		game.playerHand = new ArrayList<Card>(3);

		game.playerHand.add(new Card("clubs", 5));
		game.playerHand.add(new Card("clubs", 11));
		game.playerHand.add(new Card("clubs", 12));
		
		game.bankerHand.add(new Card("hearts", 4));
		game.bankerHand.add(new Card("hearts", 11));
		game.bankerHand.add(new Card("hearts", 13));

		assertEquals(2, game.evaluateWinnings(), "Winnings are wrong");
	}

	//Test to validate evaluateWinnings method when the 
	//player wins when betting on banker's hand.
	@Test
	void testEvaluateWinningsPlayerBankerWins(){
		game.betOn___ = "Banker";
		game.bankerHand = new ArrayList<Card>(3);
		game.playerHand = new ArrayList<Card>(3);

		game.playerHand.add(new Card("clubs", 10));
		game.playerHand.add(new Card("clubs", 11));
		game.playerHand.add(new Card("clubs", 12));
		
		game.bankerHand.add(new Card("hearts", 4));
		game.bankerHand.add(new Card("hearts", 11));
		game.bankerHand.add(new Card("hearts", 13));

		assertEquals(1.9, game.evaluateWinnings(), "Winnings are wrong");
	}

	//Test to validate evaluateWinnings method when the 
	//player loses when betting on banker's hand.
	@Test 
	void testEvaluateWinningsPlayerBankerLoses(){
		game.betOn___ = "Banker";
		game.bankerHand = new ArrayList<Card>(2);
		game.playerHand = new ArrayList<Card>(3);

		game.playerHand.add(new Card("clubs", 8));
		game.playerHand.add(new Card("clubs", 1));

		
		game.bankerHand.add(new Card("hearts", 4));
		game.bankerHand.add(new Card("hearts", 11));
		game.bankerHand.add(new Card("hearts", 13));

		assertEquals(-2, game.evaluateWinnings(), "Winnings are wrong");
	}

	//Test to validate evaluateWinnings method when the 
	//player wins when betting on a draw.
	@Test
	void testEvaluateWinningsPlayerTieWins(){
		game.betOn___ = "Draw";
		game.bankerHand = new ArrayList<Card>(3);
		game.playerHand = new ArrayList<Card>(3);

		game.playerHand.add(new Card("clubs", 8));
		game.playerHand.add(new Card("clubs", 1));
		game.playerHand.add(new Card("clubs", 12));
		
		game.bankerHand.add(new Card("hearts", 4));
		game.bankerHand.add(new Card("hearts", 5));
		game.bankerHand.add(new Card("hearts", 13));

		assertEquals(16, game.evaluateWinnings(), "Winnings are wrong");
	}

	//Test to validate evaluateWinnings method when the 
	//player loses when betting on a draw
	@Test
	void testEvaluateWinningsPlayerTieLoses(){
		game.betOn___ = "Draw";
		game.bankerHand = new ArrayList<Card>(3);
		game.playerHand = new ArrayList<Card>(3);

		game.playerHand.add(new Card("clubs", 8));
		game.playerHand.add(new Card("clubs", 2));
		game.playerHand.add(new Card("clubs", 12));
		
		game.bankerHand.add(new Card("hearts", 4));
		game.bankerHand.add(new Card("hearts", 5));
		game.bankerHand.add(new Card("hearts", 13));

		assertEquals(-2, game.evaluateWinnings(), "Winnings are wrong");
	}
}