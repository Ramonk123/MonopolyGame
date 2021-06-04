package Models;

import ObserveablePattern.Observer;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.List;

public class Player implements Model, Position {
    private String name;
    private String pawn_icon;
    private Wallet wallet;
    private int position;
    private List<View> views;

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void registerObserver(View v) {
        views.add(v);
    }

    @Override
    public void unregisterObserver(View v) {
        views.remove(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        for (View v : views) {
            v.update(ds);
        }
    }
}
