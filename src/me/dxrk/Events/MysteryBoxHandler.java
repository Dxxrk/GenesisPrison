package me.dxrk.Events;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
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




	public void spawnItem(ItemStack i, Location loc){
		Item item = loc.getWorld().dropItem(loc, i);
		final ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class);
		stand.setPassenger(item);
	}

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
		if(label.equalsIgnoreCase("givexp")){
			if(args.length == 2){
				Player p = Bukkit.getPlayer(args[0]);
				double xp = Double.parseDouble(args[1]);
				PickXPHandler.getInstance().addXP(p, xp);
			}
		}

		return false;
	}

	public void randomKey() {

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
		lore.add(m.c("&e⛀5,000,000-15,000,000"));
		lore.add(m.c(" "));
		lore.add(m.c("&b&l&m--&b&lPick XP&m--"));
		lore.add(m.c("&b✴25,000-75,000"));
		lore.add(m.c(" "));
		lore.add(m.c("&c&l&m--&c&lKeys&m--"));
		lore.add(m.c("&c1-10x Random Keys"));
		lore.add(m.c("&c3x Rank Keys"));
		lore.add(m.c(" "));
		lore.add(m.c("&5&l&m--&5&lRanks&m--"));
		lore.add(m.c("&e&lOlympian Rank"));
		lore.add(m.c("&9&lG&b&le&9&ln&b&le&9&ls&b&li&9&ls &b&lRank"));
		lore.add(m.c(" "));
		lore.add(m.c("&6&l&m--&6&lItems&m--"));
		lore.add(m.c("&61-3x Legendary Trinkets"));
		lore.add(m.c("&51-5x Epic Trinkets"));
		lore.add(m.c(" "));
		lore.add(m.c("&f&l&m--&f&lTroll&m--"));
		lore.add(m.c("&fMjölnir"));
		lore.add(m.c("&fPack-a-Punch"));
		lore.add(m.c("&fFree Lunch :)"));
		lore.add(m.c(" "));
		lore.add(m.c("&d&l&m--&5&lMisc.&m--"));
		lore.add(m.c("&dItem Rename"));
		lore.add(m.c("&d3x Sell Boost"));
		lore.add(m.c("&d2x XP Boost"));
		lore.add(m.c("&d2x Sell Boost"));
		gm.setLore(lore);
		gcrate.setItemMeta(gm);

		return gcrate;
	}

	public ItemStack Reward(String crate){
		ItemStack reward = new ItemStack(Material.PAPER);
		ItemMeta rm = reward.getItemMeta();
		if(crate.equals("genesis")){
			Random r = new Random();
			int ri = r.nextInt(100);

			if(ri <= 25){
				int tmin = 5000000;
				int tmax = 15000000;
				int tokens = r.nextInt(tmax - tmin)+ tmin;
				rm.setDisplayName(m.c("&b"+Main.formatAmt(tokens)+" Tokens"));
				reward.setType(Material.PRISMARINE_CRYSTALS);
				List<String> lore = new ArrayList<>();
				lore.add("tokens add %PLAYER% "+tokens);
				rm.setLore(lore);
			}
			if(ri > 25 && ri <=50) {
				int tmin = 25000;
				int tmax = 75000;
				int tokens = r.nextInt(tmax - tmin)+ tmin;
				rm.setDisplayName(m.c("&a"+Main.formatAmt(tokens)+" XP"));
				reward.setType(Material.EXP_BOTTLE);
				List<String> lore = new ArrayList<>();
				lore.add("givexp %PLAYER% "+tokens);
				rm.setLore(lore);
			}
			if(ri > 50 && ri <70){
				int tmin = 1;
				int tmax = 10;
				int keys = r.nextInt(tmax - tmin)+ tmin;

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
			p.setItemInHand(null);


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
				if(stand == null || stand.isDead()) cancel();
				++tick;


				Location loc = getLocationInCircle(place, rad*tick);
				assert stand != null;
				stand.teleport(loc);


			}
		}.runTaskTimer(Main.getInstance(), 0L, 1L);
	}



	public void spawnArmorStand(JavaPlugin plugin, Location place, List<ArmorStand> stands, List<ItemStack> items, List<Item> it, List<Hologram> holos, String crate, Player p, int time) {


		new BukkitRunnable() {
			@Override
			public void run() {
				if(!p.isOnline()) return;


				ItemStack reward = Reward(crate).clone();
				Item item = place.getWorld().dropItem(place, reward);
				item.setTicksLived(5635+time);
				item.setPickupDelay(6000);


				//Location faceSouth = new Location(place.getWorld(), place.getX(), place.getY(), place.getZ(), 0f, 0f);

				final ArmorStand stand = place.getWorld().spawn(place, ArmorStand.class);
				stand.setPassenger(item);

				stand.setGravity(false);
				stand.setVisible(false);

				Location holo = new Location(place.getWorld(), place.getX(), place.getY()+2.5, place.getZ());
				Hologram name = HologramsAPI.createHologram(plugin, holo);
				name.appendTextLine(reward.getItemMeta().getDisplayName());



				rotateStand(stand, place);


				stands.add(stand);
				items.add(reward);
				it.add(item);
				holos.add(name);
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

	public void startAnimation(JavaPlugin plugin, String crate, Location loc, List<ArmorStand> stands, List<ItemStack> items, List<Item> item, List<Hologram> holos, Player p, int time, double x, double y, double z){
		new BukkitRunnable() {

			@Override
			public void run() {
				if(!p.isOnline()) return;
				Location place = new Location(loc.getWorld(), loc.getX()+x, loc.getY()+y+1, loc.getZ()+z);

				for (int i = 0; i < 40; i++)
					playSound(plugin, p, Sound.NOTE_PIANO, i);

				// Try to use a SingleTask - Check GitHub
				new ParticleBuilder(ParticleEffect.ENCHANTMENT_TABLE, place)
						.setOffsetX((float) -x)
						.setOffsetZ((float) -z)
						.setSpeed(0.1f)
						.display();

				spawnArmorStand(plugin, place, stands, items, item, holos, crate, p, time);



			}
		}.runTaskLater(plugin, time);
	}

	public void displayRewards(JavaPlugin plugin, String crate, String name, Location loc, List<ArmorStand> stands, Player p) {

		Location block = new Location(loc.getWorld(), loc.getX(), loc.getY() +3, loc.getZ());


		block.getWorld().getBlockAt(block).setType(Material.CHEST);

		m.playChestAction((Chest) block.getWorld().getBlockAt(block).getState(), true, p);
		Block b = block.getWorld().getBlockAt(block);
		BlockState state = b.getState();

		double rotation = p.getLocation().getYaw() - 180;
		if (rotation < 0) {
			rotation += 360.0;
		}
		if (0 <= rotation && rotation < 90.5) {
			org.bukkit.material.Chest c = new org.bukkit.material.Chest(BlockFace.SOUTH);
			state.setData(c);
			state.update();
		}
		if (90.5 <= rotation && rotation < 180.5) {
			org.bukkit.material.Chest c = new org.bukkit.material.Chest(BlockFace.WEST);
			state.setData(c);
			state.update();
		}
		if (180.5 <= rotation && rotation < 270.5) {
			org.bukkit.material.Chest c = new org.bukkit.material.Chest(BlockFace.NORTH);
			state.setData(c);
			state.update();
		}
		if (270.5 <= rotation && rotation <= 360) {
			org.bukkit.material.Chest c = new org.bukkit.material.Chest(BlockFace.EAST);
			state.setData(c);
			state.update();
		}





		List<ItemStack> items = new ArrayList<>();
		List<Hologram> holos = new ArrayList<>();
		List<Item> item = new ArrayList<>();


		//WEST and EAST = Z coord || NORTH and SOUTH = X coord
		if ((0 <= rotation && rotation < 90.5) || (180.5 <= rotation && rotation < 270.5) ) { //SOUTH AND NORTH
			Location holo = new Location(loc.getWorld(), loc.getX()+0.5, loc.getY() +0.5, loc.getZ()+0.25);
			Hologram chest = HologramsAPI.createHologram(plugin, holo);
			chest.appendTextLine(m.c(name));
			holos.add(chest);

			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 20, 0.5, 1.75, 0.25); // Adjust by +-0.5 to account for block coords not being in the center of the block
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 65, 1.75, 1.5, 0.25);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 110, 2.0, 0, 0.25);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 155, 1.75, -1.5, 0.25);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 200, 0.5, -1.75, 0.25);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 245, -0.75, -1.5, 0.25);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 290, -1.0, 0, 0.25);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 335, -0.75, 1.5, 0.25);
		}
		if ((90.5 <= rotation && rotation < 180.5) || (270.5 <= rotation && rotation <= 360) ) { //WEST AND EAST
			Location holo = new Location(loc.getWorld(), loc.getX()+0.25, loc.getY() +0.5, loc.getZ()+0.5);
			Hologram chest = HologramsAPI.createHologram(plugin, holo);
			chest.appendTextLine(m.c(name));
			holos.add(chest);

			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 20, 0.25, 1.75, 0.5);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 65, 0.25, 1.5, 1.75);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 110, 0.25, 0, 2.0);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 155, 0.25, -1.5, 1.75);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 200, 0.25, -1.75, 0.5);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 245, 0.25, -1.5, -0.75);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 290, 0.25, 0, -1.0);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 335, 0.25, 1.5, -0.75);
		}
		finishOpen(plugin, block, p, stands, items, item, holos, 420);



	}



	public void finishOpen(JavaPlugin plugin, Location loc, Player p, List<ArmorStand> stands, List<ItemStack> items, List<Item> item, List<Hologram> holos, int time){

		new BukkitRunnable(){
			@Override
			public void run(){
				if(!p.isOnline()) return;

				for(ArmorStand s : stands){
					s.remove();

				}
				for(Item i : item){
					i.remove();
				}
				for(Hologram h : holos){
					h.delete();
				}

				loc.getWorld().getBlockAt(loc).setType(Material.AIR);

				for(ItemStack item : items){
					if(item.getItemMeta().hasLore()) {
						String s = item.getItemMeta().getLore().get(0).replace("%PLAYER%", p.getName());
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
					} else {
						p.getInventory().addItem(item);
						p.updateInventory();
					}
				}
			}
		}.runTaskLater(plugin, time);


	}







	

}
