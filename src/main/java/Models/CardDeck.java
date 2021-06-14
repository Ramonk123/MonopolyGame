package Models;

import Monopoly.UUID;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class CardDeck implements Model {
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

}
