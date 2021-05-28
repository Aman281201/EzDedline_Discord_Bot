package aman.EzDedline.commands;

import aman.EzDedline.Main;
import aman.EzDedline.Mongo_add;
import aman.EzDedline.Reminder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;

public class Remind_time extends ListenerAdapter {

    public static String name;
    public static String course;
    public static String date_time;

    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String serverID = event.getGuild().getId();


        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Main.prefix + "remindTime"))
        {
            int hrs;
            String name = "", course = "";

            if(args.length < 2) {
                EmbedBuilder rtime = new EmbedBuilder();
                rtime.setThumbnail("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/10e165e5-1c65-443e-b5c6-ee4edc64fdcc/dahsfae-75cf11ac-9c4e-4ead-840b-456e7b6cdeba.png/v1/fill/w_1024,h_1449,strp/midnight_lycanroc_vector__by_hikari_the_wolfdog_dahsfae-fullview.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MTQ0OSIsInBhdGgiOiJcL2ZcLzEwZTE2NWU1LTFjNjUtNDQzZS1iNWM2LWVlNGVkYzY0ZmRjY1wvZGFoc2ZhZS03NWNmMTFhYy05YzRlLTRlYWQtODQwYi00NTZlN2I2Y2RlYmEucG5nIiwid2lkdGgiOiI8PTEwMjQifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6aW1hZ2Uub3BlcmF0aW9ucyJdfQ.xkXs2sPIMshuczRGiwwqztKykNLqNGc6zcRBmWtfHAs");
                rtime.setTitle("\uD83C\uDF34Set up Remind Time ");
                rtime.setDescription("Commands");
                rtime.addField("{remindTime <number of hours>", "to set up default remind time",false);
                rtime.addField("{remindTime <number of hours> <deadline_name> <course>", "to set up exclusive remind time for a particular deadline",false);
                rtime.setColor(Color.decode("#d4b259"));
                rtime.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                event.getChannel().sendTyping().queue();
                event.getChannel().sendMessage(rtime.build()).queue();
                rtime.clear();
            }
            else {
                final String uri = "mongodb+srv://amank:aman@cluster0.7wsis.mongodb.net/DB?retryWrites=true&w=majority";
                String serverDB = event.getGuild().getId();
                MongoClient mongoClient = MongoClients.create(uri);

                MongoDatabase DB = MongoClients.create().getDatabase(serverDB);
                MongoCollection<Document> collection = DB.getCollection("remind_data");

            if(args.length < 3)
            {
                hrs = Integer.getInteger(args[1]);
                System.out.println(collection.find(new Document().append("Default time","")));

            }
                if(args.length < 5){
                Add_dl.name = args[1];
                Add_dl.course = args[2];
                Add_dl.date_time = args[3] + " " + args[4];

                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
                try {
                    Date date = sdf.parse(date_time);
                    if(date.after(new Date())) {

                        Mongo_add data_mongo = new Mongo_add();
                        int x = data_mongo.main(serverID, event);

                        if(x == 0)
                            return;

                        EmbedBuilder success = new EmbedBuilder();
                        success.setColor(Color.decode("#80ff80"));
                        success.setTitle("Success");
                        success.setDescription("Successfully added deadline " + Add_dl.name + " of " + Add_dl.course);
                        success.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                        event.getChannel().sendMessage(success.build()).queue();

                        Reminder reminder = new Reminder();
                        reminder.async_remind(event,name,course,date_time);

                    }



                } catch (Exception e) {
                    EmbedBuilder err = new EmbedBuilder();
                    err.setTitle("Information entered is either wrong or in wrong format");
                    err.setDescription("Enter in following format {add <Name> <course> <Time(hh:mm)> <Date(dd/mm/yyyy)>");
                    err.setColor(Color.decode("#ff4d4d"));
                    err.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                    event.getChannel().sendTyping().queue();
                    event.getChannel().sendMessage(err.build()).queue();
                    err.clear();
                }
            }

        }
    }}
}

