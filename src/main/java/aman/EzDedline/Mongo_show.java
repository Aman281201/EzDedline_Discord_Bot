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

        int count = 0;

        EmbedBuilder show_2 = new EmbedBuilder();
        int l = 0;
        if (t != 0) {
            // weekly and daily deadlines
            String  dat_tm, name, course;

            while (cursor.hasNext()) {
                //DBObject field =  cursor.next();
                Document searchQuery = new Document();
                searchQuery = cursor.next();

                dat_tm = (String) searchQuery.get("dat_tm");

                name = (String) searchQuery.get("name");
                course = (String) searchQuery.get("course");


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
                    date_end = date_end.substring(0, 6) + Integer.toString(d) + "/0" + Integer.toString(m) + date_end.substring(11);



                try {
                    Date date3 = sdf.parse(date_end);
                    if (date1.after(date2) && date1.before(date3)) {

                        l++;
                        show_2.addField("[" + l + "] " + name + " " + course, "Due on " + dat_tm, false);
                        count++;
                    }


                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
        } else {
            String name , course , dat_tm;

            while (cursor.hasNext()) {
                //DBObject field = (DBObject) cursor.next();

                Document showQuery = new Document();
                showQuery = cursor.next();

                name = (String) showQuery.get("name");
                course = (String) showQuery.get("course");
                dat_tm = (String) showQuery.get("dat_tm");


                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
                Date date1 = null;

                try {
                   date1 = sdf.parse(dat_tm);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                Date date2 = new Date();

                if (date1.after(date2)) {

                    l++;
                    show_2.addField("[" + l + "] " + name + " " + course, "Due on " + dat_tm, false);
                    count++;
                }

            }


        }

        if(count == 0 && t == 1)
            show_2.setTitle("No Deadlines in next 24 hours");
        else if(count == 0 && t == 7)
            show_2.setTitle("No Deadlines left in this week");
        else if(count == 0 && t == 0)
            show_2.setTitle("No incomplete deadlines in future");
        else
            show_2.setTitle("You have the following deadlines to complete");

        show_2.setColor(Color.decode("#6666ff"));
        show_2.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

        event.getChannel().sendTyping().queue();
        event.getChannel().sendMessage(show_2.build()).queue();
        show_2.clear();

        mongoClient.close();
    }
}