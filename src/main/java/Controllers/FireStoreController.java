package Controllers;

import Firestore.Firestore;
import Models.Player;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.HasStage;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class FireStoreController implements Controller, Subject<DocumentSnapshot>, HasStage {

    public FireStoreController() {

    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> o) {

    }

    @Override
    public void unregisterObserver(Observer<DocumentSnapshot> o) {

    }

    @Override
    public void notifyObservers() {

    }

    @Override
    public void setStage(Stage primaryStage) {

    }

    public static DocumentSnapshot getSnapshot(int token) throws IOException, ExecutionException, InterruptedException {
        com.google.cloud.firestore.Firestore db = Firestore.getFirestore();
        DocumentReference documentReference = db.collection("Lobbies").document(String.valueOf(token));
        ApiFuture<DocumentSnapshot> documentSnapshot = documentReference.get();

        return documentSnapshot.get();
    }

    public static boolean checkExistence(int token) throws ExecutionException, InterruptedException, IOException {
        DocumentSnapshot document = getSnapshot(token);

        return document.exists();
    }

    public static int getLobbySize(int token) throws IOException, ExecutionException, InterruptedException {
        DocumentSnapshot document = getSnapshot(token);

        ArrayList<Player> players = (ArrayList<Player>) document.get("players");

        assert players != null;
        return players.size();
    }


    public static void createLobby(int token) throws IOException {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);

        //TODO:
        // add the real data instead of sample.
        //    Subdirectories may be helpful for locations and players. Data structure still needs to be designed.

        Map<String, Object> lobbyData = new HashMap<>();
        lobbyData.put("players", playerController.getPlayers());
        lobbyData.put("commonFundCardDeck", "sample");
        lobbyData.put("chanceCardDeck", "sample");
        lobbyData.put("owner", "sample");
        lobbyData.put("houses", "sample");
        lobbyData.put("hotel", "sample");
        lobbyData.put("activePlayer", "sample");
        lobbyData.put("playerBids", "sample");
        lobbyData.put("trade", "sample");


        com.google.cloud.firestore.Firestore db = Firestore.getFirestore();
        ApiFuture<WriteResult> upload = db.collection("Lobbies").document(String.valueOf(token)).set(lobbyData);
    }

    public static void removePlayer(int token, Player player) throws InterruptedException, ExecutionException, IOException {
        DocumentSnapshot document = getSnapshot(token);
        ArrayList<Player> players = (ArrayList<Player>) document.get("players");

        for(int i = 0; Objects.requireNonNull(players).size() > i ; i++){
            if(players.get(i) == player){
                players.remove(i);
            }
        }

        com.google.cloud.firestore.Firestore db = Firestore.getFirestore();
        db.collection("Lobbies").document(String.valueOf(token)).update("players", players);
    }



}
