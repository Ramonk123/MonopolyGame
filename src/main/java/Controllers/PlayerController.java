package Controllers;

import Models.Board;
import Models.Player;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Optional;

public class PlayerController implements Controller {

    private ArrayList<Player> players = new ArrayList<>();

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public PlayerController() {

    }

    /*@Override
    public void registerObserver(View v) {
        player.registerObserver(v);
    }

    @Override
    public void unregisterObserver(View v) {
        player.unregisterObserver(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        player.notifyObservers(ds);
    }*/

    public Optional<Player> getPlayerByName(String name) {
        Player p = null;
        for(Player player : players) {
            if(player.getName().equals(name)) {
                p = player;
            }
        }
        return Optional.ofNullable(p);
    }

    public boolean nameExists(String name) {
        return getPlayerByName(name).isPresent();
    }

    public void setPlayer(String name) {
        Player player = new Player(name);
        players.add(player);
    }

    public void movePlayer(String name, int amountThrown) throws Exception {
        Player player = getPlayerByName(name).orElseThrow(Exception::new);
        player.movePlayer(amountThrown);
    }

}
