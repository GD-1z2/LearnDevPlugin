package fr.caemur.learndev;

import fr.ordinalteam.bot.api.plugin.Plugin;

public class Main extends Plugin {
    private LanguageManager languageManager;

    @Override
    public void onEnable() {
        System.out.println("LearnDev loaded !");

        getCommandRegistry().registerCommand(new LearnCommand(this), this);

        try {
            languageManager = new LanguageManager();
        } catch (final Exception e) {
            logger.logError("Failed to load languages config file");
            e.printStackTrace();
        }
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }
}
