package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Wallet implements Model {
    private int balance;
    private boolean sufficientBalance;

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

    public boolean checkBalance(int value){
        sufficientBalance = getBalance() >= value;
        return sufficientBalance;
    }
}
