package Controllers;

import Firestore.Firestore;
import Models.Player;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.HasStage;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.WriteResult;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import Monopoly.UUID;

public class FireStoreController implements Controller, Subject<DocumentSnapshot>, HasStage {

    private int token;

    public FireStoreController() {
        try {
            initializeFirestore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> observer) {

    }

    @Override
    public void unregisterObserver(Observer<DocumentSnapshot> observer) {

    }

    @Override
    public void notifyObservers() {

    }

    @Override
    public void setStage(Stage primaryStage) {

    }

    Firestore firestore = new Firestore();


    public void initializeFirestore() throws IOException {
        firestore.initializeFirestore();
    }

    public DocumentSnapshot getSnapshot(int token) throws ExecutionException, InterruptedException {
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        DocumentReference documentReference = database.collection("Lobbies").document(String.valueOf(token));
        ApiFuture<DocumentSnapshot> documentSnapshot = documentReference.get();

        return documentSnapshot.get();
    }

    public boolean checkExistence(int token) throws ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot = getSnapshot(token);

        return documentSnapshot.exists();
    }

    public int getLobbySize(int token) throws ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot = getSnapshot(token);

        ArrayList<Player> players = (ArrayList<Player>) documentSnapshot.get("players");

        assert players != null;
        return players.size();
    }

    public void createLobby(int token) {

        //TODO:
        // add the real data instead of sample.
        //    Subdirectories may be helpful for locations and players. Data structure still needs to be designed.

        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        CardDeckController cardDeckController = (CardDeckController) ControllerRegistry.get(CardDeckController.class);

        Map<String, Object> lobbyData = new HashMap<>();
        /*lobbyData.put("players", playerController.getPlayers());
        //lobbyData.put("commonFundCardDeck", cardDeckController.getCommonFundCardDeck());
        lobbyData.put("commonFundCardDeck", "");
        //lobbyData.put("chanceCardDeck", cardDeckController.getChanceCardDeck());
        lobbyData.put("chanceCardDeck", "");
        lobbyData.put("owner", "sample");
        lobbyData.put("activePlayer", "sample");
        lobbyData.put("playerBids", "sample");
        lobbyData.put("trade", "sample");*/

        lobbyData.put("auction", null); // is null when theres no auction. otherwise according to Lobby 0 on firestore.
        lobbyData.put("hostUuid", Players.PLAYER_ONE); // is null when theres no auction. otherwise according to Lobby 0 on firestore.
        lobbyData.put("locations", new ArrayList<Map<String, Object>>());
        lobbyData.put("nextChanceCard", null); // host player sets this when null. data should be a String card uuid.
        lobbyData.put("nextCommunityCard", null); // host player sets this when null. data should be a String card uuid.
        lobbyData.put("players", new ArrayList<Map<String, Object>>()); // host player should be in this.
        lobbyData.put("tradeInvitations", new ArrayList<Map<String, String>>());
        lobbyData.put("tradeWindows", new ArrayList<Map<String, Object>>());
        lobbyData.put("turn", new HashMap<String, Object>());

        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        ApiFuture<WriteResult> upload = database.collection("Lobbies").document(String.valueOf(token)).set(lobbyData);
    }

    public void removePlayer(int token, Player player) throws InterruptedException, ExecutionException {
        DocumentSnapshot documentSnapshot = getSnapshot(token);
        ArrayList<Player> players = (ArrayList<Player>) documentSnapshot.get("players");

        for(int i = 0; Objects.requireNonNull(players).size() > i ; i++){
            if(players.get(i) == player){
                players.remove(i);
            }
        }

        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        database.collection("Lobbies").document(String.valueOf(token)).update("players", players);
    }

    public void addPlayer(int token, Optional<Player> player) {
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();

        ApiFuture<WriteResult> upload = database.collection("Lobbies").document(String.valueOf(token))
                .update("players", FieldValue.arrayUnion(player));
    }

    public void updateChanceCard(){
        CardDeckController cardDeckController = (CardDeckController) ControllerRegistry.get(CardDeckController.class);
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();

        ApiFuture<WriteResult> upload = database.collection("Lobbies").document(String.valueOf(token))
                .update("chanceCardDeck", cardDeckController.getChanceCardDeck());
    }

    public void updateCommonFundCard(){
        CardDeckController cardDeckController = (CardDeckController) ControllerRegistry.get(CardDeckController.class);
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();

        ApiFuture<WriteResult> upload = database.collection("Lobbies").document(String.valueOf(token))
                .update("commonFundCardDeck", cardDeckController.getCommonFundCardDeck());
    }
    public ArrayList<UUID> getChanceCard() throws InterruptedException, ExecutionException {
        DocumentSnapshot documentSnapshot = getSnapshot(token);
        return (ArrayList<UUID>) documentSnapshot.get("chanceCardDeck");
    }

    public ArrayList<UUID> getCommonFundCard() throws InterruptedException, ExecutionException {
        DocumentSnapshot documentSnapshot = getSnapshot(token);
        return (ArrayList<UUID>) documentSnapshot.get("commonFundCardDeck");
    }
}