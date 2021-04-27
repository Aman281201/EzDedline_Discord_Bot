package aman.EzDedline.commands;

import aman.EzDedline.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Update_dl extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Main.prefix + "update"))
        {
            EmbedBuilder update = new EmbedBuilder();
            update.setTitle("\uD83D\uDC31\u200D\uD83D\uDE80Update Deadline ");
            update.setDescription("Enter the date time and name of deadline you wish to update <currently under-construction>");
            update.setColor(Color.decode("#a366ff"));
            update.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(update.build()).queue();
            update.clear();
        }
    }
}
