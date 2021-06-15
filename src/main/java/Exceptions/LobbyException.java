package Exceptions;

public class LobbyException extends Exception {
    protected final Throwable throwable;
    public LobbyException(String errorMessage, Throwable throwable) {
        super(errorMessage);
        this.throwable = throwable;
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
