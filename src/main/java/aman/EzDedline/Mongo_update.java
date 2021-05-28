package aman.EzDedline;
import com.mongodb.*;
import com.mongodb.client.*;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;


import java.awt.*;

public class Mongo_update {
    public static int main(String o_name ,String o_course, String n_name, String n_course, String n_time, String n_date, String serverDB, GuildMessageReceivedEvent event)
    {

            final String uri = "mongodb+srv://amank:aman@cluster0.7wsis.mongodb.net/DB?retryWrites=true&w=majority";
            MongoClient mongoClient = MongoClients.create(uri);

            MongoDatabase DB = MongoClients.create().getDatabase(serverDB);
            MongoCollection<Document> collection = DB.getCollection("Deadline_data");


            BasicDBObject searchQuery = new BasicDBObject();


            if (n_name.compareTo("/") == 0 && n_course.compareTo("/") == 0 && n_date.compareTo("/") == 0 && n_time.compareTo("/") == 0) {
                ;
            } else {
                if (n_name.compareTo("/") == 0 || n_course.compareTo("/") == 0 || n_date.compareTo("/") == 0 || n_time.compareTo("/") == 0)
                    ;
                {
                    MongoCursor<Document> cur = null;

                    searchQuery.append("name", o_name).append("course", o_course);
                    try {
                        FindIterable<Document> it = collection.find(searchQuery);
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

                    Document update = new Document();
                    while (cur.hasNext()) {
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


                collection.updateOne(search, new Document("$set", update));

            }

            mongoClient.close();
            return 1;

    }
}
