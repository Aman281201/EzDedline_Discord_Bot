package aman.EzDedline;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Commands extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Main.prefix + "help"))
        {
            EmbedBuilder help = new EmbedBuilder();
            help.setTitle("\uD83D\uDC33EzDedline bot info");


            help.addField("","Basic Commands",false);
            help.addField("\uD83E\uDD8B{add","to add deadlines",true);
            help.addField("\uD83D\uDC1E{show","to view deadlines",true);
            help.addField("\uD83C\uDF2D{remove","to remove deadline",true);
            help.addField("\uD83C\uDF40{update","to change the deadline info",true);
            help.addField("","Other Commands",false);
            help.addField("\uD83C\uDF6D{setUpRemind","to make a default reminder channel",true);
            help.addField("\uD83C\uDF34{remindTime", "how much time before deadline you wish to be reminded",true);
            help.addField("\uD83C\uDF49{clear","to clear up previous messages",true);

            help.setColor(Color.decode("#99ffff"));
            //help.setDescription("Common commands for this bot");help.setImage("https://static.vecteezy.com/system/resources/previews/000/118/039/original/free-pokemon-vector.png");
            help.setThumbnail("https://images.vectorhq.com/images/previews/424/mega-gyarados-psd-408963.png");
            //help.setThumbnail("https://cdn.wallpapersafari.com/66/6/xbHpg4.jpg");
            //help.setThumbnail("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/intermediary/f/0bb24e44-ed4f-4c54-86f7-a5f53487720b/da9ru1p-5517bc21-ae7a-4a1b-98bf-8706f3642330.png/v1/fill/w_845,h_946,strp/ninetales_vector_by_pokemon_vector_art_da9ru1p-pre.png");
            help.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());
            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(help.build()).queue();
            help.clear();
        }
    }
}
