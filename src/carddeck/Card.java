package carddeck;

/**
 * Created by aidan on 11/11/16.
 * A card has two properties: a suit and a value.
 */
class Card {
    enum Suits {HEARTS, CLUBS, DIAMONDS, SPADES}
    private Suits suit;

    enum Values {ACE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING}
    private Values value;

    Card(Suits givenSuit, Values givenValue) {
        this.suit = givenSuit;
        this.value = givenValue;
    }

    public Suits getSuit() {
        return suit;
    }

    public Values getValue() {
        return value;
    }

    public String toString() {
        return this.value.name() + " of " + this.suit.name();
    }

    public boolean equals(Object other) {
        if (other instanceof Card) {
            Card otherCard = (Card) other;
            return this.suit == otherCard.suit && this. value == otherCard.value;
        }
        return false;
    }

}
