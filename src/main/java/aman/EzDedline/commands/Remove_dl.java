package aman.EzDedline.commands;

import aman.EzDedline.Main;
import aman.EzDedline.Mongo_remove;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Remove_dl extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String serverID = event.getGuild().getId();

        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Main.prefix + "remove"))
        {

            if(args.length >= 2) {
                String name = "", course = "";
                int y = 0, x = 1;
                try {
                    if(args.length > 2) {
                        name = args[1];
                        course = args[2];
                        y = 1;
                        Mongo_remove mongo_remove = new Mongo_remove();

                         x = mongo_remove.main(name, course, y, serverID, event);
                        }
                    else {
                        if (args[1].equalsIgnoreCase( "completed")) {
                            y = 2;}
                        else if(args[1].equalsIgnoreCase("all")){
                            y = 3;
                        }
                            Mongo_remove mongo_remove = new Mongo_remove();
                          x =  mongo_remove.main("abc", "xyz", y, serverID, event);



                    }
                    if(x == 0)
                        return;
                    if(y!=0){

                        EmbedBuilder success = new EmbedBuilder();
                        success.setColor(Color.decode("#80ff80"));
                        success.setTitle("Successful");
                        if(y == 1)
                            success.setDescription("Successfully removed deadline " + name + " of course " + course);
                        else if(y==2 || y == 3)
                            success.setDescription("Successfully removed deadlines ");

                        success.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                        event.getChannel().sendMessage(success.build()).queue();
                    }
                    else
                    {
                            EmbedBuilder err = new EmbedBuilder();
                            err.setTitle("Information entered is either wrong or in wrong format");
                            err.setDescription("Enter in following format {remove <Name> <course> or {remove completed or {remove all");
                            err.setColor(Color.decode("#ff4d4d"));
                            err.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                            event.getChannel().sendTyping().queue();
                            event.getChannel().sendMessage(err.build()).queue();
                            err.clear();

                    }

                    }
                catch (IllegalArgumentException e)
                {
                    EmbedBuilder err = new EmbedBuilder();
                    err.setTitle("Information entered is either wrong or in wrong format");
                    err.setDescription("Enter in following format {remove <Name> <course> or {remove completed or {remove all");
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
                remove.setThumbnail("https://th.bing.com/th/id/R9fa0ea9813187d4771485a622a5bbddb?rik=650TnlKz%2bMfnmQ&riu=http%3a%2f%2fgetdrawings.com%2fvectors%2fpikachu-vector-23.png&ehk=cnWD9yRPCtKhfx1RBz6IMFZF1REFGwjnkxhg69lybFk%3d&risl=&pid=ImgRaw");
                remove.setTitle("\uD83C\uDF2DRemove Deadline ");
                remove.setDescription("Enter the following commands for precise results");
                remove.addField("{remove <Name> <Course>", "to remove the deadline", false);
                remove.addField("{remove completed", "removes all deadlines that are over", false);
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
