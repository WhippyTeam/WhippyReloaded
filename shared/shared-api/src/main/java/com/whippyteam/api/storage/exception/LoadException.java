package com.whippyteam.api.storage.exception;

public class LoadException extends StorageException {

    public LoadException() {
    }

    public LoadException(String message) {
        super(message);
    }

    public LoadException(Throwable cause) {
        super(cause);
    }
}
