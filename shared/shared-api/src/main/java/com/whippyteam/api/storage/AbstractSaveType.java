package com.whippyteam.api.storage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractSaveType {

    private final Map<String, Storage> storageMap;

    public AbstractSaveType() {
        this.storageMap = new HashMap<>();
    }

    public abstract String getName();

    public abstract void initialize();

    public void setupEnvironment() {
    }

    public void initializeImportantStorages() {
    }

    public Map<String, Storage> getStorageMap() {
        return this.storageMap;
    }

    public void forEach(Consumer<Storage> action) {
        for (Storage storage : getStorageMap().values()) {
            action.accept(storage);
        }
    }

}
