package ObserveablePattern;

import com.google.cloud.firestore.DocumentSnapshot;

public interface Observer<T> {
    void update(final T state);
}
