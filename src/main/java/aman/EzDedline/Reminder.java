package aman.EzDedline;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;

import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import net.dv8tion.jda.api.entities.Activity;


public class Reminder {
    public static String inc_date(String time, int h)
    {
        String inc_date;
        int t = Integer.parseInt(time.substring(0,5));
        int m = Integer.parseInt(time.substring(9,11));
        int d = Integer.parseInt(time.substring(6,8));
        int y = Integer.parseInt(time.substring(13));
        
        
        if( t < 24 - h && t >= 10 - h)
        {
            inc_date = (t+h) + time.substring(2);
        }
        else if(t < 10 - h)
        {
            inc_date = time.substring(0,1) + (t+h) + time.substring(2);
        }
        else
        {
            d = d + 1; // to increment the date
            if (m == 2) {
                if (y % 4 == 0) {
                    if (d > 29) {
                        d = 29 - d;
                        m = m + 1;
                    }
                } else if (d > 28) {
                    d = 28 - d;
                    m = m + 1;
                }
            } else if (m == 4 || m == 6 || m == 9 || m == 11) {
                if (d > 30) {
                    d = 30 - d;
                    m = m + 1;
                }
            } else if (d > 31) {
                d = 31 - d;
                m = m + 1;
            }
            
            if (m >= 10)
                if (d >= 10)
                    inc_date = (t - 24 + h) + time.substring(2, 6) + (d) + "/" + (m) + time.substring(11);
                else
                    inc_date = (t - 24 + h) + time.substring(2, 7) + (d) + "/" + (m) + time.substring(11); 
            else
                inc_date = (t - 24 + h) + time.substring(2, 6) + (d) + "/0" + (m) + time.substring(11);

        }

        return inc_date;
    }

    public void async_remind(GuildMessageReceivedEvent event, String name, String course, String dat_tm) {

        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
            Date date_now = new Date();

            String remind_time = inc_date(dat_tm,2);
            Date remind_date = null;

            try {
                remind_date = sdf.parse(remind_time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long time_span = remind_date.getTime() - date_now.getTime();
            EmbedBuilder reminder = new EmbedBuilder();
            reminder.setColor(Color.decode("#ccff00"));
            reminder.setTitle("Reminder for Deadline");
            reminder.setDescription("Deadline for " + name + " of " + course + " ends in 2 Hours");
            event.getGuild().getDefaultChannel().sendTyping().queue();
            event.getGuild().getDefaultChannel().sendMessage(reminder.build()).queue();

        });
        future.thenAccept(System.out::println);





    }

}
