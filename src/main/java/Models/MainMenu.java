package Models;

import ObserveablePattern.Observer;
import Views.*;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class MainMenu implements Model, MainMenuSubject, Observer<DocumentSnapshot>, HasStage {
    private List<Observer<MainMenuSubject>> observers = new ArrayList<>();

    public MainMenu() {
        registerObserver(new MainMenuView());
    }

    public void quitGame() {
        System.exit(1);
    }

    @Override
    public void update(final DocumentSnapshot state) {
        // do updates
        notifyObservers();
    }

    @Override
    public void registerObserver(Observer<MainMenuSubject> observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer<MainMenuSubject> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer<MainMenuSubject> observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public void setStage(Stage primaryStage) {
        ((MainMenuView) observers.get(0)).setStage(primaryStage);
    }
}
