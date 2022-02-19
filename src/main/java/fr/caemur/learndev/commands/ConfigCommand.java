package fr.caemur.learndev.commands;

import fr.caemur.learndev.LearnDevPlugin;
import fr.caemur.learndev.entities.Language;
import fr.ordinalteam.bot.api.commands.Command;
import fr.ordinalteam.bot.api.utils.EmbedUtil;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConfigCommand extends Command {
    private static final String SYNTAX = "!ldconfig <set|add|del> <langage> <?information> <?valeur>";
    private static final String SYNTAX_SET = "!ldconfig set <langage> <name|alias|desc|img|rsc|authors> <valeur...>";

    private final LearnDevPlugin plugin;

    public ConfigCommand(LearnDevPlugin plugin) {
        super("ldconfig", "Permet de configurer les langages du plugin LearnDev");
        this.plugin = plugin;
    }

    @Override
    public void run(MessageReceivedEvent event, String[] args) {
        // Return an error if the user is not authorised to use the command
        if (!plugin.getConfigManager().isModerator(event.getAuthor().getId())) {
            event.getMessage().replyEmbeds(EmbedUtil.buildEmbed(
                EmbedUtil.EmbedEnum.ERROR,
                "Vous n'avez pas la permission d'utiliser cette commande",
                "Cette commande est réservée aux personnes inscrites dans la liste des modérateurs du plugin"
            ).build()).queue();

            return;
        }

        // Return an error if there are less than 3 arguments ("ldconfig", the subcommand and the language name)
        if (args.length < 3) {
            event.getMessage().replyEmbeds(EmbedUtil.buildEmbed(
                EmbedUtil.EmbedEnum.ERROR,
                "Nombre de paramètre invalide",
                SYNTAX
            ).build()).queue();

            return;
        }

        String command = args[1], languageName = args[2];

        if (command.equalsIgnoreCase("set")) {
            // Return an error if there are less than 5 arguments ("ldconfig", "set", language name, info and value)
            if (args.length < 5) {
                event.getMessage().replyEmbeds(EmbedUtil.buildEmbed(
                    EmbedUtil.EmbedEnum.ERROR,
                    "Nombre de paramètre invalide",
                    SYNTAX_SET
                ).build()).queue();

                return;
            }

            final String info = args[3];
            final Language language = plugin.getLanguageManager().byName(languageName);

            // Return an error if the language the user is trying to update doesn't exist
            if (language == null) {
                event.getMessage().replyEmbeds(EmbedUtil.buildEmbed(
                    EmbedUtil.EmbedEnum.ERROR,
                    "Ce langage n'existe pas",
                    "Pour le créer : `!ldconfig add " + languageName + "`"
                ).build()).queue();

                return;
            }

            if (info.equalsIgnoreCase("name")) {
                final String newName = args[4];

                // Return an error if the name is already used by another language
                final Language existingLanguage = plugin.getLanguageManager().byName(newName);

                if (existingLanguage != null) {
                    event.getMessage().replyEmbeds(EmbedUtil.buildEmbed(
                        EmbedUtil.EmbedEnum.ERROR,
                        "Le langage " + newName + " existe déjà",
                        "Pour le voir : `!learn " + existingLanguage.name + "`"
                    ).build()).queue();

                    return;
                }

                language.name = newName;
            } else if (info.equalsIgnoreCase("alias") || info.equalsIgnoreCase("aliases")) {
                final List<String> newAliases = new ArrayList<>();

                // Check if each of the new aliases is unique
                for (int i = 4; i < args.length; i++) {
                    final String newAlias = args[i];

                    final Language existingLanguage = plugin.getLanguageManager().byName(newAlias);

                    if (existingLanguage != null) {
                        event.getMessage().replyEmbeds(EmbedUtil.buildEmbed(
                            EmbedUtil.EmbedEnum.ERROR,
                            "Le langage " + existingLanguage.name + " porte déjà ce nom",
                            "Pour le voir : `!learn " + existingLanguage.name + "`"
                        ).build()).queue();

                        return;
                    }

                    newAliases.add(newAlias);
                }

                language.aliases = newAliases;
            } else if (info.equalsIgnoreCase("desc") || info.equalsIgnoreCase("description")) {
                language.description = String.join(" ", Arrays.copyOfRange(args, 4, args.length));
            } else if (info.equalsIgnoreCase("img") || info.equalsIgnoreCase("image")) {
                language.image = args[4];
            } else if (info.equalsIgnoreCase("rsc") || info.equalsIgnoreCase("resources")) {
                language.resources = Arrays.asList(Arrays.copyOfRange(args, 4, args.length));
            } else if (info.equalsIgnoreCase("authors")) {
                language.authors = Arrays.asList(Arrays.copyOfRange(args, 4, args.length));
            }

            // Save the languages list after updating
            plugin.getLanguageManager().save();

        } else if (command.equalsIgnoreCase("add")) {
            final Language existingLanguage = plugin.getLanguageManager().byName(languageName);

            // Return an error if the provided name is already used
            if (existingLanguage != null) {
                event.getMessage().replyEmbeds(EmbedUtil.buildEmbed(
                    EmbedUtil.EmbedEnum.ERROR,
                    "Le langage " + languageName + " existe déjà",
                    "Pour le voir : `!learn " + existingLanguage.name + "`"
                ).build()).queue();

                return;
            }

            // Otherwise, add the language and save
            plugin.getLanguageManager().addLanguage(new Language(languageName));
        } else if (command.equalsIgnoreCase("del")) {
            final Language language = plugin.getLanguageManager().byName(languageName);

            // Return an error if the language the user wants to delete doesn't exist
            if (language == null) {
                event.getMessage().replyEmbeds(EmbedUtil.buildEmbed(
                    EmbedUtil.EmbedEnum.ERROR,
                    "Le langage " + languageName + " n'existe pas", ""
                ).build()).queue();

                return;
            }

            // Otherwise, remove the language and save
            plugin.getLanguageManager().removeLanguage(language);
        } else {
            // Unknown subcommand
            event.getMessage().replyEmbeds(EmbedUtil.buildEmbed(
                EmbedUtil.EmbedEnum.ERROR,
                "Paramètre \"" + command + "\" invalide",
                SYNTAX
            ).build()).queue();
        }
    }
}
