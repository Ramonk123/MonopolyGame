package Exceptions;

/**
 * Exception for when things are going wrong in the lobby.
 */
public class LobbyException extends Exception {
    private final Throwable throwable;
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
