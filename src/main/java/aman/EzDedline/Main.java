package aman.EzDedline;

import aman.EzDedline.commands.*;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class Main {
    public static JDA jda;
    public static String prefix = "{";

    public static void main(String[] args) throws LoginException
    {
        jda = JDABuilder.createDefault("").build();
        jda.getPresence().setStatus(OnlineStatus.IDLE);
        jda.getPresence().setActivity(Activity.watching("you complete your deadlines {help for info"));

        jda.addEventListener(new Commands());
        jda.addEventListener(new Clear());
        jda.addEventListener(new Add_dl());
        jda.addEventListener(new Remove_dl());
        jda.addEventListener(new Update_dl());
        jda.addEventListener(new Show_dl());


    }
}
