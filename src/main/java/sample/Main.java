package sample;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        Logger logger = Logger.getLogger(Main.class);
        BasicConfigurator.configure();
        logger.setLevel(Level.OFF);

        final String PRIVATEKEY = "src/main/java/sample/firebase_ipsen_pk.json";
        FileInputStream serviceAccount =
                new FileInputStream(PRIVATEKEY);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp.initializeApp(options);

        Firestore db = FirestoreClient.getFirestore();



        }

        public void deleteFromFirestore(Firestore db) throws InterruptedException, ExecutionException
        {
            DocumentReference docRef = db.collection("sampleData").document("my_new_document");
            ApiFuture<WriteResult> future = docRef.delete();
            WriteResult result = future.get();

            System.out.println("Successfully deleted at: " + future.get().getUpdateTime());
        }



        public void getQuoteFromFirestore(Firestore db) throws InterruptedException, ExecutionException {

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

        public void update(Firestore db) throws IOException, InterruptedException, ExecutionException {
            // insert & update
            // Als een document nog niet bestaat wordt het aangemaakt.
            // Als een document al bestaat wordt het aagepast.

            HashMap<String, String> quote = getSomethingToInsert();

            ApiFuture<WriteResult> future = db.collection("sampleData")
                    .document("my_new_document")
                    .set(quote);

            System.out.println("Successfully updated at: "
                    + future.get().getUpdateTime());
        }

        public HashMap<String, String> getSomethingToInsert() throws IOException
        {
            HashMap<String, String> quoteHashMap = new HashMap<String, String>();
            quoteHashMap.put("Gandhi", "Hallo iedereen");

            return quoteHashMap;
        }

    }

