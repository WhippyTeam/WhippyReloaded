package pl.tymoteuszboba.whippytools.config;

import com.google.common.io.Files;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.hjson.JsonObject;
import org.hjson.JsonValue;
import pl.tymoteuszboba.whippytools.WhippyTools;

public class ToolsConfiguration implements Configuration {

    private final WhippyTools plugin;
    private JsonObject schema;

    public ToolsConfiguration(final WhippyTools plugin) {
        this.plugin = plugin;
        this.loadConfiguration();
    }

    @Override
    public void loadConfiguration() {
        File file = new File(this.plugin.getDataFolder(), "config.hjson");

        if (!file.exists()) {
            try {
                Files.createParentDirs(file);
                file.createNewFile();
                this.plugin.saveResource("config.hjson", true);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        this.reloadConfiguration();
    }

    @Override
    public void reloadConfiguration() {
        try (FileReader reader = new FileReader(new File(this.plugin.getDataFolder(), "config.hjson"))) {
            schema = JsonValue.readHjson(reader).asObject();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public String getLocale() {
        return this.schema.getString("locale", "en");
    }

    public JsonObject getLocaleFile() {
        File file = new File(this.plugin.getDataFolder(), "locale-" + this.getLocale() + ".hjson");
        if (!file.exists()) {
            this.createLocaleFile(file, this.getLocale());
        }

        try (FileReader reader = new FileReader(new File(this.plugin.getDataFolder(),
            "locale-" + this.getLocale() + ".hjson"))) {
            return JsonValue.readHjson(reader).asObject();
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private void createLocaleFile(File file, String locale) {
        try {
            Files.createParentDirs(file);
            file.createNewFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        this.plugin.saveResource("locale-" + locale + ".hjson", true);
    }

    public JsonObject getRawObject() {
        return this.schema;
    }
}
