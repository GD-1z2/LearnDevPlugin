package fr.caemur.learndev.manager;

import com.google.gson.reflect.TypeToken;
import fr.caemur.learndev.LearnDevPlugin;
import fr.caemur.learndev.entities.BotConfig;

public class ConfigManager extends Manager<BotConfig> {
    public ConfigManager(LearnDevPlugin plugin) {
        super(plugin, new TypeToken<BotConfig>() {}.getType(), LearnDevPlugin.CONFIG_FILE, new BotConfig());
    }

    /**
     * Check if the user is a plugin moderator
     *
     * @param id The ID of the user
     * @return Whether the ID is present in the moderator list
     */
    public boolean isModerator(String id) {
        return value.moderators.contains(id);
    }
}
