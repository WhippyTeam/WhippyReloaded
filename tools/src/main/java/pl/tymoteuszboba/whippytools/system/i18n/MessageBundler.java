package pl.tymoteuszboba.whippytools.system.i18n;

import org.apache.commons.lang.Validate;
import org.hjson.JsonObject;
import pl.tymoteuszboba.whippytools.WhippyTools;

public class MessageBundler {

    public static MessageContext send(final WhippyTools plugin, JsonObject from, String key) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        return new MessageContext(plugin, from, key);
    }

    public static MessageContext send(final WhippyTools plugin, String key) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        return new MessageContext(plugin, key);
    }

    public static ExtendedMessageContext send(final WhippyTools plugin, JsonObject from, String firstKey, String secondKey) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        return new ExtendedMessageContext(plugin, from, firstKey, secondKey);
    }

    public static ExtendedMessageContext send(final WhippyTools plugin, String firstKey, String secondKey) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        return new ExtendedMessageContext(plugin, firstKey, secondKey);
    }

    private MessageBundler() {}

}
