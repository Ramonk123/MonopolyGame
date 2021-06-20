package Controllers;

import Firestore.FirestoreFormattable;
import Models.Auction;
import Models.Location;
import Models.Player;
import Monopoly.UUID;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Controller for the Auction model.
 */
public class AuctionController implements Subject<DocumentSnapshot>, FirestoreFormattable, Controller {

    private Player player;
    private Auction auction =  new Auction();

    public AuctionController() {
    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> observer) {

    }

    @Override
    public void notifyObservers() {

    }


    public void startAuction(String positionId) {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);

        List<Location> locations = locationController.getLocationArray();

        for(Location location : locations){
            if(UUID.compare(positionId, location)){
                boardController.showAuction(location.getName());
            }
        }

        //begin boring stuff
        String sellerUUID = playerController.getClientPlayersEnum().getId().getId();
        auction.startAuction(positionId, sellerUUID);

        //TODO: turn functions

    }

    //TODO: needs to be used when its the players turn. Extra thread for the timer. that needs to be added. of course.
    public void placeBid(int bid){

    }

    public Auction getAuction(){
        return auction;
    }

    public void addPlayerBid(int bid){
        auction.addPlayerBid(bid);
    }

    public Pair<String, Integer> getHighestBid(){
        Map<String, Object> playerbids = auction.getPlayerBids();
        Iterator iterator = playerbids.entrySet().iterator();
        long highestBid = 0;
        Pair highestBidPair = null;
        while(iterator.hasNext()){
            Map.Entry pair = (Map.Entry) iterator.next();
            if((int) pair.getValue() > highestBid){
                highestBidPair = new Pair(pair.getKey(), pair.getValue());
            }
        }
        return highestBidPair;
    }

    //TODO create functions that gets Auction bidding results


    @Override
    public Object getFirestoreFormat() {
        return auction.getFirestoreFormat();
    }

}
