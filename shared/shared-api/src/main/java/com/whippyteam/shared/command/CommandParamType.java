package com.whippyteam.shared.command;

import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;

public enum CommandParamType {
    STRING(String.class),
    INTEGER(Integer.class),
    DOUBLE(Double.class),
    WORLD(World.class),
    PLAYER(Player.class),
    OFFLINEPLAYER(OfflinePlayer.class);

    private Class<?> instantiatedClass;

    CommandParamType(Class<?> instanceClass) {
        this.instantiatedClass = instanceClass;
    }

    public Class<?> getInstanceClass() {
        return this.instantiatedClass;
    }
}
