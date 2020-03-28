package com.whippyteam.api;

import com.whippyteam.api.helper.enums.EngineType;
import com.whippyteam.api.manager.Manager;
import com.whippyteam.api.storage.AbstractSaveType;
import com.whippyteam.api.storage.manager.StorageManager;
import com.whippyteam.commons.configuration.file.YamlConfiguration;
import com.whippyteam.commons.storage.system.Storage;

import java.io.File;

public interface ToolsPlugin {

    static String getVersion() {
        return "0.1 Kiwi";
    }

    EngineType getEngineType();

    String getEngineVersion();

    File getDataFolder();

    ToolsLogger getWhippyLogger();

    Manager<?, ?> getManager(String name);

    ManagerSelector getManagerSelector();

    StorageManager getStorageManager();

    Storage getStorage(String saveType, String resource);

    default YamlConfiguration getWhippyConfig() {
        return (YamlConfiguration) this.getStorage("yml", "config");
    }

    AbstractSaveType getStorageType();

    void setStorageType(AbstractSaveType type);
}
