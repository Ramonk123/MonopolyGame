package Controllers;

import Exceptions.PlayerException;
import Models.Board;
import Models.Location;
import Models.OwnableLocation;
import Models.Player;
import Monopoly.UUID;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.HasStage;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Controller for the Board model & BoardView view.
 */
public class BoardController implements Controller, Subject<DocumentSnapshot>, Observer<DocumentSnapshot>, HasStage {

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

    public Location playerStandsOn(Player player) { //Player prob gets changed to Players
        long playerPosition = player.getPosition();
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        List<Location> locationArray = locationController.getLocationArray();
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

        ArrayList<Pane> labelList = setLabelListVisibility(getMortgageLabelList(), false);

        ArrayList<OwnableLocation> locationsOwnedByPlayer = addOwnedLocations(new ArrayList<>());

        int amountLocationsOwnedByPlayer = locationsOwnedByPlayer.size();

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
    private void placeBid(ActionEvent actionEvent) {
        //Do shit
    }

}
