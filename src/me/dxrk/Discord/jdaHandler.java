package me.dxrk.Discord;

import javax.security.auth.login.LoginException;

import me.dxrk.Main.Main;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

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
		
		builder.setActivity(Activity.playing("MCGenesis.net"));
		
		builder.addEventListeners(new JDAEvents());
		
		try {
			jda = builder.build();
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	
	
	

}
