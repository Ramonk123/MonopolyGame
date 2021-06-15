package Views;

import Controllers.*;
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
        updateBoardPlayers(state);
    }

    public void updateBoardPlayers(BoardSubject state) {
        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        PlayerController playerController = (PlayerController)  ControllerRegistry.get(PlayerController.class);
        Platform.runLater( () -> {
            ArrayList<Label> labelList = boardController.getUserLabelList();

            for (Label label : labelList) {
                label.setText("");
            }
            ArrayList<Player> players = playerController.getPlayers();
            int playersJoined = players.size();

            for(int i = 0; i < playersJoined; i++) {
                labelList.get(i).setText(players.get(i).getName());
            }
        });

    }
}

