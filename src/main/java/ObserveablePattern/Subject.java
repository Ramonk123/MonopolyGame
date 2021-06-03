package ObserveablePattern;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public interface Subject {
    void registerObserver(View v);
    void unregisterObserver(View v);
    void notifyObservers(DocumentSnapshot ds);
}
