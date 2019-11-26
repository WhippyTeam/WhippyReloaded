package pl.tymoteuszboba.whippytools.system.i18n;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hjson.JsonObject;
import pl.tymoteuszboba.whippytools.WhippyTools;
import pl.tymoteuszboba.whippytools.system.i18n.enums.ContextKey;
import pl.tymoteuszboba.whippytools.system.i18n.enums.MessageTarget;

public class ExtendedMessageContext {

    private final WhippyTools plugin;

    private String firstValue;
    private String secondValue;

    private char colorPrefix = '&';

    public ExtendedMessageContext(final WhippyTools plugin, String firstKey, String secondKey) {
        Validate.notNull(plugin, "Plugin cannot be null!");
        this.plugin = plugin;

        JsonObject languageFile = plugin.getWhippyConfig().getLocaleFile();
        this.firstValue = languageFile.getString(firstKey,
            "First string not found in language configuration.");

        this.secondValue = languageFile.getString(secondKey,
            "Second string not found in language configuration.");
    }

    public ExtendedMessageContext setColorPrefix(char prefix) {
        this.colorPrefix = prefix;
        return this;
    }

    public ExtendedMessageContext replace(String placeholder, String value, ContextKey key) {
        switch (key) {
            case FIRST:
                this.firstValue = StringUtils.replace(this.firstValue, "{" + placeholder + "}", value);
                break;
            case SECOND:
                this.secondValue = StringUtils.replace(this.secondValue, "{" + placeholder + "}", value);
                break;
            default:
                throw new UnsupportedOperationException("You can only use FIRST or SECOND enums from ContextKey!");
        }
        return this;
    }

    public CompletedMessage target(MessageTarget target) {
        return new CompletedMessage(this.firstValue, this.secondValue, target, this.colorPrefix);
    }

    public String toString() {
        return new ToStringBuilder(this)
            .append("firstValue", this.firstValue)
            .append("secondValue", this.secondValue)
            .toString();
    }

}