package aman.EzDedline.commands;

import aman.EzDedline.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Add_dl extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Main.prefix + "add"))
        {
            EmbedBuilder add = new EmbedBuilder();
            add.setTitle("Add Deadline ");
            add.setDescription("Add the date time and name of your deadline <currently under-construction>");
            add.setColor(Color.decode("#99ffff"));
            add.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(add.build()).queue();
            add.clear();
        }
    }
}
