package Models;

import javax.annotation.Nullable;
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

    //TODO:
    // Alles hieronder hoort hiet niet toch? Indien je deze informatie zou willen hebben moet dat toch via de Dice > Throw > ThrowController > Turn, en vice versa? - Vincent
    // Ik denk dat je gelijk hebt. We moeten volgens mij dan de currentThrow loskoppelen van deze model en alleen activePlayer en amountOfDouble erin laten.
    // Maar in plaats van (Dice > Throw > ThrowController > Turn) zal de TurnController de informatie van ThrowController opvragen. - Kadir
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
