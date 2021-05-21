package aman.EzDedline.commands;

import aman.EzDedline.Main;
import aman.EzDedline.Mongo_add;
import aman.EzDedline.Reminder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;

public class Add_dl extends ListenerAdapter {

    public static String name;
    public static String course;
    public static String date_time;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String serverID = event.getGuild().getId();


        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Main.prefix + "add"))
        {
            if(args.length < 5) {
                EmbedBuilder add = new EmbedBuilder();
                add.setTitle("Add Deadline ");
                add.setDescription("Enter in following format {add <Name> <course> <Time(hh:mm)> <Date(dd/mm/yyyy)>");
                add.setColor(Color.decode("#99ffff"));
                add.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(add.build()).queue();
                add.clear();
            }
            else {
                Add_dl.name = args[1];
                Add_dl.course = args[2];
                Add_dl.date_time = args[3]+ " " + args[4];

                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
                try {
                    Date date = sdf.parse(date_time);
                    if(date.after(new Date())) {

                        Mongo_add data_mongo = new Mongo_add();
                        int x = data_mongo.main(serverID, event);

                        if(x == 0)
                            return;

                        EmbedBuilder success = new EmbedBuilder();
                        success.setColor(Color.decode("#80ff80"));
                        success.setTitle("Success");
                        success.setDescription("Successfully added deadline " + Add_dl.name + " of " + Add_dl.course);
                        success.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                        event.getChannel().sendMessage(success.build()).queue();

                        Reminder reminder = new Reminder();
                        reminder.async_remind(event,name,course,date_time);

                    }
                    else {
                        EmbedBuilder fail = new EmbedBuilder();
                        fail.setColor(Color.decode("#ff4d4d"));
                        fail.setTitle("Entered deadline is already over");
                        fail.setDescription("Entered deadline must not be one of past");
                        fail.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                        event.getChannel().sendMessage(fail.build()).queue();

                    }


                } catch (Exception e) {
                    EmbedBuilder err = new EmbedBuilder();
                    err.setTitle("Information entered is either wrong or in wrong format");
                    err.setDescription("Enter in following format {add <Name> <course> <Time(hh:mm)> <Date(dd/mm/yyyy)>");
                    err.setColor(Color.decode("#ff4d4d"));
                    err.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(err.build()).queue();
                    err.clear();
                }
            }

        }
    }
}


