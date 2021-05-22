package aman.EzDedline.commands;

import aman.EzDedline.Main;
import aman.EzDedline.Mongo_update;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Update_dl extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        String serverID =  event.getGuild().getId();

        if(args[0].equalsIgnoreCase(Main.prefix + "update"))
        {

            if(args.length >= 7) {
                EmbedBuilder update = new EmbedBuilder();
                String new_name = args[3] , new_course = args[4] , new_date = args[6], new_time = args[5];
                String old_name = args[1] , old_course = args[2];

                try {

                    Mongo_update mongo_update = new Mongo_update();
                    int x = mongo_update.main(old_name, old_course,new_name, new_course,new_time, new_date, serverID, event);

                    if (x == 0)
                        return;

                    if (new_name.compareTo("/") == 0)
                        new_name = old_name;
                    if (new_course.compareTo("/") == 0)
                        new_course = old_course;

                    EmbedBuilder success = new EmbedBuilder();
                    success.setColor(Color.decode("#80ff80"));
                    success.setTitle("Successful");
                    success.setDescription("Successfully updated the deadline to " + new_name + " of " + new_course );
                    success.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                    event.getChannel().sendMessage(success.build()).queue();
                }
                catch (IllegalArgumentException e){
                    EmbedBuilder err = new EmbedBuilder();
                    err.setTitle("Information entered is either wrong or in wrong format");
                    update.addField("{update <o name> <o course> <n name> <n course> <n time(hh:mm)> <n date(dd/mm/yyyy)>", "o - old information, n - updated information", false);
                    update.addField("Use </> instead of <field>","To keep the field same as before", false);
                    err.setColor(Color.decode("#ff4d4d"));
                    err.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(err.build()).queue();
                    err.clear();

                }
            }
            else{
                EmbedBuilder update = new EmbedBuilder();
                //update.setThumbnail("https://th.bing.com/th/id/R851acb85b00e7ed1a21dd3a9baf6c9af?rik=0s7dKSr2pdZkYA&riu=http%3a%2f%2fimg13.deviantart.net%2f5112%2fi%2f2013%2f319%2f9%2f0%2fvaporeon_vector_by_pokinee-d6ub4lt.png&ehk=YBrgBMj4doWQOyjRhsGyK8MVIroPofdn3tVFtk%2fkLJ4%3d&risl=&pid=ImgRaw");
                update.setThumbnail("https://img00.deviantart.net/3381/i/2014/232/1/0/009_blastoise_by_pklucario-d7vy4xr.png");
                update.setTitle("\uD83C\uDF40Update Deadline ");
                update.setDescription("For updating the deadline");
                update.addField("{update <o name> <o course> <n name> <n course> <n time(hh:mm)> <n date(dd/mm/yyyy)>", "o - old information, n - updated information", false);
                update.addField("Use </> instead of <field>","To keep the field same as before", false);
                update.setColor(Color.decode("#a366ff"));
                update.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(update.build()).queue();
                update.clear();
            }
        }
    }
}
