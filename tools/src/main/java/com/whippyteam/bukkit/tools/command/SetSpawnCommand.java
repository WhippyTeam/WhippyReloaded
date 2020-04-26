package com.whippyteam.bukkit.tools.command;

import com.whippyteam.api.ToolsPlugin;
import com.whippyteam.api.command.Command.CommandContent;
import com.whippyteam.api.command.CommandInfo;
import com.whippyteam.api.i18n.enums.MessageTarget;
import com.whippyteam.bukkit.tools.i18n.MessageBundler;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand {

    private final ToolsPlugin plugin;

    public SetSpawnCommand(final ToolsPlugin plugin) {
        this.plugin = plugin;
    }

    @CommandInfo(
        name = "setspawn",
        description = "Set's spawn on the world.",
        max = 0,
        userOnly = true)
    public void onSetSpawn(CommandSender sender, CommandContent content) {
        Player player = (Player) sender;
        World world = player.getWorld();

        if (content.getParamsLength() == 0) {
            world.setSpawnLocation(player.getLocation());
        }

        MessageBundler.send(this.plugin, "spawn.set-spawn-default")
            .target(MessageTarget.CHAT).to(player);
    }
}
