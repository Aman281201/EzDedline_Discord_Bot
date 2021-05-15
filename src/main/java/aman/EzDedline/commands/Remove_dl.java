package aman.EzDedline.commands;

import aman.EzDedline.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Remove_dl extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Main.prefix + "delete"))
        {

            if(args.length >= 3) {
                String name = args[1], course = args[2];
                try {




                    EmbedBuilder success = new EmbedBuilder();
                    success.setColor(Color.decode("#80ff80"));
                    success.setTitle("Successful");
                    success.setDescription("Successfully removed deadline " + name + " of course " + course );
                    success.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                    event.getChannel().sendMessage(success.build()).queue();

                }
                catch (IllegalArgumentException e)
                {
                    EmbedBuilder err = new EmbedBuilder();
                    err.setTitle("Information entered is either wrong or in wrong format");
                    err.setDescription("Enter in following format {remove <Name> <course>");
                    err.setColor(Color.decode("#ff4d4d"));
                    err.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(err.build()).queue();
                    err.clear();
                }

            }

            else
            {
                EmbedBuilder remove = new EmbedBuilder();
                remove.setTitle("Remove Deadline ");
                remove.setDescription("\uD83D\uDC31\u200D\uD83D\uDC09Enter the following commands for precise results");
                remove.addField("{remove <Name> <Course>", "to remove the deadline", false);
                remove.addField("{remove all", "to clear all the deadlines", false);
                remove.setColor(Color.decode("#3366ff"));
                remove.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(remove.build()).queue();
                remove.clear();
            }
        }
    }
}
