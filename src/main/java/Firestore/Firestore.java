package Firestore;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

/**
 * The model that makes an instance of the database.
 */
public class Firestore {

    public com.google.cloud.firestore.Firestore database;

    public com.google.cloud.firestore.Firestore getDatabase() {
        return database;
    }

    /**
     * Makes an instance of the firestore database.
     * @throws IOException
     */
    public void initializeFirestore() throws IOException {

        String fileName = "FireStore/monopolyteam6-47708-firebase-adminsdk-tp5m6-9a482af7f7.json";

        ClassLoader classLoader = Firestore.class.getClassLoader();
        FileInputStream serviceAccount = new FileInputStream(classLoader.getResource(fileName).getFile());

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

        com.google.cloud.firestore.Firestore firestoreDatabase = FirestoreClient.getFirestore();

        Firestore firestore = new Firestore();

        database = firestoreDatabase;
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


        //use this line to add a document
        db.collection("collectie").document("documentname").set("data");

        //line for updating
        db.collection("collectie").document("documentname").update("titlething", 12);

        //update array door 1 toe te voegen
        ApiFuture<WriteResult> array1 = db.collection("collectieofc").document("documentname")
                .update("array", FieldValue.arrayUnion("extra array value"));
        //I use arrayUnion


        //verwijder een array waarde
        ApiFuture<WriteResult> array2 =  db.collection("collectieofc").document("documentname")
                .update("array", FieldValue.arrayRemove("array value you want to remove"));
        // used arrayRemove
    }
}