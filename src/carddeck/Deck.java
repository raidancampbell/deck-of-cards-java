package carddeck;

import java.util.*;

/**
 * A deck consists of exactly NUM_CARDS (52) ordered cards
 * The NUM_CARDS is only used as a default constructor
 */
public class Deck implements Iterable<Card>{
    public static final int NUM_CARDS = 52;
    // because we're dynamically changing the number of cards, we don't want to resize an array
    private ArrayList<Card> cards = new ArrayList<>(NUM_CARDS);

    /**
     * constructor. No information is needed to create a new deck
     * To create, just make one of each card
     * An alternative constructor should be made for the pinochle deck.
     */
    public Deck(){
        for(Card.Suits suit : Card.Suits.values()) {
            for(Card.Values value : Card.Values.values()) {
                cards.add(new Card(suit, value));
            }
        }
    }

    /**
     * Shuffles the deck, thanks to Java's Collection API
     * Returns a reference to this, to allow easy chaining calls
     */
    public Deck shuffle() {
        return this.shuffle(new Random());
    }

    /**
     * Shuffles the deck with the given Random object
     * This is useful for deterministic testing.  Shuffling without the Random object
     * calls this function, with a new Random
     * @param rand Random object, optionally seeded for deterministic results
     * @return this, for call chaining
     */
    public Deck shuffle(Random rand) {
        Collections.shuffle(this.cards, rand);
        return this;
    }

    /**
     * Splits the deck at a point chosen by the player;
     * cards above the split point are placed on the bottom of the deck (without reordering).
     * The first card below the split becomes the top of the deck.
     * The first card above the split becomes the bottom of the deck.
     *
     * int index: the index at which we are cutting the deck
     * Returns a reference to this, to allow easy chaining calls
     *
     * Calls with an invalid index will throw an IOOB Exception
     */
    public Deck cut(int index) {
        if (index < 0 || index >= this.cards.size()) {
            throw new IndexOutOfBoundsException();
        }
        ArrayList<Card> ending =  new ArrayList<>(this.cards.subList(index, this.cards.size()));
        ArrayList<Card> beginning =  new ArrayList<>(this.cards.subList(0, index));
        ending.addAll(beginning);
        this.cards = ending;
        return this;
    }

    /**
     * Retrieves the top card and removes it from the deck.
     * @return the top card off the deck.  Returns Null if no cards are left in the deck
     * Side effect: the card returned is removed from the deck.
     */
    public Card deal() {
        if(this.cards.isEmpty()) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            return this.cards.remove(0);
        }
    }

    /**
     * Turn Over:  Retrieves the top card but does not remove it from the deck.
     * @return the top card off the deck.  Returns null if no cards are left in the deck
     */
    public Card turnOver() {
        if(this.cards.isEmpty()) {
            return null;
        }
        return this.cards.get(0);
    }

    /**
     * Search:  Finds the position of a given card in the deck
     * (top of the deck is the first card, next card is the second, etc.)
     * @param searchCard card to search for in the deck
     * @return index of the card in the deck, -1 if not found.
     */
    public int search(Card searchCard) {

        for(int i = 0; i < cards.size(); i++) {
            if (cards.get(i).equals(searchCard)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Places the remaining cards in the deck in the order of a new deck of cards
     * (top to bottom:  hearts A-K, clubs A-K, diamonds K-A, spades K-A).
     * @return a reference to this, to allow easy chaining calls
     */
    public Deck newOrder() {
        // we're gonna go O(N)
        ArrayList<Card> sortedCards = new ArrayList<>(this.cards.size());

        for(Card.Suits suit : Card.Suits.values()) {
            for(Card.Values value : Card.Values.values()) {
                if(cards.contains(new Card(suit, value))) {
                    sortedCards.add(new Card(suit, value));
                }
            }
        }
        this.cards = sortedCards;
        return this;
    }

    /**
     * simple function to help test.  Returns the number of cards in the deck
     * @return number of cards in the deck
     */
    public int length() {
        return this.cards.size();
    }

    /**
     * The string representation of the deck is the value of each card,
     * printed in order, one on each line
     * @return the string representation of a deck of cards
     */
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for(Card card : this.cards) {
            builder.append(card.toString()).append('\n');
        }

        return builder.toString().trim();  // trim off the trailing newline
    }

    @Override
    public Iterator<Card> iterator() {
        return this.cards.iterator();
    }

    @Override
    public Spliterator<Card> spliterator() {
        return this.cards.spliterator();
    }
}
