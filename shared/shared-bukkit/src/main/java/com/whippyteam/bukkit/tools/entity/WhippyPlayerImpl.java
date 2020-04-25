package com.whippyteam.bukkit.tools.entity;

import com.whippyteam.api.entity.WhippyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class WhippyPlayerImpl implements WhippyPlayer {

    private final UUID identifier;
    private WeakReference<Player> originalPlayer;

    private String name;

    public WhippyPlayerImpl(final UUID identifier) {
        this.identifier = identifier;
        this.name = Bukkit.getPlayer(this.identifier).getName();

        this.updatePlayerReference();
    }

    public WhippyPlayerImpl(final String name) {
        this.name = name;
        this.identifier = UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.name).getBytes(
            StandardCharsets.UTF_8));

        this.updatePlayerReference();
    }

    @Override
    public UUID getIdentifier() {
        return this.identifier;
    }

    public Player getPlayer() {
        if (originalPlayer != null) {
            return this.originalPlayer.get();
        }

        this.updatePlayerReference();
        return originalPlayer != null ? this.originalPlayer.get() : null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updatePlayerReference() {
        if (Bukkit.getPlayer(this.identifier) != null) {
            this.originalPlayer = new WeakReference<>(Bukkit.getPlayer(this.identifier));
        }
    }
}
