package Controllers;

import Monopoly.Identifiable;
import Monopoly.UUID;

import java.util.Optional;

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

    public static Optional<Players> getByOrder(int order) {
        Players playersEnum = null;
        for (Players p : Players.values()) {
            if (p.order() == order) {
                playersEnum = p;
                break;
            }
        }
        return Optional.ofNullable(playersEnum);
    }

    public int order() {
        return this.ordinal() + 1;
    }

    public static Optional<Players> getByStringUuid(String uuid) {
        Players playersEnum = null;
        System.out.println("ENUM ENUM");
        System.out.println(uuid);
        System.out.println("loop");
        for (Players p : Players.values()) {
            System.out.println(p.getId().getId());
            if (UUID.compare(uuid, p)) {
                System.out.println("found");
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
