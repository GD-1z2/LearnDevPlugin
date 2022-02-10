package fr.caemur.learndev;

import com.google.gson.stream.JsonReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LanguageManager {
    private final Main plugin;
    private final List<Language> languages;

    public LanguageManager(Main plugin) throws IOException {
        this.plugin = plugin;

        languages = Arrays.asList(plugin.getGson().fromJson(
                new JsonReader(new InputStreamReader(new FileInputStream(Main.LANGUAGES_FILE), StandardCharsets.UTF_8)),
                Language[].class));
    }

    /**
     * Finds a language using its name or aliases
     *
     * @param name A name/alias of the searched language
     * @return A Language object if found, null otherwise
     */
    public Language byName(String name) {
        for (Language language : languages)
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
        return languages.stream().map(language -> language.name).collect(Collectors.toList());
    }

    /**
     * Export the languages as JSON. This method is used when saving the languages after a modification.
     *
     * @return A String containing formatted JSON
     */
    public String export() {
        return plugin.getGson().toJson(languages);
    }
}
