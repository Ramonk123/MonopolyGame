package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public abstract class SpecialLocation extends Location {
    private final List<View> observers = new ArrayList<>();

    public SpecialLocation(String name, int position) {
        super(name, position);
    }
}
