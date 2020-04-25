package com.whippyteam.api.helper;

import com.whippyteam.api.ToolsPlugin;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FileHelper {

    private static final Logger LOGGER = Logger.getLogger("WhippyTools");

    public static void saveResource(final ToolsPlugin plugin, @NotNull String resourcePath,
        boolean replace) {
        if (resourcePath.equals("")) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = getResource(resourcePath);
        if (in == null) {
            throw new IllegalArgumentException(
                "The embedded resource '" + resourcePath + "' cannot be found!");
        }

        File outFile = new File(plugin.getDataFolder(), resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(plugin.getDataFolder(),
            resourcePath.substring(0, Math.max(lastIndex, 0)));

        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        try {
            if (!outFile.exists() || replace) {
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            } else {
                LOGGER.log(Level.WARNING, "Could not save " +
                    outFile.getName() + " to " + outFile + " because " + outFile.getName()
                    + " already exists.");
            }
        } catch (IOException ex) {
            plugin.getWhippyLogger()
                .severe("Could not save " + outFile.getName() + " to " + outFile, ex);
        }
    }

    @Nullable
    public static InputStream getResource(@NotNull String filename) {
        try {
            URL url = FileHelper.class.getClassLoader().getResource(filename);

            if (url == null) {
                return null;
            }

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        } catch (IOException ex) {
            return null;
        }
    }

}
