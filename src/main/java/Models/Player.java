package Models;

import Controllers.ControllerRegistry;
import Controllers.LocationController;
import Controllers.PlayerController;
import Controllers.Players;
import Firestore.FirestoreFormattable;
import Monopoly.Identifiable;
import Monopoly.UUID;
import ObserveablePattern.Observer;
import Views.BoardSubject;
import Views.BoardView;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model which contains everything about the player.
 */
public class Player implements Model, Observer<DocumentSnapshot>, BoardSubject, Position, Nameable, Identifiable, FirestoreFormattable, Payer, Receiver {
    ArrayList<Observer<BoardSubject>> observers = new ArrayList<>();

    private String name;
    private String pawnIcon;
    private Wallet wallet = new Wallet();
    private long oldPosition;
    private long currentPosition;
    private boolean inJail;
    private Players playersEnum;

    public Player(Players playersEnum, String name) {
        this.playersEnum = playersEnum;
        this.name = name;;
        registerObserver(new BoardView());
    }

    @Override
    public long getPosition() {
        return currentPosition;
    }

    public long getOldPosition() {
        return oldPosition;
    }

    private void setOldPosition(long position) {
        oldPosition = position;
    }

    public void setPosition(long position) {
        setOldPosition(this.currentPosition);
        this.currentPosition = position;
    }

    public void movePlayer(long amountThrown) {
        LocationController locationController = ((PlayerController) ControllerRegistry.get(PlayerController.class)).getLocationController();
        long amountOfLocations = locationController.getLocationArray().size();
        long newPosition = getPosition() + amountThrown;
        if(newPosition >= amountOfLocations) {
            newPosition -= amountOfLocations;
            // This means the player is standing on or went over Start/Go.
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

    @Override
    public void subtractBalance(int value) {
        Payer wallet = this.wallet;
        wallet.subtractBalance(value);
    }

    @Override
    public int getBalance() {
        Payer wallet = this.wallet;
        return wallet.getBalance();
    }

    @Override
    public boolean checkBalance(int value) {
        Payer wallet = this.wallet;
        return wallet.checkBalance(value);
    }

    @Override
    public void addBalance(int value) {
        Receiver wallet = this.wallet;
        wallet.addBalance(value);
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
