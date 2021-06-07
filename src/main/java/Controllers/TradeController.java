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

    /*@Override
    public void registerObserver(View v) {
        trade.registerObserver(v);
    }

    @Override
    public void unregisterObserver(View v) {
        trade.unregisterObserver(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        trade.notifyObservers(ds);
    }*/
}
