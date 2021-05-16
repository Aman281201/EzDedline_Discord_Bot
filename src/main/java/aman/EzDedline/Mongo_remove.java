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
        final String uri = "mongodb+srv://amank:aman@cluster0.7wsis.mongodb.net/DB?retryWrites=true&w=majority";
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

            while(cur.hasNext())
            {
                String dat_tm , n, c;
                n = (String) cur.next().get("name");
                System.out.println(n + "\n");
                c = (String) cur.next().get("course");
                System.out.println(c);
                dat_tm = (String) cur.next().get("dat_tm");
                System.out.println(dat_tm);





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
                    System.out.println("*****hii");
                    Document doc = new Document();
                    doc.append("name", n).append("course", c);

                    collection.deleteOne(doc);
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
