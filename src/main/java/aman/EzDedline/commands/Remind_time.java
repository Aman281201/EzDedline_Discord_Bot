package aman.EzDedline.commands;

import aman.EzDedline.Main;
import aman.EzDedline.Mongo_add;
import aman.EzDedline.Reminder;
import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;

public class Remind_time extends ListenerAdapter {

    public static String name;
    public static String course;


    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        //String serverID = event.getGuild().getId();


        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Main.prefix + "remindTime"))
        {
            int hrs;
            String name = "", course = "";

            if(args.length < 2) {
                EmbedBuilder rtime = new EmbedBuilder();
                rtime.setThumbnail("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/10e165e5-1c65-443e-b5c6-ee4edc64fdcc/dahsfae-75cf11ac-9c4e-4ead-840b-456e7b6cdeba.png/v1/fill/w_1024,h_1449,strp/midnight_lycanroc_vector__by_hikari_the_wolfdog_dahsfae-fullview.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MTQ0OSIsInBhdGgiOiJcL2ZcLzEwZTE2NWU1LTFjNjUtNDQzZS1iNWM2LWVlNGVkYzY0ZmRjY1wvZGFoc2ZhZS03NWNmMTFhYy05YzRlLTRlYWQtODQwYi00NTZlN2I2Y2RlYmEucG5nIiwid2lkdGgiOiI8PTEwMjQifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6aW1hZ2Uub3BlcmF0aW9ucyJdfQ.xkXs2sPIMshuczRGiwwqztKykNLqNGc6zcRBmWtfHAs");
                rtime.setTitle("\uD83C\uDF34Set up Remind Time ");
                rtime.setDescription("Commands");
                rtime.addField("{remindTime <number of hours>", "to set up default remind time",false);
                rtime.addField("{remindTime <number of hours> <deadline_name> <course>", "to set up exclusive remind time for a particular deadline",false);
                rtime.addField("{remindTime clear","to reset remind time data", false);
                rtime.setColor(Color.decode("#d4b259"));
                rtime.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(rtime.build()).queue();
                rtime.clear();
            }
            else {
                final String uri = "mongodb+srv://amank:aman@cluster0.7wsis.mongodb.net/DB?retryWrites=true&w=majority";

                String serverDB = event.getGuild().getId();
                MongoClient mongoClient = MongoClients.create(uri);

                MongoDatabase DB = MongoClients.create().getDatabase(serverDB);
                MongoCollection<Document> collection = DB.getCollection("remind_data");
                MongoCollection<Document> collection2 = DB.getCollection("Deadline_data");
                MongoCursor<Document> cur = null;

                hrs = 2;

                if(args[1].equalsIgnoreCase("clear"))
                {
                    collection.deleteMany(new BasicDBObject());
                    collection.insertOne(new Document().append("name","Default").append("time",String.valueOf(hrs)));

                    EmbedBuilder success = new EmbedBuilder();
                    success.setColor(Color.decode("#80ff80"));
                    success.setTitle("Success");
                    success.setDescription("Successfully cleared remind time data");
                    success.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                    event.getChannel().sendMessage(success.build()).queue();
                    return;
                }


                try {
                    hrs = Integer.parseInt(args[1]);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    EmbedBuilder err = new EmbedBuilder();
                    err.setTitle("Make sure you entered data correctly");
                    err.setDescription("Number of hours must be numeric values");
                    err.setColor(Color.decode("#ff4d4d"));
                    err.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(err.build()).queue();
                    err.clear();
                    return;

                }


                if (args.length < 3) {

                    BasicDBObject searchDefault = new BasicDBObject().append("name", "Default");
                    FindIterable<Document> it =  collection.find(searchDefault);
                    cur = it.iterator();
                    if(cur.hasNext())
                    {
                        collection.updateOne(new Document().append("name","Default"),new Document("$set", new Document().append("name","Default").append("time",hrs)));
                    }
                    else
                    {
                        collection.insertOne(new Document().append("name","Default").append("time",hrs));
                    }


                    EmbedBuilder success = new EmbedBuilder();
                    success.setColor(Color.decode("#80ff80"));
                    success.setTitle("Success");
                    success.setDescription("Successfully updated default remind time to deadline " + hrs + " hours");
                    success.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                    event.getChannel().sendMessage(success.build()).queue();


                } else if (args.length < 5) {
                    name = args[2];
                    course = args[3];
                    MongoCursor<Document> cursor = null;

                    BasicDBObject checkData = new BasicDBObject().append("name", name).append("course",course);
                    FindIterable<Document> iterable =  collection2.find(checkData);
                    cursor = iterable.iterator();
                    if(!cursor.hasNext())
                    {
                        EmbedBuilder err = new EmbedBuilder();
                        err.setTitle("Please check the data that you entered");
                        err.setDescription("Deadline of this name and course does not exist");
                        err.setColor(Color.decode("#ff4d4d"));
                        err.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                        event.getChannel().sendTyping().queue();
                        event.getChannel().sendMessage(err.build()).queue();
                        err.clear();
                        return;
                    }
                    else
                    {


                    BasicDBObject searchQuery = new BasicDBObject().append("name", name).append("course",course);
                    FindIterable<Document> it =  collection.find(searchQuery);
                    cur = it.iterator();
                    if(cur.hasNext())
                    {
                        collection.updateOne(new Document().append("name",name).append("course",course), new Document("$set", new Document().append("name",name).append("course",course).append("time",hrs)));
                    }
                    else
                    {
                        collection.insertOne(new Document().append("name",name).append("course", course).append("time",hrs));
                    }


                    EmbedBuilder success = new EmbedBuilder();
                    success.setColor(Color.decode("#80ff80"));
                    success.setTitle("Success");
                    success.setDescription("Successfully updated default remind time for deadline " + name + " of " + course);
                    success.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                    event.getChannel().sendMessage(success.build()).queue();


                }


            }

            }

        }
    }
}


