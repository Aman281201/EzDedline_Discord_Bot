package aman.EzDedline;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;

import java.awt.*;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import net.dv8tion.jda.api.entities.Activity;


public class Reminder {

    public void async_remind(GuildMessageReceivedEvent event, String name, String course, String dat_tm) {


        CompletableFuture.runAsync(() -> {

            Guild guild = event.getGuild();
            TextChannel sendChannel = null;
            if(guild.getTextChannelsByName("ezdedline_reminder",true).isEmpty())
            {
                sendChannel = event.getChannel();
            }
            else{
                sendChannel = guild.getTextChannelsByName("ezdedline_reminder",true).get(0);
            }



            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
            Date date_now = new Date();


            Date deadline_date = null;

            try {
                deadline_date = sdf.parse(dat_tm);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            long time_span = deadline_date.getTime() - date_now.getTime() - 2*3600000;
            if(time_span < 0)
                time_span = 0;

            try {
                Thread.sleep(time_span);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            EmbedBuilder reminder = new EmbedBuilder();
            reminder.setThumbnail("https://img00.deviantart.net/8967/i/2014/146/1/f/pokemon_003m___mega_venusaur_by_illustrationoverdose-d7jt58g.png");
            reminder.setColor(Color.decode("#ccff00"));
            reminder.setTitle("Reminder for Deadline");
            if(time_span!= 0)
            reminder.setDescription("Deadline for " + name + " of " + course + " ends in 2 Hours");
            else
                reminder.setDescription("Deadline for " + name + " of " + course + " ends in less than 2 Hours");
            event.getGuild().getDefaultChannel().sendTyping().queue();
            //reminder.setThumbnail("https://th.bing.com/th/id/R726966fbcce6f698ac8627ab524be42b?rik=0yhERuxHf8xs5Q&riu=http%3a%2f%2fimg09.deviantart.net%2f91a2%2fi%2f2012%2f309%2ff%2fa%2fcharizard_by_cornmanthe3rd-d5k35vm.png&ehk=yrbLiTbmg2RgzSl3fozRfOOpRxDhClpSXMwO6mC5OR4%3d&risl=&pid=ImgRaw");
            sendChannel.sendMessage(reminder.build()).queue();
            sendChannel.sendMessage(event.getGuild().getRolesByName("@everyone",false).get(0).getAsMention()).queue();




        });






    }

}
