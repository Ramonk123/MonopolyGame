package Models;

import Views.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Model which combines 2 Dice models into one result.
 */
public class Throw implements DiceThrower {
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
        return diceOne.getTotalEyes() == diceTwo.getTotalEyes();
    }

    @Override
    public int getTotalEyes() {
        return (diceOne.getTotalEyes() + diceTwo.getTotalEyes());
    }

    public int getEyesDiceOne() {
        return diceOne.getTotalEyes();
    }

    public int getEyesDiceTwo() {
        return diceTwo.getTotalEyes();
    }
}
