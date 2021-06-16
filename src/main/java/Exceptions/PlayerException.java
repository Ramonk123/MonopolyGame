package Exceptions;

/**
 * Exception for when things are going wrong with the player.
 */
public class PlayerException extends Exception {
    public PlayerException(String errorMessage) {
        super(errorMessage);
    }
}
