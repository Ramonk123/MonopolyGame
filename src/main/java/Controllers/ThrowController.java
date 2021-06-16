package Controllers;

import Models.DiceThrower;
import Models.Player;
import Models.Throw;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class ThrowController implements Controller, DiceThrower {

    private Throw currentThrow = new Throw();

    public ThrowController() {
    }

    @Override
    public void throwDice() {
        currentThrow.throwDice();
    }

    @Override
    public boolean isDouble() {
        return currentThrow.isDouble();
    }

    @Override
    public int getTotalEyes() {
        return currentThrow.getTotalEyes();
    }

}
