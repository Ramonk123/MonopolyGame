package Controllers;

import Models.Player;
import Models.Trade;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class TradeController implements Controller {

    private Trade trade;

    private TradeController() {
        trade = new Trade();
    }

}
