package com.whippyteam.bukkit.tools.i18n;

import com.whippyteam.api.ToolsPlugin;
import com.whippyteam.api.configuration.file.FileConfiguration;
import com.whippyteam.api.i18n.enums.MessageTarget;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class MessageContext {

    private String value;

    private char colorPrefix = '&';

    MessageContext(final ToolsPlugin plugin, String key) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        FileConfiguration languageFile = (FileConfiguration) plugin.getStorage("yml", "locale");
        this.value = languageFile.getString(key,
            "String not found in language configuration.");
    }

    public MessageContext replace(String placeholder, String value) {
        this.value = StringUtils.replace(this.value, "{" + placeholder + "}", value);
        return this;
    }

    public MessageContext setColorPrefix(char prefix) {
        this.colorPrefix = prefix;
        return this;
    }

    public void broadcast() {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes(this.colorPrefix, this.value));
    }

    public CompletedMessage target(MessageTarget target) {
        return new CompletedMessage(this.value, target, this.colorPrefix);
    }

    public String toString() {
        return ChatColor.translateAlternateColorCodes(colorPrefix, this.value);
    }

}
