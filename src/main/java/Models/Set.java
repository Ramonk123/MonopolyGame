package Models;

public enum Set {
    BROWN(2),
    BLUE(2),
    GREEN(4),
    UTILITY(2),
    TRAINSTATION(4),
    NONE(0);

    private int amount;

    private Set(int amount) {
        this.amount = amount;
    }
}
