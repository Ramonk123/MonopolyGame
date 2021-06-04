package Firestore;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class Firestore {

    public com.google.cloud.firestore.Firestore getdb() throws IOException {

        FileInputStream serviceAccount =
                new FileInputStream("monopolyteam6-47708-firebase-adminsdk-tp5m6-9a482af7f7.json");

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

        com.google.cloud.firestore.Firestore db = FirestoreClient.getFirestore();

        Firestore firestore = new Firestore();

        return db;
    }

    // methods you can use to upload data to firestore.
    //Exceptions are for the last line in this function, update time.
    public void exampleUpload(com.google.cloud.firestore.Firestore db) throws ExecutionException, InterruptedException {
        // for regular vars like string int and all that good stuff.
        Map<String, Object> docData = new HashMap<>();
        docData.put("titelkop", "texthierinofzo");

        ArrayList<Object> arrayExample = new ArrayList<>();
        Collections.addAll(arrayExample, 12, 32, "hello world", false);
        docData.put("arrayExample", arrayExample);

        ApiFuture<WriteResult> future = db.collection("dataplace").document("submap").set(docData);
        // docData is the data you upload of course.

        //only use this for testing, print Update time to console
        System.out.println("Update time: " + future.get().getUpdateTime());
    }
}
