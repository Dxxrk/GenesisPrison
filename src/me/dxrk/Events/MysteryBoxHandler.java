package me.dxrk.Events;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.Blocks;
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockAction;
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
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.EnderChest;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MysteryBoxHandler implements Listener, CommandExecutor {

	Methods m = Methods.getInstance();




	public void spawnItem(ItemStack i, Location loc) {
		Item item = loc.getWorld().dropItem(loc, i);
		final ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class);
		stand.setPassenger(item);
	}

	@EventHandler
	public void armorstand(PlayerArmorStandManipulateEvent e) {
		if(!e.getPlayer().isOp())
			e.setCancelled(true);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(label.equalsIgnoreCase("givecrate")){
			if(!sender.isOp()) return false;
			if(args.length == 2) {
				Player p = Bukkit.getPlayer(args[0]);
				if(args[1].equalsIgnoreCase("genesis")){
					p.getInventory().addItem(CrateFunctions.GenesisCrate());
					p.updateInventory();
				}
				if(args[1].equalsIgnoreCase("contraband")){
					p.getInventory().addItem(CrateFunctions.ContrabandCrate());
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
			if(args.length == 3){
				Random r = new Random();
				Player p = Bukkit.getPlayer(args[0]);
				int xp1 = Integer.parseInt(args[1]);
				int xp2 = Integer.parseInt(args[2]);
				int xp = r.nextInt(xp2 - xp1)+ xp1;

				PickXPHandler.getInstance().addXP(p, xp);
			}
		}

		return false;
	}






	
	@EventHandler
	public void onPlace(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
			if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
			if(p.getItemInHand() == null) return;
			if(!p.getItemInHand().hasItemMeta()) return;
			if(!p.getItemInHand().getItemMeta().hasDisplayName()) return;
			if(!p.getItemInHand().getItemMeta().hasLore()) return;



			Location loc = e.getClickedBlock().getLocation();
			List<ArmorStand> stands = new ArrayList<>();


		if(p.getItemInHand().getItemMeta().getDisplayName().equals(m.c("&f&l&k[&7&l*&f&l&k]&r &9&lGenesis &b&lCrate &f&l&k[&7&l*&f&l&k]&r")) && p.getItemInHand().getType().equals(Material.ENDER_CHEST)) {
			e.setCancelled(true);
			p.updateInventory();
			displayRewards(Main.getInstance(), "genesis", "&f&l&k[&7&l*&f&l&k]&r &9&lGenesis &b&lCrate &f&l&k[&7&l*&f&l&k]&r", loc, stands, p);
			if(p.getItemInHand().getAmount() > 1){
				int i = p.getItemInHand().getAmount();
				p.getItemInHand().setAmount(i-1);
			} else {
				p.setItemInHand(null);
			}
			return;
		}
		if(p.getItemInHand().getItemMeta().getDisplayName().equals(m.c("&c&lContraband Crate")) && p.getItemInHand().getType().equals(Material.ENDER_CHEST)) {
			e.setCancelled(true);
			p.updateInventory();
			displayRewards(Main.getInstance(), "contraband", "&c&lContraband Crate", loc, stands, p);
			if(p.getItemInHand().getAmount() > 1){
				int i = p.getItemInHand().getAmount();
				p.getItemInHand().setAmount(i-1);
			} else {
				p.setItemInHand(null);
			}

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


				ItemStack reward = CrateFunctions.Reward(crate).clone();
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

	public enum Yaw {
		NORTH, SOUTH, EAST, WEST;

		public static Yaw getYaw(Player p) {
			float yaw = p.getLocation().getYaw();
			yaw = (yaw % 360 + 360) % 360; // true modulo, as javas modulo is weird for negative values
			if (yaw > 225 && yaw <315) {
				return Yaw.EAST;
			}else if (yaw > 135 && yaw < 225) {
				return Yaw.NORTH;
			}else if (yaw > 45 && yaw < 135) {
				return Yaw.WEST;
			} else {
				return Yaw.SOUTH;
			}
		}
	}

	public void displayRewards(JavaPlugin plugin, String crate, String name, Location loc, List<ArmorStand> stands, Player p) {

		Location block = new Location(loc.getWorld(), loc.getX(), loc.getY() +3, loc.getZ());


		block.getWorld().getBlockAt(block).setType(Material.ENDER_CHEST);
		m.changeChestState(block, true);
		Block b =  block.getWorld().getBlockAt(block);
		BlockState state = b.getState();



		//FACE WEST == 45 - 135
		//FACE EAST == 225 - 315
		//FACE SOUTH == 315 - 45 on opposite side
		//FACE NORTH (-)135 - 225(+)



		if (Yaw.getYaw(p).equals(Yaw.NORTH)) {
			org.bukkit.material.EnderChest c = new org.bukkit.material.EnderChest(BlockFace.SOUTH);
			state.setData(c);
			state.update();
		}
		if (Yaw.getYaw(p).equals(Yaw.EAST)) {
			org.bukkit.material.EnderChest c = new org.bukkit.material.EnderChest(BlockFace.WEST);
			state.setData(c);
			state.update();
		}
		if (Yaw.getYaw(p).equals(Yaw.SOUTH)) {
			org.bukkit.material.EnderChest c = new org.bukkit.material.EnderChest(BlockFace.NORTH);
			state.setData(c);
			state.update();
		}
		if (Yaw.getYaw(p).equals(Yaw.WEST)) {
			org.bukkit.material.EnderChest c = new org.bukkit.material.EnderChest(BlockFace.EAST);
			state.setData(c);
			state.update();
		}





		List<ItemStack> items = new ArrayList<>();
		List<Hologram> holos = new ArrayList<>();
		List<Item> item = new ArrayList<>();


		//WEST and EAST = Z coord || NORTH and SOUTH = X coord
		if ((Yaw.getYaw(p).equals(Yaw.NORTH) || Yaw.getYaw(p).equals(Yaw.SOUTH)) ) { //SOUTH AND NORTH
			Location holo = new Location(loc.getWorld(), loc.getX()+0.5, loc.getY() +4.5, loc.getZ()+0.25);
			Hologram chest = HologramsAPI.createHologram(plugin, holo);
			chest.appendTextLine(m.c(name));
			holos.add(chest);

			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 20, 0.5, 2.5, 0.25); // Adjust by +-0.5 to account for block coords not being in the center of the block
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 65, 1.75, 2.25, 0.25);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 110, 2.0, 0.75, 0.25);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 155, 1.75, -0.75, 0.25);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 200, 0.5, -1.0, 0.25);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 245, -0.75, -0.75, 0.25);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 290, -1.0, 0.75, 0.25);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 335, -0.75, 2.25, 0.25);
		}
		if ((Yaw.getYaw(p).equals(Yaw.WEST) || Yaw.getYaw(p).equals(Yaw.EAST))) { //WEST AND EAST
			Location holo = new Location(loc.getWorld(), loc.getX()+0.25, loc.getY() +4.5, loc.getZ()+0.5);
			Hologram chest = HologramsAPI.createHologram(plugin, holo);
			chest.appendTextLine(m.c(name));
			holos.add(chest);

			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 20, 0.25, 2.5, 0.5);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 65, 0.25, 2.25, 1.75);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 110, 0.25, 0.75, 2.0);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 155, 0.25, -0.75, 1.75);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 200, 0.25, -1.0, 0.5);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 245, 0.25, -0.75, -0.75);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 290, 0.25, 0.75, -1.0);
			startAnimation(plugin, crate, loc, stands, items, item, holos, p, 335, 0.25, 2.25, -0.75);
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
