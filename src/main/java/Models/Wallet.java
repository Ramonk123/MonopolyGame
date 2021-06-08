package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Wallet implements Model {
    private int balance;

    public Wallet() {
        balance = 1500;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public void addBalance(int value) {
        this.balance += value;
    }

    public void subtractBalance(int value) {
        this.balance -= value;
    }
}
