package com.whippyteam.shared.bukkit.command;

import com.whippyteam.shared.command.Command;
import com.whippyteam.shared.command.CommandContent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.bukkit.command.CommandSender;

public class BukkitCommand implements Command {

    private final Method method;
    private final Object commandClassObject;

    private String[] names;
    private String description;
    private boolean userOnly;
    private String[] permissions;

    private int minArgs;
    private int maxArgs;

    private String usage;

    public BukkitCommand(String[] names, String description, int minArgs, int maxArgs, boolean userOnly,
        String[] permissions, String usage, Method method, Object commandClassObject) {

        this.names = names;
        this.description = description;

        this.minArgs = minArgs;
        this.maxArgs = maxArgs;

        this.userOnly = userOnly;
        this.permissions = permissions;

        this.usage = usage;

        this.method = method;
        this.commandClassObject = commandClassObject;
    }

    @Override
    public void executeCommand(CommandSender sender, CommandContent context) throws Throwable {
        if (this.getCommandMethod() == null) {
            return;
        }

        try {
            this.getCommandMethod().setAccessible(true);
            this.getCommandMethod().invoke(this.getCommandObject(), sender, context);
        } catch (InvocationTargetException ex) {
            throw ex.getTargetException();
        }
    }

    public String[] getNames() {
        return names;
    }

    public String getPrimaryName() {
        return names[0];
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

    public String getUsage() {
        return this.usage;
    }

    public Method getCommandMethod() {
        return method;
    }

    public Object getCommandObject() {
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

    public void setUsage(String usage) {
        this.usage = usage;
    }
}
