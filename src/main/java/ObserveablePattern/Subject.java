package ObserveablePattern;

public interface Subject<T> {
    void registerObserver(Observer<T> observer);
    void notifyObservers();
}
