package Models;

import Controllers.AuctionController;
import Controllers.Players;
import ObserveablePattern.Observer;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Auction implements Model {
    private Player player;
    private boolean startedAuction = false;
    private HashMap<Players, Integer> playerBids = new HashMap<>();

    public void addPlayerBid(int bid) {
        //playerBidCollection.add(playerBidInfo.put(player,bid));


        //TODO: Create method to send playerBidCollection to Firestore
    }

    public boolean hasStartedAuction() {
        return startedAuction;
    }

    public void setStartedAuction() {
        this.startedAuction = true;
    }

    //TODO: create functions that handle bidding results and receiving property
}


