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

    public DocumentSnapshot getSnapshot(int token) throws IOException, ExecutionException, InterruptedException {
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        DocumentReference documentReference = database.collection("Lobbies").document(String.valueOf(token));
        ApiFuture<DocumentSnapshot> documentSnapshot = documentReference.get();

        return documentSnapshot.get();
    }

    public boolean checkExistence(int token) throws ExecutionException, InterruptedException, IOException {
        DocumentSnapshot documentSnapshot = getSnapshot(token);

        return documentSnapshot.exists();
    }

    public int getLobbySize(int token) throws IOException, ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot = getSnapshot(token);

        ArrayList<Player> players = (ArrayList<Player>) documentSnapshot.get("players");

        assert players != null;
        return players.size();
    }


    public void createLobby(int token) throws IOException {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);

        //TODO:
        // add the real data instead of sample.
        //    Subdirectories may be helpful for locations and players. Data structure still needs to be designed.

        Map<String, Object> lobbyData = new HashMap<>();
        lobbyData.put("players", playerController.getPlayers());
        lobbyData.put("commonFundCardDeck", "sample");
        lobbyData.put("chanceCardDeck", "sample");
        lobbyData.put("owner", "sample");
        lobbyData.put("activePlayer", "sample");
        lobbyData.put("playerBids", "sample");
        lobbyData.put("trade", "sample");


        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        ApiFuture<WriteResult> upload = database.collection("Lobbies").document(String.valueOf(token)).set(lobbyData);
    }

    public void removePlayer(int token, Player player) throws InterruptedException, ExecutionException, IOException {
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

    public void addPlayer(int token, Optional<Player> player) throws IOException {
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();

        ApiFuture<WriteResult> upload = database.collection("Lobbies").document(String.valueOf(token))
                .update("players", FieldValue.arrayUnion(player));
    }



}
