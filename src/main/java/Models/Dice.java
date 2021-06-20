package Models;

import java.util.Random;

/**
 * Model that creates random number and is used by the Throw model.
 */
public class Dice implements DiceThrower {
    private int eyes = 0;

    @Override
    public void throwDice() {
        Random random = new Random();
        //eyes = random.nextInt(6) + 1;
        //eyes = 11; //Always land on Chance
        eyes = 3; //Always lands on steenstraat
    }

    @Override
    public boolean isDouble() {
        return false;
    }

    @Override
    public int getTotalEyes() {
        return eyes;
    }
}
