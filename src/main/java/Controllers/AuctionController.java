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

    @FXML Pane auctionPane;
    @FXML TextArea bidTextArea;
    @FXML Button placeBidButton;
    @FXML Label NoNumberOnInputError;
    @FXML Label cardPlaceHolder;



    public void startAuction(String positionId) {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        auctionPane.setVisible(!auctionPane.isVisible());

        List<Location> locations = locationController.getLocationArray();

        for(Location location : locations){
            if(UUID.compare(positionId, location)){
                cardPlaceHolder.setText(location.getName());
            }
        }

        //begin boring stuff
        String sellerUUID = playerController.getClientPlayersEnum().getId().getId();

        auction.startAuction(positionId, sellerUUID);

        //TODO: turn functions

    }

    //TODO: needs to be used when its the players turn. Extra thread for the timer. that needs to be added. of course.
    public void placeBid(){
        placeBidButton.setOnAction(actionEvent -> {

            if (!auction.hasStartedAuction()){
                try{
                    int bid = Integer.parseInt(bidTextArea.getText());
                    if(bid <= player.getWallet().getBalance()) {
                        auction.addPlayerBid(bid);
                    }

                } catch(NumberFormatException numberFormatException) {
                    NoNumberOnInputError.setVisible(true);
                    bidTextArea.clear();
                }

            }
        });
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
