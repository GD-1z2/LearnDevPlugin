package fr.caemur.learndev.commands;

import fr.caemur.learndev.LearnDevPlugin;
import fr.caemur.learndev.entities.Language;
import fr.ordinalteam.bot.api.commands.Command;
import fr.ordinalteam.bot.api.utils.EmbedUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class LearnCommand extends Command {
    public static final String SYNTAX = "!learn <langage>";

    private final LearnDevPlugin plugin;

    public LearnCommand(LearnDevPlugin plugin) {
        super("learn", SYNTAX + " : Affiche des ressources pour apprendre un langage de programmation");
        this.plugin = plugin;
    }

    @Override
    public void run(MessageReceivedEvent event, String[] args) {
        if (args.length != 2) {
            event.getMessage().replyEmbeds(EmbedUtil.buildEmbed(
                EmbedUtil.EmbedEnum.ERROR,
                "Nombre de paramètre invalide",
                SYNTAX
            ).addField(
                "Liste des langages",
                plugin.getLanguageManager().hasLanguages()
                    ? String.join(", ", plugin.getLanguageManager().getLanguagesNames())
                    : "Aucun langage n'est défini",
                false
            ).build()).queue();

            return;
        }

        if (!plugin.getLanguageManager().hasLanguages()) {
            event.getMessage().replyEmbeds(EmbedUtil.buildEmbed(
                EmbedUtil.EmbedEnum.ERROR,
                "Erreur",
                "Aucun langage n'est défini"
            ).build()).queue();

            return;
        }

        final Language language = plugin.getLanguageManager().byName(args[1]);

        if (language == null) {
            event.getMessage().replyEmbeds(EmbedUtil.buildEmbed(
                EmbedUtil.EmbedEnum.ERROR,
                "Je ne connais pas ce langage",
                "Tu peux l'ajouter avec la commande `!suggest`"
            ).build()).queue();

            return;
        }

        final EmbedBuilder embed = EmbedUtil.buildEmbed(
                EmbedUtil.EmbedEnum.INFO,
                language.name,
                language.description)
            .addField("Ressources", language.getResourcesAsString(), false)
            .addField("Auteurs", language.getAuthors(), false);

        if (!language.image.isEmpty()) embed.setThumbnail(language.image);

        event.getMessage().replyEmbeds(embed.build()).queue();
    }
}
