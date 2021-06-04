package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Throw implements Model, DiceThrower {
    private final List<View> observers = new ArrayList<>();
    private Dice dice_one;
    private Dice dice_two;

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
        dice_one.throwDice();
        dice_two.throwDice();
    }

    @Override
    public boolean isDouble() {
        if (dice_one.getTotalEyes() == dice_two.getTotalEyes()) {
            return true;
        }
        return false;
    }

    @Override
    public int getTotalEyes() {
        return (dice_one.getTotalEyes() + dice_two.getTotalEyes());
    }
}
