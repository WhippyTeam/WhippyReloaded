package pl.tymoteuszboba.whippytools.system.i18n;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import pl.tymoteuszboba.whippytools.WhippyTools;
import pl.tymoteuszboba.whippytools.system.i18n.enums.Locale;
import pl.tymoteuszboba.whippytools.system.i18n.enums.MessageTarget;

public class MessageContext {

    private String value;
    private Locale locale = Locale.ENGLISH;

    private char colorPrefix = '&';

    MessageContext(final WhippyTools plugin, String key) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        this.value = plugin.getConfig().getString(this.locale.name() + "." + key); //Temporary

        //TODO the way of getting a string from config should be changed when we will implement Diorite Configs.
    }

    MessageContext(final WhippyTools plugin, String key, Locale locale) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        this.value = plugin.getConfig().getString(locale.name() + "." + key); //Temporary.
        //TODO the way of getting a string from config should be changed when we will implement Diorite Configs.
    }

    public MessageContext replace(String placeholder, String value) {
        this.value = StringUtils.replace(this.value, "{" + placeholder + "}", value);
        return this;
    }

    public MessageContext setColorPrefix(char prefix) {
        this.colorPrefix = prefix;
        return this;
    }

    public MessageContext fromLocale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public void broadcast() {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes(this.colorPrefix, this.value));
    }

    public CompletedMessage target(MessageTarget target) {
        return new CompletedMessage(this.value, this.locale, target, this.colorPrefix);
    }

    public String toString() {
        return ChatColor.translateAlternateColorCodes(colorPrefix, this.value);
    }

}
