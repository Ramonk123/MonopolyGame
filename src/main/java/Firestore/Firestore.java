package Firestore;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Firestore {


    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

        FileInputStream serviceAccount =
                new FileInputStream("monopolyteam6-47708-firebase-adminsdk-tp5m6-9a482af7f7.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

        com.google.cloud.firestore.Firestore db = FirestoreClient.getFirestore();

        Firestore firestore = new Firestore();

        firestore.getQuoteFromFirestore(db);
    }

    public void getQuoteFromFirestore(com.google.cloud.firestore.Firestore db) throws InterruptedException, ExecutionException {

        DocumentReference docRef = db.collection("sampleData").document("my_new_document");
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if(document.exists()) {
            String quote = (String) document.get("Gandhi");
            System.out.println(quote);
        }else {
            System.out.println("No such document :(");
        }
    }

}
