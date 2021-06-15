package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Throw implements Model, DiceThrower {
    private final List<View> observers = new ArrayList<>();
    private Dice diceOne = new Dice();
    private Dice diceTwo = new Dice();


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
