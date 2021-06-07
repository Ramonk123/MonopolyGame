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

    /*@Override
    public void registerObserver(View v) {
        player.registerObserver(v);
    }

    @Override
    public void unregisterObserver(View v) {
        player.unregisterObserver(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        player.notifyObservers(ds);
    }*/
}
