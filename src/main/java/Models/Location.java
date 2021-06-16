package Models;

import Monopoly.Identifiable;
import Monopoly.UUID;

/**
 * Abstract model that is a blueprint for all locations.
 */
public abstract class Location implements Model, Position, Action, Nameable, Identifiable {
    private UUID id;
    private String name;
    private int position;
    private Set set;

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
