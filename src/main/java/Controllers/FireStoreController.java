package Controllers;

import Firestore.Firestore;
import Firestore.FirestoreFormattable;
import Models.Player;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.HasStage;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.cloud.firestore.EventListener;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import javax.annotation.Nullable;

/**
 * Controller for the Firestore model, this controller is the gateway to the database.
 */
public class FireStoreController implements Subject<DocumentSnapshot>, HasStage, Controller {

    private int token;
    private Consumer<DocumentSnapshot> lambda = (doc) -> {};
    private final List<Observer<DocumentSnapshot>> observers = new ArrayList<>();
    private DocumentSnapshot documentSnapshot;



    public FireStoreController() {
        try {
            initializeFirestore();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer<DocumentSnapshot> observer : observers) {
            observer.update(documentSnapshot);
        }
    }

    @Override
    public void setStage(Stage primaryStage) {

    }

    private final Firestore firestore = new Firestore();


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

    public boolean gameHasStarted(int token) throws ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot = getSnapshot(token);
        return (boolean) documentSnapshot.get("gameHasStarted");



    }

    public void startGame(int token) {
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        ApiFuture<WriteResult> upload = database.collection("Lobbies").document(String.valueOf(token)).update("gameHasStarted", true);
    }

    public int getLobbySize(int token) throws ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot = getSnapshot(token);

        HashMap<String, Players> players = (HashMap<String, Players>) documentSnapshot.get("players");
        assert players != null;
        return players.size();
    }

    public void createLobby(int token) {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);
        CardDeckController cardDeckController = (CardDeckController) ControllerRegistry.get(CardDeckController.class);
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);

        Map<String, Object> lobbyData = (Map<String, Object>) ((LobbyController) ControllerRegistry.get(LobbyController.class)).getFirestoreFormat();
        lobbyData.put("players", playerController.getFirestoreFormat());
        lobbyData.put("turn", turnController.getFirestoreFormat());
        lobbyData.put("nextChanceCard", cardDeckController.getNextChanceCard());
        lobbyData.put("nextCommonFundCard", cardDeckController.getNextCommonFundCard());
        lobbyData.put("locations", locationController.getFirestoreFormat());

        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        ApiFuture<WriteResult> upload = database.collection("Lobbies").document(String.valueOf(token)).set(lobbyData);

    }

    public void removePlayer(int token, Player player) throws InterruptedException, ExecutionException {
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        DocumentSnapshot documentSnapshot = getSnapshot(token);
        Map<String, Object> map = (Map<String, Object>) documentSnapshot.get("players");
        map.remove(player.getPlayersEnum().getId().getId());

        database.collection("Lobbies").document(String.valueOf(token)).update("players", map);
    }

    public void addPlayer(int token, Player player) throws ExecutionException, InterruptedException {
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        DocumentSnapshot documentSnapshot = getSnapshot(token);
        Map<String, Object> map = (Map<String, Object>) documentSnapshot.get("players");
        map.put(player.getPlayersEnum().getId().getId(), player.getFirestoreFormat());
        ApiFuture<WriteResult> upload = database.collection("Lobbies").document(String.valueOf(token))
                .update("players", map);
    }

    public void updateChanceCard() {
        CardDeckController cardDeckController = (CardDeckController) ControllerRegistry.get(CardDeckController.class);
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();

        ApiFuture<WriteResult> upload = database.collection("Lobbies").document(String.valueOf(token))
                .update("nextChanceCard", cardDeckController.getNextChanceCard());
    }

    public void updateCommonFundCard() {
        CardDeckController cardDeckController = (CardDeckController) ControllerRegistry.get(CardDeckController.class);
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();

        ApiFuture<WriteResult> upload = database.collection("Lobbies").document(String.valueOf(token))
                .update("nextCommonFundCard", cardDeckController.getNextCommonFundCard());
    }

    public String getChanceCard(int token) throws InterruptedException, ExecutionException {
        DocumentSnapshot documentSnapshot = getSnapshot(token);
        return (String) documentSnapshot.get("nextChanceCard");
    }

    public void updateLocations() {
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();

        ApiFuture<WriteResult> upload = database.collection("Lobbies").document(String.valueOf(token))
                .update("locations", locationController.getLocationArray());
    }


    public void setChanceCardNull(int token) {
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        database.collection("Lobbies").document(String.valueOf(token))
                .update("nextChanceCard", null);
    }

    public String getCommonFundCard() throws InterruptedException, ExecutionException {
        DocumentSnapshot documentSnapshot = getSnapshot(token);
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        database.collection("Lobbies").document(String.valueOf(token))
                .update("nextCommonFundCard", null);
        return (String) documentSnapshot.get("nextCommonFundCard");
    }

    public void updatePlayer(int token, Player player) throws ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot = getSnapshot(token);
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        Map<String, Object> map = (Map<String, Object>) documentSnapshot.get("players");
        map.put(player.getPlayersEnum().getId().getId(), player.getFirestoreFormat());
        database.collection("Lobbies").document(String.valueOf(token))
                .update("players", map);
    }

    public void updateAllPlayers(int token, Collection<Player> playerCollection) throws ExecutionException, InterruptedException {
        DocumentSnapshot documentSnapshot = getSnapshot(token);
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        Map<String, Object> map = (Map<String, Object>) documentSnapshot.get("players");
        for (Player player : playerCollection) {
            map.put(player.getPlayersEnum().getId().getId(), player.getFirestoreFormat());
        }
        database.collection("Lobbies").document(String.valueOf(token))
                .update("players", map);
    }

    public void updateTurn(int token, FirestoreFormattable turn) {
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        database.collection("Lobbies").document(String.valueOf(token))
                .update("turn", turn.getFirestoreFormat());
    }

    public void updateAllLocations(int token) {
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        database.collection("Lobbies").document(String.valueOf(token))
                .update("locations", locationController.getFirestoreFormat());
    }

    public void setConsumer(Consumer<DocumentSnapshot> lambda) {
        this.lambda = lambda;
    }

    public void createAuction(HashMap auction){
        AuctionController auctionController = (AuctionController) ControllerRegistry.get(AuctionController.class);
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        database.collection("Lobbies").document(String.valueOf(token))
                .update("auction", auctionController.getFirestoreFormat());
    }

    public void endAuction(){
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        database.collection("Lobbies").document(String.valueOf(token))
                .update("auction", null);

    }

    public void listen(int lobbyToken) {
        DocumentReference docRef = firestore.getDatabase().collection("Lobbies").document(String.valueOf(lobbyToken));
        docRef.addSnapshotListener(new EventListener<>() {


            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirestoreException e) {
                if (e != null) {
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    documentSnapshot = snapshot;
                    notifyObservers();

                }
            }
        });
    }


}