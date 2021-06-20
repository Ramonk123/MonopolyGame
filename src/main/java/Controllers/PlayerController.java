package Controllers;

import Firestore.FirestoreFormattable;
import Models.Player;
import Monopoly.UUID;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Resetter.Resettable;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.*;

/**
 * Controller for the Player model, controls everything of the player expect the Wallet.
 */
public class PlayerController
        implements
        FirestoreFormattable,
            Subject<DocumentSnapshot>,
            Observer<DocumentSnapshot>,
            Resettable, Controller {

    private Players clientPlayer;
    private ArrayList<Player> players;
    private DocumentSnapshot documentSnapshot;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public PlayerController() {
        reset();
    }

    public Players getClientPlayersEnum() {
        return clientPlayer;
    }

    //TODO: Should delete this method and use getPlyaerByPlayersEnum
    public Optional<Player> getPlayerByName(String name) {
        Player player = null;
        for(Player p : players) {
            if(player.getName().equals(name)) {
                player = p;
            }
        }
        return Optional.ofNullable(player);
    }

    /**
     * Gets the Player object by its PlayersEnum if it exists.
     * @param playersEnum PlayerEnum is an identifier for the player.
     * @return Returns the Player object or null if the player was not found.
     */
    public Optional<Player> getPlayerByPlayersEnum(Players playersEnum) {
        Player correctPlayer = null;
        for (Player player : players) {
            if (UUID.compare(playersEnum, player)) {
                correctPlayer = player;
                break;
            }
        }
        return Optional.ofNullable(correctPlayer);
    }

    public boolean nameExists(String name) {
        return getPlayerByName(name).isPresent();
    }

    public void setClientPlayersEnum(Players playersEnum) {
        clientPlayer = playersEnum;
    }


    public void removeByPlayersEnum(Players playersEnum) {
        for (Player player : players) {
            if (UUID.compare(playersEnum, player.getPlayersEnum())) {
                players.remove(player);
                break;
            }
        }
    }

    /**
     * Sets a new Player object and returns the Player that is created.
     * @param name Name that the user gave itself.
     * @throws Exception
     */
    public void setPlayer(String name) throws Exception {
        int arraySize = players.size();
        Players playerEnum = Players.getByOrder(arraySize+1)
                .orElseThrow( () -> new Exception("Order out of bounds")
        );
        Player player = new Player(playerEnum, name);
        players.add(player);
    }

    public Player setPlayerWithPlayersEnum(Players playersEnum, String name) {
        Player player = new Player(playersEnum, name);
        players.add(player);
        return player;
    }

    public void movePlayer(String name, int amountThrown) throws Exception {
        Player player = getPlayerByName(name).orElseThrow(Exception::new);
        player.movePlayer(amountThrown);
    }

    public void movePlayerWithPlayerObject(Player player, long steps) {
        player.movePlayer(steps);
    }

    public void teleportTo(Player player, long position) {
        player.setPosition(position);
    }

    @Override
    public Object getFirestoreFormat() {
        Map<String, Object> map = new HashMap<>();
        for (Player player : players) {
            map.put(player.getId().getId(), player.getFirestoreFormat());
        }
        return map;
    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> observer) { }

    protected void updatePlayersSize(Map<String, Object> map) {
        Players playersEnum;
        Optional<Player> player;
        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                playersEnum = Players.getByStringUuid(entry.getKey()).orElseThrow(() -> new Exception("invalid PlayersEnum UUID."));
                player = getPlayerByPlayersEnum(playersEnum);
                if (player.isEmpty()) {
                    setPlayerWithPlayersEnum(playersEnum, (String) ((Map<String, Object>) entry.getValue()).get("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyObservers() {
        for (Player player : players) {
            player.update(documentSnapshot);
        }
    }

    @Override
    public void update(DocumentSnapshot documentSnapshot) {
        this.documentSnapshot = documentSnapshot;
        Map<String, Object> map = (Map<String, Object>) documentSnapshot.get("players");
        assert map != null;

        if (map.size() > players.size()) {
            updatePlayersSize(map);
        }
        notifyObservers();
    }

    @Override
    public void reset() {
        clientPlayer = Players.PLAYER_ONE;
        players = new ArrayList<>();
    }

    public LocationController getLocationController() {
        return (LocationController) ControllerRegistry.get(LocationController.class);
    }

    public void sortPlayerArrayList() {
        ArrayList<Player> newPlayerList = new ArrayList<>();

        for(Player player : players) {
            if (UUID.compare(player, Players.PLAYER_ONE)) {
                newPlayerList.add(0, player);
            } else if (UUID.compare(player, Players.PLAYER_TWO)) {
                newPlayerList.add(1, player);
            } else if (UUID.compare(player, Players.PLAYER_THREE)) {
                newPlayerList.add(2, player);
            } else if (UUID.compare(player, Players.PLAYER_FOUR)) {
                newPlayerList.add(3, player);
            } else if (UUID.compare(player, Players.PLAYER_FIVE)) {
                newPlayerList.add(4, player);
            } else if (UUID.compare(player, Players.PLAYER_SIX)) {
                newPlayerList.add(5, player);
            } else if (UUID.compare(player, Players.PLAYER_SEVEN)) {
                newPlayerList.add(6, player);
            } else if (UUID.compare(player, Players.PLAYER_EIGHT)) {
                newPlayerList.add(7, player);
            }
        }

        players = newPlayerList;
    } //TODO: Very nice sorting algorithm

    public Player getPlayerFromPlayersByEnum(List<Player> list, Players ID) {
        for (Player player : list) {
            if (UUID.compare(player, ID)) {
                return player;
            }
        }
        return null;
    }
}
