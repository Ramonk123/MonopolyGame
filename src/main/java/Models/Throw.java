package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Throw implements Model, DiceThrower {
    private final List<View> observers = new ArrayList<>();
    private Dice diceOne;
    private Dice diceTwo;

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

    @Override
    public void throwDice() {
        diceOne.throwDice();
        diceTwo.throwDice();
    }

    @Override
    public boolean isDouble() {
        if (diceOne.getTotalEyes() == diceTwo.getTotalEyes()) {
            return true;
        }
        return false;
    }

    @Override
    public int getTotalEyes() {
        return (diceOne.getTotalEyes() + diceTwo.getTotalEyes());
    }
}
