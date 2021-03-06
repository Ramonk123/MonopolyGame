package Views;

import Controllers.ControllerRegistry;
import Controllers.LobbyController;
import Controllers.PlayerController;
import Models.Player;
import ObserveablePattern.Observer;
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
 * Sets the Stage/Scene for the LobbyView.fxml and has additional methods that add things to the view.
 */
public class LobbyView implements View, Observer<LobbySubject>, HasStage {
    //Screensize
    private final int WIDTH = 600;
    private final int HEIGHT = 400;
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

    public Stage getStage() {
        return primaryStage;
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
            for (Label label : labelList) {
                label.setText("");
            }

            lobbyController.getTokenLabel().setText(String.valueOf(lobbyController.getToken()));

            List<Player> players = state.getPlayers();
            int playersJoined = players.size();
            for(int i = 0; i < playersJoined; i++) {
                labelList.get(i).setText(players.get(i).getName());
            }
        });
    }



}
