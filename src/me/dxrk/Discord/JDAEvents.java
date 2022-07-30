package me.dxrk.Discord;

import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.CacheRestAction;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class JDAEvents extends ListenerAdapter implements Listener, CommandExecutor {

	static JDAEvents instance = new JDAEvents();
	public static JDAEvents getInstance(){
		return instance;
	}

	SettingsManager settings = SettingsManager.getInstance();
	Methods m = Methods.getInstance();


	public HashMap<UUID, String>uuidIdMap = jdaHandler.uuidIdMap;
	public HashMap<UUID, String>uuidCodeMap = jdaHandler.uuidCodeMap;

	private ArrayList<User> ulist = new ArrayList<>();


	public Guild guild = jdaHandler.guild;

	public void serverLink(){
		new BukkitRunnable() {
			@Override
			public void run(){
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(settings.getDiscord().contains(p.getUniqueId().toString())) {
						String discordid = (String) settings.getDiscord().get(p.getUniqueId().toString());
						jdaHandler.jda.retrieveUserById(discordid).queue(user -> {
							if(p.hasPermission("rank.zeus")){
								Role zeus = jdaHandler.jda.getRolesByName("Zeus", false).get(0);
								jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, zeus).queue();
							} else if(p.hasPermission("rank.kronos")){
								Role kronos = jdaHandler.jda.getRolesByName("Kronos", false).get(0);
								jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, kronos).queue();
							} else if(p.hasPermission("rank.apollo")){
								Role apollo = jdaHandler.jda.getRolesByName("Apollo", false).get(0);
								jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, apollo).queue();
							} else if(p.hasPermission("rank.hermes")){
								Role hermes = jdaHandler.jda.getRolesByName("Hermes", false).get(0);
								jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, hermes).queue();
							} else if(p.hasPermission("rank.ares")){
								Role ares = jdaHandler.jda.getRolesByName("Ares", false).get(0);
								jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, ares).queue();
							} else if(p.hasPermission("rank.colonel")){
								Role colonel = jdaHandler.jda.getRolesByName("Colonel", false).get(0);
								jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, colonel).queue();
							} else if(p.hasPermission("rank.captain")){
								Role captain = jdaHandler.jda.getRolesByName("Captain", false).get(0);
								jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, captain).queue();
							} else if(p.hasPermission("rank.hoplite")){
								Role hoplite = jdaHandler.jda.getRolesByName("Hoplite", false).get(0);
								jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, hoplite).queue();
							} else if(p.hasPermission("rank.cavalry")){
								Role cavalry = jdaHandler.jda.getRolesByName("Cavalry", false).get(0);
								jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, cavalry).queue();
							}
						});
					}
				}
			}

		}.runTaskTimer(Main.plugin, 0, 20L);

	}




	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getAuthor().isBot()||event.isWebhookMessage())return;
		User user = event.getAuthor();
		Message msg = event.getMessage();
		if(event.isFromType(ChannelType.PRIVATE)){
				if(!ulist.contains(user)) return;

				Player p = Bukkit.getPlayer(msg.getContentRaw());
				MessageChannel channel = event.getChannel();

				if(uuidIdMap.values().contains(user.getId())) {
					channel.sendMessage("You already have a code generated.").queue();
					return;
				}

				if(!Bukkit.getOnlinePlayers().contains(p)) {
					channel.sendMessage("That Player is not Online. Did you type the Name right? (Case Sensitive)") /* => RestAction<Message> */
							.queue();
							return;
				}

						String randomcode = new Random().nextInt(800000) + 200000 + "AA"; //6581446AA
						uuidCodeMap.put(p.getUniqueId(), randomcode);
						uuidIdMap.put(p.getUniqueId(), user.getId());
						channel.sendMessage("Type '/discord link " + randomcode + "' to link your account").queue();
						channel.sendMessage(uuidIdMap.keySet().toString()).queue();
						channel.sendMessage(uuidIdMap.get(p.getUniqueId())).queue();



		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		uuidCodeMap.remove(p.getUniqueId());
		uuidIdMap.remove(p.getUniqueId());
	}

	@Override
	public void onMessageReactionAdd(@NotNull MessageReactionAddEvent event) {
		if(event.getMessageId().equals("1003041933561692201")){
			User user = event.getUser();
			if(uuidIdMap.values().contains(user.getId())){
				user.openPrivateChannel().flatMap(channel -> channel.sendMessage("You already have a code Generated.")).queue();
				return;
			}

				if(event.getMember().getRoles().stream().filter(role -> role.getName().equals("Linked")).findAny().orElse(null) != null){
					user.openPrivateChannel().flatMap(channel -> channel.sendMessage("Your Account is already linked with "+settings.getDiscord().get(user.getId()))).queue();
					return;
				}
				user.openPrivateChannel().flatMap(channel -> channel.sendMessage("Please Send your Minecraft IGN.")).queue();
				ulist.add(user);
		}

	}
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

		if(label.equalsIgnoreCase("discord")){
			if (args.length == 0) {
				cs.sendMessage(m.c("&bJoin the Discord: &dhttps://discord.gg/arWtNhM3aZ"));
			}
			if(args.length == 1){
				Player p = (Player)cs;
				p.sendMessage(m.c("&bPlease react to the message in &7#minecraft-link &bin the discord"));
				return false;
			}
			if (args.length == 2) {
				if(args[0].equalsIgnoreCase("link")){
					Player p = (Player)cs;

					if(settings.getDiscord().contains(p.getUniqueId().toString())){
						p.sendMessage(m.c("&bYou are Already Verified!"));
						return false;
					}


					if(!uuidCodeMap.containsKey(p.getUniqueId())){
						p.sendMessage(m.c("&bYou are not pending Verification."));
						return false;
					}
						if(!args[1].equals(uuidCodeMap.get(p.getUniqueId()))) {
							p.sendMessage(m.c("&bInvalid Code!"));
							return false;
						}

							String discordid = uuidIdMap.get(p.getUniqueId());

							jdaHandler.jda.retrieveUserById(discordid).queue(u -> {

								if (u == null) {
									uuidIdMap.remove(p.getUniqueId());
									uuidCodeMap.remove(p.getUniqueId());
									p.sendMessage(m.c("You appreared to have left the Discord.."));
									return;
								}

								Role linked = jdaHandler.jda.getRolesByName("Linked", false).get(0);
								jdaHandler.jda.getGuilds().get(0).addRoleToMember(u, linked).queue();
								p.sendMessage(m.c("&bYour account has been linked successfully to "+u.getName()+"#"+u.getDiscriminator()));
								u.openPrivateChannel().flatMap(channel -> channel.sendMessage("Account Successfully Linked with "+p.getName())).queue();


								settings.getDiscord().set(p.getUniqueId().toString(), uuidIdMap.get(p.getUniqueId()));
								settings.getDiscord().set(uuidIdMap.get(p.getUniqueId()), p.getName());
								settings.saveDiscord();
								uuidIdMap.remove(p.getUniqueId());
								uuidCodeMap.remove(p.getUniqueId());

							});






				}

			}
		}



		return false;
	}



	@Override
	public void onReady(@NotNull ReadyEvent event) {
		Bukkit.getConsoleSender().sendMessage("DiscordBot is Online!");
	}
}
