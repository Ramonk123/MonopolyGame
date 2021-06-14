package Controllers;

import Firestore.FirestoreFormattable;
import Models.Player;
import Monopoly.UUID;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import com.google.cloud.firestore.DocumentSnapshot;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlayerController
        implements
            Controller,
            FirestoreFormattable,
            Subject<DocumentSnapshot>,
            Observer<DocumentSnapshot> {

    private Players clientPlayer;
    private ArrayList<Player> players = new ArrayList<>();
    private DocumentSnapshot documentSnapshot;

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public PlayerController() {

    }

    public Optional<Player> getPlayerByName(String name) {
        Player player = null;
        for(Player p : players) {
            if(player.getName().equals(name)) {
                player = p;
            }
        }
        return Optional.ofNullable(player);
    }

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

    public Player setPlayer(String name) throws Exception {
        int arraySize = players.size();
        Players playerEnum =  Players.getByOrder(arraySize+1)
                .orElseThrow( () -> new Exception("Order out of bounds")
        );
        Player player = new Player(playerEnum, name);
        players.add(player);
        return player;
    }

    public Player setPlayerWithPlayersEnum(String name, Players playersEnum) throws Exception {
        Player player = new Player(playersEnum, name);
        players.add(player);
        return player;
    }

    public void movePlayer(String name, int amountThrown) throws Exception {
        Player player = getPlayerByName(name).orElseThrow(Exception::new);
        player.movePlayer(amountThrown);
    }

    public void teleportTo(Player player, int position) {
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

    @Override
    public void unregisterObserver(Observer<DocumentSnapshot> observer) { }

    protected void updatePlayersSize(Map<String, Object> map) {
        Players playersEnum;
        Optional<Player> player;
        try {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                playersEnum = Players.getByStringUuid(entry.getKey()).orElseThrow(() -> new Exception("invalid PlayersEnum UUID."));
                player = getPlayerByPlayersEnum(playersEnum);
                if (player.isEmpty()) {
                    setPlayer((String) ((Map<String, Object>) entry.getValue()).get("name"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notifyObservers() {
        Map<String, Object> map = (Map<String, Object>) documentSnapshot.get("players");
        if (map.size() > players.size()) {
            updatePlayersSize(map);
        }
        for (Player player : players) {
            player.update(documentSnapshot);
        }
    }

    @Override
    public void update(DocumentSnapshot state) {
        documentSnapshot = state;
        notifyObservers();
    }
}
