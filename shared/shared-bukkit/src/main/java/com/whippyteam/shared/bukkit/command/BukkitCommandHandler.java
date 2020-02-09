package com.whippyteam.shared.bukkit.command;

import com.whippyteam.shared.bukkit.exception.CommandArgumentException;
import com.whippyteam.shared.bukkit.exception.CommandPermissionException;
import com.whippyteam.shared.bukkit.exception.CommandUnpermittedSenderException;
import com.whippyteam.shared.command.CommandContent;
import java.util.Arrays;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BukkitCommandHandler implements CommandExecutor {

    private final BukkitCommandLoader loader;

    public BukkitCommandHandler(BukkitCommandLoader loader) {
        this.loader = loader;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> params = Arrays.asList(args);
        CommandContent content = new CommandContentImpl(params, label,
            this.loader.getCommand(label));

        return this.execute(sender, content);
    }

    private boolean execute(CommandSender sender, CommandContent content) {
        try {
            if (content.getCommand().isUserOnly() && !(sender instanceof Player)) {
                throw new CommandUnpermittedSenderException("Sender must be a player!");
            }

            if (content.getCommand().getPermissions() != null) {
                for (String permission : content.getCommand().getPermissions()) {
                    if (permission.equalsIgnoreCase("")) {
                        continue;
                    }

                    if (!sender.hasPermission(permission)) {
                        throw new CommandPermissionException(
                            "Sender must have a permission to execute the command!");
                    }
                }
            }

            if (content.getCommand().getMinArgs() > content.getParamsLength()
                || content.getCommand().getMaxArgs() < content.getParamsLength()) {
                throw new CommandArgumentException(
                    "Sender didn't provided proper amount of arguments!");
            }

            try {
                content.getCommand().executeCommand(sender, content);
                return true;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

        } catch (CommandUnpermittedSenderException exception) {
            String message = ChatColor.RED + "This command can be executed only by a Player!";
            sender.sendMessage(message);
        } catch (CommandPermissionException exception) {
            String message =
                ChatColor.RED + "You don't have enough permission to use this command!";
            sender.sendMessage(message);
        } catch (CommandArgumentException exception) {
            String message = ChatColor.translateAlternateColorCodes('&', "&7Usage: &e" +
                content.getCommand().getUsage());
            sender.sendMessage(message);
        } catch (NumberFormatException ex) {
            sender.sendMessage(ChatColor.RED + "You need to write a number in the argument!");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Usage: &e" +
                content.getCommand().getUsage()));
        } catch (Throwable ex) {
            sender.sendMessage(
                ChatColor.RED
                    + "Critical error detected - please, send this message to The WhippyTeam developers!");
            sender.sendMessage(ChatColor.RED + ex.getLocalizedMessage());
            ex.printStackTrace();
        }
        return false;
    }
}
