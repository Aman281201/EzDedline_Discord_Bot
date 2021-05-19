package aman.EzDedline;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;

import java.awt.*;
import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Mongo_remove {

    public static int main(String name , String course, int key, String serverDB, GuildMessageReceivedEvent event)
    {
        final String uri = "mongodb+srv://amank:aman@cluster0.7wsis.mongodb.net/DB?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);

        MongoDatabase DB = MongoClients.create().getDatabase(serverDB);
        MongoCollection<Document> collection = DB.getCollection("Deadline_data");

        FindIterable<Document> it = collection.find();
        MongoCursor<Document> cur = null;
        try {
            cur = it.iterator();
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



        if(key == 1) {

            BasicDBObject del = new BasicDBObject("name", name);
            del.append("course", course);

            collection.deleteOne(del);
        }
        else if(key == 2)
        {

            String dat_tm , n, c;

            while(cur.hasNext())
            {

                Document removeQuery = new Document();
                removeQuery = cur.next();

                n = (String) removeQuery.get("name");

                c = (String) removeQuery.get("course");

                dat_tm = (String) removeQuery.get("dat_tm");


                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
                Date date1 = new Date(); // current date
                Date date2 = null;
                try {
                    date2 = sdf.parse(dat_tm);
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
                if(date1.after(date2))
                {
                    BasicDBObject remove = new BasicDBObject();
                    remove.append("name", n).append("course", c);

                    Document doc = new Document();
                    doc.append("name", n).append("course", c);

                    collection.deleteOne(remove);
                }



            }
        }

        else if(key == 3)
        {
            BasicDBObject del_all = new BasicDBObject();
            collection.deleteMany(del_all);
        }

        mongoClient.close();
        return 1;
    }
}
