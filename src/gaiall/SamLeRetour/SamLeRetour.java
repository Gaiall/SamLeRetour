package gaiall.SamLeRetour;

import gaiall.SamLeRetour.Commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SamLeRetour {
    public static JDA jda;
    public static String prefix = "|";
    private static String token;

    //Main method
    public static void main(String[] args) throws IOException, LoginException {
        token = Files.readString(Path.of(".\\Token"));
        jda = JDABuilder.createDefault(token).build();
        jda.getPresence().setStatus(OnlineStatus.DO_NOT_DISTURB);
        jda.getPresence().setActivity(Activity.playing("se faire coder"));

        jda.addEventListener(new Clear());
    }
}
