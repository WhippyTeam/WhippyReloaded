package com.whippyteam.bukkit.tools.i18n;

import com.whippyteam.api.ToolsPlugin;
import com.whippyteam.api.configuration.file.FileConfiguration;
import com.whippyteam.api.i18n.enums.MessageTarget;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ExtendedMessageContext {

    private final String[] valueList;
    private char colorPrefix = '&';

    public ExtendedMessageContext(final ToolsPlugin plugin, String... keys) {
        Validate.notNull(plugin, "Plugin cannot be null!");

        FileConfiguration languageFile = plugin.getWhippyConfig();

        this.valueList = new String[keys.length];
        for (int i = 0; i < keys.length; i++) {
            this.valueList[i] = languageFile.getString(keys[i],
                "First string not found in language configuration.");
        }
    }

    public ExtendedMessageContext setColorPrefix(char prefix) {
        this.colorPrefix = prefix;
        return this;
    }

    public ExtendedMessageContext replace(String placeholder, String value, int index) {
        this.valueList[index] = StringUtils
            .replace(this.valueList[index], "{" + placeholder + "}", value);
        return this;
    }

    public CompletedMessage target(MessageTarget target) {
        return new CompletedMessage(this.valueList, target, this.colorPrefix);
    }

    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this);
        for (int i = 0; i < this.valueList.length; i++) {
            builder.append("Value " + i + ": " + this.valueList[i]);
        }

        return builder.toString();
    }

}
