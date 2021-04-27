package aman.EzDedline;
import aman.EzDedline.commands.Add_dl;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoCredential;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;


import javax.swing.text.Document;

public class Mongo {
    public static void main(String[] args)
    {
    // connect to local instance in unsecured mode
    String uri = "mongodb+srv://amank:aman@ezdedlinecluster.7wsis.mongodb.net/DB?retryWrites=true&w=majority";
    MongoClient mongoClient = MongoClients.create(uri);

    MongoDatabase DB = MongoClients.create().getDatabase("DB");
    MongoCollection collection = DB.getCollection("collection1");

    Add_dl ad = new Add_dl();
    String name = ad.name;
    String course = ad.course;
    String dat_tm = ad.date_time;

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("course", course);
        jsonObject.put("dat_tm", dat_tm);

        collection.insertOne(jsonObject);



    }
}
