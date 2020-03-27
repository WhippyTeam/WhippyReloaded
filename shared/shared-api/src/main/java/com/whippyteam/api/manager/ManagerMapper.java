package com.whippyteam.api.manager;

import org.apache.commons.lang3.Validate;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class ManagerMapper {

    private Map<String, Manager<?, ?>> managerMap;

    public ManagerMapper() {
        this.managerMap = new ConcurrentHashMap<>(8, 0.8F, 3);
    }

    public Optional<Manager<?, ?>> getManager(String name) {
        return Optional.of(this.managerMap.get(name));
    }

    public void addManager(String name, Manager<?, ?> manager) {
        Validate.notNull(name, "Name cannot be null!");
        Validate.notNull(manager, "Manager cannot be null!");

        this.managerMap.putIfAbsent(name, manager);
    }

    public void removeManager(String name) {
        Validate.notNull(name, "Name cannot be null!");

        this.managerMap.remove(name);
    }

    public Collection<Manager<?, ?>> getManagers() {
        return Collections.unmodifiableCollection(this.managerMap.values());
    }

}
