package Controllers;

import Firestore.Firestore;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class FireStoreController {

    public static boolean checkExistence(int token) throws ExecutionException, InterruptedException, IOException {
        com.google.cloud.firestore.Firestore db = Firestore.getFirestore();

        DocumentReference documentReference = db.collection("monopolyteam6-47708").document(String.valueOf(token));
        ApiFuture<DocumentSnapshot> documentSnapshot = documentReference.get();

        DocumentSnapshot document = documentSnapshot.get();
        return document.exists();
    }
}
