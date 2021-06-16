package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Throw implements Model, DiceThrower {
    private final List<View> observers = new ArrayList<>();
    private final DiceThrower diceOne = new Dice();
    private final DiceThrower diceTwo = new Dice();


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
