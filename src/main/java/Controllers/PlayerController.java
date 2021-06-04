package Controllers;

import Models.Board;
import Models.Player;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class PlayerController implements Controller {

    private static PlayerController playerController;
    private Player player;

    private PlayerController() {
        player = new Player();
    }

    public static PlayerController getInstance() {
        if(playerController == null) {
            playerController = new PlayerController();
        }
        return playerController;
    }

    @Override
    public void registerObserver(View v) {

    }

    @Override
    public void unregisterObserver(View v) {

    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {

    }
}
