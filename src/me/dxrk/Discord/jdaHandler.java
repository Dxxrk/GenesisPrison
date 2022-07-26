package me.dxrk.Discord;

import me.dxrk.Main.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import javax.security.auth.login.LoginException;

public class jdaHandler extends ListenerAdapter {
	
	
	
	
	
	public Main plugin;
	
	public jdaHandler(Main main){
		this.plugin = main;
		startBot();
		
	}
	
	
	private String token = "ODg0MjE4NjIwMzI1MDE5Njcx.YTVSww.pkb92aPrSoH8GWih77pTRiOYEFA";
	
	public static JDA jda;
	
	public void startBot(){
		JDABuilder builder = JDABuilder.createDefault(token);
		
		builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
		builder.setStatus(OnlineStatus.ONLINE);
		
		builder.setActivity(Activity.playing("MCGenesis.net"));
		
		builder.addEventListeners(new JDAEvents());
		try {
			jda = builder.build().awaitReady();
		} catch (LoginException | InterruptedException e) {
			e.printStackTrace();
		}
	}


}
