package fr.caemur.learndev;

import fr.ordinalteam.bot.api.commands.Command;
import fr.ordinalteam.bot.api.utils.ColorHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.stream.Collectors;

public class LearnCommand extends Command {
    public static final String SYNTAX = "!learn <langage>";

    private final Main plugin;

    public LearnCommand(Main plugin) {
        super("learn", "!learn <langage> : Affiche des ressources pour apprendre un langage de programmation");
        this.plugin = plugin;
    }

    @Override
    public void run(MessageReceivedEvent event, String[] args) {
        if (args.length != 1) {
            event.getMessage().replyEmbeds(new EmbedBuilder()
                    .setTitle("Nombre de paramètre invalide")
                    .setDescription(SYNTAX)
                    .addField(
                            "Liste des langages :",
                            String.join(", ", plugin.getLanguageManager().getLanguagesNames()),
                            false
                    )
                    .setColor(ColorHelper.ERROR)
                    .build()
            ).queue();

            return;
        }

        final var language = plugin.getLanguageManager().byName(args[0]);

        if (language == null) {
            event.getMessage().replyEmbeds(new EmbedBuilder()
                    .setTitle("Je ne connais pas ce langage")
                    .setDescription("Tu peux demander à un modérateur de l'ajouter")
                    .setThumbnail("https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/160/twitter/282/pensive-face_1f614.png")
                    .setColor(ColorHelper.ERROR)
                    .build()
            ).queue();

            return;
        }

        final var embed = new EmbedBuilder()
                .setTitle(language.name)
                .setDescription(language.description)
                .setThumbnail(language.image)
                .setColor(ColorHelper.DONE);

        embed.addField(
                "Ressources",
                language.resources.stream()
                        .map(s -> " - " + s)
                        .collect(Collectors.joining("\n")),
                false);

        embed.addField(
                "Auteurs :",
                language.authors.stream()
                        .map(s -> "<@" + s + ">")
                        .collect(Collectors.joining(" ")),
                false);

        event.getMessage().replyEmbeds(embed.build()).queue();
    }
}
