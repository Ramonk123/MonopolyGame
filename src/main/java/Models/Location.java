package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public abstract class Location implements Model, Position, Action, Nameable {
    private String name;
    private int position;

    public Location(String name, int position) {
        this.name = name;
        this.position = position;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public String getName() {
        return name;
    }
}
