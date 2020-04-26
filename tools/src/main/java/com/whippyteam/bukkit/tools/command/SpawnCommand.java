package com.whippyteam.bukkit.tools.command;

import com.whippyteam.api.ToolsPlugin;
import com.whippyteam.api.command.Command.CommandContent;
import com.whippyteam.api.command.CommandInfo;
import com.whippyteam.api.i18n.enums.MessageTarget;
import com.whippyteam.bukkit.tools.i18n.MessageBundler;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand {

    private final ToolsPlugin plugin;

    public SpawnCommand(final ToolsPlugin plugin) {
        this.plugin = plugin;
    }

    @CommandInfo(
        name = "spawn",
        description = "Teleports the player to spawn.",
        max = 0,
        userOnly = true
    )
    public void onSpawn(CommandSender sender, CommandContent content) {
        Player player = (Player) sender;
        World world = player.getWorld();

        player.teleport(world.getSpawnLocation());

        MessageBundler.send(this.plugin, "spawn.teleporting-to-spawn")
            .target(MessageTarget.CHAT).to(player);
    }

}
