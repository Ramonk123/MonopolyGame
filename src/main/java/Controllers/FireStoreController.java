package Controllers;

import Firestore.Firestore;
import Models.Player;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class FireStoreController {

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

}
