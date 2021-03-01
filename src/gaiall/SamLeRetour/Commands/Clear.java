package gaiall.SamLeRetour.Commands;

import gaiall.SamLeRetour.SamLeRetour;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Clear extends ListenerAdapter {
    public static String commandName = "clear";
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(SamLeRetour.prefix + commandName)) {
            List<Message> messages;
            try {
                if (Integer.parseInt(args[1]) < 2) {
                    //Pas d'arg = 1 message a suppr
                    messages = event.getChannel().getHistory().retrievePast(1).complete();
                } else {
                    //Suppression de plusieurs messages
                    messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
                }
                //On supprime
                event.getChannel().deleteMessages(messages).queue();
                //message de reussite
                EmbedBuilder reussite = new EmbedBuilder();
                reussite.setColor(0x22ff2a);
                reussite.setTitle(messages.size() + " messages supprimés");
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(reussite.build()).queue();

            } catch (IllegalArgumentException e) {
                //Le embed pour afficher l'erreur
                EmbedBuilder error = new EmbedBuilder();
                error.setColor(0xff3923);

                if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval")) {
                    //Si > 100 messages a supprimer, erreur
                    error.setTitle("Trop de messages sélectionnés");
                    error.setDescription("Limite de messages à sélectionner: entre 1 et 100");
                } else {
                    //Si messages plus vieux que 2 semaines, erreur
                    error.setTitle("Les messages sélectionnés sont trop vieux");
                    error.setDescription("Les messages datant de plus de 2 semaines ne peuvent pas être supprimés");
                }
                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(error.build()).queue();
            }
        }
    }
}
