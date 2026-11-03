package practical3;

import java.util.Objects;

class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() { return rank; }
    public String getSuit() { return suit; }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(rank, card.rank) && Objects.equals(suit, card.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}

public class CardDriver {
    public static void main(String[] args) {
        System.out.println("=== Practical 3 - Task 2: Duplicate Card Check ===");
        Card[] stream = {
            new Card("King", "Diamonds"),
            new Card("Ace", "Spades"),
            new Card("10", "Hearts"),
            new Card("Ace", "Spades"), // duplicate
            new Card("7", "Clubs")
        };

        Card[] deck = new Card[stream.length];
        int count = 0;

        for (Card nextCard : stream) {
            boolean duplicate = false;
            for (int i = 0; i < count; i++) {
                if (deck[i].equals(nextCard)) {
                    System.out.println("Duplicate found: " + nextCard);
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate) {
                deck[count++] = nextCard;
                System.out.println("Added card: " + nextCard);
            }
        }
    }
}
