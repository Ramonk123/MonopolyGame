package Models;

import java.util.function.Consumer;

public class Card implements Model {

    public Card(String title, String description, Consumer action) {
        this.title = title;
        this.description = description;
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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

    private String title;
    private String description;
    private Consumer action;


}
