package com.whippyteam.whippytools.system.i18n;

import com.whippyteam.whippytools.WhippyTools;
import org.apache.commons.lang.Validate;

public class MessageBundler {

    public static MessageContext send(final WhippyTools plugin, String key) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        return new MessageContext(plugin, key);
    }

    public static ExtendedMessageContext send(final WhippyTools plugin, String firstKey, String secondKey) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        return new ExtendedMessageContext(plugin, firstKey, secondKey);
    }

    private MessageBundler() {}

}
