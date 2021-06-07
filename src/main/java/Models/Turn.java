package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// still needs work
public class Turn implements Model, DiceThrower {
    private Player player;
    private int amountOfDouble;
    private Throw currentThrow;

    public Turn() {
        this.player = null;
        this.amountOfDouble = 0;
        this.currentThrow = new Throw();
    }

    public void setCurrentPlayer(Player player) {
        this.player = player;
    }

    public Optional<Player> getCurrentPlayer()
    {
        return Optional.ofNullable(player);
    }

    public Throw getCurrentThrow() {
        return currentThrow;
    }

    @Override
    public void throwDice() {
        currentThrow.throwDice();
    }

    @Override
    public boolean isDouble() {
        return currentThrow.isDouble();
    }

    @Override
    public int getTotalEyes() {
        return currentThrow.getTotalEyes();
    }
}
