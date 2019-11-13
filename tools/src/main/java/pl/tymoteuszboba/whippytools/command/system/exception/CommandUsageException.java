package pl.tymoteuszboba.whippytools.command.system.exception;

import pl.tymoteuszboba.whippytools.command.system.exception.CommandException;

public class CommandUsageException extends CommandException {
    public CommandUsageException() {
        super();
    }

    public CommandUsageException(String message) {
        super(message);
    }
}
