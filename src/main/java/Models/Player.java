package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Player implements Model, Position, Nameable {
    private final List<View> observers = new ArrayList<>();

    private String name;
    private String pawnIcon;
    private Wallet wallet;
    private int position;

    @Override
    public void registerObserver(View v) {
        observers.add(v);
    }

    @Override
    public void unregisterObserver(View v) {
        observers.remove(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        for(View v : observers) {
            v.update(ds);
        }
    }

    @Override
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getPawnIcon() {
        return pawn_icon;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
