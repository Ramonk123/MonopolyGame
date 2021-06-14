package Controllers;

import Firestore.Firestore;
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
import java.util.function.Supplier;

import Monopoly.UUID;

import javax.annotation.Nullable;

public class FireStoreController implements Controller, Subject<DocumentSnapshot>, HasStage {

    private int token;
    private Consumer<DocumentSnapshot> lambda = (doc) -> {};
    private List<Observer<DocumentSnapshot>> observers = new ArrayList<>();
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
    public void unregisterObserver(Observer<DocumentSnapshot> observer) {
        observers.remove(observer);
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

        HashMap<String, Players> players = (HashMap<String, Players>) documentSnapshot.get("players");
        System.out.println(players);
        assert players != null;
        return players.size();
    }

    public void createLobby(int token) {

        //TODO:
        // add the real data instead of sample.
        //    Subdirectories may be helpful for locations and players. Data structure still needs to be designed.
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);
        CardDeckController cardDeckController = (CardDeckController) ControllerRegistry.get(CardDeckController.class);

        Map<String, Object> lobbyData = (Map<String, Object>) ((LobbyController) ControllerRegistry.get(LobbyController.class)).getFirestoreFormat();
        lobbyData.put("players", playerController.getFirestoreFormat());
        lobbyData.put("turn", turnController.getFirestoreFormat());
        

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

    public void addPlayer(int token, Player player) throws ExecutionException, InterruptedException {
        com.google.cloud.firestore.Firestore database = firestore.getDatabase();
        DocumentSnapshot documentSnapshot = getSnapshot(token);
        Map<String, Object> map = (Map<String, Object>) documentSnapshot.get("players");
        map.put(player.getPlayersEnum().getId().getId(), player.getFirestoreFormat());
        ApiFuture<WriteResult> upload = database.collection("Lobbies").document(String.valueOf(token))
                .update("players", map);
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

    public void setConsumer(Consumer<DocumentSnapshot> lambda) {
        this.lambda = lambda;
    }

    public void listen(int lobbyToken) {
        DocumentReference docRef = firestore.getDatabase().collection("Lobbies").document(String.valueOf(lobbyToken));
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {


            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirestoreException e) {
                System.out.println("OMG A DOCSNAP");
                if (e != null) {
                    System.err.println("Listen failed: " + e);
                    return;
                }
                if (snapshot != null && snapshot.exists()) {
                    documentSnapshot = snapshot;
                    notifyObservers();

                    System.out.println("Current data: " + snapshot.getData());
                } else {
                    System.out.print("Current data: null");
                }
            }
        });
    }
}