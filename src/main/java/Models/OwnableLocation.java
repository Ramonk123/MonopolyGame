package Models;

import java.util.Optional;

/**
 * Abstract model for all ownable locations.
 */
public abstract class OwnableLocation extends Location {
    private final int price;
    private Optional<Player> owner = Optional.empty();
    private boolean containsMortgage = false;
    private boolean isOwned = false;

    public OwnableLocation(Locations locationEnum, String name, Set set, int position, int price) {
        super(locationEnum, name, set, position);
        this.price = price;
    }

    public Optional<Player> getOwner() {
        return owner;
    }

    public void setOwner(Player player, boolean isOwned) {
        this.owner = Optional.ofNullable(player);
        this.isOwned = isOwned;
    }

    public void setOwnerNull() {
        owner = null;
        isOwned = false;
    }

    public int getPrice() {
        return price;
    }

    public boolean hasMortgage() {
        return containsMortgage;
    }

    public void setMortgage(boolean value) {
        containsMortgage = value;
    }

    public boolean isOwned(){
        return isOwned;
    }

    public int getMortgageValue() {
        return price / 2;
    }
}
