package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Player implements Model, Position, Nameable {

    private String name;
    private String pawnIcon;
    private Wallet wallet;
    private int position;

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
        return pawnIcon;
    }

    public Wallet getWallet() {
        return wallet;
    }
}
