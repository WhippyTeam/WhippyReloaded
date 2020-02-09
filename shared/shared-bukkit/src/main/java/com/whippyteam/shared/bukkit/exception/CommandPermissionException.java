package com.whippyteam.shared.bukkit.exception;

public class CommandPermissionException extends CommandExecutionException {

    public CommandPermissionException() {
        super();
    }

    public CommandPermissionException(String message) {
        super();
    }

    public CommandPermissionException(Throwable throwable) {
        super(throwable);
    }

    public CommandPermissionException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
