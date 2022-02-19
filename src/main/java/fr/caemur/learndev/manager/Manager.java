package fr.caemur.learndev.manager;

import fr.caemur.learndev.LearnDevPlugin;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

public abstract class Manager<T> {
    protected final LearnDevPlugin plugin;
    protected final String file;
    protected T value;

    protected Manager(LearnDevPlugin plugin, Type type, String file, T defaultValue) {
        this.plugin = plugin;
        this.file = file;

        try {
            final InputStreamReader reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            value = plugin.getGson().fromJson(reader, type);
        } catch (final FileNotFoundException e) {
            plugin.logger.logWarn("File not found " + file);
        }

        // The default value is used in case the file doesn't exist, or contains invalid data
        if (value == null)
            value = defaultValue;
    }

    /**
     * A method used to export the data as JSON and save it in the config directory after a modification
     */
    public void save() {
        try {
            FileWriter writer = new FileWriter(file);
            plugin.getGson().toJson(value, writer);
            writer.flush();
            writer.close();
        } catch (final IOException e) {
            plugin.logger.logError("Failed to save data to " + file);
            e.printStackTrace();
        }
    }
}
