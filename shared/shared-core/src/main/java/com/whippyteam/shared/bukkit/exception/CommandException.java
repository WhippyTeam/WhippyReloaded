package main.java.com.whippyteam.shared.bukkit.exception;

public class CommandException extends RuntimeException {

    public CommandException() {
        super();
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(Throwable throwable) {
        super(throwable);
    }

    public CommandException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
