package fr.caemur.learndev;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.ordinalteam.bot.api.plugin.Plugin;

public class Main extends Plugin {
    public static final String CONFIG_DIR = "config/learndev";
    public static final String LANGUAGES_FILE = CONFIG_DIR + "/languages.json";

    private Gson gson;
    private LanguageManager languageManager;

    @Override
    public void onEnable() {
        gson = new GsonBuilder().setPrettyPrinting().create();

        getCommandRegistry().registerCommand(new LearnCommand(this), this);

        try {
            languageManager = new LanguageManager(this);
        } catch (final Exception e) {
            logger.logError("Failed to load languages config file");
            e.printStackTrace();

            return;
        }

        getCommandRegistry().registerCommand(new LearnCommand(this), this);

        logger.log("LearnDev loaded !");
    }

    public Gson getGson() {
        return gson;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }
}
