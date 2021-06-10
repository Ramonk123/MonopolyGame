package Models;

import Controllers.Players;
import Firestore.FirestoreFormattable;
import Monopoly.Identifiable;
import Monopoly.UUID;
import ObserveablePattern.Observer;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player implements Model, Observer<DocumentSnapshot>, Position, Nameable, Identifiable, FirestoreFormattable {

    private String name;
    private String pawnIcon;
    private Wallet wallet;
    private int position;
    private boolean inJail;
    private Players playersEnum;

    public Player(Players playersEnum, String name) {
        this.playersEnum = playersEnum;
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
        return playersEnum.getId();
    }

    public Players getPlayersEnum() {
        return playersEnum;
    }

    @Override
    public Object getFirestoreFormat() {
        Map<String, Object> map = new HashMap<>();
        map.put("inJail", inJail);
        map.put("name", getName());
        map.put("pawnIcon", "car.jpg");
        map.put("position", getPosition());
        map.put("wallet", wallet.getBalance());
        return map;
    }

    @Override
    public void update(DocumentSnapshot state) {
        // do some updates mane
    }
}
