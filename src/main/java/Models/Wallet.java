package Models;

import ObserveablePattern.Observer;
import Views.BoardSubject;
import Views.BoardView;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for the Wallet of a Player.
 * Wallet has a Payer interface and a Receiver interface implemented to control what can and can't be used.
 */
public class Wallet implements Model, Payer, Receiver, BoardSubject {
    private ArrayList<Observer<BoardSubject>> observers = new ArrayList<>();

    private int balance = 1500;

    public Wallet() {
        registerObserver(new BoardView());
    }

    public void setBalance(int balance) {
        this.balance = balance;
        //notifyObservers();
    }

    @Override
    public int getBalance() {
        return balance;
    }

    @Override
    public void addBalance(int value) {
        this.balance += value;
        notifyObservers();
    }

    @Override
    public void subtractBalance(int value) {
        this.balance -= value;
        notifyObservers();
    }

    @Override
    public boolean checkBalance(int value){
        return getBalance() >= value;
    }

    @Override
    public void registerObserver(Observer<BoardSubject> observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        observers.get(0).update(this);
    }

    @Override
    public List<Location> getLocations() {
        return null;
    }

    @Override
    public List<Player> getPlayers() {
        return null;
    }
}
