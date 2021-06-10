package Controllers;

import Models.Board;
import Models.Player;
import Models.Wallet;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.HasStage;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Optional;

public class BoardController implements Controller, Subject<DocumentSnapshot>, HasStage {

    private Board board;

    public void setDocumentSnapshot(DocumentSnapshot documentSnapshot) {
        this.documentSnapshot = documentSnapshot;
    }

    private DocumentSnapshot documentSnapshot;

    public BoardController() {
        board = new Board();
    }

    // isn't there a way for u to store these labels in an array?
    // and aren't these supposed to be in the Board view itself?
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

    @Override
    public void registerObserver(Observer<DocumentSnapshot> observer) { }

    @Override
    public void unregisterObserver(Observer<DocumentSnapshot> observer) { }

    @Override
    public void notifyObservers() {
        board.update(documentSnapshot);
    }

    @Override
    public void setStage(Stage primaryStage) {
        board.setStage(primaryStage);
    }

    public void setPlayersNamesToLabels() {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        ArrayList<Player> players = playerController.getPlayers();
        // if the labels were in an array, and invisible by default you could do:
        // with the array you'd also avoid that switch you're using right now. (getLabelByNumber).
        // maybe this link will help.
        // https://stackoverflow.com/questions/28587297/create-array-of-label-using-fxml-in-javafx
        /*
        for (int i = 0; i < players.size(); i++) {
            // pseudo code for the label
            labelsArray[i].setVisible()
            labelsArray[i].setName(players.get(i).getName());
        }
         */
        for(int i = 0; i < 8; i++) {
            try {
                setPlayerNameToLabel(players.get(i).getName(), i + 1);
            } catch(IndexOutOfBoundsException indexOutOfBoundsException) {
                setVisibilityLabel(i + 1);
            }
        }
    }

    private void setPlayerNameToLabel(String name, int labelNumber) {
        Label label = getLabelByNumber(labelNumber);
        assert label != null;
        label.setText(name);
    }

    private void setVisibilityLabel(int labelNumber) {
        Label label = getLabelByNumber(labelNumber);
        assert label != null;
        label.setVisible(false);
    }

    @Nullable
    private Label getLabelByNumber(int labelNumber) {
        Label label = null;

        switch(labelNumber) {
            case 1:
                label = BoardViewUsername1Label;
            case 2:
                label = BoardViewUsername2Label;
            case 3:
                label = BoardViewUsername3Label;
            case 4:
                label = BoardViewUsername4Label;
            case 5:
                label = BoardViewUsername5Label;
            case 6:
                label = BoardViewUsername6Label;
            case 7:
                label = BoardViewUsername7Label;
            case 8:
                label = BoardViewUsername8Label;
        }
        return label;
    }
}
