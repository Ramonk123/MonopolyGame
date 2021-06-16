package Models;

/**
 * Identifier and restrictor for the Player model.
 */
public interface Payer {
    void subtractBalance(int value);
    int getBalance();
    boolean checkBalance(int value);
}
