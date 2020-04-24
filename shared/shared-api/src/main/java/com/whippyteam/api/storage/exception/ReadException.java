package com.whippyteam.api.storage.exception;

public class ReadException extends StorageException {

    private String transaction;

    public ReadException() {
        super();
    }

    public ReadException(String message) {
        super(message);
    }

    public ReadException(String message, String transaction) {
        super(message);
        this.transaction = transaction;
    }

    public ReadException(Throwable cause) {
        super(cause);
    }

    public ReadException(Throwable cause, String transaction) {
        super(cause);
        this.transaction = transaction;
    }

    public String getTransaction() {
        return this.transaction;
    }

}
