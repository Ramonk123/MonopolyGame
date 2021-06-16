package Controllers;

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

/**
 * Controller for the Auction model.
 */
public class AuctionController implements Controller, Subject<DocumentSnapshot> {

    private Player player;
    private Auction auction =  new Auction();

    public AuctionController() {
    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> observer) {

    }

    @Override
    public void unregisterObserver(Observer<DocumentSnapshot> observer) {

    }

    @Override
    public void notifyObservers() {

    }

    @FXML Pane auctionPane;
    @FXML TextArea bidTextArea;
    @FXML Button placeBidButton;
    @FXML Label NoNumberOnInputError;



    public void startAuction() {
        //TODO:  add card to FX:ID cardPlaceHolder
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

    public void  setAuctionStarter() {
        auction.setStartedAuction();

    }

    //TODO create functions that gets Auction bidding results
}
