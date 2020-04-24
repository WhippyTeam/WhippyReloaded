package com.whippyteam.api.storage.flat.yml;

import com.whippyteam.api.ToolsPlugin;
import com.whippyteam.api.configuration.file.YamlConfiguration;
import com.whippyteam.api.helper.FileHelper;
import com.whippyteam.api.storage.AbstractSaveType;
import java.io.File;

public class YamlSaveType extends AbstractSaveType {

    private final ToolsPlugin plugin;

    public YamlSaveType(final ToolsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void initializeImportantStorages() {
        File configFile = new File(this.plugin.getDataFolder(), "config.yml");
        this.initializeFile(configFile, "config");

        if (!configFile.exists()) {
            FileHelper.saveResource(this.plugin, "config.yml", false);
        }
    }

    @Override
    public String getName() {
        return "yml";
    }

    @Override
    public void initialize() {
    }

    private void initializeFile(File file, String name) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        super.getStorageMap().putIfAbsent(name, config);
    }
}