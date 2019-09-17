import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;

//Class to test the BaccaratGameLogic class
class GameLogicTest{
	
	//Test to validate handTotal method when sum is 9.
	@Test
	void testHandTotal(){
		ArrayList<Card> hand = new ArrayList<Card>(3);
		hand.add(new Card("spades", 1));
		hand.add(new Card("spades", 5));
		hand.add(new Card("spades", 3));

		assertEquals(9, BaccaratGameLogic.handTotal(hand), "did not initialize proper Card object");
	}

	//Test to validate handTotal method when sum is modded.
	@Test
	void testHandTotal2(){
		ArrayList<Card> hand = new ArrayList<Card>(3);
		hand.add(new Card("spades", 5));
		hand.add(new Card("spades", 9));
		hand.add(new Card("spades", 8));

		assertEquals(2, BaccaratGameLogic.handTotal(hand), "did not initialize proper Card object");
	}

	//Test to validate handTotal method when sum is 0.
	@Test
	void testHandTotal3(){
		ArrayList<Card> hand = new ArrayList<Card>(3);
		hand.add(new Card("hearts", 13));
		hand.add(new Card("clubs", 13));
		hand.add(new Card("spades", 13));

		assertEquals(0, BaccaratGameLogic.handTotal(hand), "did not initialize proper Card object");
	}

	//Test to validate whoWon method when the player's hand wins with a 3 card vs 3 card game.
	@Test
	void testWhoWonHand1(){
		ArrayList<Card> bankerHand = new ArrayList<Card>(3);
		ArrayList<Card> playerHand = new ArrayList<Card>(3);

		playerHand.add(new Card("clubs", 1));
		playerHand.add(new Card("clubs", 3));
		playerHand.add(new Card("clubs", 1));//5

		bankerHand.add(new Card("hearts", 9));
		bankerHand.add(new Card("hearts", 8));
		bankerHand.add(new Card("hearts", 7));//4
	
		assertEquals("Player", BaccaratGameLogic.whoWon(playerHand, bankerHand), "did not initialize proper Card object");
	}

	//Test to validate whoWon method when the player's hand wins with a 2 card vs 2 card game.
	@Test
	void testWhoWonHandNatural(){
		ArrayList<Card> bankerHand = new ArrayList<Card>(2);
		ArrayList<Card> playerHand = new ArrayList<Card>(2);

		playerHand.add(new Card("clubs", 7));
		playerHand.add(new Card("clubs", 2));

		bankerHand.add(new Card("hearts", 2));
		bankerHand.add(new Card("hearts", 6));
		
		assertEquals("Player", BaccaratGameLogic.whoWon(playerHand, bankerHand), "did not initialize proper Card object");
	}

	//Test to validate whoWon method when the banker's hand wins with a 2 card vs 3 card game.
	@Test
	void testWhoWonBankerWin(){
		ArrayList<Card> bankerHand = new ArrayList<Card>(3);
		ArrayList<Card> playerHand = new ArrayList<Card>(2);

		playerHand.add(new Card("clubs", 5));
		playerHand.add(new Card("clubs", 2));
		
		bankerHand.add(new Card("hearts", 8));
		bankerHand.add(new Card("hearts", 9));
		bankerHand.add(new Card("hearts", 1));
		
		assertEquals("Banker", BaccaratGameLogic.whoWon(playerHand, bankerHand), "did not initialize proper Card object");
	}

	//Test to validate whoWon method when there is a draw.
	@Test
	void testWhoWonDraw(){
		ArrayList<Card> bankerHand = new ArrayList<Card>(3);
		ArrayList<Card> playerHand = new ArrayList<Card>(3);

		playerHand.add(new Card("clubs", 10));
		playerHand.add(new Card("clubs", 11));
		playerHand.add(new Card("clubs", 12));
		
		bankerHand.add(new Card("hearts", 10));
		bankerHand.add(new Card("hearts", 11));
		bankerHand.add(new Card("hearts", 13));

		assertEquals("Draw", BaccaratGameLogic.whoWon(playerHand, bankerHand), "did not initialize proper Card object");
	}

	//Test to validate evaluateBankerDraw when banker's hand sum is 6 and players third card is 7.
	@Test
	void testEvaluateBankerDraw1(){
		ArrayList<Card> bankerHand =  new ArrayList<Card>(2);
		bankerHand.add(new Card("hearts", 3));
		bankerHand.add(new Card("spades", 3));
		Card playerCard = new Card("hearts", 7);

		assertTrue(BaccaratGameLogic.evaluateBankerDraw( bankerHand, playerCard));
	}

	//Test to validate evaluateBankerDraw when banker's hand sum is 6 and players third card is 8.
	@Test
	void testEvaluateBankerDraw2(){
		ArrayList<Card> bankerHand =  new ArrayList<Card>(2);
		bankerHand.add(new Card("hearts", 3));
		bankerHand.add(new Card("spades", 3));
		Card playerCard = new Card("hearts", 8);

		assertFalse(BaccaratGameLogic.evaluateBankerDraw( bankerHand, playerCard));
	}

	//Test to validate evaluateBankerDraw when banker's hand sum is 7 and players third card is 7.
	@Test
	void testEvaluateBankerDraw3(){
		ArrayList<Card> bankerHand =  new ArrayList<Card>(2);
		bankerHand.add(new Card("hearts", 3));
		bankerHand.add(new Card("spades", 4));
		Card playerCard = new Card("hearts", 7);

		assertFalse(BaccaratGameLogic.evaluateBankerDraw( bankerHand, playerCard));
	}

	//Test to validate evaluateBankerDraw when banker's hand sum is 5 and players third card is not drawn.
	@Test
	void testEvaluateBankerDraw4(){
		ArrayList<Card> bankerHand =  new ArrayList<Card>(2);
		bankerHand.add(new Card("hearts", 3));
		bankerHand.add(new Card("spades", 2));
		Card playerCard = null;

		assertTrue(BaccaratGameLogic.evaluateBankerDraw( bankerHand, playerCard));
	}

	//Test to validate evaluateBankerDraw when banker's hand sum is 6 and players third card is not drawn.
	@Test
	void testEvaluateBankerDraw5(){
		ArrayList<Card> bankerHand =  new ArrayList<Card>(2);
		bankerHand.add(new Card("hearts", 4));
		bankerHand.add(new Card("spades", 2));
		Card playerCard = null;

		assertFalse(BaccaratGameLogic.evaluateBankerDraw( bankerHand, playerCard));
	}

	//Test to validate evaluatePlayerDraw when player's two card hand sums to 5.
	@Test
	void testEvaluatePlayerDraw1(){
		ArrayList<Card> playerHand =  new ArrayList<Card>(2);
		playerHand.add(new Card("clubs", 12));
		playerHand.add(new Card("clubs", 3));

		assertTrue(BaccaratGameLogic.evaluatePlayerDraw(playerHand));
	}

	//Test to validate evaluatePlayerDraw when player's two card hand sums to 8.
	@Test
	void testEvaluatePlayerDraw2(){
		ArrayList<Card> playerHand =  new ArrayList<Card>(2);
		playerHand.add(new Card("clubs", 12));
		playerHand.add(new Card("clubs", 6));

		assertFalse(BaccaratGameLogic.evaluatePlayerDraw(playerHand));
	}

	//Test to validate evaluatePlayerDraw when player's two card hand sums to 3.
	@Test
	void testEvaluatePlayerDraw3(){
		ArrayList<Card> playerHand =  new ArrayList<Card>(2);
		playerHand.add(new Card("clubs", 12));
		playerHand.add(new Card("clubs", 13));

		assertTrue(BaccaratGameLogic.evaluatePlayerDraw(playerHand));
	}

	//Test to validate isNatural method when players sum is 4 and banker's sum is 7.
	@Test
	void testIsNatural1(){
		ArrayList<Card> bankerHand = new ArrayList<Card>(2);
		ArrayList<Card> playerHand = new ArrayList<Card>(2);

		playerHand.add(new Card("clubs", 1));
		playerHand.add(new Card("clubs", 3));
		
		bankerHand.add(new Card("hearts", 9));
		bankerHand.add(new Card("hearts", 8));
	
		assertEquals("Not natural", BaccaratGameLogic.isNatural(playerHand, bankerHand), "did not initialize proper Card object");
	}

	//Test to validate isNatural method when players sum is 9 and banker's sum is 7.
	@Test
	void testIsNatural2(){
		ArrayList<Card> bankerHand = new ArrayList<Card>(2);
		ArrayList<Card> playerHand = new ArrayList<Card>(2);

		playerHand.add(new Card("clubs", 6));
		playerHand.add(new Card("clubs", 3));

		bankerHand.add(new Card("hearts", 9));
		bankerHand.add(new Card("hearts", 8));
	
		assertEquals("Player", BaccaratGameLogic.isNatural(playerHand, bankerHand), "did not initialize proper Card object");
	}

	//Test to validate isNatural method when players sum is 9 and banker's sum is 9.
	@Test
	void testIsNatural3(){
		ArrayList<Card> bankerHand = new ArrayList<Card>(2);
		ArrayList<Card> playerHand = new ArrayList<Card>(2);

		playerHand.add(new Card("clubs", 6));
		playerHand.add(new Card("clubs", 3));

		bankerHand.add(new Card("hearts", 8));
		bankerHand.add(new Card("hearts", 1));
	
		assertEquals("Draw", BaccaratGameLogic.isNatural(playerHand, bankerHand), "did not initialize proper Card object");
	}

	//Test to validate isNatural method when players sum is 8 and banker's sum is 9.
	@Test
	void testIsNatural4(){
		ArrayList<Card> bankerHand = new ArrayList<Card>(2);
		ArrayList<Card> playerHand = new ArrayList<Card>(2);

		playerHand.add(new Card("clubs", 6));
		playerHand.add(new Card("clubs", 2));
		
		bankerHand.add(new Card("hearts", 8));
		bankerHand.add(new Card("hearts", 1));
		
		assertEquals("Banker", BaccaratGameLogic.isNatural(playerHand, bankerHand), "did not initialize proper Card object");
	}
}