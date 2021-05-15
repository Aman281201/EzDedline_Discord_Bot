package aman.EzDedline;
import aman.EzDedline.commands.Add_dl;
import com.mongodb.*;
import com.mongodb.client.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.awt.*;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mongo_show {

    public static void main(String args, int t, GuildMessageReceivedEvent event) throws UnknownHostException {
        String uri = "mongodb+srv://amank:aman@cluster0.7wsis.mongodb.net/DB?retryWrites=true&w=majority";

        MongoClient mongoClient = MongoClients.create(uri);

        MongoDatabase DB = MongoClients.create().getDatabase("DB");
        MongoCollection<Document> collection = DB.getCollection("collection1");
        FindIterable<Document> iterable =  collection.find();
        MongoCursor<Document> cursor = iterable.iterator();


        EmbedBuilder show_2 = new EmbedBuilder();
        int l = 0;
        if (t != 0) {
            // weekly and daily deadlines


            while (cursor.hasNext()) {
                //DBObject field =  cursor.next();
                System.out.println(cursor.next().get("dat_tm"));
                String dat_tm = (String) cursor.next().get("dat_tm");

                System.out.println(dat_tm);


                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
                Date date1 = null;

                try {
                    date1 = sdf.parse(dat_tm);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Date date2 = new Date();
                String date_end = sdf.format(date2);
                int d = Integer.parseInt(date_end.substring(6, 8));
                int m = date2.getMonth() + 1;
                int y = date1.getYear();
                d = d + t;
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
                        date_end = date_end.substring(0, 6) + Integer.toString(d) + "/" + Integer.toString(m) + date_end.substring(11);
                    else
                        date_end = date_end.substring(0, 7) + Integer.toString(d) + "/" + Integer.toString(m) + date_end.substring(11); //hh:mm dd/MM/yyyy
                else
                    date_end = date_end.substring(0, 7) + Integer.toString(d) + "/0" + Integer.toString(m) + date_end.substring(11);

                try {
                    Date date3 = sdf.parse(date_end);
                    if (date1.after(date2) && date1.before(date3)) {

                        String name = (String) cursor.next().get("name");
                        String course = (String) cursor.next().get("course");
                        l++;
                        show_2.addField("[" + l + "] " + name + " " + course, "Due on " + dat_tm, false);
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        } else {
            while (cursor.hasNext()) {
                //DBObject field = (DBObject) cursor.next();

                String dat_tm = (String) cursor.next().get("dat_tm");
                String name = (String) cursor.next().get("name");
                String course = (String) cursor.next().get("course") ;

                System.out.println(name + "  " + course);



                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
                Date date1 = null;

                try {
                   date1 = sdf.parse(dat_tm);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Date date2 = new Date();

                System.out.println("date1 "+ date1);
                System.out.println("  \n");
                System.out.println("date2 " + date2);
                if (date1.after(date2)) {
                    System.out.println("****** hey");

                    l++;
                    show_2.addField("[" + l + "] " + name + " " + course, "Due on " + dat_tm, false);
                }

            }


        }

        show_2.setColor(Color.decode("#6666ff"));
        show_2.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage(show_2.build()).queue();
        show_2.clear();
    }
}