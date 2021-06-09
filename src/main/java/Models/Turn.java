package Models;

import Controllers.ControllerRegistry;
import Controllers.MainMenuController;
import Controllers.ThrowController;
import Controllers.TurnController;

import javax.annotation.Nullable;
import java.util.Optional;

// still needs work
public class Turn implements Model {
    private Player activePlayer;
    private int amountOfDouble;

    public Turn() {
        this.activePlayer = null;
        this.amountOfDouble = 0;
    }

    public void setCurrentPlayer(Player player) {
        this.activePlayer = player;
    }

    public Optional<Player> getCurrentPlayer()
    {
        return Optional.ofNullable(activePlayer);
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
}
