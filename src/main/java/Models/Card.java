package Models;

import Monopoly.Identifiable;
import Monopoly.UUID;
import java.util.function.Consumer;

/**
 * Model which is used by the CardDeck model to create a deck of different kind of cards.
 */
public class Card implements Action, Identifiable {

    private final UUID id;
    private String description;
    private Consumer<Player> action;

    public Card(UUID id, String description, Consumer<Player> action) {
        this.id = id;
        this.description = description;
        this.action = action;
    }

    @Override
    public void action(Player player) {
        this.action.accept(player);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Consumer getAction() {
        return action;
    }

    public void setAction(Consumer action) {
        this.action = action;
    }

    @Override
    public UUID getId() {
        return id;
    }
}
