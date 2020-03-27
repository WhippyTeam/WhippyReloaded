package com.whippyteam.bukkit.tools.exception.command;

public class CommandArgumentException extends CommandExecutionException {

    public CommandArgumentException() {
        super();
    }

    public CommandArgumentException(String message) {
        super();
    }

    public CommandArgumentException(Throwable throwable) {
        super(throwable);
    }

    public CommandArgumentException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
