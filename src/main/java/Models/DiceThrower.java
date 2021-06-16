package Models;

/**
 * Interface that is used by Dice, Throw and ThrowController to make a loose coupling possible and shared methods.
 */
public interface DiceThrower {
    void throwDice();
    boolean isDouble();
    int getTotalEyes();
}
