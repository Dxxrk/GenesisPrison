package me.dxrk.Events;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MysteryBoxHandler implements Listener, CommandExecutor {

	Methods m = Methods.getInstance();



	@EventHandler
	public void armorstand(PlayerArmorStandManipulateEvent e){
		if(!e.getPlayer().isOp())
			e.setCancelled(true);
	}
	public void summonStand(Player p){
		Location place = p.getLocation();
		ItemStack reward = Reward("genesis").clone();




		final ArmorStand stand = place.getWorld().spawn(place, ArmorStand.class);


		stand.setGravity(false);
		stand.setVisible(false);
		stand.setCustomName(reward.getItemMeta().getDisplayName());
		stand.setCustomNameVisible(true);

		stand.setItemInHand(reward);
		EulerAngle block_pose = new EulerAngle( Math.toRadians( -43 ), Math.toRadians( -41.5 ), Math.toRadians( 19.5 ) );
		EulerAngle item_pose = new EulerAngle( Math.toRadians( -20 ), Math.toRadians( 0), Math.toRadians( 0 ) );
		EulerAngle test = new EulerAngle(Math.toRadians(280), Math.toRadians(354), Math.toRadians(1));

			stand.setRightArmPose(test);

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(label.equalsIgnoreCase("givecrate")){
			if(!sender.isOp()) return false;
			if(args.length == 2) {
				Player p = Bukkit.getPlayer(args[0]);
				if(args[1].equalsIgnoreCase("genesis")){
					p.getInventory().addItem(GenesisCrate());
					p.updateInventory();
				}
			}
		}
		if(label.equalsIgnoreCase("testchestopen")){
			summonStand((Player)sender);
		}

		return false;
	}

	public ItemStack GenesisCrate() {
		ItemStack gcrate = new ItemStack(Material.CHEST);
		ItemMeta gm = gcrate.getItemMeta();
		gm.setDisplayName(m.c("&f&l&k[&7&l*&f&l&k]&r &9&lGenesis &b&lCrate &f&l&k[&7&l*&f&l&k]&r"));
		List<String> lore = new ArrayList<>();
		lore.add(m.c("&7Upon placing this item, you will recieve 5 random items"));
		lore.add(m.c("&7From the list below, all rewards are randomly selected."));
		lore.add(m.c("&a&lRewards:"));
		lore.add(m.c(" "));
		lore.add(m.c("&e&l&m--&e&lTokens&m--"));
		lore.add(m.c("&e⛀25,000-75,000"));
		lore.add(m.c(" "));
		lore.add(m.c("&b&l&m--&b&lPick XP&m--"));
		lore.add(m.c("&b✴250,000-750,000"));
		lore.add(m.c(" "));
		lore.add(m.c("&c&l&m--&c&lKeys&m--"));
		lore.add(m.c("&c1-10x Random Keys"));
		lore.add(m.c(" "));
		lore.add(m.c("&5&l&m--&5&lRanks&m--"));
		lore.add(m.c("&5Hermes-Zeus Rank"));
		lore.add(m.c(" "));
		lore.add(m.c("&6&l&m--&6&lItems&m--"));
		lore.add(m.c("&61-5x Legendary Trinkets"));
		lore.add(m.c("&61-3x Legendary me.dxrk.Enchants"));
		lore.add(m.c(" "));
		lore.add(m.c("&f&l&m--&f&lTroll&m--"));
		lore.add(m.c("&fMjölnir"));
		lore.add(m.c("&fPack-a-Punch"));
		lore.add(m.c("&fFree Lunch :)"));
		lore.add(m.c(" "));
		lore.add(m.c("&5&l&m--&5&lMisc.&m--"));
		lore.add(m.c("&5Item Rename"));
		lore.add(m.c("&53x Sell Boost"));
		lore.add(m.c("&52x XP Boost"));
		lore.add(m.c("&52x Sell Boost"));
		gm.setLore(lore);
		gcrate.setItemMeta(gm);

		return gcrate;
	}

	public ItemStack Reward(String crate){
		ItemStack reward = new ItemStack(Material.PAPER);
		ItemMeta rm = reward.getItemMeta();
		if(crate.equals("genesis")){
			Random r = new Random();
			int ri = r.nextInt(2);

			if(ri < 2){
				int tmin = 25000;
				int tmax = 75000;
				int tokens = r.nextInt(tmax - tmin)+ tmin;
				rm.setDisplayName(m.c("&b"+Main.formatAmt(tokens)+" Tokens"));
				reward.setType(Material.DIAMOND_PICKAXE);
			}

		}


		reward.setItemMeta(rm);
		return reward;
	}


	
	@EventHandler
	public void onPlace(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
			if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
			if(!p.getItemInHand().hasItemMeta()) return;
			if(!p.getItemInHand().getItemMeta().hasDisplayName()) return;


			Location loc = e.getClickedBlock().getLocation();
			List<ArmorStand> stands = new ArrayList<>();


		if(p.getItemInHand().getItemMeta().getDisplayName().equals(m.c("&f&l&k[&7&l*&f&l&k]&r &9&lGenesis &b&lCrate &f&l&k[&7&l*&f&l&k]&r"))) {
			e.setCancelled(true);
			p.updateInventory();
			displayRewards(Main.getInstance(), "genesis", "&f&l&k[&7&l*&f&l&k]&r &9&lGenesis &b&lCrate &f&l&k[&7&l*&f&l&k]&r", loc, stands, p);


		}
		
	}
		//Add local ArrayList inside Interact Event to get called inside spawnItem Function to make sure other players items do not get interrupted.
		//Create a Runnable Task inside spawnItem to make the item go up a little and stop to reveal the item obtained. This will be called in a for loop with an Integer from the loop being a parameter.
		//Add particles directly above the opened chest block that spawns when interacted. After 1-2 seconds have the particles pop. Revealing the item at that location + Name.
		//Use finishOpen function to run the necessary events to remove the chest, remove the holograms, and remove the armorstands.


		private float rad = 0.261799f;
	private Location getLocationInCircle(Location center, double angle) {
		double x = center.getX() + 0.397*Math.cos(angle) + 0.505*Math.sin(angle);
		double z = center.getZ() + 0.397*Math.sin(angle) - 0.505*Math.cos(angle);

		return new Location(center.getWorld(), x, center.getY(), z, (float) Math.toDegrees(angle), 0);
	}


	public void rotateStand(ArmorStand stand, Location place){
		new BukkitRunnable(){
			int tick = 0;
			@Override
			public void run(){
				if(stand.equals(null) || stand.isDead()) cancel();
				++tick;


				Location loc = getLocationInCircle(place, rad*tick);
				stand.teleport(loc);


			}
		}.runTaskTimer(Main.getInstance(), 0L, 1L);
	}



	public void spawnArmorStand(JavaPlugin plugin, Location place, List<ArmorStand> stands, List<ItemStack> items, String crate, Player p) {


		new BukkitRunnable() {
			@Override
			public void run() {
				if(!p.isOnline()) return;


				ItemStack reward = Reward(crate).clone();


				//Location faceSouth = new Location(place.getWorld(), place.getX(), place.getY(), place.getZ(), 0f, 0f);

				final ArmorStand stand = place.getWorld().spawn(place, ArmorStand.class);


				stand.setGravity(false);
				stand.setVisible(false);

				Location holo = new Location(place.getWorld(), place.getX(), place.getY()+2.5, place.getZ());
				Hologram name = HologramsAPI.createHologram(plugin, holo);
				name.appendTextLine(reward.getItemMeta().getDisplayName());

				stand.setItemInHand(reward);

				EulerAngle item_pose = new EulerAngle(Math.toRadians(280), Math.toRadians(354), Math.toRadians(1));

				stand.setRightArmPose(item_pose);
				stand.setRightLegPose(new EulerAngle(0, 0, 0));
				stand.setHeadPose(new EulerAngle(0, 0, 0));
				stand.setLeftArmPose(new EulerAngle(0, 0, 0));
				stand.setLeftLegPose(new EulerAngle(0, 0, 0));
				stand.setBodyPose(new EulerAngle(0, 0, 0));



				rotateStand(stand, place);


				stands.add(stand);
				items.add(reward);
				p.playSound(p.getLocation(), Sound.CHICKEN_EGG_POP, 1.0f, 1.0f);

			}
		}.runTaskLater(plugin, 60L);
	}

	public void playSound(JavaPlugin plugin, Player p, Sound s, int time) {
		new BukkitRunnable(){
			@Override
			public void run(){
				p.playSound(p.getLocation(), s, 1.0F, 1.0F);
			}
		}.runTaskLater(plugin, time);
	}

	public void startAnimation(JavaPlugin plugin, String crate, Location loc, List<ArmorStand> stands, List<ItemStack> items, Player p, int time, double x, double z){
		new BukkitRunnable() {

			@Override
			public void run() {
				if(!p.isOnline()) return;
				Location place = new Location(loc.getWorld(), loc.getX()+x, loc.getY()+0.5, loc.getZ()+z);

				for (int i = 0; i < 60; i++)
					playSound(plugin, p, Sound.NOTE_PIANO, i);

				// Try to use a SingleTask - Check GitHub
				new ParticleBuilder(ParticleEffect.ENCHANTMENT_TABLE, place)
						.setOffsetX((float) -x)
						.setOffsetZ((float) -z)
						.setSpeed(0.1f)
						.display();

				spawnArmorStand(plugin, place, stands, items, crate, p);



			}
		}.runTaskLater(plugin, time);
	}

	public void displayRewards(JavaPlugin plugin, String crate, String name, Location loc, List<ArmorStand> stands, Player p) {

		Location block = new Location(loc.getWorld(), loc.getX(), loc.getY() +1, loc.getZ());


		block.getWorld().getBlockAt(block).setType(Material.CHEST);
		m.playChestAction((Chest) block.getWorld().getBlockAt(block).getState(), true, p);


		Location holo = new Location(loc.getWorld(), loc.getX()+0.5, loc.getY() +3.5, loc.getZ());
		Hologram chest = HologramsAPI.createHologram(plugin, holo);
		chest.appendTextLine(m.c(name));

		List<ItemStack> items = new ArrayList<>();
		List<Hologram> holos = new ArrayList<>();


		//keep y level constant
		startAnimation(plugin, crate, loc, stands, items, p, 20, 3, -1); // -1z +3x
		startAnimation(plugin, crate, loc, stands, items, p, 85, 2, 1); // +1z +2x
		startAnimation(plugin, crate, loc, stands, items, p, 150, 0, 2); // +2z
		startAnimation(plugin, crate, loc, stands, items, p, 215, -2, 1); // +1z -2x
		startAnimation(plugin, crate, loc, stands, items, p, 280, -3, -1); // -1z -3x
		finishOpen(plugin, block, p, stands, items, 365);



	}



	public void finishOpen(JavaPlugin plugin, Location loc, Player p, List<ArmorStand> stands, List<ItemStack> items, int time){

		new BukkitRunnable(){
			@Override
			public void run(){
				if(!p.isOnline()) return;

				for(ArmorStand s : stands){
					s.remove();

				}

				loc.getWorld().getBlockAt(loc).setType(Material.AIR);

				for(ItemStack item : items){
					p.getInventory().addItem(item);
					p.updateInventory();
				}
			}
		}.runTaskLater(plugin, time);


	}







	

}
