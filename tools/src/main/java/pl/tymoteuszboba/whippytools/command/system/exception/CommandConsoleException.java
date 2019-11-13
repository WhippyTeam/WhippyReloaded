package pl.tymoteuszboba.whippytools.command.system.exception;

public class CommandConsoleException extends CommandException {
    private boolean console;

    public CommandConsoleException() {
        this(false);
    }

    public CommandConsoleException(boolean console) {
        this.console = console;
    }

    public boolean isConsoleLevel() {
        return this.console;
    }
}
