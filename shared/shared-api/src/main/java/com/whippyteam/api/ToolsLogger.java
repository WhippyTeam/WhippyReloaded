package com.whippyteam.api;

import java.util.logging.Logger;

public class ToolsLogger {

    private final ToolsPlugin plugin;
    private final Logger logger;

    public ToolsLogger(final ToolsPlugin plugin) {
        this.plugin = plugin;
        this.logger = Logger.getLogger("WhippyTools");
    }

    public void info(String message) {
        this.logger.info(message);
    }

    public void warning(String message) {
        this.logger.warning(message);
    }

    public void debug(String message) {
        if (this.plugin.getWhippyConfig().getBoolean("debug")) {
            this.logger.info("[DEBUG] > " + message);
        }
    }

    public void severe(String message) {
        this.logger.severe(message);
    }

    public void severe(String message, Throwable throwable) {
        this.severe("");
        this.severe(" : WHIPPY TOOLS : ");
        this.severe(" " + message);
        this.severe("");
        this.severe(throwable.toString());
        for (StackTraceElement element : throwable.getStackTrace()) {
            this.severe("  | at " + element.toString());
        }

        this.severe(" ");
        this.severe("  : Server information");
        this.severe("  | WhippyTools: " + ToolsPlugin.getVersion());
        this.severe("  | Engine: " + this.plugin.getEngineType().name() + " " + this.plugin.getEngineVersion());
        this.severe("  | Java: " + System.getProperty("java.version"));
        this.severe("  | Thread: " + Thread.currentThread());
    }
}
