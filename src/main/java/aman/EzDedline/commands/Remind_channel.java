package aman.EzDedline.commands;

import aman.EzDedline.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;

import java.awt.*;


public class Remind_channel extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(Main.prefix + "setUpRemind")) {
            Guild guild = event.getGuild();
            System.out.println(guild.getTextChannelsByName("ezdedline_reminder",true).isEmpty());

            if(!guild.getTextChannelsByName("ezdedline_reminder",true).isEmpty())
            {
                guild.getChannels().contains(guild.getTextChannelsByName("ezdedline_reminder", false).get(0));
                EmbedBuilder fail = new EmbedBuilder();
                fail.setColor(Color.decode("#ff4d4d"));
                fail.setTitle("Reminder Channel has already been created in this server");
                fail.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                event.getChannel().sendMessage(fail.build()).queue();}
            else {

                    try {


                        guild.createTextChannel("ezdedline_reminder").queue();
                        System.out.println(guild.getTextChannelsByName("ezdedline_reminder", true));


                        EmbedBuilder success = new EmbedBuilder();
                        success.setColor(Color.decode("#80ff80"));
                        success.setTitle("Success");
                        success.setDescription("Successfully created reminder channel EzDedline_Reminder");
                        success.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                        event.getChannel().sendMessage(success.build()).queue();


                    } catch (Exception ex) {
                        if (ex.toString().startsWith("net.dv8tion.jda.api.exceptions.InsufficientPermissionException")) {
                            EmbedBuilder fail = new EmbedBuilder();
                            fail.setColor(Color.decode("#ff4d4d"));
                            fail.setTitle("You cannot use this command");
                            fail.setDescription("User must have permissions to Manage Channels to use this command");
                            fail.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                            event.getChannel().sendMessage(fail.build()).queue();
                        } else {
                            EmbedBuilder fail = new EmbedBuilder();
                            fail.setColor(Color.decode("#ff4d4d"));
                            fail.setTitle("Unknown error occurred");
                            fail.setFooter(event.getMember().getUser().getAsTag(), event.getMember().getUser().getAvatarUrl());

                            event.getChannel().sendMessage(fail.build()).queue();
                            ex.printStackTrace();
                        }
                    }


            }


        }
    }

}