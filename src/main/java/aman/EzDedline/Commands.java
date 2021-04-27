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
            help.setTitle("\uD83D\uDC31\u200D\uD83D\uDC64 EzDedline bot info");
            help.setDescription("Common commands for this bot");
            help.addField("{add","to add deadlines",false);
            help.addField("{show","to view deadlines",false);
            help.addField("{delete","to remove deadline",false);
            help.addField("{update","to change the deadline info",false);

            help.setColor(Color.decode("#99ffff"));
            help.setFooter(event.getMember().getEffectiveName(), event.getMember().getUser().getAvatarUrl());

            event.getChannel().sendTyping().queue();
            event.getChannel().sendMessage(help.build()).queue();
            help.clear();
        }
    }
}
