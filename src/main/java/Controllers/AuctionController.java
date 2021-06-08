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

public class AuctionController implements Controller, Subject<DocumentSnapshot> {

    private static AuctionController auctionController;
    private Player player;
    private Auction auction;

    public AuctionController() {
        auction = new Auction();
    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> o) {

    }

    @Override
    public void unregisterObserver(Observer<DocumentSnapshot> o) {

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

        placeBidButton.setOnAction(event -> {

            if (!auction.hasStartedAuction()){
                try{
                    int bid = Integer.parseInt(bidTextArea.getText());
                    if(bid <= player.getWallet().getBalance()) {
                        auction.addPlayerBid(bid);
                    }

                } catch(NumberFormatException e) {
                    System.out.println(e);
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
