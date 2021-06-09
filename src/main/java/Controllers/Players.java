package Controllers;

import Monopoly.Identifiable;
import Monopoly.UUID;

public enum Players implements Identifiable {
    PLAYER_ONE(new UUID("PLAYER-ONE")),
    PLAYER_TWO(new UUID("PLAYER-TWO")),
    PLAYER_THREE(new UUID("PLAYER-THREE")),
    PLAYER_FOUR(new UUID("PLAYER-FOUR")),
    PLAYER_FIVE(new UUID("PLAYER-FIVE")),
    PLAYER_SIX(new UUID("PLAYER-SIX")),
    PLAYER_SEVEN(new UUID("PLAYER-SEVEN")),
    PLAYER_EIGHT(new UUID("PLAYER-EIGHT"));

    private final UUID id;

    Players(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
