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
    private int amountOfDouble;

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
        Map<String, Object> map = (Map<String, Object>) state.get("turn");
        try {
            Players playersEnum = Players.getByStringUuid((String) map.get("activePlayer"))
                    .orElseThrow(() -> new Exception("Player Id doesn't exist."));
            activePlayer = playersEnum;
        } catch (Exception e) {
            e.printStackTrace();
        }
        amountOfDouble = (int) map.get("amountOfDoubles");
    }
}
