package pl.tymoteuszboba.whippytools.system.i18n;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.tymoteuszboba.whippytools.system.i18n.enums.MessageTarget;

public class CompletedMessage {

    private final String message;
    private String secondMessage;

    private char colorPrefix;

    private MessageTarget target;

    public CompletedMessage(String message, MessageTarget target, char colorPrefix) {
        this.message = message;
        this.colorPrefix = colorPrefix;
        this.target = target;
    }

    public CompletedMessage(String firstValue, String secondValue,
        MessageTarget target, char colorPrefix) {
        this.message = firstValue;
        this.secondMessage = secondValue;
        this.colorPrefix = colorPrefix;
        this.target = target;
    }

    public void to(Player player) {
        if (this.target == MessageTarget.CHAT) {
            player.sendMessage(ChatColor.translateAlternateColorCodes(this.colorPrefix, message));
        } else {
            throw new UnsupportedOperationException("If you want to use something other than "
                + "MessageTarget.CHAT, you should use to(Player, int, int, int) method!");
        }
    }

    public void to(CommandSender sender) {
        if (this.target == MessageTarget.CHAT) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes(this.colorPrefix, message));
        } else {
            throw new UnsupportedOperationException("If you want to use something other than "
                + "MessageTarget.CHAT, you should use to(Player, int, int, int) method!");
        }
    }

    public void to(Player player, int fadeIn, int stay, int fadeOut) {
        switch (this.target) {
            case TITLE:
                player.sendTitle(message, "", fadeIn, stay, fadeOut);
                break;
            case SUBTITLE:
                player.sendTitle("", this.message, fadeIn, stay, fadeOut);
                break;
            case TITLE_AND_SUBTITLE:
                if (secondMessage != null) {
                    player.sendTitle(this.message, this.secondMessage, fadeIn, stay, fadeOut);
                } else {
                    throw new IllegalArgumentException(
                        "For MessageTarget.TITLE_AND_SUBTITLE secondKey cannot be null!");
                }
            default:
                throw new UnsupportedOperationException("If you want to use MessageTarget.CHAT, "
                    + "you should use to(Player) method!");
        }
    }

    public void toAllPlayers() {
        if (this.target == MessageTarget.CHAT) {
            Bukkit.broadcastMessage(
                ChatColor.translateAlternateColorCodes(this.colorPrefix, this.message));
        } else {
            throw new UnsupportedOperationException("If you want to use something other than "
                + "MessageTarget.CHAT, you should use toAllPlayers(int, int, int) method!");
        }
    }

    public void toAllPlayers(int fadeIn, int stay, int fadeOut) {
        switch (this.target) {
            case TITLE:
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.sendTitle(this.message, "", -1, -1, -1);
                });
                break;
            case SUBTITLE:
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.sendTitle("", this.message, -1, -1, -1);
                });
            case TITLE_AND_SUBTITLE:
                if (this.secondMessage != null) {
                    Bukkit.getOnlinePlayers().forEach(player -> player
                        .sendTitle(this.message, this.secondMessage, fadeIn, stay, fadeOut));
                    break;
                } else {
                    throw new IllegalArgumentException(
                        "For MessageTarget.TITLE_AND_SUBTITLE secondKey cannot be null!");
                }
            default:
                throw new UnsupportedOperationException("If you want to use MessageTarget.CHAT, "
                    + "you should use toAllPlayers(Player) method!");
        }
    }

}
