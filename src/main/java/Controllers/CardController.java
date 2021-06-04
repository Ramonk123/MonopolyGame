package Controllers;

import Models.Card;
import Models.Wallet;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class CardController implements Controller {
    private static CardController cardController;

    public static CardController getInstance() {
        if(cardController == null) {
            cardController = new CardController();
        }
        return cardController;
    }

    @Override
    public void registerObserver(View v) {

    }

    @Override
    public void unregisterObserver(View v) {

    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {

    }
}
