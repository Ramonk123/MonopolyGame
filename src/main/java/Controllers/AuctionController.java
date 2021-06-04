package Controllers;

import Models.Auction;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class AuctionController implements Controller {

    private static AuctionController auctionController;
    private Auction auction;

    private AuctionController() {
        auction = new Auction();
    }

    public static AuctionController getInstance() {
        if(auctionController == null) {
            auctionController = new AuctionController();
        }
        return auctionController;
    }

    @Override
    public void registerObserver(View v) {
        auction.registerObserver(v);
    }

    @Override
    public void unregisterObserver(View v) {
        auction.unregisterObserver(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        auction.notifyObservers(ds);
    }

}
