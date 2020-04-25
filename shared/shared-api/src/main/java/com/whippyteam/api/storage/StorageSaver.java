package com.whippyteam.api.storage;

import com.whippyteam.api.ToolsPlugin;
import com.whippyteam.api.storage.manager.StorageManager;
import com.whippyteam.api.storage.phase.DefaultPhaseLoader;

public class StorageSaver {

    private final ToolsPlugin plugin;
    private final StorageManager manager;

    public StorageSaver(final ToolsPlugin plugin, final StorageManager manager) {
        this.plugin = plugin;
        this.manager = manager;
    }

    public void startPhaseLoading() {
        DefaultPhaseLoader firstPhase = new DefaultPhaseLoader(this.plugin, this.manager);
        firstPhase.start();
    }

}
