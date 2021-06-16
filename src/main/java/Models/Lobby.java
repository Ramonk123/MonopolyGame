package Models;

import Controllers.ControllerRegistry;
import Controllers.PlayerController;
import Controllers.Players;
import Firestore.FirestoreFormattable;
import ObserveablePattern.Observer;
import Views.*;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model which contains methods for the creation of the lobby and setting different lobby stages.
 */
public class Lobby implements Model, LobbySubject, Observer<DocumentSnapshot>, HasStage, FirestoreFormattable {
    private List<Observer<LobbySubject>> observers = new ArrayList<>();

    public Lobby() {
        registerObserver(new LobbyView());
        registerObserver(new CreateLobbyView());
        registerObserver(new JoinLobbyView());
    }

    @Override
    public void registerObserver(Observer<LobbySubject> observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer<LobbySubject> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer<LobbySubject> observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public void update(final DocumentSnapshot state) {
        // perform updates on this model
        notifyObservers();
    }

    @Override
    public void setStage(Stage primaryStage) {
        ((LobbyView) observers.get(0)).setStage(primaryStage);
    }

    public Stage getStage() {
        return ((LobbyView) observers.get(0)).getStage();
    }

    public void setCreateLobbyStage(Stage primaryStage) {
        ((CreateLobbyView) observers.get(1)).setStage(primaryStage);
    }

    public void setJoinLobbyStage(Stage primaryStage) {
        ((JoinLobbyView) observers.get(2)).setStage(primaryStage);
    }

    @Override
    public Object getFirestoreFormat() {
        Map<String, Object> lobbyData = new HashMap<>();
        lobbyData.put("auction", null); // is null when theres no auction. otherwise according to Lobby 0 on firestore.
        lobbyData.put("hostUuid", Players.PLAYER_ONE); // is null when theres no auction. otherwise according to Lobby 0 on firestore.
        lobbyData.put("locations", new ArrayList<Map<String, Object>>());
        lobbyData.put("nextChanceCard", null); // host player sets this when null. data should be a String card uuid.
        lobbyData.put("nextCommonFundCard", null); // host player sets this when null. data should be a String card uuid.
        lobbyData.put("players", new HashMap<String, Object>()); // host player should be in this.
        lobbyData.put("tradeInvitations", new ArrayList<Map<String, String>>());
        lobbyData.put("tradeWindows", new ArrayList<Map<String, Object>>());
        lobbyData.put("turn", new HashMap<String, Object>());
        lobbyData.put("gameHasStarted", false);
        return lobbyData;
    }

    @Override
    public List<Player> getPlayers() {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        return playerController.getPlayers();
    }
}
