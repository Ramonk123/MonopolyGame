package Models;

import Monopoly.Identifiable;
import Monopoly.UUID;

/**
 * Abstract model that is a blueprint for all locations.
 */
public abstract class Location implements Position, Action, Nameable, Identifiable {
    private final UUID id;
    private final String name;
    private final int position;
    private final Set set;

    public Location(Locations locationEnum, String name, Set set, int position) {
        this.id = locationEnum.getId();
        this.name = name;
        this.set = set;
        this.position = position;
    }

    @Override
    public long getPosition() {
        return position;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public Set getSet() {
        return set;
    }
}
