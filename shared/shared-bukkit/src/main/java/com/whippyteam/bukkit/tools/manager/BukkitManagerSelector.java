package com.whippyteam.bukkit.tools.manager;

import com.whippyteam.api.ManagerSelector;
import com.whippyteam.api.ToolsPlugin;
import com.whippyteam.api.manager.Manager;
import com.whippyteam.api.manager.ManagerMapper;
import com.whippyteam.api.manager.type.WhippyPlayerManager;
import com.whippyteam.bukkit.tools.manager.type.WhippyPlayerManagerImpl;

import java.util.Optional;

public class BukkitManagerSelector implements ManagerSelector {

    private final ToolsPlugin plugin;
    private final ManagerMapper mapper;

    public BukkitManagerSelector(final ToolsPlugin plugin, final ManagerMapper mapper) {
        this.plugin = plugin;
        this.mapper = mapper;
        this.loadDefaults();
    }

    private void loadDefaults() {
        WhippyPlayerManager manager = new WhippyPlayerManagerImpl();
        this.mapper.addManager("whippyPlayer", manager);
    }

    @Override
    public Manager<?, ?> getManager(String name) {
        Optional<Manager<?, ?>> manager = this.mapper.getManager(name);
        if (manager.isPresent()) {
            return manager.get();
        } else {
            this.plugin.getWhippyLogger().warning("Failed to find \"" + name + "\" manager!");
            return null;
        }
    }

    @Override
    public ManagerMapper getMapper() {
        return this.mapper;
    }

}
