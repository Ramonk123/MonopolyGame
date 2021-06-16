package Models;

import Controllers.*;
import Exceptions.PlayerException;
import Firestore.FirestoreFormattable;
import ObserveablePattern.Observer;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

// still needs work
public class Turn implements Model, FirestoreFormattable, Observer<DocumentSnapshot> {
    private Players activePlayer = Players.PLAYER_ONE;
    private int amountOfDouble = 0;
    private long eyesThrown = 0;

    public Turn() {
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

    public void setEyesThrown(long eyesThrown) {
        this.eyesThrown = eyesThrown;
    }

    public void addEyesThrown(long eyesThrown) {
        this.eyesThrown += eyesThrown;
    }

    public long getEyesThrown() {
        return eyesThrown;
    }
}
