package Controllers;

import Models.Player;
import Models.Trade;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class TradeController implements Controller {

    private static TradeController tradeController;
    private Trade trade;

    private TradeController() {
        trade = new Trade();
    }

    public static TradeController getInstance() {
        if(tradeController == null) {
            tradeController = new TradeController();
        }
        return tradeController;
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
