package com.whippyteam.shared.bukkit.exception;

public class CommandExecutionException extends CommandException {

    public CommandExecutionException() {
        super();
    }

    public CommandExecutionException(String message) {
        super();
    }

    public CommandExecutionException(Throwable throwable) {
        super(throwable);
    }

    public CommandExecutionException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
