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
        jda = JDABuilder.createDefault("ODM2MjcNDYxMDU5MjQ4MTY4.YIboNw.HavP8vOVxxoFjnTXcTtKmYVIUjY").build();
        jda.getPresence().setStatus(OnlineStatus.IDLE);
        jda.getPresence().setActivity(Activity.watching("you complete your deadlines {help for info"));

        jda.addEventListener(new Commands());

    }
}
