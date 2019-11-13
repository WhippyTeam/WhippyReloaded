package pl.tymoteuszboba.whippytools.system.i18n;

import org.apache.commons.lang.Validate;
import pl.tymoteuszboba.whippytools.WhippyTools;
import pl.tymoteuszboba.whippytools.system.i18n.enums.Locale;

public class MessageBundler {

    public static MessageContext send(final WhippyTools plugin, String key) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        return new MessageContext(plugin, key);
    }

    public static MessageContext send(final WhippyTools plugin, String key, Locale locale) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        return new MessageContext(plugin, key, locale);
    }

    //TODO change MessageContext class to ExtendedMessageContext.
    public static MessageContext send(final WhippyTools plugin, String key, String secondkey) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        throw new UnsupportedOperationException("This function isn't available yet!");
    }

    //TODO change MessageContext class to ExtendedMessageContext.
    public static MessageContext send(final WhippyTools plugin, String key, String secondkey,
        Locale locale) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        throw new UnsupportedOperationException("This function isn't available yet!");
    }

    private MessageBundler() {}

}
