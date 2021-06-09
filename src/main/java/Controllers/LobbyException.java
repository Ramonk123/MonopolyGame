package Controllers;

public class LobbyException extends Exception {
    protected final Throwable throwable;
    public LobbyException(String msg, Throwable e) {
        super(msg);
        this.throwable = e;
    }

    @Override
    public void printStackTrace() {
        if (throwable == null) {
            super.printStackTrace();
            return;
        }
        throwable.printStackTrace();
    }
}
