package aman.EzDedline;
import aman.EzDedline.commands.Add_dl;
import com.mongodb.*;
import com.mongodb.client.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;


import java.awt.*;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Mongo_update {
    public static void main(String o_name ,String o_course, String n_name, String n_course, String n_time, String n_date)
    {
        final String uri = "";
        MongoClient mongoClient = MongoClients.create(uri);

        MongoDatabase DB = MongoClients.create().getDatabase("DB");
        MongoCollection<Document> collection = DB.getCollection("collection1");




        BasicDBObject searchQuery = new BasicDBObject();




        if(n_name.compareTo("/") == 0 && n_course.compareTo("/") == 0 && n_date.compareTo("/") == 0 && n_time.compareTo("/") == 0)
        {
            ;
        }
        else{
        if(n_name.compareTo("/") == 0 || n_course.compareTo("/") == 0 || n_date.compareTo("/") == 0  || n_time.compareTo("/")== 0 );
        {


            searchQuery.append("name", o_name).append("course", o_course);

            FindIterable<Document> it = collection.find(searchQuery);
            MongoCursor<Document> cur = it.iterator();
            Document update = new Document();
            while(cur.hasNext())
            {
                update = cur.next();
                break;
            }

            if (n_name.compareTo("/") == 0)
                n_name = (String) update.get("name");
            if (n_course.compareTo("/") == 0)
                n_course = (String) update.get("course");
            if (n_date.compareTo("/") == 0)
                n_date = ((String) update.get("dat_tm")).split(" ")[1];
            if (n_time.compareTo("/") == 0)
                n_time = ((String) update.get("dat_tm")).split(" ")[0];

        }

        String n_dat_tm = n_time + " " + n_date;

            Document update = new Document();
            update.append("name", n_name).append("course", n_course).append("dat_tm", n_dat_tm);

            Document search = new Document();
            search.append("name", o_name).append("course", o_course);


        collection.updateOne(search,new Document("$set", update));

        }

        mongoClient.close();

    }
}
