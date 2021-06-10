package Controllers;

import Firestore.FirestoreFormattable;
import Models.Player;
import Monopoly.UUID;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlayerController implements Controller, FirestoreFormattable {

    private Players clientPlayer;
    private ArrayList<Player> players = new ArrayList<>();

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
}
