package Controllers;

import Models.DiceThrower;
import Models.Throw;

/**
 * Controller for the Throw and Dice model.
 */
public class ThrowController implements DiceThrower, Controller {

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

    public int getEyesDiceOne() {
        return currentThrow.getEyesDiceOne();
    }

    public int getEyesDiceTwo() {
        return currentThrow.getEyesDiceTwo();
    }
}
