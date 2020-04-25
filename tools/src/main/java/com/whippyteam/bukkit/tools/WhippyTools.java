package com.whippyteam.bukkit.tools;

import com.whippyteam.api.ManagerSelector;
import com.whippyteam.api.ToolsLogger;
import com.whippyteam.api.ToolsPlugin;
import com.whippyteam.api.entity.WhippyPlayer;
import com.whippyteam.api.helper.enums.EngineType;
import com.whippyteam.api.manager.Manager;
import com.whippyteam.api.manager.ManagerMapper;
import com.whippyteam.api.manager.type.WhippyPlayerManager;
import com.whippyteam.api.storage.AbstractSaveType;
import com.whippyteam.api.storage.Storage;
import com.whippyteam.api.storage.StorageSaver;
import com.whippyteam.api.storage.exception.ReadException;
import com.whippyteam.api.storage.manager.StorageManager;
import com.whippyteam.bukkit.tools.entity.WhippyPlayerImpl;
import com.whippyteam.bukkit.tools.listener.PlayerJoinListener;
import com.whippyteam.bukkit.tools.listener.PlayerQuitListener;
import com.whippyteam.bukkit.tools.manager.BukkitManagerSelector;
import com.whippyteam.bukkit.tools.scheduler.DataSaveScheduler;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class WhippyTools extends JavaPlugin implements ToolsPlugin {

    private ToolsLogger logger;

    private ManagerSelector managerSelector;

    private AbstractSaveType storageType;
    private StorageManager storageManager;

    @Override
    public void onLoad() {
        this.logger = new ToolsLogger(this);
        this.storageManager = new StorageManager();
    }

    @Override
    public void onEnable() {
        this.registerManagerSelector();
        this.registerStorageSaver();

        this.registerListeners(new PlayerJoinListener(this),
            new PlayerQuitListener(this));

        this.registerOnlinePlayers();
        this.registerSchedulers();
    }

    private void registerManagerSelector() {
        ManagerMapper mapper = new ManagerMapper();
        this.managerSelector = new BukkitManagerSelector(this, mapper);
    }

    private void registerStorageSaver() {
        StorageSaver saver = new StorageSaver(this, this.storageManager);
        saver.startPhaseLoading();
    }

    private void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

    private void registerOnlinePlayers() {
        if (Bukkit.getOnlinePlayers().size() == 0) {
            return;
        }

        Bukkit.getOnlinePlayers().forEach(player -> {
            WhippyPlayer whippyPlayer = new WhippyPlayerImpl(player.getUniqueId());

            Storage storage = this.getStorage(this.storageType.getName(),
                "whippyPlayer");

            WhippyPlayerManager manager = (WhippyPlayerManager) this.getManager("whippyPlayer");
            manager.add(whippyPlayer);
            try {
                storage.load(whippyPlayer);
            } catch (ReadException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void registerSchedulers() {
        if (this.getWhippyConfig().getBoolean("data-saving-cycle", true)) {
            int dataSavingTime = this.getWhippyConfig().getInt("data-saving-time", 10);
            Bukkit.getScheduler().runTaskTimerAsynchronously(this, new DataSaveScheduler(this),
                0, dataSavingTime * 20 * 60);
        }
    }

    @Override
    public EngineType getEngineType() {
        return EngineType.SPIGOT;
    }

    @Override
    public String getEngineVersion() {
        return Bukkit.getBukkitVersion();
    }

    @Override
    public ToolsLogger getWhippyLogger() {
        return logger;
    }

    @Override
    public Manager<?, ?> getManager(String name) {
        return this.managerSelector.getManager(name);
    }

    public ManagerSelector getManagerSelector() {
        return this.managerSelector;
    }

    @Override
    public StorageManager getStorageManager() {
        return this.storageManager;
    }

    @Override
    public Storage getStorage(String saveType, String resource) {
        Optional<AbstractSaveType> type = this.storageManager.getType(saveType);
        if (!type.isPresent()) {
            this.logger.warning("Save type \"" + saveType + "\" doesn't exist!");
            return null;
        }

        Storage storage = type.get().getStorageMap().get(resource);
        if (storage == null) {
            this.logger.warning(
                "Resource \"" + resource + "\" from type \"" + saveType + "\" doesn't exist!");
            return null;
        }
        return storage;
    }

    @Override
    public AbstractSaveType getStorageType() {
        return this.storageType;
    }

    @Override
    public void setStorageType(AbstractSaveType type) {
        this.storageType = type;
    }

}
