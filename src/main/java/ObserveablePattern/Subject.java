package ObserveablePattern;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public interface Subject<T> {
    void registerObserver(Observer<T> o);
    void unregisterObserver(Observer<T> o);
    void notifyObservers();
}
