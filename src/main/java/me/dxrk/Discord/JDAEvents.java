package me.dxrk.Discord;

import me.dxrk.Events.PlayerDataHandler;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
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

import java.util.*;

public class JDAEvents extends ListenerAdapter implements Listener, CommandExecutor {

    static JDAEvents instance = new JDAEvents();

    public static JDAEvents getInstance() {
        return instance;
    }

    SettingsManager settings = SettingsManager.getInstance();
    Methods m = Methods.getInstance();


    public HashMap<UUID, String> uuidIdMap = jdaHandler.uuidIdMap;
    public HashMap<UUID, String> uuidCodeMap = jdaHandler.uuidCodeMap;
    private ArrayList<User> ulist = new ArrayList<>();


    public Guild guild = jdaHandler.guild;

    public void serverLink() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (settings.getDiscord().contains(p.getUniqueId().toString())) {
                        String discordid = settings.getDiscord().getString(p.getUniqueId().toString());
                        jdaHandler.jda.retrieveUserById(discordid).queue(user -> {
                            if (p.hasPermission("rank.genesis")) {
                                Role genesis = jdaHandler.jda.getRolesByName("Genesis", false).get(0);
                                jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, genesis).queue();
                            } else if (p.hasPermission("rank.olympian")) {
                                Role olympian = jdaHandler.jda.getRolesByName("Olympian", false).get(0);
                                jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, olympian).queue();
                            } else if (p.hasPermission("rank.god")) {
                                Role god = jdaHandler.jda.getRolesByName("God", false).get(0);
                                jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, god).queue();
                            } else if (p.hasPermission("rank.titan")) {
                                Role titan = jdaHandler.jda.getRolesByName("Titan", false).get(0);
                                jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, titan).queue();
                            } else if (p.hasPermission("rank.demi-god")) {
                                Role demigod = jdaHandler.jda.getRolesByName("Demi-God", false).get(0);
                                jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, demigod).queue();
                            } else if (p.hasPermission("rank.hero")) {
                                Role hero = jdaHandler.jda.getRolesByName("Hero", false).get(0);
                                jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, hero).queue();
                            } else if (p.hasPermission("rank.mvp")) {
                                Role mvp = jdaHandler.jda.getRolesByName("MVP", false).get(0);
                                jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, mvp).queue();
                            } else if (p.hasPermission("rank.vip")) {
                                Role vip = jdaHandler.jda.getRolesByName("VIP", false).get(0);
                                jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, vip).queue();
                            } else if (p.hasPermission("rank.sponsor")) {
                                Role sponsor = jdaHandler.jda.getRolesByName("Sponsor", false).get(0);
                                jdaHandler.jda.getGuilds().get(0).addRoleToMember(user, sponsor).queue();
                            }
                            if (Objects.requireNonNull(jdaHandler.jda.getGuilds().get(0).getMember(user)).isBoosting()) {
                                PlayerDataHandler.getInstance().getPlayerData(p).set("NitroBoosting", true);
                            }
                        });
                    }
                }
            }

        }.runTaskTimer(Main.plugin, 0, 20L);

    }

    private Random r = new Random();

    private String generateCode() {
        char[] cs = new char[7];
        for (int i = 0; i < cs.length; i++)
            cs[i] = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(r.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".length()));
        return new String(cs);
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.isWebhookMessage()) return;
        User user = event.getAuthor();
        Message msg = event.getMessage();
        if (event.isFromType(ChannelType.PRIVATE)) {
            if (!ulist.contains(user)) return;

            Player p = Bukkit.getPlayer(msg.getContentRaw());
            MessageChannel channel = event.getChannel();

            if (uuidIdMap.containsValue(user.getId())) {
                channel.sendMessage("You already have a code generated.").queue();
                return;
            }

            if (!Bukkit.getOnlinePlayers().contains(p)) {
                channel.sendMessage("That Player is not Online. Did you type the Name right?") /* => RestAction<Message> */
                        .queue();
                return;
            }

            String randomcode = generateCode();
            uuidCodeMap.put(p.getUniqueId(), randomcode);
            uuidIdMap.put(p.getUniqueId(), user.getId());
            channel.sendMessage("Type '/discord link " + randomcode + "' to link your account").queue();


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
        if (event.getMessageId().equals("1003041933561692201")) {
            User user = event.getUser();
            assert user != null;
            if (uuidIdMap.containsValue(user.getId())) {
                user.openPrivateChannel().flatMap(channel -> channel.sendMessage("You already have a code Generated.")).queue();
                return;
            }

            if (Objects.requireNonNull(event.getMember()).getRoles().stream().filter(role -> role.getName().equals("Linked")).findAny().orElse(null) != null) {
                user.openPrivateChannel().flatMap(channel -> channel.sendMessage("Your Account is already linked with " + settings.getDiscord().get(user.getId()))).queue();
                return;
            }
            user.openPrivateChannel().flatMap(channel -> channel.sendMessage("Please Send your Minecraft IGN.")).queue();
            ulist.add(user);
        }

    }

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("discord")) {
            if (args.length == 0) {
                cs.sendMessage(m.c("&bJoin the Discord: &dhttps://discord.gg/arWtNhM3aZ"));
            }
            if (args.length == 1) {
                Player p = (Player) cs;
                if (args[0].equalsIgnoreCase("link"))
                    p.sendMessage(m.c("&bPlease react to the message in &7#minecraft-link &bin the discord"));
                return false;
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("link")) {
                    Player p = (Player) cs;

                    if (settings.getDiscord().contains(p.getUniqueId().toString())) {
                        p.sendMessage(m.c("&bYou are Already Verified!"));
                        return false;
                    }


                    if (!uuidCodeMap.containsKey(p.getUniqueId())) {
                        p.sendMessage(m.c("&bYou are not pending Verification."));
                        return false;
                    }
                    if (!args[1].equals(uuidCodeMap.get(p.getUniqueId()))) {
                        p.sendMessage(m.c("&bInvalid Code!"));
                        return false;
                    }

                    String discordid = uuidIdMap.get(p.getUniqueId());

                    jdaHandler.jda.retrieveUserById(discordid).queue(u -> {

                        if (u == null) {
                            uuidIdMap.remove(p.getUniqueId());
                            uuidCodeMap.remove(p.getUniqueId());
                            p.sendMessage(m.c("You apprear to have left the Discord.."));
                            return;
                        }

                        Role linked = jdaHandler.jda.getRolesByName("Linked", false).get(0);
                        jdaHandler.jda.getGuilds().get(0).addRoleToMember(u, linked).queue();
                        p.sendMessage(m.c("&bYour account has been linked successfully to " + u.getName() + "#" + u.getDiscriminator()));
                        u.openPrivateChannel().flatMap(channel -> channel.sendMessage("Account Successfully Linked with " + p.getName())).queue();

                        Objects.requireNonNull(jdaHandler.jda.getGuilds().get(0).getMember(u)).modifyNickname(p.getName()).queue();
                        settings.getDiscord().set(p.getUniqueId().toString(), uuidIdMap.get(p.getUniqueId()));
                        settings.getDiscord().set(uuidIdMap.get(p.getUniqueId()), p.getName());
                        settings.saveDiscord();
                        serverLink();
                        int i = r.nextInt(2);
                        if (i == 0)
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell " + p.getName() + " 2.0 300");
                        else
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost xp " + p.getName() + " 2.0 300");
                        uuidIdMap.remove(p.getUniqueId());
                        uuidCodeMap.remove(p.getUniqueId());
                        ulist.remove(u);
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
