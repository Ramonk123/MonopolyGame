package Models;

import ObserveablePattern.Observer;
import Views.MainMenuSubject;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.application.Platform;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Model, MainMenuSubject, Observer<DocumentSnapshot> {
    private List<Observer<MainMenuSubject>> observers = new ArrayList<>();

    public void quitGame() {
        System.exit(1);
    }

    @Override
    public void update(final DocumentSnapshot state) {
        // do updates
        for (Observer<MainMenuSubject> o : observers) {
            o.update(this);
        }
    }

    @Override
    public void registerObserver(Observer<MainMenuSubject> o) {
        observers.add(o);
    }

    @Override
    public void unregisterObserver(Observer<MainMenuSubject> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer<MainMenuSubject> o : observers) {
            o.update(this);
        }
    }
}
