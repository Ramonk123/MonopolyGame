package Models;

import Controllers.*;
import Firestore.FirestoreFormattable;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// still needs work
public class Turn implements Model, FirestoreFormattable {
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

    private Throw getCurrentThrow() {
        TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);
        return turnController.getCurrentThrow();
    }

    public void throwDice() {
        getCurrentThrow().throwDice();
    }

    public boolean isDouble() {
        return getCurrentThrow().isDouble();
    }

    public int getTotalEyes() {
        return getCurrentThrow().getTotalEyes();
    }

    @Override
    public Object getFirestoreFormat() {
        Map<String, Object> map = new HashMap<>();
        map.put("activePlayer", activePlayer.getId().getId());
        map.put("amountOfDoubles", amountOfDouble);
        map.put("eyesThrown", 0);
        return map;
    }
}
