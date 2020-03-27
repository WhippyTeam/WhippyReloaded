package com.whippyteam.bukkit.tools.command;

import com.whippyteam.api.command.Command;
import com.whippyteam.api.command.CommandInfo;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicComparator;
import org.bukkit.help.IndexHelpTopic;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Loads all commands with suitable method.
 * Basic concept: BukkitCommands by TheMolkaPL
 *
 * @author IceMeltt
 * @version 1.0
 */
public class BukkitCommandLoader {

    private CommandMap commandMap;
    private Map<String, Command> customCommandMap;
    private Set<HelpTopic> helpTopicSet;

    private Plugin plugin;

    public BukkitCommandLoader(Plugin plugin) {
        this.plugin = plugin;

        try {
            this.initializeCommandMap();
        } catch (ReflectiveOperationException exception) {
            exception.printStackTrace();
        }

        this.customCommandMap = new HashMap<>();
        this.helpTopicSet = new TreeSet<>(HelpTopicComparator.helpTopicComparatorInstance());
    }

    public org.bukkit.command.Command prepareCommand(Command command) {
        List<String> aliases = new ArrayList<>();
        for (int i = 1; i < command.getNames().length; i++) {
            aliases.add(command.getNames()[i]);
        }

        org.bukkit.command.Command performer = new CommandPerformer(command.getPrimaryName());
        performer.setAliases(aliases);
        performer.setDescription(command.getDescription());
        performer.setUsage(command.getUsage());
        return performer;
    }

    public void registerCommand(Command command) {
        org.bukkit.command.Command preparedCommand = this.prepareCommand(command);
        this.commandMap.register(this.getPluginName(), preparedCommand);
        for (String name : command.getNames()) {
            this.customCommandMap.put(name, command);
            this.customCommandMap.put(this.getPluginName().toLowerCase() + ":" + name, command);
        }
    }

    public void registerCommand(Class clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            method.setAccessible(true);

            CommandInfo annotation = method.getDeclaredAnnotation(CommandInfo.class);
            if (annotation != null) {
                this.registerCommand(method, null, annotation);
            }
        }
    }

    public void registerCommand(Class... classes) {
        for (Class clazz : classes) {
            this.registerCommand(clazz);
        }
    }

    public void registerCommand(Object object) {
        for (Method method : object.getClass().getDeclaredMethods()) {
            method.setAccessible(true);

            CommandInfo annotation = method.getDeclaredAnnotation(CommandInfo.class);
            if (annotation != null) {
                this.registerCommand(method, object, annotation);
            }
        }
    }

    public void registerCommand(Object... objects) {
        for (Object object : objects) {
            this.registerCommand(object);
        }
    }

    public void registerCommand(Method method, Object object, CommandInfo info) {
        this.registerCommand(new BukkitCommand(
            info.name(),
            info.description(),
            info.min(),
            info.max(),
            info.userOnly(),
            info.permission(),
            info.usage(),
            method,
            object));
    }

    private IndexHelpTopic createHelpIndex() {
        return new IndexHelpTopic(
            this.getPluginName(),
            "Wszystkie komendy " + this.getPluginName(),
            null,
            this.helpTopicSet
        );
    }

    public Command getCommand(String command) {
        return this.customCommandMap.get(command.toLowerCase());
    }

    public Set<String> getCommandNames() {
        return this.customCommandMap.keySet();
    }

    public List<Command> getCommands() {
        List<Command> commands = new ArrayList<>();

        for (Command command : this.customCommandMap.values()) {
            if (!commands.contains(command)) {
                commands.add(command);
            }
        }

        return commands;
    }

    private String getPluginName() {
        return this.plugin.getName();
    }

    private String getPrefixName() {
        return this.getPluginName().toLowerCase();
    }

    private void initializeCommandMap() throws ReflectiveOperationException {
        if (commandMap != null) {
            return;
        }

        Field field = this.plugin.getServer().getClass().getDeclaredField("commandMap");
        field.setAccessible(true);
        this.commandMap = (CommandMap) field.get(this.plugin.getServer());
    }

    private class CommandPerformer extends org.bukkit.command.Command {

        protected CommandPerformer(String name) {
            super(name);
        }

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            return new BukkitCommandHandler(BukkitCommandLoader.this).onCommand(sender, this, label, args);
        }
    }
}
