package aman.EzDedline;

import com.mongodb.BasicDBObject;
import com.mongodb.client.*;
import org.bson.Document;
import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Mongo_remove {

    public static void main(String name , String course, int key)
    {
        final String uri = "";
        MongoClient mongoClient = MongoClients.create(uri);

        MongoDatabase DB = MongoClients.create().getDatabase("DB");
        MongoCollection<Document> collection = DB.getCollection("collection1");

        if(key == 1) {

            FindIterable<Document> it = collection.find();
            MongoCursor<Document> cur = it.iterator();

            BasicDBObject del = new BasicDBObject("name", name);
            del.append("course", course);

            collection.deleteOne(del);
        }
        else if(key == 2)
        {
            FindIterable<Document> it = collection.find();
            MongoCursor<Document> cur = it.iterator();
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

    }
}
