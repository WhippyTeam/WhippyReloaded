package com.whippyteam.shared.command;

import java.lang.reflect.Method;

public class Command {

    private final Method method;
    private final Object commandClassObject;

    private String[] names;
    private String description;
    private boolean userOnly;
    private String[] permissions;

    private int minArgs;
    private int maxArgs;

    public Command(String[] names, String description, int minArgs, int maxArgs, boolean userOnly,
        String[] permissions, Method method, Object commandClassObject) {

        this.names = names;
        this.description = description;

        this.minArgs = minArgs;
        this.maxArgs = maxArgs;

        this.userOnly = userOnly;
        this.permissions = permissions;

        this.method = method;
        this.commandClassObject = commandClassObject;
    }

    public String[] getNames() {
        return names;
    }

    public String getDescription() {
        return description;
    }

    public boolean isUserOnly() {
        return userOnly;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public int getMinArgs() {
        return minArgs;
    }

    public int getMaxArgs() {
        return maxArgs;
    }

    public Method getMethod() {
        return method;
    }

    public Object getCommandClassObject() {
        return commandClassObject;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public void setPrimaryName(String name) {
        this.names[0] = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserOnly(boolean userOnly) {
        this.userOnly = userOnly;
    }

    public void setPermissions(String[] permissions) {
        this.permissions = permissions;
    }

    public void setMinArgs(int minArgs) {
        this.minArgs = minArgs;
    }

    public void setMaxArgs(int maxArgs) {
        this.maxArgs = maxArgs;
    }
}
