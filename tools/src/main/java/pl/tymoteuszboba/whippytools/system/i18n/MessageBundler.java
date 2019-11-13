package pl.tymoteuszboba.whippytools.system.i18n;

import com.sun.istack.internal.NotNull;
import pl.tymoteuszboba.whippytools.WhippyTools;
import pl.tymoteuszboba.whippytools.system.i18n.enums.Locale;

public class MessageBundler {

    public static MessageContext send(@NotNull WhippyTools plugin, String key) {
        return new MessageContext(plugin, key);
    }

    public static MessageContext send(@NotNull WhippyTools plugin, String key, Locale locale) {
        return new MessageContext(plugin, key, locale);
    }

    //TODO change MessageContext class to ExtendedMessageContext.
    public static MessageContext send(@NotNull WhippyTools plugin, String key, String secondkey) {
        throw new UnsupportedOperationException("This function isn't available yet!");
    }

    //TODO change MessageContext class to ExtendedMessageContext.
    public static MessageContext send(@NotNull WhippyTools plugin, String key, String secondkey,
        Locale locale) {
        throw new UnsupportedOperationException("This function isn't available yet!");
    }

    private MessageBundler() {}

}
