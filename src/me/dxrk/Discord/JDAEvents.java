package me.dxrk.Discord;

import java.awt.Color;

import org.jetbrains.annotations.NotNull;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JDAEvents extends ListenerAdapter{
	
	
	public void onMessage(@NotNull MessageReceivedEvent e) {
		if(e.getChannel().getId().equals("883900303181770793")) {
			String msg = e.getMessage().getContentRaw();
			if(msg.equalsIgnoreCase("!test")) {
				EmbedBuilder emb = new EmbedBuilder();
				emb.setTitle("Title");
				emb.setDescription("Desc");
				emb.addField("Field1", "text", false);
				emb.addField("Field2", "text", false);
				emb.setColor(Color.BLUE);
				emb.setFooter("Created by: ", e.getGuild().getOwner().getUser().getAvatarUrl());
				e.getChannel().sendMessageEmbeds(emb.build()).queue();
				emb.clear();
				//push
				
				
			}
		}
	}
	
	
	public void on(ButtonInteractionEvent e) {
		
	}

}
