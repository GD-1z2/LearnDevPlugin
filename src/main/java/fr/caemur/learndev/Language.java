package fr.caemur.learndev;

import java.util.List;

public class Language {
    /**
     * The name of the programming language
     */
    public String name;

    /**
     * Names aliases, such as abbreviations or nicknames
     *
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
}
