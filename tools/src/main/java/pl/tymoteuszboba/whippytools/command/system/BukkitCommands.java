package pl.tymoteuszboba.whippytools.command.system;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.help.GenericCommandHelpTopic;
import org.bukkit.help.HelpTopic;
import org.bukkit.help.HelpTopicComparator;
import org.bukkit.help.IndexHelpTopic;
import pl.tymoteuszboba.whippytools.WhippyTools;
import pl.tymoteuszboba.whippytools.command.system.exception.CommandConsoleException;
import pl.tymoteuszboba.whippytools.command.system.exception.CommandException;
import pl.tymoteuszboba.whippytools.command.system.exception.CommandPermissionException;
import pl.tymoteuszboba.whippytools.command.system.exception.CommandUsageException;
import pl.tymoteuszboba.whippytools.system.i18n.MessageBundler;
import pl.tymoteuszboba.whippytools.system.i18n.enums.MessageTarget;

/**
 * BukkitCommands library, originally developed by TheMolkaPL, and partially changed by WhippyTeam.
 * @author TheMolkaPL, WhippyTeam
 */
public class BukkitCommands extends Commands implements CommandExecutor, TabCompleter {
    private final WhippyTools plugin;

    private CommandMap bukkitCommandMap;
    private Set<HelpTopic> helpTopics = new TreeSet<>(HelpTopicComparator.helpTopicComparatorInstance());

    private final ExceptionHandler exceptionHandler;

    private final String tooFewArguments;

    public BukkitCommands(final WhippyTools plugin) {
        this.plugin = plugin;

        try {
            this.bukkitCommandMap = this.getBukkitCommandMap();
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }

        this.tooFewArguments = plugin.getMessageFile().getString("errors.command.too-few-arguments", "Too few arguments!");
        this.exceptionHandler = new ExceptionHandler(this.plugin);

        this.plugin.getServer().getHelpMap().addTopic(this.createHelpIndex());
    }

    @Override
    public void handleCommand(CommandSender sender, CommandContext context) {
        try {
            if (context.getCommand().isUserOnly() && !(sender instanceof Player)) {
                throw new CommandConsoleException(true);
            } else if (context.getCommand().hasPermission() && !sender.hasPermission(context.getCommand().getPermission())) {
                throw new CommandPermissionException(context.getCommand().getPermission());
            } else if (context.getCommand().getMin() > context.getParamsLength()) {
                throw new CommandUsageException(this.tooFewArguments);
            } else {
                context.getCommand().handleCommand(sender, context);
            }
        } catch (CommandConsoleException ex) {
            exceptionHandler.handleException(ex, sender);
        } catch (CommandPermissionException ex) {
            exceptionHandler.handleException(ex, sender);
        } catch (CommandUsageException ex) {
            exceptionHandler.handleException(ex, context, sender);
        } catch (CommandException ex) {
            exceptionHandler.handleCommandException(ex, sender);
        } catch (NumberFormatException ex) {
            exceptionHandler.handleNumberFormatException(sender);
        } catch (Throwable ex) {
            exceptionHandler.handleUnknownException(ex, sender);
        }
    }

    @Override
    public List<String> handleCompleter(CommandSender sender, CommandContext context) {
        try {
            if (context.getCommand().isUserOnly() && !(sender instanceof Player)) {
                throw new CommandConsoleException();
            } else if (context.getCommand().hasPermission() && !sender.hasPermission(context.getCommand().getPermission())) {
                throw new CommandPermissionException(context.getCommand().getPermission());
            } else {
                return context.getCommand().handleCompleter(sender, context);
            }
        } catch (Throwable ex) {
            return null;
        }
    }

    @Override
    public void registerCommand(Command command) {
        super.registerCommand(command);

        this.injectCommand(this.getPrefixName(), command);
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        this.handleCommand(sender, this.getCommand(label), label, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        return this.handleCompleter(sender, this.getCommand(label), label, args);
    }

    public Set<HelpTopic> getHelpTopics() {
        return this.helpTopics;
    }

    private org.bukkit.command.Command createBukkitCommand(Command command) {
        List<String> aliases = new ArrayList<>();
        for (int i = 1; i < command.getName().length; i++) {
            aliases.add(command.getName()[i]);
        }

        org.bukkit.command.Command performer = new CommandPerformer(command.getCommand());
        performer.setAliases(aliases);
        performer.setDescription(command.getDescription());
        performer.setUsage(command.getUsage());
        return performer;
    }

    private IndexHelpTopic createHelpIndex() {
        return new IndexHelpTopic(
                this.getPluginName(),
                MessageBundler.send(this.plugin, "random.commandListIndex").toString(),
                null,
                this.getHelpTopics()
        );
    }

    private CommandMap getBukkitCommandMap() throws ReflectiveOperationException {
        Field field = this.plugin.getServer().getClass().getDeclaredField("commandMap");
        field.setAccessible(true);
        return (CommandMap) field.get(this.plugin.getServer());
    }

    private String getPluginName() {
        return this.plugin.getName();
    }

    private String getPrefixName() {
        return this.getPluginName().toLowerCase();
    }

    private void injectCommand(String prefix, Command command) {
        org.bukkit.command.Command performer = this.createBukkitCommand(command);

        this.bukkitCommandMap.register(prefix, performer);
        this.helpTopics.add(new GenericCommandHelpTopic(performer));
    }

    private class CommandPerformer extends org.bukkit.command.Command {
        CommandPerformer(String name) {
            super(name);
        }

        @Override
        public boolean execute(CommandSender sender, String label, String[] args) {
            return BukkitCommands.this.onCommand(sender, this, label, args);
        }

        @Override
        public List<String> tabComplete(CommandSender sender, String label, String[] args) throws IllegalArgumentException {
            return BukkitCommands.this.onTabComplete(sender, this, label, args);
        }
    }

    private static class ExceptionHandler {

        private WhippyTools plugin;

        private final String gameOperator;
        private final String consoleOperator;

        public ExceptionHandler(final WhippyTools plugin) {
            this.plugin = plugin;
            this.gameOperator = plugin.getMessageFile().getString("game", "game");
            this.consoleOperator = plugin.getMessageFile().getString("console", "console");
        }

        public void handleException(CommandConsoleException exception, CommandSender sender) {
            String level = this.gameOperator;
            if (exception.isConsoleLevel()) {
                level = this.consoleOperator;
            }

            MessageBundler.send(this.plugin, "errors.command.only-from-device")
                .replace("DEVICE", level)
                .target(MessageTarget.CHAT)
                .to(sender);
        }

        public void handleException(CommandPermissionException exception, CommandSender sender) {
            String permission = ".";
            if (exception.getPermission() != null) {
                permission = "'" + exception.getPermission() + "'";
            }

            MessageBundler.send(this.plugin, "errors.command.no-permission")
                .replace("PERMISSION", permission)
                .target(MessageTarget.CHAT)
                .to(sender);
        }

        public void handleException(CommandUsageException exception, CommandContext context,
            CommandSender sender) {
            if (exception.getMessage() != null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', exception.getMessage()));
            }

            sender.sendMessage(context.getCommand().getUsage());
        }

        public void handleCommandException(CommandException exception, CommandSender sender) {
            if (exception.getMessage() != null) {
                sender.sendMessage(exception.getMessage());
            } else {
                MessageBundler.send(this.plugin, "errors.command.unknown-error")
                    .target(MessageTarget.CHAT)
                    .to(sender);
                exception.printStackTrace();
            }
        }

        public void handleNumberFormatException(CommandSender sender) {
            MessageBundler.send(this.plugin, "errors.command.number-instead-of-string")
                .target(MessageTarget.CHAT)
                .to(sender);
        }

        public void handleUnknownException(Throwable exception, CommandSender sender) {
            MessageBundler.send(this.plugin, "errors.command.unknown-error")
                .target(MessageTarget.CHAT)
                .to(sender);

            sender.sendMessage(ChatColor.RED + exception.getLocalizedMessage());
            exception.printStackTrace();
        }
    }
}
