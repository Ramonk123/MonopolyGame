package Models;

import Monopoly.Identifiable;
import Monopoly.UUID;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Player implements Model, Position, Nameable, Identifiable {

    private String name;
    private String pawnIcon;
    private Wallet wallet;
    private int position;
    private boolean inJail;
    private UUID id;

    public Player(UUID id, String name) {
        this.id = id;
        this.name = name;
        wallet = new Wallet();
    }

    @Override
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void movePlayer(int amountThrown) {
        //TODO: 40 should not be hard coded
        int newPosition = getPosition() + amountThrown;
        if(newPosition >= 40) {
            newPosition -= 40;
        }
        setPosition(newPosition);
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

    @Override
    public UUID getId() {
        return id;
    }
}
