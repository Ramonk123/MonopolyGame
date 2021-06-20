package ObserveablePattern;

public interface Observer<T> {
    void update(final T state);
}
