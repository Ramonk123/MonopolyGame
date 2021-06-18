package Models;

import Controllers.AuctionController;
import Controllers.FireStoreController;
import Controllers.PlayerController;
import Controllers.Players;
import Firestore.FirestoreFormattable;
import Monopoly.UUID;
import ObserveablePattern.Observer;
import Views.BoardView;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model for the Auction.
 */
public class Auction implements Model, Observer<DocumentSnapshot>, FirestoreFormattable {
    private boolean startedAuction = false;
    private HashMap<String, Object> playerBids = new HashMap<>();
    private HashMap<String, Object> auction= new HashMap<>();
    private String locationID;
    private String seller;


    public void startAuction(String locationID, String seller) {
        this.locationID = locationID;
        this.seller = seller;
        this.startedAuction = true;
    }

    public void addPlayerBid(int bid) {
        PlayerController playerController = new PlayerController();
        playerBids.put("player_uuid", playerController.getClientPlayersEnum().getId().getId());
        playerBids.put("bid", bid);
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
        System.out.println("AUCTIONwow");
        HashMap<String, Object> firestoreAuction = (HashMap<String, Object>) documentSnapshot.get("auction");
        if(firestoreAuction == null){
            startedAuction = false;
        }else{
            System.out.println(firestoreAuction);
            startedAuction = true;
            playerBids = (HashMap<String, Object>) firestoreAuction.get("playerBids");
            locationID = (String) firestoreAuction.get("location");
        }
    }

    public String getLocationID(){
        return locationID;
    }

    public HashMap<String, Object> getPlayerBids() {
        return playerBids;
    }

    public void setPlayerBids(HashMap<String, Object> playerBids) {
        this.playerBids = playerBids;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    //TODO: create functions that handle bidding results and receiving property
}


