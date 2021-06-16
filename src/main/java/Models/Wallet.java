package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Wallet implements Model, Payer, Receiver {
    private int balance = 1500;

    public Wallet() {

    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void addBalance(int value) {
        this.balance += value;
    }

    @Override
    public void subtractBalance(int value) {
        this.balance -= value;
    }

    @Override
    public boolean checkBalance(int value){
        boolean sufficientBalance = getBalance() >= value;
        return sufficientBalance;
    }
}
