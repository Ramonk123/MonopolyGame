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

/**
 * Model for the board which mostly has methods for the BoardView view.
 */
public class Board implements Model, BoardSubject, Observer<DocumentSnapshot>, HasStage {
    private List<Observer<BoardSubject>> observers = new ArrayList<>();

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
