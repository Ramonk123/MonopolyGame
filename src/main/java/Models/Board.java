package Models;

import Controllers.BoardController;
import Controllers.ControllerRegistry;
import Controllers.LocationController;
import Controllers.PlayerController;
import ObserveablePattern.Observer;
import Views.*;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Board implements Model, BoardSubject, Observer<DocumentSnapshot>, HasStage {
    private List<Observer<BoardSubject>> observers = new ArrayList<>();
    private List<Pair<Integer, Integer>> gridpanePositions = new ArrayList<>();

    public Board() {
        registerObserver(new BoardView());
    }

    @Override
    public void registerObserver(Observer<BoardSubject> observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer<BoardSubject> observer) {

    }

    @Override
    public void notifyObservers() {
        for (Observer<BoardSubject> observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public void update(DocumentSnapshot state) {
        // perform updates here
        notifyObservers();
    }

    @Override
    public List<Location> getLocations() {
        return null;
    }

    @Override
    public List<Player> getPlayers() {
        return null;
    }

    @Override
    public void setStage(Stage primaryStage) {
        ((BoardView) observers.get(0)).setStage(primaryStage);
    }

    public void setGridpanePositions() { // Maybe not needed
        gridpanePositions.add(new Pair<>(10, 10));
        gridpanePositions.add(new Pair<>(9, 10));
        gridpanePositions.add(new Pair<>(8, 10));
        gridpanePositions.add(new Pair<>(7, 10));
        gridpanePositions.add(new Pair<>(6, 10));
        gridpanePositions.add(new Pair<>(5, 10));
        gridpanePositions.add(new Pair<>(4, 10));
        gridpanePositions.add(new Pair<>(3, 10));
        gridpanePositions.add(new Pair<>(2, 10));
        gridpanePositions.add(new Pair<>(1, 10));
        gridpanePositions.add(new Pair<>(0, 10));
        gridpanePositions.add(new Pair<>(0, 9));
        gridpanePositions.add(new Pair<>(0, 8));
        gridpanePositions.add(new Pair<>(0, 7));
        gridpanePositions.add(new Pair<>(0, 6));
        gridpanePositions.add(new Pair<>(0, 5));
        gridpanePositions.add(new Pair<>(0, 4));
        gridpanePositions.add(new Pair<>(0, 3));
        gridpanePositions.add(new Pair<>(0, 2));
        gridpanePositions.add(new Pair<>(0, 1));
        gridpanePositions.add(new Pair<>(0, 0));
        gridpanePositions.add(new Pair<>(1, 0));
        gridpanePositions.add(new Pair<>(2, 0));
        gridpanePositions.add(new Pair<>(3, 0));
        gridpanePositions.add(new Pair<>(4, 0));
        gridpanePositions.add(new Pair<>(5, 0));
        gridpanePositions.add(new Pair<>(6, 0));
        gridpanePositions.add(new Pair<>(7, 0));
        gridpanePositions.add(new Pair<>(8, 0));
        gridpanePositions.add(new Pair<>(9, 0));
        gridpanePositions.add(new Pair<>(10, 0));
        gridpanePositions.add(new Pair<>(10, 1));
        gridpanePositions.add(new Pair<>(10, 2));
        gridpanePositions.add(new Pair<>(10, 3));
        gridpanePositions.add(new Pair<>(10, 4));
        gridpanePositions.add(new Pair<>(10, 5));
        gridpanePositions.add(new Pair<>(10, 6));
        gridpanePositions.add(new Pair<>(10, 7));
        gridpanePositions.add(new Pair<>(10, 8));
        gridpanePositions.add(new Pair<>(10, 9));

    }

    public void displayMortgageMenuLocations() {
        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);

        ArrayList<Pane> labelList = boardController.getMortgageLabelList();
        ArrayList<OwnableLocation> locationsOwnedByPlayer = new ArrayList<>();
        List<OwnableLocation> ownableLocations = locationController.getOwnableLocations();

        locationsOwnedByPlayer.add(ownableLocations.get(3));
        locationsOwnedByPlayer.add(ownableLocations.get(4));
        locationsOwnedByPlayer.add(ownableLocations.get(6));

        for (Pane label : labelList) {
            label.setVisible(false);
        }
        int amountOfLocationsOwnedByPlayer = locationsOwnedByPlayer.size();
        for (OwnableLocation location : ownableLocations) {
            if (location.getOwner().equals(playerController.getClientPlayersEnum())) {
                locationsOwnedByPlayer.add(location);
            }
        }
        if (amountOfLocationsOwnedByPlayer == 0) {
            boardController.showNoLocationsToMortgagePopup();
        } else {
            for (int i = 0; i < amountOfLocationsOwnedByPlayer; i++) {
                System.out.println(labelList.get(i).getChildren());
                labelList.get(i).setVisible(true);
                Label locationName = (Label) labelList.get(i).getChildren().get(0);
                Label locationPrice = (Label) labelList.get(i).getChildren().get(1);
                Button locationButton = (Button) labelList.get(i).getChildren().get(3);
                boolean hasMortgage = locationsOwnedByPlayer.get(i).getMortgage();
                locationName.setText("");
                locationPrice.setText("");



                locationName.setText(locationsOwnedByPlayer.get(i).getName());
                locationPrice.setText(String.valueOf(locationsOwnedByPlayer.get(i).getPrice()));
            }
        }
    }
}
