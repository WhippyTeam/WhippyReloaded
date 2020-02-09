package com.whippyteam.whippytools.storage.exception;

public class TransactionException extends StorageException {

    private String transaction;

    public TransactionException() {
        super();
    }

    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, String transaction) {
        super(message);
        this.transaction = transaction;
    }

    public TransactionException(Throwable cause) {
        super(cause);
    }

    public TransactionException(Throwable cause, String transaction) {
        super(cause);
        this.transaction = transaction;
    }

    public String getTransaction() {
        return this.transaction;
    }

}
