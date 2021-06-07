package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Dice implements Model, DiceThrower {
    private int eyes;

    @Override
    public void throwDice() {
        Random rand = new Random();
        int n = rand.nextInt(6);
        eyes = n + 1;
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
