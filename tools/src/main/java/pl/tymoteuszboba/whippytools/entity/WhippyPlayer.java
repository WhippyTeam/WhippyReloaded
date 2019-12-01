package pl.tymoteuszboba.whippytools.entity;

import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WhippyPlayer implements IdentifableEntity<UUID> {

    private final UUID identifier;
    private WeakReference<Player> originalPlayer;

    private String name;

    public WhippyPlayer(final UUID identifier) {
        this.identifier = identifier;
        this.name = Bukkit.getPlayer(this.identifier).getName();

        this.updateBukkitPlayer();
    }

    public WhippyPlayer(final String name) {
        this.name = name;
        this.identifier = UUID.nameUUIDFromBytes(("OfflinePlayer:" + this.name).getBytes(
            StandardCharsets.UTF_8));

        this.updateBukkitPlayer();
    }

    @Override
    public UUID getIdentifier() {
        return this.identifier;
    }

    public Player getPlayer() {
        if (originalPlayer != null) {
            return this.originalPlayer.get();
        }

        this.updateBukkitPlayer();
        return originalPlayer != null ? this.originalPlayer.get() : null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateBukkitPlayer() {
        if (Bukkit.getPlayer(this.identifier) != null) {
            this.originalPlayer = new WeakReference<>(Bukkit.getPlayer(this.identifier));
        }
    }
}
