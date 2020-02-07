package com.whippyteam.shared.command;

import java.lang.reflect.Method;

public interface Command {

    String[] getNames();

    String getPrimaryName();

    String getDescription();

    boolean isUserOnly();

    String[] getPermissions();

    int getMinArgs();

    int getMaxArgs();

    Method getCommandMethod();

    Object getCommandObject();

    void setNames(String[] name);

    void setPrimaryName(String name);

    void setDescription(String description);

    void setUserOnly(boolean userOnly);

    void setPermissions(String[] permissions);

    void setMinArgs(int minArgs);

    void setMaxArgs(int maxArgs);

}
