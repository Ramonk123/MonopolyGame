package Controllers;

import Models.DiceThrower;
import Models.Player;
import Models.Throw;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class ThrowController implements Controller, DiceThrower {

    private static ThrowController throwController;
    private Throw currentThrow;

    private ThrowController() {
        currentThrow  = new Throw();
    }

    /*@Override
    public void registerObserver(View v) {
        currentThrow.registerObserver(v);
    }

    @Override
    public void unregisterObserver(View v) {
        currentThrow.unregisterObserver(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        currentThrow.notifyObservers(ds);
    }*/
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
