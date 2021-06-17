package Views;

import Controllers.*;
import Exceptions.PlayerException;
import Models.Player;
import ObserveablePattern.Observer;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Sets the Stage/Scene for the BoardView.fxml and has additional methods that add things to the view.
 */
public class BoardView implements View, Observer<BoardSubject>, HasStage {
    //Screensize
    int WIDTH = 1080;
    int HEIGHT = 720;

    private Stage primaryStage;

    public BoardView() {

    }

    private void createPrimaryStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/BoardView.fxml"));
        loader.setController(ControllerRegistry.get(BoardController.class));
        Parent root = loader.load();

        ((BoardController) ControllerRegistry.get(BoardController.class)).setBackgroundImageView();

        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    @Override
    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            createPrimaryStage();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void update(BoardSubject state) {
        updatePlayerLabels(state);
        updatePlayerPosition(state);
    }

    public void updatePlayerLabels(BoardSubject state) {
        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        PlayerController playerController = (PlayerController)  ControllerRegistry.get(PlayerController.class);

        if(boardController.checkGameHasStarted()) {
            Platform.runLater(() -> {
                ArrayList<Player> players = playerController.getPlayers();
                ArrayList<Label> labelList = boardController.getUserLabelList();
                for (int i = 0; i < players.size(); i++) {
                    String text = String.format("%s - $%s", players.get(i).getName(), players.get(i).getBalance());
                    labelList.get(i).setText(text);
                    labelList.get(i).setVisible(true);
                }
            });
        }
    }

    public void updatePlayerPosition(BoardSubject state) {
        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        PlayerController playerController = (PlayerController)  ControllerRegistry.get(PlayerController.class);
        TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);

        if(boardController.checkGameHasStarted()) {
            Platform.runLater(() -> {
                for (Player player : playerController.getPlayers()) {
                    long oldPosition = player.getOldPosition();
                    long currentPosition = player.getPosition();
                    long eyesThrown = currentPosition - oldPosition;

                    try {
                        turnController.movePlayer(player.getPlayersEnum(), eyesThrown);
                    } catch (PlayerException playerException) {
                        playerException.printStackTrace();
                    }
                }
            });
        }
    }

}

