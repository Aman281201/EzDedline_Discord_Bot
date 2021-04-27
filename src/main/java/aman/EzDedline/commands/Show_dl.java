package aman.EzDedline.commands;

import aman.EzDedline.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Show_dl extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Main.prefix + "show"))
        {
            EmbedBuilder show = new EmbedBuilder();
            show.setTitle("Show Deadlines ");
            show.setDescription("\uD83D\uDC31\u200D\uD83C\uDFCDThese are your deadlines ");
            show.setColor(Color.decode("#6666ff"));
            show.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(show.build()).queue();
            show.clear();
        }
    }

}
