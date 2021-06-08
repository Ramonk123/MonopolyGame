package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class OwnableLocation extends Location {
    private final int price;
    private Player owner = null;
    private Set set;

    public OwnableLocation(Locations locationEnum, String name, Set set, int position, int price) {
        super(locationEnum, name, position);
        this.price = price;
        this.set = set;
    }

    public Optional<Player> getOwner() {
        return Optional.ofNullable(owner);
    }

    public int getPrice() {
        return price;
    }



}
