package Models;

/**
 * Enum to group different locations together.
 */
public enum Set {
    BROWN(2),
    LIGHTBLUE(3),
    PINK(3),
    ORANGE(3),
    RED(3),
    YELLOW(3),
    GREEN(3),
    DARKBLUE(2),
    UTILITY(2),
    TRAINSTATION(4),
    NONE(12);

    private final int amount;

    Set(int amount) {
        this.amount = amount;
    }
}
