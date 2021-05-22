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
        String serverID = event.getGuild().getId();
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
                        show_data.main( 1, event, serverID);
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
                        show_data.main(7, event, serverID);
                    }

                    catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
                else if(args[1].equalsIgnoreCase("all"))
                {
                    Mongo_show show_data = new Mongo_show();
                    try {
                        show_data.main( 0, event,serverID);
                    }

                    catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }

            }
            else {
                EmbedBuilder show = new EmbedBuilder();
                show.setThumbnail("https://th.bing.com/th/id/R026e648eb4e34394db24db00a16becac?rik=oMVX9YVftznbiQ&riu=http%3a%2f%2fth06.deviantart.net%2ffs70%2fPRE%2fi%2f2014%2f016%2f9%2f9%2fbulbasaur_vector_by_pokinee-d72gf6c.png&ehk=wlKKnFg2oS6M60px12bncDc2oK4ajxR%2fy2QESV1IvSE%3d&risl=&pid=ImgRaw");
                show.setTitle("\uD83D\uDC1EShow Deadlines ");
                show.setDescription("enter following commands for precise results");
                show.addField("{show today", "to see today's deadlines", false);
                show.addField("{show weekly", "to see deadlines of this week", false);
                show.addField("{show all", "to see all future deadlines", false);
                show.setColor(Color.decode("#6666ff"));
                show.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(show.build()).queue();
                show.clear();
            }




        }
    }

}
