package Controllers;

import Firestore.FirestoreFormattable;
import Models.Auction;
import Models.OwnableLocation;
import Models.Player;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

/**
 * Controller for the Auction model.
 */
public class AuctionController implements Controller, Subject<DocumentSnapshot>, FirestoreFormattable {

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



    public void startAuction(String positionId) {
        //TODO:  add card to FX:ID cardPlaceHolder

        //TODO: 2. Ik (Brandon) fix de fxml en view voor de nieuwe auction later.
        auctionPane.setVisible(!auctionPane.isVisible());







        placeBidButton.setOnAction(actionEvent -> {

            if (!auction.hasStartedAuction()){
                try{
                    int bid = Integer.parseInt(bidTextArea.getText());
                    if(bid <= player.getWallet().getBalance()) {
                        auction.addPlayerBid(bid);
                    }

                } catch(NumberFormatException numberFormatException) {
                    System.out.println(numberFormatException);
                    NoNumberOnInputError.setVisible(true);
                    bidTextArea.clear();
                }

            }
        });

    }

    //TODO create functions that gets Auction bidding results


    @Override
    public Object getFirestoreFormat() {
        return auction.getFirestoreFormat();
    }

}
