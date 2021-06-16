package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Abstract model for all ownable locations.
 */
public abstract class OwnableLocation extends Location {
    private final int price;
    private Player owner = null;
    private boolean hasMortgage = false;

    public OwnableLocation(Locations locationEnum, String name, Set set, int position, int price) {
        super(locationEnum, name, set, position);
        this.price = price;
    }

    public Optional<Player> getOwner() {
        return Optional.ofNullable(owner);
    }

    public int getPrice() {
        return price;
    }

    public boolean getMortgage() {
        return hasMortgage;
    }
}
