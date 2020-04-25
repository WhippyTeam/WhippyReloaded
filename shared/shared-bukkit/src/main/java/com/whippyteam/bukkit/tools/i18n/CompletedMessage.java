package com.whippyteam.bukkit.tools.i18n;

import com.whippyteam.api.i18n.enums.MessageTarget;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CompletedMessage {

    private final String[] messageList;
    private final char colorPrefix;
    private final MessageTarget target;

    public CompletedMessage(String message, MessageTarget target, char colorPrefix) {
        this.messageList = new String[1];
        this.messageList[0] = message;
        this.colorPrefix = colorPrefix;
        this.target = target;
    }

    public CompletedMessage(String[] keys, MessageTarget target, char colorPrefix) {
        this.messageList = keys;
        this.colorPrefix = colorPrefix;
        this.target = target;
    }

    public void to(Player player) {
        if (this.target == MessageTarget.CHAT) {
            player.sendMessage(
                ChatColor.translateAlternateColorCodes(this.colorPrefix, this.messageList[0]));
        } else {
            throw new UnsupportedOperationException("If you want to use something other than "
                + "MessageTarget.CHAT, you should use to(Player, int, int, int) method!");
        }
    }

    public void to(CommandSender sender) {
        if (this.target == MessageTarget.CHAT) {
            sender.sendMessage(
                ChatColor.translateAlternateColorCodes(this.colorPrefix, this.messageList[0]));
        } else {
            throw new UnsupportedOperationException("If you want to use something other than "
                + "MessageTarget.CHAT, you should use to(Player, int, int, int) method!");
        }
    }

    public void to(Player player, int fadeIn, int stay, int fadeOut) {
        switch (this.target) {
            case TITLE:
                player.sendTitle(this.messageList[0], "", fadeIn, stay, fadeOut);
                break;
            case SUBTITLE:
                player.sendTitle("", this.messageList[0], fadeIn, stay, fadeOut);
                break;
            case TITLE_AND_SUBTITLE:
                player.sendTitle(this.messageList[0], this.messageList[1], fadeIn, stay, fadeOut);
            default:
                throw new UnsupportedOperationException("If you want to use MessageTarget.CHAT, "
                    + "you should use to(Player) method!");
        }
    }

    public void toAllPlayers() {
        if (this.target == MessageTarget.CHAT) {
            Bukkit.broadcastMessage(
                ChatColor.translateAlternateColorCodes(this.colorPrefix, this.messageList[0]));
        } else {
            throw new UnsupportedOperationException("If you want to use something other than "
                + "MessageTarget.CHAT, you should use toAllPlayers(int, int, int) method!");
        }
    }

    public void toAllPlayers(int fadeIn, int stay, int fadeOut) {
        switch (this.target) {
            case TITLE:
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.sendTitle(this.messageList[0], "", -1, -1, -1);
                });
                break;
            case SUBTITLE:
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.sendTitle("", this.messageList[0], -1, -1, -1);
                });
            case TITLE_AND_SUBTITLE:
                Bukkit.getOnlinePlayers().forEach(player -> player
                    .sendTitle(this.messageList[0], this.messageList[1], fadeIn, stay, fadeOut));
            default:
                throw new UnsupportedOperationException("If you want to use MessageTarget.CHAT, "
                    + "you should use toAllPlayers(Player) method!");
        }
    }

}
