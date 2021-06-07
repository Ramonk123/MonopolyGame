package Models;

import java.util.function.Consumer;

public class Card implements Model {

    public Card(cardType title, String description, Consumer<Player> action) {
        this.title = title;
        this.description = description;
        this.action = action;
    }

    public enum cardType{
        CHANCE, COMMUNITY_CHEST
    }

    public cardType getTitle() {
        return title;
    }

    public void setTitle(cardType title) {
        this.title = title;
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

    private cardType title;
    private String description;
    private Consumer action;


}
