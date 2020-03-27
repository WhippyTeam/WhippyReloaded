package com.whippyteam.bukkit.tools.exception.command;

public class CommandUnpermittedSenderException extends CommandExecutionException {

    public CommandUnpermittedSenderException() {
        super();
    }

    public CommandUnpermittedSenderException(String message) {
        super();
    }

    public CommandUnpermittedSenderException(Throwable throwable) {
        super(throwable);
    }

    public CommandUnpermittedSenderException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
