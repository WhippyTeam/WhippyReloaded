package com.whippyteam.bukkit.tools.manager.type;

import com.google.common.collect.ImmutableSet;
import com.whippyteam.api.entity.WhippyPlayer;
import com.whippyteam.api.manager.type.WhippyPlayerManager;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

public class WhippyPlayerManagerImpl implements WhippyPlayerManager {

    private ConcurrentMap<UUID, WhippyPlayer> uuidPlayerMap = new ConcurrentHashMap<>(8, 0.9F, 3);
    private ConcurrentMap<String, WhippyPlayer> namePlayerMap = new ConcurrentHashMap<>(8, 0.9F, 3);

    @Override
    public Optional<WhippyPlayer> get(String name) {
        Validate.notNull(name, "Name cannot be null!");

        return Optional.ofNullable(this.namePlayerMap.get(name));
    }

    public Optional<WhippyPlayer> get(UUID identifier) {
        Validate.notNull(identifier, "Identifier cannot be null!");
        return Optional.ofNullable(this.uuidPlayerMap.get(identifier));
    }

    @Override
    public void add(WhippyPlayer entity) {
        Validate.notNull(entity, "WhippyPlayer cannot be null!");

        this.uuidPlayerMap.putIfAbsent(entity.getIdentifier(), entity);
        this.namePlayerMap.putIfAbsent(entity.getName(), entity);
    }

    @Override
    public void remove(WhippyPlayer entity) {
        Validate.notNull(entity, "WhippyPlayer cannot be null!");

        this.uuidPlayerMap.remove(entity.getIdentifier(), entity);
        this.namePlayerMap.remove(entity.getName(), entity);
    }

    @Override
    public Set<WhippyPlayer> entrySet() {
        return new HashSet<>(this.uuidPlayerMap.values());
    }

    @Override
    public Set<WhippyPlayer> getOnlineEntities() {
        return ImmutableSet.copyOf(Bukkit.getServer().getOnlinePlayers().stream()
            .map(player -> this.get(player.getUniqueId()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toSet()));
    }
}
