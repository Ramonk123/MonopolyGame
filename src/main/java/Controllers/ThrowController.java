package Controllers;

import Models.DiceThrower;
import Models.Player;
import Models.Throw;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class ThrowController implements Controller, DiceThrower {

    private Throw currentThrow;

    private ThrowController() {
        currentThrow  = new Throw();
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

    // dit is niet nodig omdat de 3 methods hierboven al alle functionaliteiten van throw publiekelijk maken
    public Throw getCurrentThrow() {
        return currentThrow;
    }
}
