package carddeck;
import org.junit.Before;
import org.junit.Test;


import java.util.Random;

import static org.junit.Assert.*;


public class Tests {
    Deck deck;
    Card aceOfSpades;
    Random rand;

    @Before
    public void setup() {
        rand = new Random(42);  // seed for deterministic testing
        deck = new Deck();
        aceOfSpades = new Card(Card.Suits.SPADES, Card.Values.ACE);
    }

    @Test
    public void testCardConstructor() {
        assertTrue(aceOfSpades != null);
        assertEquals(aceOfSpades.getValue(), Card.Values.ACE);
        assertEquals(aceOfSpades.getSuit(), Card.Suits.SPADES);
    }

    @Test
    public void testCardEquality() {
        Card aceOfSpadesDup = new Card(Card.Suits.SPADES, Card.Values.ACE);
        assertEquals(aceOfSpades, aceOfSpadesDup);
        Card aceOfHearts = new Card(Card.Suits.HEARTS, Card.Values.ACE);
        assertNotEquals(aceOfSpades, aceOfHearts);
        Card kingOfSpades = new Card(Card.Suits.SPADES, Card.Values.KING);
        assertNotEquals(aceOfSpades, kingOfSpades);
        Card kingOfHearts = new Card(Card.Suits.HEARTS, Card.Values.KING);
        assertNotEquals(aceOfSpades, kingOfHearts);
    }

    @Test
    public void testCardString() {
        assertEquals(aceOfSpades.toString(), "ACE of SPADES");
    }

    @Test
    public void testDeckConstructor() {
        assertTrue(deck != null);
        assertEquals(deck.length(), Deck.NUM_CARDS);
        assertNotEquals(deck.toString().length(), 0);
        assertTrue(deck.toString().contains(aceOfSpades.toString()));
    }

    @Test
    public void testShuffle() {
        Card top = deck.turnOver();
        Card newTop = deck.shuffle(rand).turnOver();
        assertNotEquals(top, newTop);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testCutNegativeException() {
        deck.cut(-1);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void testCutOverException() {
        deck.cut(100);
    }

    @Test
    public void testDeckCut() {
        Card topCard = deck.turnOver();
        deck.cut(32);
        assertNotEquals(topCard, deck.turnOver());
        assertEquals(deck.length(), Deck.NUM_CARDS);
        assertEquals(deck.turnOver(), new Card(Card.Suits.DIAMONDS, Card.Values.SEVEN));
    }

    @Test
    public void testDeal() {
        Card top = deck.turnOver();
        Card topDealt = deck.deal();
        assertEquals(top, topDealt);
        assertNotNull(topDealt);
        assertEquals(deck.search(topDealt), -1);
        assertEquals(deck.length(), Deck.NUM_CARDS -1);
    }

    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void testOverDeal() {
        int deckLength = deck.length();
        for(int i = 0; i < deckLength; i++){
            deck.deal();
        }
        deck.deal();
    }

    @Test
    public void testTurnOver() {
        assertEquals(deck.turnOver(), new Card(Card.Suits.HEARTS, Card.Values.ACE));
    }

    @Test
    public void testSearch() {
        Card topCard = deck.turnOver();
        assertEquals(deck.search(topCard), 0);
        assertEquals(deck.search(aceOfSpades), 39);
        deck.deal();
        assertEquals(deck.search(topCard), -1);
        assertEquals(deck.search(aceOfSpades), 38);
    }

    @Test
    public void testNewOrder() {
        Card topCard = deck.turnOver();
        assertEquals(deck.search(topCard), 0);
        assertEquals(deck.search(aceOfSpades), 39);
        deck.shuffle(rand);
        assertNotEquals(deck.search(topCard), 0);
        assertNotEquals(deck.search(aceOfSpades), 39);
        deck.newOrder();
        assertEquals(deck.search(topCard), 0);
        assertEquals(deck.search(aceOfSpades), 39);
    }
}
