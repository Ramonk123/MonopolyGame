package Views;

import Controllers.*;
import Exceptions.PlayerException;
import Models.Player;
import Monopoly.UUID;
import ObserveablePattern.Observer;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;
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

    int updateCount = 0;

    private Stage primaryStage;

    public BoardView() {

    }

    private void createPrimaryStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/BoardView.fxml"));
        loader.setController(ControllerRegistry.get(BoardController.class));
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    @Override
    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            createPrimaryStage();
            setPlayerIcons();
            setBackgroundImageView();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public void setBackgroundImageView() {
        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);

        BackgroundImage backgroundImage = new BackgroundImage(new Image("/FXML/IMG/background.png",480,360,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        boardController.getBackgroundImageView().setBackground(new Background(backgroundImage));
    }

    public void setPlayerIcons() {
        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);

        ObservableList<Node> boardArray = boardController.getBoardViewPlayerPane().getChildren();
        ObservableList<Node> currentPlayerGrid = ((GridPane) boardArray.get(0)).getChildren();

        for(int i = 0; i < playerController.getPlayers().size(); i++) {
            setPlayerIcon((Pane) currentPlayerGrid.get(i), i+1);
        }
    }

    private void setPlayerIcon(Pane playerPane, int playerNumber) {
        String URL = "/FXML/Icons/player"+playerNumber+".png";
        BackgroundImage backgroundImage= new BackgroundImage(new Image(URL,playerPane.getWidth(),playerPane.getHeight(),false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        playerPane.setBackground(new Background(backgroundImage));
    }

    @Override
    public void update(BoardSubject state) {
        updatePlayerLabels(state);
        updatePlayerPosition(state);
    }

    public void updatePlayerLabels(BoardSubject state) {
        BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
        PlayerController playerController = (PlayerController)  ControllerRegistry.get(PlayerController.class);
        TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);

        if(boardController.checkGameHasStarted()) {
            Platform.runLater(() -> {
                ArrayList<Player> players = playerController.getPlayers();
                ArrayList<Label> labelList = boardController.getUserLabelList();
                for (int i = 0; i < players.size(); i++) {
                    String text = String.format("%s - $%d", players.get(i).getName(), players.get(i).getBalance());
                    if(UUID.compare(turnController.getCurrentPlayer(), players.get(i).getPlayersEnum())) {
                        text = "X - " + text;
                    }
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
                updateCount += 1;
                System.out.println("UpdateCount" + updateCount);
                System.out.println("Current player: " + turnController.getCurrentPlayer());
                System.out.println("Client player: " + playerController.getClientPlayersEnum());

                Player player = null;
                try {
                    player = playerController.getPlayerByPlayersEnum(turnController.getCurrentPlayer()).orElseThrow(Exception::new);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }

                long oldPosition = player.getOldPosition();
                long currentPosition = player.getPosition();
                long eyesThrownOld = currentPosition - oldPosition;

                System.out.println("eyesthrown old " + eyesThrownOld);


                long eyesThrown = turnController.getEyesThrown();
                System.out.println("Updated eyesthrown " + eyesThrown);

                if (eyesThrown != 0) {
                    System.out.println("Move player");
                    try {
                        turnController.movePlayerOnBoard(player.getPlayersEnum());
                    } catch (PlayerException e) {
                        e.printStackTrace();
                    }
                }


            });
        }
    }

}

