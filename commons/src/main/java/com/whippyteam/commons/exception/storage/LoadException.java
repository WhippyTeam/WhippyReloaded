package com.whippyteam.commons.exception.storage;

public class LoadException extends ReadException {

    public LoadException() {
    }

    public LoadException(String message) {
        super(message);
    }

    public LoadException(Throwable cause) {
        super(cause);
    }
}
