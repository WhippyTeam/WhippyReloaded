package com.whippyteam.api.storage.manager;

import com.whippyteam.api.storage.AbstractSaveType;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class StorageManager {

    private Map<String, AbstractSaveType> types = new ConcurrentHashMap<>(8, 0.8F, 3);

    public void addType(String name, AbstractSaveType type) {
        this.types.putIfAbsent(name, type);
    }

    public void removeType(String name) {
        this.types.remove(name);
    }

    public Optional<AbstractSaveType> getType(String name) {
        return Optional.ofNullable(types.get(name));
    }

    public Map<String, AbstractSaveType> getTypeMap() {
        return Collections.unmodifiableMap(types);
    }

    public Collection<AbstractSaveType> getSaveTypes() {
        return Collections.unmodifiableCollection(types.values());
    }

}
