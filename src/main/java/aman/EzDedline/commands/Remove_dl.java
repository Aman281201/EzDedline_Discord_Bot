package aman.EzDedline.commands;

import aman.EzDedline.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Remove_dl extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(Main.prefix + "delete"))
        {
            EmbedBuilder remove = new EmbedBuilder();
            remove.setTitle("Remove Deadline ");
            remove.setDescription("\uD83D\uDC31\u200D\uD83D\uDC09Enter the date time and name of deadline you wish to remove <currently under-construction>");
            remove.setColor(Color.decode("#3366ff"));
            remove.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(remove.build()).queue();
            remove.clear();
        }
    }
}
