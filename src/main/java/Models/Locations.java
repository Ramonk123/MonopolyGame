package Models;

import Monopoly.Identifiable;
import Monopoly.UUID;

public enum Locations implements Identifiable {
    Jail(new UUID("LOCATION-JAIL")),
    Amsterdam(new UUID("LOCATION-AMSTERDAM"));

    private UUID id;

    private Locations(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
