package Controllers;

import Monopoly.Identifiable;
import Monopoly.UUID;

import java.util.Optional;

public enum Players implements Identifiable {
    PLAYER_ONE(new UUID("PLAYER-ONE"), 1),
    PLAYER_TWO(new UUID("PLAYER-TWO"), 2),
    PLAYER_THREE(new UUID("PLAYER-THREE"), 3),
    PLAYER_FOUR(new UUID("PLAYER-FOUR"), 4),
    PLAYER_FIVE(new UUID("PLAYER-FIVE"), 5),
    PLAYER_SIX(new UUID("PLAYER-SIX"), 6),
    PLAYER_SEVEN(new UUID("PLAYER-SEVEN"), 7),
    PLAYER_EIGHT(new UUID("PLAYER-EIGHT"), 8);

    private final UUID id;
    private final int order;

    Players(UUID id, int playerOrder) {
        this.id = id;
        this.order = playerOrder;
    }

    public static Optional<Players> getByOrder(int order) {
        Players playersEnum = null;
        for (Players p : Players.values()) {
            if (p.order == order) {
                playersEnum = p;
                break;
            }
        }
        return Optional.ofNullable(playersEnum);
    }

    @Override
    public UUID getId() {
        return id;
    }
}
