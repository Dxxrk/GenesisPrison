package me.dxrk.Discord;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class JDAEvents extends ListenerAdapter implements Listener {


	@Override
	public void onMessageReceived(@NotNull MessageReceivedEvent event) {
		if(event.getMessage().getContentRaw().contains("ping")){
			event.getGuild().getTextChannelById("991316164011622452").sendMessage("pong!");
		}
	}

	@Override
	public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
		User user = event.getUser();
		String emoji = event.getReaction().getEmoji().getAsReactionCode();
		String channelMention = event.getChannel().getAsMention();
		String jumpLink = event.getJumpUrl();
		String message = user.getAsTag() + " reacted with "+emoji+" in "+channelMention+" channel";
		EmbedBuilder b = new EmbedBuilder();
		b.setTitle("__Dxrk Activated a Sell Boost__");
		b.addField("Multiplier: 2.0x", "Length: 15:00", false);
		b.setColor(Color.BLUE);



		event.getGuild().getTextChannelById("991316164011622452").sendMessageEmbeds(b.build()).queue();

	}

	@Override
	public void onReady(@NotNull ReadyEvent event) {
		Bukkit.getConsoleSender().sendMessage("DiscordBot is Online!");
	}
}
