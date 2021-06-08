package Controllers;

import Models.Board;
import Models.Player;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class PlayerController implements Controller {

    private ArrayList<Player> players = new ArrayList<>();

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

    public Player getPlayerByName(String name) {
        for(Player player : players) {
            if(player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public boolean nameExists(String name) {
        if(getPlayerByName(name) == null) {
            return true;
        }
        return false;
    }

    public void setPlayer(String name) {
        Player player = new Player(name);
        players.add(player);
    }

}
