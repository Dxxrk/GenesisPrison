package me.dxrk.Events;

import me.dxrk.Enchants.PickaxeLevel;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrinketHandler implements Listener, CommandExecutor{
	static TrinketHandler instance = new TrinketHandler();
	
	public static TrinketHandler getInstance() {
		return instance;
	}
	Methods m = Methods.getInstance();

	SettingsManager settings = SettingsManager.getInstance();
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("givedust")) {
				if(sender.hasPermission("rank.owner")) {
					 if(args.length == 2) {
						 Player p = Bukkit.getPlayer(args[0]);
						 if(args[1].equalsIgnoreCase("Common")) {
							 p.getInventory().addItem(commonDust());
						 } else if(args[1].equalsIgnoreCase("Rare")) {
							 p.getInventory().addItem(rareDust());
						 } else if(args[1].equalsIgnoreCase("Epic")) {
							 p.getInventory().addItem(epicDust());
						 } else if(args[1].equalsIgnoreCase("Legendary")) {
							 p.getInventory().addItem(legDust());
						 } else if(args[1].equalsIgnoreCase("Heroic")) {
							 p.getInventory().addItem(herDust());
						 }
						 
					 }
				}
			
		}
		if(label.equalsIgnoreCase("givetrinket")) {
			
				if(sender.hasPermission("rank.owner")) {
					 if(args.length == 2) {
						 Player p = Bukkit.getPlayer(args[0]);
						 if(args[1].equalsIgnoreCase("Common")) {
							 p.getInventory().addItem(commonTrinket(1));
						 } else if(args[1].equalsIgnoreCase("Rare")) {
							 p.getInventory().addItem(rareTrinket(1));
						 } else if(args[1].equalsIgnoreCase("Epic")) {
							 p.getInventory().addItem(epicTrinket(1));
						 } else if(args[1].equalsIgnoreCase("Legendary")) {
							 p.getInventory().addItem(legTrinket(1));
						 } else if(args[1].equalsIgnoreCase("Heroic")) {
							 p.getInventory().addItem(herTrinket(1));
						 }
						 
					 }
					 if(args.length == 3){
						 int i = Integer.parseInt(args[2]);
						 Player p = Bukkit.getPlayer(args[0]);
						 if(args[1].equalsIgnoreCase("Common")) {
							 p.getInventory().addItem(commonTrinket(i));
						 } else if(args[1].equalsIgnoreCase("Rare")) {
							 p.getInventory().addItem(rareTrinket(i));
						 } else if(args[1].equalsIgnoreCase("Epic")) {
							 p.getInventory().addItem(epicTrinket(i));
						 } else if(args[1].equalsIgnoreCase("Legendary")) {
							 p.getInventory().addItem(legTrinket(i));
						 } else if(args[1].equalsIgnoreCase("Heroic")) {
							 p.getInventory().addItem(herTrinket(i));
						 }
					 }
				}
			}
		if(label.equalsIgnoreCase("trinket") || label.equalsIgnoreCase("trinkets") ) {
			Player p = (Player) sender;
			if(p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE) || p.getItemInHand().getType().equals(Material.IRON_PICKAXE) || p.getItemInHand().getType().equals(Material.GOLD_PICKAXE)
			|| p.getItemInHand().getType().equals(Material.STONE_PICKAXE) || p.getItemInHand().getType().equals(Material.WOOD_PICKAXE)) {
				openTrinkets(p);
			} else {
				p.sendMessage(m.c("&f&lTrinkets &8| &7Please hold your pickaxe!"));
			}
		}
		
		
		
		return false;
	}
	
	
	public ItemStack commonDust() {
		ArrayList<String> lore = new ArrayList<>();
		ItemStack dust = new ItemStack(Material.SUGAR);
		ItemMeta dm = dust.getItemMeta();
		dm.setDisplayName(m.c("&bCommon Trinket Dust"));
		lore.add(m.c("&7&oUse this to craft a &bCommon &7&oTrinket"));
		dm.setLore(lore);
		dust.setItemMeta(dm);
		return dust;
	}
	public ItemStack rareDust() {
		ArrayList<String> lore = new ArrayList<>();
		ItemStack dust = new ItemStack(Material.SUGAR);
		ItemMeta dm = dust.getItemMeta();
		dm.setDisplayName(m.c("&9Rare Trinket Dust"));
		lore.add(m.c("&7&oUse this to craft a &9Rare &7&oTrinket"));
		dm.setLore(lore);
		dust.setItemMeta(dm);
		return dust;
	}
	public ItemStack epicDust() {
		ArrayList<String> lore = new ArrayList<>();
		ItemStack dust = new ItemStack(Material.SUGAR);
		ItemMeta dm = dust.getItemMeta();
		dm.setDisplayName(m.c("&5Epic Trinket Dust"));
		lore.add(m.c("&7&oUse this to craft a &5Epic &7&oTrinket"));
		dm.setLore(lore);
		dust.setItemMeta(dm);
		return dust;
	}
	public ItemStack legDust() {
		ArrayList<String> lore = new ArrayList<>();
		ItemStack dust = new ItemStack(Material.SUGAR);
		ItemMeta dm = dust.getItemMeta();
		dm.setDisplayName(m.c("&6Legendary Trinket Dust"));
		lore.add(m.c("&7&oUse this to craft a &6Legendary &7&oTrinket"));
		dm.setLore(lore);
		dust.setItemMeta(dm);
		return dust;
	}
	public ItemStack herDust() {
		ArrayList<String> lore = new ArrayList<>();
		ItemStack dust = new ItemStack(Material.SUGAR);
		ItemMeta dm = dust.getItemMeta();
		dm.setDisplayName(m.c("&4Heroic Trinket Dust"));
		lore.add(m.c("&7&oUse this to craft a &4Heroic &7&oTrinket"));
		dm.setLore(lore);
		dust.setItemMeta(dm);
		return dust;
	}
	
	public ItemStack commonTrinket(int amount) {
		ArrayList<String> lore = new ArrayList<>();
		ItemStack trinket = new ItemStack(Material.GOLD_NUGGET, amount);
		ItemMeta dm = trinket.getItemMeta();
		dm.setDisplayName(m.c("&bCommon Trinket"));
		lore.add(m.c("&7&oRight Click to unveil"));
		dm.setLore(lore);
		trinket.setItemMeta(dm);
		return trinket;
	}
	public ItemStack rareTrinket(int amount) {
		ArrayList<String> lore = new ArrayList<>();
		ItemStack trinket = new ItemStack(Material.GOLD_NUGGET, amount);
		ItemMeta dm = trinket.getItemMeta();
		dm.setDisplayName(m.c("&9Rare Trinket"));
		lore.add(m.c("&7&oRight Click to unveil"));
		dm.setLore(lore);
		trinket.setItemMeta(dm);
		return trinket;
	}
	public ItemStack epicTrinket(int amount) {
		ArrayList<String> lore = new ArrayList<>();
		ItemStack trinket = new ItemStack(Material.GOLD_NUGGET, amount);
		ItemMeta dm = trinket.getItemMeta();
		dm.setDisplayName(m.c("&5Epic Trinket"));
		lore.add(m.c("&7&oRight Click to unveil"));
		dm.setLore(lore);
		trinket.setItemMeta(dm);
		return trinket;
	}
	public ItemStack legTrinket(int amount) {
		ArrayList<String> lore = new ArrayList<>();
		ItemStack trinket = new ItemStack(Material.GOLD_NUGGET, amount);
		ItemMeta dm = trinket.getItemMeta();
		dm.setDisplayName(m.c("&6Legendary Trinket"));
		lore.add(m.c("&7&oRight Click to unveil"));
		dm.setLore(lore);
		trinket.setItemMeta(dm);
		return trinket;
	}
	public ItemStack herTrinket(int amount) {
		ArrayList<String> lore = new ArrayList<>();
		ItemStack trinket = new ItemStack(Material.GOLD_NUGGET, amount);
		ItemMeta dm = trinket.getItemMeta();
		dm.setDisplayName(m.c("&4Heroic Trinket"));
		lore.add(m.c("&7&oRight Click to unveil"));
		dm.setLore(lore);
		trinket.setItemMeta(dm);
		return trinket;
	}

	
	
    
	public void customDustCommon() {
		ShapedRecipe r = new ShapedRecipe(commonTrinket(1));

		r.shape("###", "%%%", "$$$");
		r.setIngredient('#', Material.SUGAR);
		r.setIngredient('%', Material.SUGAR);
		r.setIngredient('$', Material.SUGAR);

		Bukkit.addRecipe(r);

		}
	public void customDustRare() {
		ShapedRecipe r = new ShapedRecipe(rareTrinket(1));

		r.shape("##$", "%#%", "$%$");
		r.setIngredient('#', Material.SUGAR);
		r.setIngredient('%', Material.SUGAR);
		r.setIngredient('$', Material.SUGAR);

		Bukkit.addRecipe(r);

		}
	public void customDustEpic() {
		ShapedRecipe r = new ShapedRecipe(epicTrinket(1));

		r.shape("%##", "$%%", "#$$");
		r.setIngredient('#', Material.SUGAR);
		r.setIngredient('%', Material.SUGAR);
		r.setIngredient('$', Material.SUGAR);

		Bukkit.addRecipe(r);

		}
	public void customDustLegendary() {
		ShapedRecipe r = new ShapedRecipe(legTrinket(1));

		r.shape("#$#", "#%%", "$$%");
		r.setIngredient('#', Material.SUGAR);
		r.setIngredient('%', Material.SUGAR);
		r.setIngredient('$', Material.SUGAR);

		Bukkit.addRecipe(r);

		}
	public void customDustHeroic() {
		ShapedRecipe r = new ShapedRecipe(herTrinket(1));

		r.shape("%##", "%%$", "$$#");
		r.setIngredient('#', Material.SUGAR);
		r.setIngredient('%', Material.SUGAR);
		r.setIngredient('$', Material.SUGAR);

		Bukkit.addRecipe(r);

		}
	public void commonDusting() {
		ShapelessRecipe r = new ShapelessRecipe(rareDust());
		
		r.addIngredient(1, Material.GOLD_NUGGET);
		
		Bukkit.addRecipe(r);
	}
	public void rareDusting() {
		ShapelessRecipe r = new ShapelessRecipe(epicDust());
		
		r.addIngredient(1, Material.GOLD_NUGGET);
		
		Bukkit.addRecipe(r);
	}
	public void epicDusting() {
		ShapelessRecipe r = new ShapelessRecipe(legDust());
		
		r.addIngredient(1, Material.GOLD_NUGGET);
		
		Bukkit.addRecipe(r);
	}
	public void legendaryDusting() {
		ShapelessRecipe r = new ShapelessRecipe(herDust());
		
		r.addIngredient(1, Material.GOLD_NUGGET);
		
		Bukkit.addRecipe(r);
	}
	
	
	//Create Custom inventory for creating higher tier trinkets -- nevermind?

	public ItemStack Spacer() {
		ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)8);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', "&9Genesis"));
		im.addEnchant(Enchantment.DURABILITY, 0, false);
		i.setItemMeta(im);
		i.removeEnchantment(Enchantment.DURABILITY);
		return i;
	}
	public void openTrinkets(Player p){
		Inventory trinket = Bukkit.createInventory(null, 9, m.c("&a&lTrinkets:"));

		List<String> TrinketList = settings.getPlayerData().getStringList(p.getUniqueId().toString()+".Trinkets");

		ItemStack trinket1 = new ItemStack(Material.GOLD_NUGGET);
		ItemMeta tm1 = trinket1.getItemMeta();
		List<String> lore = new ArrayList<>();
		if(TrinketList.size() > 0){
			if(TrinketList.get(0) != null){
				tm1.setDisplayName(TrinketList.get(0));
			}
		} else {
			tm1.setDisplayName(m.c("&cNo Trinket Applied"));
			lore.add(m.c("&7&oDrag a Trinket Here to Apply."));
			tm1.setLore(lore);
		}
		trinket1.setItemMeta(tm1);
		trinket.setItem(1, trinket1);
		lore.clear();

		ItemStack trinket2 = new ItemStack(Material.GOLD_NUGGET);
		ItemMeta tm2 = trinket2.getItemMeta();
		if(TrinketList.size() > 1){
			if(TrinketList.get(1) != null){
				tm2.setDisplayName(TrinketList.get(1));
			}
		} else {
			tm2.setDisplayName(m.c("&cNo Trinket Applied"));
			lore.add(m.c("&7&oDrag a Trinket Here to Apply."));
			tm2.setLore(lore);
		}
		trinket2.setItemMeta(tm2);
		trinket.setItem(3, trinket2);
		lore.clear();

		ItemStack trinket3 = new ItemStack(Material.GOLD_NUGGET);
		ItemMeta tm3 = trinket3.getItemMeta();
		if(TrinketList.size() > 2){
			if(TrinketList.get(2) != null){
				tm3.setDisplayName(TrinketList.get(2));
			}
		} else {
			tm3.setDisplayName(m.c("&cNo Trinket Applied"));
			lore.add(m.c("&7&oDrag a Trinket Here to Apply."));
			tm3.setLore(lore);
		}
		trinket3.setItemMeta(tm3);
		trinket.setItem(5, trinket3);
		lore.clear();

		ItemStack trinket4 = new ItemStack(Material.GOLD_NUGGET);
		ItemMeta tm4 = trinket4.getItemMeta();
		if(TrinketList.size() > 3){
			if(TrinketList.get(3) != null){
				tm4.setDisplayName(TrinketList.get(3));
			}
		} else {
			tm4.setDisplayName(m.c("&cNo Trinket Applied"));
			lore.add(m.c("&7&oDrag a Trinket Here to Apply."));
			tm4.setLore(lore);
		}
		trinket4.setItemMeta(tm4);
		trinket.setItem(7, trinket4);
		lore.clear();

		trinket.setItem(0, Spacer());
		trinket.setItem(2, Spacer());
		trinket.setItem(4, Spacer());
		trinket.setItem(6, Spacer());
		trinket.setItem(8, Spacer());

		p.openInventory(trinket);

	}

	@EventHandler
	public void onInvClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		if (e.getClickedInventory() == null)
			return;
		if (e.getClickedInventory().getName() == null)
			return;
		if(e.getCurrentItem() == null) {
			return;
		}

		if(e.getClickedInventory().getName().equals(m.c("&a&lTrinkets:"))){
			if(e.getSlot() == 1 || e.getSlot() == 3 || e.getSlot() == 5 || e.getSlot() == 7){
				e.setCancelled(true);
				if(e.getCursor().getType().equals(Material.GOLD_NUGGET)){
					if(ChatColor.stripColor(e.getCursor().getItemMeta().getDisplayName()).contains("Trinket")){
						List<String> trinket = settings.getPlayerData().getStringList(p.getUniqueId().toString()+".Trinkets");
						if(e.getSlot() == 1) {
							if(trinket.size() > 0 && trinket.get(0) !=null) {
								trinket.set(0, e.getCursor().getItemMeta().getDisplayName() + " - "+e.getCursor().getItemMeta().getLore().get(0).split(" ")[0]);
							} else {
								trinket.add(e.getCursor().getItemMeta().getDisplayName() + " - "+e.getCursor().getItemMeta().getLore().get(0).split(" ")[0]);
							}
						}
						if(e.getSlot() == 3) {
							if(trinket.size() > 1 && trinket.get(1) !=null) {
								trinket.set(1, e.getCursor().getItemMeta().getDisplayName() + " - "+e.getCursor().getItemMeta().getLore().get(0).split(" ")[0]);
							} else {
								trinket.add(e.getCursor().getItemMeta().getDisplayName() + " - "+e.getCursor().getItemMeta().getLore().get(0).split(" ")[0]);
							}
						}
						if(e.getSlot() == 5) {
							if(trinket.size() > 2 && trinket.get(2) !=null) {
								trinket.set(2, e.getCursor().getItemMeta().getDisplayName() + " - "+e.getCursor().getItemMeta().getLore().get(0).split(" ")[0]);
							} else {
								trinket.add(e.getCursor().getItemMeta().getDisplayName() + " - "+e.getCursor().getItemMeta().getLore().get(0).split(" ")[0]);
							}
						}
						if(e.getSlot() == 7) {
							if(trinket.size() > 3 && trinket.get(3) !=null) {
								trinket.set(3, e.getCursor().getItemMeta().getDisplayName() + " - "+e.getCursor().getItemMeta().getLore().get(0).split(" ")[0]);
							} else {
								trinket.add(e.getCursor().getItemMeta().getDisplayName() + " - "+e.getCursor().getItemMeta().getLore().get(0).split(" ")[0]);
							}
						}
						settings.getPlayerData().set(p.getUniqueId().toString()+".Trinkets", trinket);
						e.setCursor(null);
						openTrinkets(p);
						ItemStack pitem = p.getItemInHand().clone();
						ItemMeta pm = pitem.getItemMeta();
						List<String> lore = pm.getLore();
						pm.setLore(PickaxeLevel.getInstance().Lore(lore, p));
						pitem.setItemMeta(pm);
						p.setItemInHand(pitem);
						p.updateInventory();
					}

				}
			} else {
				e.setCancelled(true);
			}
		}
	}





	
	@EventHandler
	public void afterCraft(PrepareItemCraftEvent e) {
			if(e.getRecipe().getResult().equals(rareDust())) {
				ItemStack[] items = e.getInventory().getMatrix();
				String common = m.c("&bCommon Trinket");
				String rare = m.c("&9Rare Trinket");
				String epic = m.c("&5Epic Trinket");
				String legendary = m.c("&6Legendary Trinket");
				String heroic = m.c("&4Heroic Trinket");
				for(int i = 0; i < 9; i++) {
					if(items[i] != null && !items[i].getType().equals(Material.AIR) && items[i].hasItemMeta()) {
						String[] name = ChatColor.stripColor(items[i].getItemMeta().getDisplayName()).split(" ");
						if(items[i].getItemMeta().getDisplayName().equals(common)) {
							e.getInventory().setResult(rareDust());
							break;
						}
						else if(items[i].getItemMeta().getDisplayName().equals(rare)) {
							e.getInventory().setResult(epicDust());
							break;
						}
						else if(items[i].getItemMeta().getDisplayName().equals(epic)) {
							e.getInventory().setResult(legDust());
							break;
						}
						else if(items[i].getItemMeta().getDisplayName().equals(legendary)) {
							e.getInventory().setResult(herDust());
							break;
						}
						else if(items[i].getItemMeta().getDisplayName().equals(heroic)) {
							e.getInventory().setResult(null);
							break;
						} else {
							e.getInventory().setResult(null);
						}
					}else {
						e.getInventory().setResult(null);
					}
				}
			}
			if(e.getRecipe().getResult().equals(new ItemStack(Material.GOLD_INGOT))) {
				e.getInventory().setResult(new ItemStack(Material.AIR));
			}
			
			
			if(e.getRecipe().getResult().equals(commonTrinket(1))) {
				List<String> lore = new ArrayList<>();
				lore.add(m.c("&7&oUse this to craft a &bCommon &7&oTrinket"));
				boolean goodToCraft = false;
				ItemStack[] items = e.getInventory().getMatrix();
				for(int i = 0; i < 9; i++) {
					if(items[i].hasItemMeta()) {
						if(items[i].getItemMeta().getLore().equals(lore)) {
							goodToCraft = true;
						} else {
							goodToCraft = false;
							break;
						}
					}else {
						goodToCraft = false;
						break;
					}
				}
				if(goodToCraft == true) {
					e.getInventory().setResult(commonTrinket(1));
				} else if(goodToCraft == false) {
					lore.clear();
					lore.add(m.c("&7&oUse this to craft a &9Rare &7&oTrinket"));
					items = e.getInventory().getMatrix();
					for(int i = 0; i < 9; i++) {
						if(items[i].hasItemMeta()) {
							if(items[i].getItemMeta().getLore().equals(lore)) {
								goodToCraft = true;
							} else {
								goodToCraft = false;
								break;
							}
						}else {
							goodToCraft = false;
							break;
						}
					}
					if(goodToCraft == true) {
						e.getInventory().setResult(rareTrinket(1));
					} else if(goodToCraft == false) {
						lore.clear();
						lore.add(m.c("&7&oUse this to craft a &5Epic &7&oTrinket"));
						
						items = e.getInventory().getMatrix();
						for(int i = 0; i < 9; i++) {
							if(items[i].hasItemMeta()) {
								if(items[i].getItemMeta().getLore().equals(lore)) {
									goodToCraft = true;
								} else {
									goodToCraft = false;
									break;
								}
							}else {
								goodToCraft = false;
								break;
							}
						}
						if(goodToCraft == true) {
							e.getInventory().setResult(epicTrinket(1));
						} else if(goodToCraft == false) {
							lore.clear();
							lore.add(m.c("&7&oUse this to craft a &6Legendary &7&oTrinket"));
							items = e.getInventory().getMatrix();
							for(int i = 0; i < 9; i++) {
								if(items[i].hasItemMeta()) {
									if(items[i].getItemMeta().getLore().equals(lore)) {
										goodToCraft = true;
									} else {
										goodToCraft = false;
										break;
									}
								}else {
									goodToCraft = false;
									break;
								}
							}
							if(goodToCraft == true) {
								e.getInventory().setResult(legTrinket(1));
							} else if(goodToCraft == false) {
								lore.clear();
								lore.add(m.c("&7&oUse this to craft a &4Heroic &7&oTrinket"));
								items = e.getInventory().getMatrix();
								for(int i = 0; i < 9; i++) {
									if(items[i].hasItemMeta()) {
										if(items[i].getItemMeta().getLore().equals(lore)) {
											goodToCraft = true;
										} else {
											goodToCraft = false;
											break;
										}
									}else {
										goodToCraft = false;
										break;
									}
								}
								if(goodToCraft == true) {
									e.getInventory().setResult(herTrinket(1));
								} else if(goodToCraft == false) {
									e.getInventory().setResult(null);
								}
								
							}
						}
					}
				}
			}
			 
		}

	
	public void openTrinket(Player p, int rarity) {
		Random r = new Random();
		
		
		if(rarity == 1) {
			int rint = r.nextInt(5);
			if(rint == 0) {
				int min = 3;
				int max = 10;
				 int Sell = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&bCommon Sell Trinket"));
				lore.add(m.c("&b"+(Sell)+ "% &7Sell Boost"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				p.getInventory().addItem(trinket);
				
			} else if(rint == 1) {
				int min = 2;
				int max = 10;
				 int XP = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&bCommon XP Trinket"));
				lore.add(m.c("&b"+(XP)+ "% &7Bonus XP"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 2) {
				int min = 2;
				int max = 5;
				 int Key = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&bCommon Double Keys Trinket"));
				lore.add(m.c("&b"+(Key)+ "% &7Double Keys"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 3){
				int min = 5;
				int max = 10;
				 int Luck = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&bCommon Lucky Trinket"));
				lore.add(m.c("&b"+(Luck)+ "% &7Additional Luck"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();

				p.getInventory().addItem(trinket);
			} else {
				int min = 5;
				int max = 10;
				int Luck = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&bCommon Token Trinket"));
				lore.add(m.c("&b"+(Luck)+ "% &7Extra Tokens"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				p.getInventory().addItem(trinket);
			}
			
			
		} else if(rarity == 2) {
			int rint = r.nextInt(5);
			if(rint == 0) {
				int min = 10;
				int max = 18;
				 int Sell = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&9Rare Sell Trinket"));
				lore.add(m.c("&9"+(Sell)+ "% &7Sell Boost"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				p.getInventory().addItem(trinket);
				
			} else if(rint == 1) {
				int min = 10;
				int max = 17;
				 int XP = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&9Rare XP Trinket"));
				lore.add(m.c("&9"+(XP)+ "% &7Bonus XP"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 2) {
				int min = 5;
				int max = 8;
				 int Key = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&9Rare Double Keys Trinket"));
				lore.add(m.c("&9"+(Key)+ "% &7Double Keys"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 3) {
				int min = 10;
				int max = 15;
				 int Luck = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&9Rare Lucky Trinket"));
				lore.add(m.c("&9"+(Luck)+ "% &7Additional Luck"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else {
				int min = 10;
				int max = 15;
				int Luck = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&9Rare Token Trinket"));
				lore.add(m.c("&9"+(Luck)+ "% &7Extra Tokens"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();


				p.getInventory().addItem(trinket);

			}
		} else if(rarity == 3) {
			int rint = r.nextInt(5);
			if(rint == 0) {
				int min = 18;
				int max = 25;
				 int Sell = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&5Epic Sell Trinket"));
				lore.add(m.c("&5"+(Sell)+ "% &7Sell Boost"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				p.getInventory().addItem(trinket);
				
			} else if(rint == 1) {
				int min = 17;
				int max = 25;
				 int XP = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&5Epic XP Trinket"));
				lore.add(m.c("&5"+(XP)+ "% &7Bonus XP"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 2) {
				int min = 8;
				int max = 11;
				 int Key = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&5Epic Double Keys Trinket"));
				lore.add(m.c("&5"+(Key)+ "% &7Double Keys"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 3){
				int min = 15;
				int max = 20;
				 int Luck = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&5Epic Lucky Trinket"));
				lore.add(m.c("&5"+(Luck)+ "% &7Additional Luck"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else {
				int min = 15;
				int max = 20;
				int Luck = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&5Epic Token Trinket"));
				lore.add(m.c("&5"+(Luck)+ "% &7Extra Tokens"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();


				p.getInventory().addItem(trinket);

			}
		} else if(rarity == 4) {
			int rint = r.nextInt(5);
			if(rint == 0) {
				int min = 25;
				int max = 40;
				 int Sell = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&6Legendary Sell Trinket"));
				lore.add(m.c("&6"+(Sell)+ "% &7Sell Boost"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				p.getInventory().addItem(trinket);
				
			} else if(rint == 1) {
				int min = 25;
				int max = 35;
				 int XP = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&6Legendary XP Trinket"));
				lore.add(m.c("&6"+(XP)+ "% &7Bonus XP"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 2) {
				int min = 11;
				int max = 14;
				 int Key = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&6Legendary Double Keys Trinket"));
				lore.add(m.c("&6"+(Key)+ "% &7Double Keys"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 3){
				int min = 20;
				int max = 25;
				 int Luck = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&6Legendary Lucky Trinket"));
				lore.add(m.c("&6"+(Luck)+ "% &7Additional Luck"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else {
				int min = 20;
				int max = 25;
				int Luck = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&6Legendary Token Trinket"));
				lore.add(m.c("&6"+(Luck)+ "% &7Extra Tokens"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();


				p.getInventory().addItem(trinket);

			}
		} else if(rarity == 5) {
			int rint = r.nextInt(5);
			if(rint == 0) {
				int min = 45;
				int max = 75;
				 int Sell = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&4Heroic Sell Trinket"));
				lore.add(m.c("&4"+(Sell)+ "% &7Sell Boost"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				p.getInventory().addItem(trinket);
				
			} else if(rint == 1) {
				int min = 50;
				int max = 100;
				 int XP = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&4Heroic XP Trinket"));
				lore.add(m.c("&4"+(XP)+ "% &7Bonus XP"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 2) {
				int min = 14;
				int max = 20;
				 int Key = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&4Heroic Double Keys Trinket"));
				lore.add(m.c("&4"+(Key)+ "% &7Double Keys"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 3){
				int min = 25;
				int max = 35;
				 int Luck = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&4Heroic Lucky Trinket"));
				lore.add(m.c("&4"+(Luck)+ "% &7Additional Luck"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else {
				int min = 25;
				int max = 35;
				int Luck = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<>();
				ItemStack trinket = new ItemStack(Material.GOLD_NUGGET);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&4Heroic Token Trinket"));
				lore.add(m.c("&4"+(Luck)+ "% &7Extra Tokens"));
				lore.add(m.c("&7&o/Trinkets to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();


				p.getInventory().addItem(trinket);

			}
		}
	}
	
	
	@EventHandler
	public void clickTrinket(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			int amount = p.getItemInHand().getAmount();
			if(p.getItemInHand().equals(commonTrinket(amount))) {
				if(amount == 1) {
					p.setItemInHand(null);
				} else {
					p.getItemInHand().setAmount(amount-1);
				}
				openTrinket(p, 1);
			} else if(p.getItemInHand().equals(rareTrinket(amount))) {
				if(amount == 1) {
					p.setItemInHand(null);
				} else {
					p.getItemInHand().setAmount(amount-1);
				}
				openTrinket(p, 2);
			} else if(p.getItemInHand().equals(epicTrinket(amount))) {
				if(amount == 1) {
					p.setItemInHand(null);
				} else {
					p.getItemInHand().setAmount(amount-1);
				}
				openTrinket(p, 3);
			} else if(p.getItemInHand().equals(legTrinket(amount))) {
				if(amount == 1) {
					p.setItemInHand(null);
				} else {
					p.getItemInHand().setAmount(amount-1);
				}
				openTrinket(p, 4);
			} else if(p.getItemInHand().equals(herTrinket(amount))) {
				if(amount == 1) {
					p.setItemInHand(null);
				} else {
					p.getItemInHand().setAmount(amount-1);
				}
				openTrinket(p, 5);
			}
		}
			
	}
	
	
		
	}
	
    
   
	
	
	


