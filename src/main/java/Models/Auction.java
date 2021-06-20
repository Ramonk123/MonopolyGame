package Models;

import Controllers.PlayerController;
import Firestore.FirestoreFormattable;
import ObserveablePattern.Observer;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.HashMap;

/**
 * Model for the Auction.
 */
public class Auction implements Observer<DocumentSnapshot>, FirestoreFormattable {
    private boolean startedAuction = false;
    private HashMap<String, Integer> playerBids = new HashMap<>();
    private final HashMap<String, Object> auction= new HashMap<>();
    private String locationID;
    private String seller;

    public void startAuction(String locationID, String seller) {
        this.locationID = locationID;
        this.seller = seller;
        this.startedAuction = true;
    }

    public void addPlayerBid(int bid) {
        PlayerController playerController = new PlayerController();
        playerBids.put(playerController.getClientPlayersEnum().getId().getId(), bid);
        auction.put("playerBids", playerBids);

        //TODO: Create method to send playerBidCollection to Firestore
    }

    public boolean hasStartedAuction() {
        return startedAuction;
    }

    @Override
    public Object getFirestoreFormat() {
        return auction;
    }

    @Override
    public void update(DocumentSnapshot documentSnapshot) {
        HashMap<String, Object> firestoreAuction = (HashMap<String, Object>) documentSnapshot.get("auction");
        if(firestoreAuction == null){
            startedAuction = false;
        }else{
            startedAuction = true;
            playerBids = (HashMap<String, Integer>) firestoreAuction.get("playerBids");
            locationID = (String) firestoreAuction.get("location");
        }
    }

    public String getLocationID(){
        return locationID;
    }

    public HashMap<String, Integer> getPlayerBids() {
        return playerBids;
    }

    public void setPlayerBids(HashMap<String, Integer> playerBids) {
        this.playerBids = playerBids;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    //TODO: create functions that handle bidding results and receiving property

    /*TODO:
        The new way the auction will be handled.
        1. Player gets a turn,
        2. Player has 10 seconds to place a bid or leave the auction.
        3. Player that bids the most wins the auction.
        -
        Once you leave the auction you cannot bid anymore.
        -
        New UI.
        1. Card must be displayed
        2. player who's turn it is must be displayed.
        3. Highest current bid and player who owns that bid must be displayed.
        4. Enter bid and leave buttons.
        5. Timer in the right upper corner.


     */

}


