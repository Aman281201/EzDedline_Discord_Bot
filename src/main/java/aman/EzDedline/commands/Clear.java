package aman.EzDedline.commands;
import java.awt.*;
import java.util.List;

import aman.EzDedline.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Clear extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Main.prefix + "clear"))
        {
            if(args.length < 2){
                //TODO error
            }
            else {
                try {
                    List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
                    event.getChannel().deleteMessages(messages).queue();

                    EmbedBuilder success = new EmbedBuilder();
                    success.setColor(Color.decode("#80ff80"));
                    success.setTitle("Success");
                    success.setDescription("Successfully deleted " + args[1] + " messages");
                    success.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                    event.getChannel().sendMessage(success.build()).queue();

                }
                catch (IllegalArgumentException e){
                    if(e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval"))
                    {
                        //Too many messages
                        EmbedBuilder error = new EmbedBuilder();
                        error.setColor(Color.decode("#ff4d4d"));
                        error.setTitle("Too many messages selected for deletion");
                        error.setDescription("You can only delete between 1 and 100 messages, no more, no less");
                        error.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                        event.getChannel().sendMessage(error.build()).queue();
                    }

                    else {
                        //Too old messages
                        EmbedBuilder usage = new EmbedBuilder();
                        usage.setColor(Color.decode("#ff4d4d"));
                        usage.setTitle("Selected messages are too old to be deleted");
                        usage.setDescription("Messages older than 2 weeks can't be deleted");
                        usage.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                        event.getChannel().sendMessage(usage.build()).queue();

                    }
                }
            }
        }
    }
}
