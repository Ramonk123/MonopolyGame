package Controllers;

import Models.Player;
import Monopoly.UUID;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Optional;

public class PlayerController implements Controller {

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

    public Optional<Player> getPlayerByUUID(UUID playerUUID) {
        Player player = null;
        return Optional.ofNullable(player);
    }

    public boolean nameExists(String name) {
        return getPlayerByName(name).isPresent();
    }

    public void setPlayer(String name) {
        //Player player = new Player(name);
        //players.add(player);
    }

    public void movePlayer(String name, int amountThrown) throws Exception {
        Player player = getPlayerByName(name).orElseThrow(Exception::new);
        player.movePlayer(amountThrown);
    }

}
