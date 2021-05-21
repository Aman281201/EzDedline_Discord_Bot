package aman.EzDedline;
import aman.EzDedline.commands.Add_dl;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.WriteConcern;
import com.mongodb.client.*;
import com.mongodb.MongoCredential;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import com.mongodb.MongoClientSettings;

import com.mongodb.ServerAddress;


import java.awt.*;
import java.net.UnknownHostException;

public class Mongo_add {
    public static int main(String serverDB, GuildMessageReceivedEvent event) throws UnknownHostException
    {
        final String uri = "mongodb+srv://amank:aman@cluster0.7wsis.mongodb.net/DB?retryWrites=true&w=majority";

        MongoClient mongoClient = MongoClients.create(uri);

        MongoDatabase DB = MongoClients.create().getDatabase(serverDB);
        MongoCollection<Document> collection = DB.getCollection("Deadline_data");

        Add_dl ad = new Add_dl();
        String name = ad.name;
        String course = ad.course;
        String dat_tm = ad.date_time;


        Document doc = new Document();


        doc.append("name", name);
        doc.append("course",course);
        doc.append("dat_tm", dat_tm);
        doc.append("user", event.getMember().getUser().getId());

        System.out.println(event.getMember().getUser().getId());

        try{
            collection.insertOne(doc);
        }
        catch (Exception e)
        {if(e.toString().startsWith("com.mongodb.MongoTimeoutException: Timed out after 30000 ms while waiting to connect."))
        {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(Color.decode("#ff4d4d"));
            error.setTitle("Server Error");
            error.setDescription("Our Servers are either undergoing construction or are offline at this moment. Please try again later");
            error.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendMessage(error.build()).queue();


        }
        else
        {
            EmbedBuilder error = new EmbedBuilder();
            error.setColor(Color.decode("#ff4d4d"));
            error.setTitle("Unknown error occurred");
            error.setDescription("Some unknown error occured make sure you entered data correctly");
            error.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendMessage(error.build()).queue();
        }
        return 0;
        }

        mongoClient.close();
        return 1;
    }


}