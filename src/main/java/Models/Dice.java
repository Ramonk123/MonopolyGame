package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model that creates random number and is used by the Throw model.
 */
public class Dice implements Model, DiceThrower {
    private int eyes = 0;

    @Override
    public void throwDice() {
        Random random = new Random();
        eyes = random.nextInt(6) + 1;
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
