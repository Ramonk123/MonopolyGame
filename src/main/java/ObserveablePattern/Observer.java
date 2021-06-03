package ObserveablePattern;

import com.google.cloud.firestore.DocumentSnapshot;

public interface Observer {
    void update(DocumentSnapshot ds);
}
