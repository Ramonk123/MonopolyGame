package Controllers;

import Models.Board;
import Models.Location;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class LocationController implements Controller {
    private AuctionController auctionController = new AuctionController();

    private static LocationController locationController;

    /*@Override
    public void registerObserver(View v) {

    }

    @Override
    public void unregisterObserver(View v) {

    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {

    }*/


    //TODO: Create onEvent function when refusing to buy location
    private void refuseToBuyLocation() {
        auctionController.setAuctionStarter();
        auctionController.startAuction();
    }
}
