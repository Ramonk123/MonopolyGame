package Controllers;

import Exceptions.PlayerException;

import Exceptions.TransactionException;
import Models.*;
import Monopoly.UUID;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.HasStage;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Controller for the Board model & BoardView view.
 */
public class BoardController implements Subject<DocumentSnapshot>, Observer<DocumentSnapshot>, HasStage, Controller {

    private Board board = new Board();

    private DocumentSnapshot documentSnapshot;

    public BoardController() {
    }

    @FXML
    private Pane BackgroundImageView;

    public Pane getBackgroundImageView() {
        return BackgroundImageView;
    }

    @FXML
    private GridPane BoardViewBoardPane;

    public Pair<Double, Double> getBoardViewBoardPanePosition(Pair<Integer, Integer> gridPositionPair) {
        double x = BoardViewBoardPane.getCellBounds(gridPositionPair.getKey(), gridPositionPair.getValue()).getMinX();
        double y = BoardViewBoardPane.getCellBounds(gridPositionPair.getKey(), gridPositionPair.getValue()).getMinY();
        return new Pair<Double, Double>(x, y);
    }

    public Pair<Double, Double> getGridSize(Pair<Integer, Integer> gridPositionPair) {
        double width = BoardViewBoardPane.getWidth();
        double height = BoardViewBoardPane.getHeight();
        return new Pair<Double, Double>(width, height);
    }

    @FXML
    private void EndTurnPlayer() {
        toggleRollDiceButton(false);
        toggleEndTurnButton(false);

        TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);
        turnController.nextPlayerTurn();
    }

    @FXML
    private void RollDiceAction(ActionEvent actionEvent) throws PlayerException {
        TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);
        turnController.RollDice();
    }

    @FXML
    private FlowPane DiceLabelPane;

    public void setDiceLabelPane() {
        ThrowController throwController = (ThrowController) ControllerRegistry.get(ThrowController.class);

        if(throwController.getEyesDiceOne() != 0 && throwController.getEyesDiceTwo() != 0) {
            String DiceOneValue = String.format("%s", throwController.getEyesDiceOne());
            String DiceTwoValue = String.format("%s", throwController.getEyesDiceTwo());

            ((Label) DiceLabelPane.getChildren().get(0)).setText(DiceOneValue);
            ((Label) DiceLabelPane.getChildren().get(1)).setText(DiceTwoValue);
            DiceLabelPane.getChildren().get(2).setVisible(throwController.isDouble());
        }

    }


    @FXML
    private GridPane BoardViewPlayerPane;

    public GridPane getBoardViewPlayerPane() {
        return BoardViewPlayerPane;
    }

    public void movePlayerOnBoard(Players player, long oldPosition, long newPosition) {
        int playerNumber = player.ordinal();

        System.out.println("player number: " + playerNumber);

        System.out.println(oldPosition);
        System.out.println(newPosition);

        ObservableList<Node> boardArray = BoardViewPlayerPane.getChildren(); //Sets the whole board in an array/list
        ObservableList<Node> currentPlayerGrid = ((GridPane) boardArray.get((int) oldPosition)).getChildren(); //Gets the current grid the playerIcon is on

        System.out.println("current player grid: " + currentPlayerGrid);
        Pane playerIcon = (Pane) currentPlayerGrid.get(playerNumber); //Gets the playerIcon out of the array/list
        if(UUID.compare("PLAYER-" + playerIcon.getId(), player)) {

            //currentPlayerGrid.remove(playerNumber);
            currentPlayerGrid.set(playerNumber, new Pane());

            System.out.println("current player grid: " + currentPlayerGrid);

            ObservableList<Node> newPlayerGrid = ((GridPane) boardArray.get((int) newPosition)).getChildren(); //Gets the grid where the player moved to

            System.out.println("new player grid: " + newPlayerGrid);

            newPlayerGrid.set(playerNumber, playerIcon);

            System.out.println("new player grid: " + newPlayerGrid);
        }
    }

    public Location playerStandsOn(Position player) {
        long playerPosition = player.getPosition();
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        List<Location> locationArray = locationController.getLocationArray();
        System.out.println(playerPosition + "yeshello");
        return locationArray.get((int) playerPosition);
    }

    @FXML
    private Button BoardViewRollDiceButton;

    public void toggleRollDiceButton(boolean state) {
        BoardViewRollDiceButton.setDisable(!state);
    }

    @FXML
    private Button EndTurnButton;

    public void toggleEndTurnButton(boolean state) {
        EndTurnButton.setDisable(!state);
    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> observer) { }

    @Override
    public void notifyObservers() {
        board.update(documentSnapshot);
    }

    @Override
    public void setStage(Stage primaryStage) {
        board.setStage(primaryStage);

        TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);
        turnController.startGameTurn();
    }

    @FXML
    private Label BoardViewUsername1Label;
    @FXML
    private Label BoardViewUsername2Label;
    @FXML
    private Label BoardViewUsername3Label;
    @FXML
    private Label BoardViewUsername4Label;
    @FXML
    private Label BoardViewUsername5Label;
    @FXML
    private Label BoardViewUsername6Label;
    @FXML
    private Label BoardViewUsername7Label;
    @FXML
    private Label BoardViewUsername8Label;

    public ArrayList<Label> getUserLabelList() {
        ArrayList<Label> labelList = new ArrayList<>();
        labelList.add(BoardViewUsername1Label);
        labelList.add(BoardViewUsername2Label);
        labelList.add(BoardViewUsername3Label);
        labelList.add(BoardViewUsername4Label);
        labelList.add(BoardViewUsername5Label);
        labelList.add(BoardViewUsername6Label);
        labelList.add(BoardViewUsername7Label);
        labelList.add(BoardViewUsername8Label);
        return labelList;

    }

    public boolean checkGameHasStarted() {
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        try {
            LobbyController lobbyController = (LobbyController) ControllerRegistry.get(LobbyController.class);
            int token = lobbyController.getToken();
            return fireStoreController.gameHasStarted(token);
        } catch (ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
        }
        return false;

    }
    @FXML
    private Pane MortgagePopup;
    @FXML
    private void showMortgagePopup() throws Exception {
        if(!MortgagePopup.isVisible()) {
            MortgagePopup.setVisible(true);
            displayMortgageMenuLocations();
        } else {
            MortgagePopup.setVisible(false);
        }
    }

    @FXML
    private Pane buyLocationPane;
    @FXML
    private Label locationForSaleName;
    @FXML
    private Label locationForSalePrice;
    @FXML
    private Label locationForSaleHouse;
    @FXML
    private Label locationForSaleMortgage;
    @FXML
    private Button buyLocationButton;
    @FXML
    private Button auctionLocationButton;

    public void updateBuyLocationPane(OwnableLocation location){
        locationForSaleName.setText("Name: " + location.getName());
        locationForSalePrice.setText("Price: $" + location.getPrice() + ",--");
        locationForSaleHouse.setText("Tax Amount: " + " TAX NEEDS TO BE ADDED");  //TODO: as of now idk wtf tax is.
        locationForSaleMortgage.setText("Mortgage: $" + location.getMortgageValue() + ",--");
    }

    public void showBuyLocationPopup(Player player, OwnableLocation location) {
        System.out.println("boardcontroller popup method");
        updateBuyLocationPane(location);
        buyLocationPane.setVisible(true);
        buyLocationButton.setOnAction(event -> { buyLocation(player,location); });
        auctionLocationButton.setOnAction(event -> { auctionLocation(player,location); });
    }

    public void buyLocation(Player player, OwnableLocation location) {
        location.setOwner(player, true);
        System.out.println("Eigenaar: " + location.getOwner());
        player.subtractBalance(location.getPrice());
        buyLocationPane.setVisible(false);
    }

    @FXML private Pane sellLocationPopup;

    public void showSellLocationPopup(Player player, OwnableLocation location){
        sellLocationPopup.setVisible(true);
    }

    public void sellStreetLocation(Player player, StreetLocation location){
        if (location.getHouses() == 0 && location.getHotel()== false){
        player.addBalance(location.getPrice()/2);
        location.setOwnerNull();
        }
    }

    @FXML private Pane payStreetRentPane;
    @FXML private Label payStreetRentAmount;
    @FXML private Button payStreetRentButton;

    public void showStreetPayRent(Player player, StreetLocation location){
        buyLocationPane.setVisible(false);
        payStreetRentAmount.setText("Pay Amount: " + location.getRent());
        payStreetRentPane.setVisible(true);
        payStreetRentButton.setOnAction(event -> {
            Players receivingPlayer = location.getOwner().orElseThrow().getPlayersEnum();
            int amount = location.getRent();
            TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
            try {
                transactionController.payBalance(player.getPlayersEnum(),receivingPlayer,amount);
            } catch (TransactionException e) {
                e.printStackTrace();
            }
        });
    };

    @FXML
    public void auctionLocation(Player player, OwnableLocation location) {
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        buyLocationPane.setVisible(false);
        locationController.refuseToBuyLocation();
    }


    @FXML
    private Pane auctionPane;
    @FXML
    private TextArea bidTextArea;
    @FXML
    private Button placeBidButton;
    @FXML
    private Label NoNumberOnInputError;
    @FXML
    private Label cardPlaceholder;

    public void showAuction(String locationName) {

        auctionPane.setVisible(true);
        cardPlaceholder.setText(locationName);
    }

    public void sellLocation(Player player, OwnableLocation location) {
        location.setOwnerNull();
        player.addBalance(location.getPrice());
    }

    @FXML
    private Pane notEnoughBalancePane;

    public void showNotEnoughBalance(){
        notEnoughBalancePane.setVisible(true);
    }

    @FXML
    private Pane Location1Pane;
    @FXML
    private Pane Location2Pane;
    @FXML
    private Pane Location3Pane;
    @FXML
    private Pane Location4Pane;
    @FXML
    private Pane Location5Pane;
    @FXML
    private Pane Location6Pane;

    public ArrayList<Pane> getMortgageLabelList() {
        ArrayList<Pane> mortgageLabelList = new ArrayList<>();
        mortgageLabelList.add(Location1Pane);
        mortgageLabelList.add(Location2Pane);
        mortgageLabelList.add(Location3Pane);
        mortgageLabelList.add(Location4Pane);
        mortgageLabelList.add(Location5Pane);
        mortgageLabelList.add(Location6Pane);
        return mortgageLabelList;
    }

    @FXML
    private Label NoLocationsToMortgagePopup;

    public void showNoLocationsToMortgagePopup() {
        NoLocationsToMortgagePopup.setVisible(true);
    }

    public void displayMortgageMenuLocations() {
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        ArrayList<Pane> labelList = setLabelListVisibility(getMortgageLabelList(), false);
        Players playersEnum = playerController.getClientPlayersEnum();
        Player player = playerController.getPlayerByPlayersEnum(playersEnum).orElseThrow();


        List<OwnableLocation> locationsOwnedByPlayer = locationController.getLocationsOwnedByPlayer(player);
        int amountLocationsOwnedByPlayer = locationsOwnedByPlayer.size();
        System.out.println("WOW!: " + locationsOwnedByPlayer);

        if(amountLocationsOwnedByPlayer > 0) {
            for (int i = 0; i < amountLocationsOwnedByPlayer; i++) {
                labelList.get(i).setVisible(true);
                OwnableLocation location = locationsOwnedByPlayer.get(i);
                Label locationName = (Label) labelList.get(i).getChildren().get(0);
                Label locationPrice = (Label) labelList.get(i).getChildren().get(1);
                Button locationButton = (Button) labelList.get(i).getChildren().get(2);

                locationName.setText("");
                locationPrice.setText("");

                locationName.setText(location.getName());
                locationPrice.setText(String.valueOf((location.getPrice() / 2)));

                locationButton.setOnAction(event -> {
                    if(!location.hasMortgage()) {
                        locationButton.setText("Unmortgage");
                        locationController.getMortgageOnLocation(location);
                    }else {
                        locationButton.setText("Mortgage");
                        locationController.payMortgage(location);
                    }
                });
            }
        } else {
            showNoLocationsToMortgagePopup();
        }
    }

    public ArrayList<Pane> setLabelListVisibility(ArrayList<Pane> labelList, boolean state) {
        for (Pane label : labelList) {
            label.setVisible(state);
        }
        return labelList;
    }

    public ArrayList<OwnableLocation> addOwnedLocations(ArrayList<OwnableLocation> ownedLocationsByPlayer) {
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);

        for (OwnableLocation location : locationController.getOwnableLocations()) {
            if (location.getId().equals(playerController.getClientPlayersEnum().getId())) {
                ownedLocationsByPlayer.add(location);
            }
        }
        return ownedLocationsByPlayer;
    }

    @Override
    public void update(DocumentSnapshot documentSnapshot) {
        this.documentSnapshot = documentSnapshot;
        notifyObservers();

    }

    @FXML
    private void placeBid() {
        System.out.println("You bid something");
        AuctionController auctionController = (AuctionController) ControllerRegistry.get(AuctionController.class);
        Auction auction = auctionController.getAuction();
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        if (!auction.hasStartedAuction()){
            try{
                int bid = Integer.parseInt(bidTextArea.getText());
                System.out.println("You bid: " + bid);
                Players clientPlayersEnum = playerController.getClientPlayersEnum();
                for(Player player : playerController.getPlayers()){
                    if(UUID.compare(clientPlayersEnum, player)){
                        if(bid <= player.getWallet().getBalance()) {
                            System.out.println("Good money");
                            if(bid > auctionController.getHighestBid().getValue()){
                                System.out.println("Je hebt het hoogste bod lekker man");
                                auctionController.addPlayerBid(bid);
                            } else {
                                System.out.println("niet het hoogste bod");
                                bidTextArea.clear();
                            }
                        } else {
                            System.out.println("No affordo compadre");
                            bidTextArea.clear();
                        }
                    }
                }
            } catch(NumberFormatException numberFormatException) {
                NoNumberOnInputError.setVisible(true);
                bidTextArea.clear();
            }
        }
    }
    @FXML
    private Label chanceCardText;
    @FXML
    private Button chanceCardButton;
    @FXML
    private Pane ChancePopup;

    public void setChancePopupVisible(Player player) {
        CardDeckController cardDeckController = (CardDeckController) ControllerRegistry.get(CardDeckController.class);
        if(!ChancePopup.isVisible()) {
            ChancePopup.setVisible(true);
            //try {

                //Card card = cardDeckController.grabChanceCard();
                Card card = cardDeckController.getRandomChanceCard();
                Platform.runLater(() -> {
                    chanceCardText.setText(card.getDescription());
                });
                card.action(player);

            /*} catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            chanceCardButton.setOnAction((e) -> {

                ChancePopup.setVisible(false);
            });
        }
    }

}
