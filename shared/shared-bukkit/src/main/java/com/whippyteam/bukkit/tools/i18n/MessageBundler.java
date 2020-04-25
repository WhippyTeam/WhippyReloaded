package com.whippyteam.bukkit.tools.i18n;

import com.whippyteam.api.ToolsPlugin;
import org.apache.commons.lang3.Validate;

public class MessageBundler {

    public static MessageContext send(final ToolsPlugin plugin, String key) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        return new MessageContext(plugin, key);
    }

    public static ExtendedMessageContext send(final ToolsPlugin plugin, String... keys) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        return new ExtendedMessageContext(plugin, keys);
    }

    private MessageBundler() {}

}
