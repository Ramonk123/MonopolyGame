package Views;

import Controllers.ControllerRegistry;
import Controllers.LobbyController;
import Controllers.MainMenuController;
import Controllers.PlayerController;
import Models.Player;
import ObserveablePattern.Observer;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LobbyView implements View, Observer<LobbySubject>, HasStage {
    //Screensize
    int WIDTH = 630;
    int HEIGHT = 600;
    private Stage primaryStage;

    public LobbyView() {

    }

    private void createPrimaryStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/LobbyView.fxml"));
        loader.setController(ControllerRegistry.get(LobbyController.class));
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            createPrimaryStage();
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void update(LobbySubject state) {
        updateLobbyView(state);
    }
    //This function will check for new players joining lobby and updating player names
    public void updateLobbyView(LobbySubject state) {

        LobbyController lobbyController = (LobbyController) ControllerRegistry.get(LobbyController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);

        ArrayList<Label> labelList = lobbyController.getUserLabelList();
        Platform.runLater(() -> {
            lobbyController.getTokenLabel().setText(String.valueOf(lobbyController.getToken()));

            List<Player> players = state.getPlayers();
            int playersJoined = players.size();
            System.out.println(playersJoined);
            for(int i = 0; i < playersJoined; i++) {
                labelList.get(i).setText(players.get(i).getName());
            }
        });
    }



}
