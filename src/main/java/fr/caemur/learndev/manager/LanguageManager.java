package fr.caemur.learndev.manager;

import com.google.gson.reflect.TypeToken;
import fr.caemur.learndev.LearnDevPlugin;
import fr.caemur.learndev.entities.Language;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LanguageManager extends Manager<List<Language>> {
    public LanguageManager(LearnDevPlugin plugin) {
        super(plugin, new TypeToken<List<Language>>() {}.getType(), LearnDevPlugin.LANGUAGES_FILE, new ArrayList<>());
    }

    /**
     * Is there any language in the configuration.
     *
     * @return True if the languages list's length is greater than 0, false otherwise
     */
    public boolean hasLanguages() {
        return value.size() > 0;
    }

    /**
     * Finds a language using its name or aliases
     *
     * @param name A name/alias of the searched language
     * @return A Language object if found, null otherwise
     */
    public Language byName(String name) {
        for (Language language : value)
            if (language.name.equalsIgnoreCase(name) || language.aliases.contains(name.toLowerCase()))
                return language;

        return null;
    }

    /**
     * Get all the languages' names (not aliases)
     *
     * @return A list of names
     */
    public List<String> getLanguagesNames() {
        return value.stream().map(language -> language.name).collect(Collectors.toList());
    }

    /**
     * Add a language to the list and save
     *
     * @param language The language to add
     */
    public void addLanguage(Language language) {
        value.add(language);
        save();
    }

    /**
     * Remove a language from the list and save
     *
     * @param language The language to remove
     */
    public void removeLanguage(Language language) {
        value.remove(language);
        save();
    }
}
