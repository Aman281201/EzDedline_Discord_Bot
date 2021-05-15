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

public class Mongo_update {
    public static void main(String[] args)
    {
        String uri = "mongodb+srv://amank:aman@cluster0.7wsis.mongodb.net/DB?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);

        MongoDatabase DB = MongoClients.create().getDatabase("DB");
        MongoCollection<Document> collection = DB.getCollection("collection1");

        FindIterable<Document> it = collection.find();

        MongoCursor<Document> cur = it.iterator();


    }
}
