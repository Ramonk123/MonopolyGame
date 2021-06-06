package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.application.Platform;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Model {
    private final List<View> observers = new ArrayList<>();

    @Override
    public void registerObserver(View v) {
        observers.add(v);
    }

    @Override
    public void unregisterObserver(View v) {
        observers.remove(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        for(View v : observers) {
            v.update(ds);
        }
    }

    public void quitGame() {
        System.exit(1);
    }

}
