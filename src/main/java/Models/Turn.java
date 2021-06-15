package Models;

import Controllers.*;
import Firestore.FirestoreFormattable;
import ObserveablePattern.Observer;
import com.google.cloud.firestore.DocumentSnapshot;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// still needs work
public class Turn implements Model, FirestoreFormattable, Observer<DocumentSnapshot> {
    private Players activePlayer;
    private int amountOfDouble; // Not used so could be deleted I think - Vincent

    public Turn() {
        this.activePlayer = Players.PLAYER_ONE;
        this.amountOfDouble = 0;
    }

    public void setCurrentPlayer(Players players) {
        this.activePlayer = players;
    }

    public Players getCurrentPlayer()
    {
        return activePlayer;
    }

    @Override
    public Object getFirestoreFormat() {
        Map<String, Object> map = new HashMap<>();
        map.put("activePlayer", activePlayer.getId().getId());
        map.put("amountOfDoubles", amountOfDouble);
        map.put("eyesThrown", 0);
        return map;
    }

    @Override
    public void update(DocumentSnapshot state) {
        try {
            Map<String, Object> map = (Map<String, Object>) state.get("turn");
            assert map != null;
            activePlayer = Players.getByStringUuid((String) map.get("activePlayer"))
                    .orElseThrow(() -> new PlayerException("Player Id doesn't exist."));
            amountOfDouble = (int) map.get("amountOfDoubles"); //Shouldn't this cast to (long) instead of (int)? -Vincent
        } catch(PlayerException playerException) {
            playerException.printStackTrace();
        }
    }
}
