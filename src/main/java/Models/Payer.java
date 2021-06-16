package Models;

public interface Payer {
    void subtractBalance(int value);
    int getBalance();
    boolean checkBalance(int value);
}
