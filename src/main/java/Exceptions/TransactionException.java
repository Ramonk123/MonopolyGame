package Exceptions;

/**
 * Exception for when things are going wrong with a transaction.
 */
public class TransactionException extends Exception {
    public TransactionException(String errorMessage) {
        super(errorMessage);
    }
}
