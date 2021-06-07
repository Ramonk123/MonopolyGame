package Controllers;

import Models.Card;
import Models.CardDeck;
import Models.Wallet;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class CardDeckController implements Controller {

    private static CardDeckController cardController;
    private CardDeck cardDeck;

    private CardDeckController() {
        cardDeck = new CardDeck();
    }

    public static CardDeckController getInstance() {
        if(cardController == null) {
            cardController = new CardDeckController();
        }
        return cardController;
    }

    /*@Override
    public void registerObserver(View v) {
        cardDeck.registerObserver(v);
    }

    @Override
    public void unregisterObserver(View v) {
        cardDeck.unregisterObserver(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        cardDeck.notifyObservers(ds);
    }*/
}
