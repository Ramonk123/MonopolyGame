package Models;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 */
public class CardDeck {
    private ArrayList<Card> cardDeck;

    public ArrayList<Card> getCardDeck() {
        return cardDeck;
    }

    public void setCardDeck(ArrayList<Card> cardDeck) {
        this.cardDeck = cardDeck;
    }

    public CardDeck(ArrayList<Card> cardDeck) {
        this.cardDeck = cardDeck;
    }

    public void add(Card card){
        this.cardDeck.add(card);
    }

    public void shuffle(){
        Collections.shuffle(this.cardDeck);
    }

    public void take(int i, Card card) {
        this.cardDeck.add(card);
        this.cardDeck.remove(i);
    }

    public int size() {
        return  this.cardDeck.size();
    }
}
