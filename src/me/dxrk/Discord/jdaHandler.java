package me.dxrk.Discord;

import me.dxrk.Main.Main;
import me.dxrk.Main.SettingsManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.bukkit.Bukkit;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.UUID;

public class jdaHandler extends ListenerAdapter {


    public Main plugin;
    public static HashMap<UUID, String> uuidCodeMap;
    public static HashMap<UUID, String> uuidIdMap;
    public static Guild guild;
    SettingsManager settings = SettingsManager.getInstance();

    public jdaHandler(Main main) {
        this.plugin = main;
        uuidCodeMap = new HashMap<>();
        uuidIdMap = new HashMap<>();
        startBot();
        Bukkit.getScheduler().runTaskLater(plugin, () -> guild = jda.getGuilds().get(0), 100L);


    }


    private String token = settings.getOptions().getString("DiscordToken");

    public static JDA jda;

    public void startBot() {
        try {
            jda = JDABuilder.createLight(token, GatewayIntent.MESSAGE_CONTENT, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGE_REACTIONS
                            , GatewayIntent.DIRECT_MESSAGE_REACTIONS)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setStatus(OnlineStatus.ONLINE)
                    .setActivity(Activity.playing("MCGenesis.net"))
                    .addEventListeners(new JDAEvents()).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }


}
