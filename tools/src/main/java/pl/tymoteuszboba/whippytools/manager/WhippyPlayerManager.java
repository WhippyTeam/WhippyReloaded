package pl.tymoteuszboba.whippytools.manager;

import com.google.common.collect.ImmutableSet;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import pl.tymoteuszboba.whippytools.entity.WhippyPlayer;

public class WhippyPlayerManager implements EntityManager<WhippyPlayer, UUID> {

    private ConcurrentMap<UUID, WhippyPlayer> uuidPlayerMap = new ConcurrentHashMap<>(8, 0.9F, 3);
    private ConcurrentMap<String, WhippyPlayer> namePlayerMap = new ConcurrentHashMap<>(8, 0.9F, 3);

    @Override
    public Optional<WhippyPlayer> get(String name) {
        return Optional.ofNullable(this.namePlayerMap.get(name));
    }

    public Optional<WhippyPlayer> get(UUID identifier) {
        return Optional.ofNullable(this.uuidPlayerMap.get(identifier));
    }

    @Override
    public void add(WhippyPlayer entity) {
        this.uuidPlayerMap.putIfAbsent(entity.getIdentifier(), entity);
        this.namePlayerMap.putIfAbsent(entity.getPlayer().getName(), entity);
    }

    @Override
    public void remove(WhippyPlayer entity) {
        this.uuidPlayerMap.remove(entity.getIdentifier(), entity);
        this.namePlayerMap.remove(entity.getName(), entity);
    }

    @Override
    public Set<WhippyPlayer> getAllEntities() {
        return new HashSet<>(this.uuidPlayerMap.values());
    }

    public Set<WhippyPlayer> getOnlineEntities() {
        return ImmutableSet.copyOf(Bukkit.getServer().getOnlinePlayers().stream()
            .map(player -> this.get(player.getUniqueId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet()));
    }
}
