package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SpecialLocation extends Location {
    private final List<View> observers = new ArrayList<>();
    private Consumer<Player> action;

    public SpecialLocation(Locations locationEnum, String name, Set set, int position, Consumer<Player> action) {
        super(locationEnum, name, set, position);
        this.action = action;
    }

    @Override
    public void action(Player player) {
        this.action.accept(player);
    }
}
