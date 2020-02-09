package com.whippyteam.shared.command;

import java.lang.reflect.Method;
import org.bukkit.command.CommandSender;

public interface Command {

    String[] getNames();

    String getPrimaryName();

    String getDescription();

    boolean isUserOnly();

    String[] getPermissions();

    int getMinArgs();

    int getMaxArgs();

    String getUsage();

    Method getCommandMethod();

    Object getCommandObject();

    void setNames(String[] name);

    void setPrimaryName(String name);

    void setDescription(String description);

    void setUserOnly(boolean userOnly);

    void setPermissions(String[] permissions);

    void setMinArgs(int minArgs);

    void setMaxArgs(int maxArgs);

    void setUsage(String name);

    void executeCommand(CommandSender sender, CommandContent context) throws Throwable;

    interface CommandContent {
        String[] getArgs();

        String getParam(int index);

        String getParam(int index, String def);

        String getParams(int from, int to);

        default int getParamsLength() {
            return this.getArgs().length;
        }

        String getLabel();

        Command getCommand();
    }
}
