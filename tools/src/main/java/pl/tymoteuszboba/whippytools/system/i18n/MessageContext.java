package pl.tymoteuszboba.whippytools.system.i18n;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import pl.tymoteuszboba.whippytools.WhippyTools;
import pl.tymoteuszboba.whippytools.system.i18n.enums.MessageTarget;

public class MessageContext {

    private String value;

    private char colorPrefix = '&';

    MessageContext(final WhippyTools plugin, String key) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        this.value = plugin.getMessageFile().getString(key,
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
