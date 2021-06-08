package Models;

import java.util.Optional;

// still needs work
public class Turn implements Model, DiceThrower {
    private Player activePlayer;
    private int amountOfDouble;
    private Throw currentThrow;


    public Turn() {
        this.activePlayer = null;
        this.amountOfDouble = 0;
        this.currentThrow = new Throw();
    }

    public void setCurrentPlayer(Player player) {
        this.activePlayer = player;
    }

    public Optional<Player> getCurrentPlayer()
    {
        return Optional.ofNullable(activePlayer);
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
