package Models;

public enum Set {
    BLUE(2),
    GREEN(4),
    UTILITY(2),
    STATION(4),
    NONE(0);

    private int amount;

    private Set(int amount) {
        this.amount = amount;
    }
}
