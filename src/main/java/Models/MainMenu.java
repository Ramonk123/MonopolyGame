package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.application.Platform;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Model {

    public void quitGame() {
        System.exit(1);
    }

}
