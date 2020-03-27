package com.whippyteam.api.storage.phase;

import com.whippyteam.api.ToolsPlugin;
import com.whippyteam.api.storage.AbstractSaveType;
import com.whippyteam.api.storage.manager.StorageManager;

import java.util.List;
import java.util.Optional;

public class SelectionPhaseLoader extends AbstractPhaseLoader {

    private final ToolsPlugin plugin;
    private final StorageManager manager;

    protected SelectionPhaseLoader(final ToolsPlugin plugin, StorageManager manager) {
        super(manager);
        this.plugin = plugin;
        this.manager = manager;
    }

    public void start() {
        String stringPriorityMethod = this.plugin.getWhippyConfig().getString("data-loading.type");
        List<String> stringFallbackMethods = this.plugin.getWhippyConfig()
                .getStringList("data-loading.fallback-types");

        if (stringPriorityMethod != null && !stringPriorityMethod.isEmpty()) {
            Optional<AbstractSaveType> priorityMethod = this.manager.getType(stringPriorityMethod);

            if (priorityMethod.isPresent()) {
                this.setStorageType(priorityMethod.get());
                return;
            }
        }

        this.plugin.getWhippyLogger().severe("Failed to load data save type \"" + stringPriorityMethod + "\"!");
        this.plugin.getWhippyLogger().severe("Trying to load fallback types...");

        if (stringFallbackMethods.isEmpty()) {
            this.plugin.getWhippyLogger().severe("WhippyTools can't find any fallback types!");
            this.loadDefaultType();
            return;
        }

        for (String stringType : stringFallbackMethods) {
            this.plugin.getWhippyLogger().debug("Trying to load \"" + stringType + "\" type...");
            Optional<AbstractSaveType> type = this.manager.getType(stringType);
            if (type.isPresent()) {
                this.setStorageType(type.get());
                this.plugin.getWhippyLogger().info("Save type \"" + stringType + "\" has been set as the default save type!");
                return;
            }
        }

        this.loadDefaultType();
    }

    private void loadDefaultType() {
        this.plugin.getWhippyLogger().severe("Loading default save type: \"yml\"...");
        Optional<AbstractSaveType> ymlSaveType = this.plugin.getStorageManager()
                .getType("yml");

        if (ymlSaveType.isPresent()) {
            this.setStorageType(ymlSaveType.get());
        } else {
            this.plugin.getWhippyLogger().severe("Error while loading storage type: \"yml\"",
                    new NullPointerException());
        }
    }

    public void setStorageType(AbstractSaveType type) {
        type.setupEnvironment();
        type.initialize();
        this.plugin.setStorageType(type);
    }

}
