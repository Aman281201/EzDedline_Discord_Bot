package aman.EzDedline.commands;

import aman.EzDedline.Main;
import aman.EzDedline.Mongo_show;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.net.UnknownHostException;

public class Show_dl extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Main.prefix + "show"))
        {
            if(args.length >= 2)
            {
                if(args[1].equalsIgnoreCase("today"))
                {
                    EmbedBuilder show_2 = new EmbedBuilder();
                    Mongo_show show_data = new Mongo_show();

                    try {
                        show_data.main("abc", 1, event);
                    }

                    catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
                else if(args[1].equalsIgnoreCase("weekly"))
                {
                    EmbedBuilder show_2 = new EmbedBuilder();
                    Mongo_show show_data = new Mongo_show();
                    try {
                        show_data.main("abc", 7, event);
                    }

                    catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
                else if(args[1].equalsIgnoreCase("all"))
                {
                    Mongo_show show_data = new Mongo_show();
                    try {
                        show_data.main("abc", 0, event);
                    }

                    catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }

            }
            else {
                EmbedBuilder show = new EmbedBuilder();
                show.setTitle("\uD83D\uDC31\u200D\uD83C\uDFCDShow Deadlines ");
                show.setDescription("enter following commands for precise results");
                show.addField("{show today", "to see today's deadlines", false);
                show.addField("{show weekly", "to see deadlines of this week", false);
                show.addField("{show all", "to see all the deadlines", false);
                show.setColor(Color.decode("#6666ff"));
                show.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(show.build()).queue();
                show.clear();
            }




        }
    }

}
