package Models;

import Controllers.*;
import Exceptions.TransactionException;
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
public class Player implements Observer<DocumentSnapshot>, BoardSubject, Position, Nameable, Identifiable, FirestoreFormattable, Payer, Receiver {
    private final ArrayList<Observer<BoardSubject>> observers = new ArrayList<>();

    private String name;
    private final Wallet wallet = new Wallet();
    private long oldPosition;
    private long currentPosition;
    private boolean inJail;
    private final Players playersEnum;

    public Player(Players playersEnum, String name) {
        this.playersEnum = playersEnum;
        this.name = name;
        this.currentPosition = 0;
        this.oldPosition = 0;
        registerObserver(new BoardView());

    }

    @Override
    public long getPosition() {
        return currentPosition;
    }

    public long getOldPosition() {
        return oldPosition;
    }

    public void setOldPosition(long position) {
        oldPosition = position;
    }

    public void setPosition(long position) {
        System.out.println(position + "playerpositionhierzo");
        setOldPosition(this.currentPosition);
        this.currentPosition = position;
        System.out.println(currentPosition + "currentpositionhier");
    }

    /**
     * Moves the player on the back-end.
     * @param amountThrown The amount of eyes the player has thrown.
     */
    public void movePlayer(long amountThrown) {
        LocationController locationController = ((PlayerController) ControllerRegistry.get(PlayerController.class)).getLocationController();
        long amountOfLocations = locationController.getLocationArray().size();
        long newPosition = getPosition() + amountThrown;
        if(newPosition >= amountOfLocations) {
            newPosition -= amountOfLocations;

            TransactionController transactionController = (TransactionController) ControllerRegistry.get(TransactionController.class);
            try {
                transactionController.addBalance(playersEnum, 200);
            } catch (TransactionException transactionException) {
                transactionException.printStackTrace();
            }
        }
        setPosition(newPosition);
    }

    public boolean wentPastGo() {
        if(currentPosition == 0) {
            return false;
        }
        return oldPosition > currentPosition;
    }

    @Override
    public String getName() {
        return name;
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
        map.put("oldPosition", getOldPosition());
        map.put("currentPosition", getPosition());
        map.put("wallet", wallet.getBalance());
        return map;
    }

    @Override
    public void update(DocumentSnapshot state) {
        Map<String, Object> playerMap = (Map<String, Object>) state.get("players");
        System.out.println(playerMap);
        for (Map.Entry<String, Object> entry : playerMap.entrySet()) {
            if (!UUID.compare(entry.getKey(), playersEnum)) {
                continue;
            }
            Map<String, Object> map = (Map<String, Object>) entry.getValue();
            inJail = (boolean) map.get("inJail");
            name = (String) map.get("name");
            currentPosition = (long) map.get("currentPosition");
            oldPosition = (long) map.get("oldPosition");
            wallet.setBalance((int) (long) map.get("wallet"));
        }
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
