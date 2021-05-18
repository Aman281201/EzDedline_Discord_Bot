package aman.EzDedline;
import aman.EzDedline.commands.Add_dl;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.WriteConcern;
import com.mongodb.client.*;
import com.mongodb.MongoCredential;
import org.bson.Document;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import com.mongodb.MongoClientSettings;

import com.mongodb.ServerAddress;



import java.net.UnknownHostException;

public class Mongo_add {
    public static void main(String args) throws UnknownHostException
    {
    final String uri = "";

    MongoClient mongoClient = MongoClients.create(uri);

    MongoDatabase DB = MongoClients.create().getDatabase("DB");
    MongoCollection<Document> collection = DB.getCollection("collection1");

    Add_dl ad = new Add_dl();
    String name = ad.name;
    String course = ad.course;
    String dat_tm = ad.date_time;

//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("name", name);
//        jsonObject.put("course", course);
//        jsonObject.put("dat_tm", dat_tm);
// BasicDBObject doc = new BasicDBObject();

        Document doc = new Document();


        doc.append("name", name);
        doc.append("course",course);
        doc.append("dat_tm", dat_tm);

        collection.insertOne(doc);

        mongoClient.close();
    }


}
