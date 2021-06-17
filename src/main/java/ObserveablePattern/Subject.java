package ObserveablePattern;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public interface Subject<T> {
    void registerObserver(Observer<T> observer);
    void notifyObservers();
}
