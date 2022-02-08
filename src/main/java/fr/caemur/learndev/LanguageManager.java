package fr.caemur.learndev;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class LanguageManager {
    public static final String CONFIG_FILE = "config/learndev/languages.json";

    private final List<Language> languages;
    private final Gson gson;

    public LanguageManager() throws IOException {
        gson = new GsonBuilder().setPrettyPrinting().create();

        languages = List.of(gson.fromJson(
                new JsonReader(new FileReader(CONFIG_FILE, StandardCharsets.UTF_8)),
                Language[].class));
    }

    /**
     * Finds a language using its name or aliases
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
     * @return A list of names
     */
    public List<String> getLanguagesNames() {
        return languages.stream().map(language -> language.name).toList();
    }

    /**
     * Export the languages as JSON. This method is used when saving the languages after a modification.
     * @return A String containing formatted JSON
     */
    public String export() {
        return gson.toJson(languages);
    }
}
