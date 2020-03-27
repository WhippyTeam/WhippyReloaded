package com.whippyteam.api.storage.phase;

import com.whippyteam.api.storage.AbstractSaveType;
import com.whippyteam.api.storage.manager.StorageManager;

public abstract class AbstractPhaseLoader {

    private final StorageManager manager;

    protected AbstractPhaseLoader(final StorageManager manager) {
        this.manager = manager;
    }

    public abstract void start();

    public void addType(String name, AbstractSaveType type) {
        type.initializeImportantStorages();
        this.manager.addType(name, type);
    }
}
