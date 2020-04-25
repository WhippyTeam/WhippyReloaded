package com.whippyteam.api.storage.phase;

import com.whippyteam.api.ToolsPlugin;
import com.whippyteam.api.storage.database.mysql.MySqlSaveType;
import com.whippyteam.api.storage.flat.yml.YamlSaveType;
import com.whippyteam.api.storage.manager.StorageManager;

public class DefaultPhaseLoader extends AbstractPhaseLoader {

    private final ToolsPlugin plugin;
    private final StorageManager storageManager;

    public DefaultPhaseLoader(final ToolsPlugin plugin, final StorageManager manager) {
        super(manager);
        this.plugin = plugin;
        this.storageManager = manager;
    }

    public void start() {
        super.addType("yml", new YamlSaveType(this.plugin));
        super.addType("mysql", new MySqlSaveType(this.plugin));

        SelectionPhaseLoader selectionLoader = new SelectionPhaseLoader(this.plugin, this.storageManager);
        selectionLoader.start();
    }

}