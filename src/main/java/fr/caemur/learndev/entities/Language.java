package fr.caemur.learndev.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Language {
    /**
     * The name of the programming language
     */
    public String name;

    /**
     * Names aliases, such as abbreviations or nicknames
     * <p>
     * Ex: Golang for Go or JS for Javascript
     */
    public List<String> aliases;

    /**
     * A short description of the language
     */
    public String description;

    /**
     * The language's logo (displayed on embeds)
     */
    public String image;

    /**
     * A list of recommended courses/tutorials/documentations
     */
    public List<String> resources;

    /**
     * The list of discord ids of the people who contributed to this entry
     */
    public List<String> authors;

    /**
     * Create an empty language
     *
     * @param name The language's name
     */
    public Language(String name) {
        this.name = name;
        this.aliases = new ArrayList<>();
        this.description = "Aucune description définie";
        this.image = "";
        this.resources = new ArrayList<>();
        this.authors = new ArrayList<>();
    }

    /**
     * Get the list of resources, formatted for a discord embed
     */
    public String getResourcesAsString() {
        return resources.stream().map(s -> "- " + s).collect(Collectors.joining("\n"));
    }

    /**
     * Get the list of authors, formatted for a discord embed
     */
    public String getAuthors() {
        return authors.stream().map(s -> "<@" + s + ">").collect(Collectors.joining(" "));
    }
}
