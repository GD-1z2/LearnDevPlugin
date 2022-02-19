package fr.caemur.learndev;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.caemur.learndev.commands.ConfigCommand;
import fr.caemur.learndev.commands.LearnCommand;
import fr.caemur.learndev.manager.ConfigManager;
import fr.caemur.learndev.manager.LanguageManager;
import fr.ordinalteam.bot.api.plugin.Plugin;

import java.io.File;

public class LearnDevPlugin extends Plugin {
    public static final String CONFIG_DIR = "config/learndev";

    public static final String CONFIG_FILE = CONFIG_DIR + "/config.json";
    public static final String LANGUAGES_FILE = CONFIG_DIR + "/languages.json";

    private Gson gson;

    private ConfigManager configManager;
    private LanguageManager languageManager;

    @Override
    public void onEnable() {
        gson = new GsonBuilder().setPrettyPrinting().create();

        new File(CONFIG_DIR).mkdirs();

        languageManager = new LanguageManager(this);
        configManager = new ConfigManager(this);

        getCommandRegistry().registerCommand(new LearnCommand(this), this);
        getCommandRegistry().registerCommand(new ConfigCommand(this), this);

        logger.log("LearnDev loaded !");
    }

    /**
     * Get the plugin's gson object
     *
     * @return A gson instance with the pretty printing option enabled
     */
    public Gson getGson() {
        return gson;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }
}
