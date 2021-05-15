package aman.EzDedline;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import org.bson.Document;

public class Mongo_remove {

    public static void main(String name , String course, Boolean key)
    {
        final String uri = "mongodb+srv://amank:aman@cluster0.7wsis.mongodb.net/DB?retryWrites=true&w=majority";
        MongoClient mongoClient = MongoClients.create(uri);

        MongoDatabase DB = MongoClients.create().getDatabase("DB");
        MongoCollection<Document> collection = DB.getCollection("collection1");

        if(!key) {

            FindIterable<Document> it = collection.find();
            MongoCursor<Document> cur = it.iterator();

            BasicDBObject del = new BasicDBObject("name", name);
            del.append("course", course);

            collection.deleteOne(del);
        }

        else
        {
            BasicDBObject del_all = new BasicDBObject();
            collection.deleteMany(del_all);
        }


    }
}
