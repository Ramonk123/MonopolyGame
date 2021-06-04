package Controllers;

import Models.Board;
import Models.Lobby;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class LobbyController implements Controller {

    private static LobbyController lobbyController;
    private Lobby lobby;

    private LobbyController() {
        lobby = new Lobby();
    }

    public static LobbyController getInstance() {
        if(lobbyController == null) {
            lobbyController = new LobbyController();
        }
        return lobbyController;
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
