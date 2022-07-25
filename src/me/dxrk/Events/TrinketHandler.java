package me.dxrk.Events;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import me.dxrk.Main.Methods;

public class TrinketHandler implements Listener, CommandExecutor{
	static TrinketHandler instance = new TrinketHandler();
	
	public static TrinketHandler getInstance() {
		return instance;
	}
	Methods m = Methods.getInstance();
	
	
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
				}
			}
		
		
		
		return false;
	}
	
	
	public ItemStack commonDust() {
		ArrayList<String> lore = new ArrayList<String>();
		ItemStack dust = new ItemStack(Material.SUGAR);
		ItemMeta dm = dust.getItemMeta();
		dm.setDisplayName(m.c("&bCommon Trinket Dust"));
		lore.add(m.c("&7&oUse this to craft a &bCommon &7&oTrinket"));
		dm.setLore(lore);
		dust.setItemMeta(dm);
		return dust;
	}
	public ItemStack rareDust() {
		ArrayList<String> lore = new ArrayList<String>();
		ItemStack dust = new ItemStack(Material.SUGAR);
		ItemMeta dm = dust.getItemMeta();
		dm.setDisplayName(m.c("&9Rare Trinket Dust"));
		lore.add(m.c("&7&oUse this to craft a &9Rare &7&oTrinket"));
		dm.setLore(lore);
		dust.setItemMeta(dm);
		return dust;
	}
	public ItemStack epicDust() {
		ArrayList<String> lore = new ArrayList<String>();
		ItemStack dust = new ItemStack(Material.SUGAR);
		ItemMeta dm = dust.getItemMeta();
		dm.setDisplayName(m.c("&5Epic Trinket Dust"));
		lore.add(m.c("&7&oUse this to craft a &5Epic &7&oTrinket"));
		dm.setLore(lore);
		dust.setItemMeta(dm);
		return dust;
	}
	public ItemStack legDust() {
		ArrayList<String> lore = new ArrayList<String>();
		ItemStack dust = new ItemStack(Material.SUGAR);
		ItemMeta dm = dust.getItemMeta();
		dm.setDisplayName(m.c("&6Legendary Trinket Dust"));
		lore.add(m.c("&7&oUse this to craft a &6Legendary &7&oTrinket"));
		dm.setLore(lore);
		dust.setItemMeta(dm);
		return dust;
	}
	public ItemStack herDust() {
		ArrayList<String> lore = new ArrayList<String>();
		ItemStack dust = new ItemStack(Material.SUGAR);
		ItemMeta dm = dust.getItemMeta();
		dm.setDisplayName(m.c("&4Heroic Trinket Dust"));
		lore.add(m.c("&7&oUse this to craft a &4Heroic &7&oTrinket"));
		dm.setLore(lore);
		dust.setItemMeta(dm);
		return dust;
	}
	
	public ItemStack commonTrinket(int amount) {
		ArrayList<String> lore = new ArrayList<String>();
		ItemStack trinket = new ItemStack(Material.EMERALD, amount);
		ItemMeta dm = trinket.getItemMeta();
		dm.setDisplayName(m.c("&bCommon Trinket"));
		lore.add(m.c("&7&oRight Click to unveil"));
		dm.setLore(lore);
		trinket.setItemMeta(dm);
		return trinket;
	}
	public ItemStack rareTrinket(int amount) {
		ArrayList<String> lore = new ArrayList<String>();
		ItemStack trinket = new ItemStack(Material.EMERALD, amount);
		ItemMeta dm = trinket.getItemMeta();
		dm.setDisplayName(m.c("&9Rare Trinket"));
		lore.add(m.c("&7&oRight Click to unveil"));
		dm.setLore(lore);
		trinket.setItemMeta(dm);
		return trinket;
	}
	public ItemStack epicTrinket(int amount) {
		ArrayList<String> lore = new ArrayList<String>();
		ItemStack trinket = new ItemStack(Material.EMERALD, amount);
		ItemMeta dm = trinket.getItemMeta();
		dm.setDisplayName(m.c("&5Epic Trinket"));
		lore.add(m.c("&7&oRight Click to unveil"));
		dm.setLore(lore);
		trinket.setItemMeta(dm);
		return trinket;
	}
	public ItemStack legTrinket(int amount) {
		ArrayList<String> lore = new ArrayList<String>();
		ItemStack trinket = new ItemStack(Material.EMERALD, amount);
		ItemMeta dm = trinket.getItemMeta();
		dm.setDisplayName(m.c("&6Legendary Trinket"));
		lore.add(m.c("&7&oRight Click to unveil"));
		dm.setLore(lore);
		trinket.setItemMeta(dm);
		return trinket;
	}
	public ItemStack herTrinket(int amount) {
		ArrayList<String> lore = new ArrayList<String>();
		ItemStack trinket = new ItemStack(Material.EMERALD, amount);
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
		
		r.addIngredient(1, Material.EMERALD);
		
		Bukkit.addRecipe(r);
	}
	public void rareDusting() {
		ShapelessRecipe r = new ShapelessRecipe(epicDust());
		
		r.addIngredient(1, Material.EMERALD);
		
		Bukkit.addRecipe(r);
	}
	public void epicDusting() {
		ShapelessRecipe r = new ShapelessRecipe(legDust());
		
		r.addIngredient(1, Material.EMERALD);
		
		Bukkit.addRecipe(r);
	}
	public void legendaryDusting() {
		ShapelessRecipe r = new ShapelessRecipe(herDust());
		
		r.addIngredient(1, Material.EMERALD);
		
		Bukkit.addRecipe(r);
	}
	
	
	
	
	@EventHandler
	public void afterCraft(PrepareItemCraftEvent e) {
		if(e.getView().getType().equals(InventoryType.PLAYER)) return;
		if(e.getView().getType().equals(InventoryType.WORKBENCH)) {
			
			if(e.getRecipe().getResult().equals(rareDust())) {
				ItemStack[] items = e.getInventory().getMatrix();
				String common = m.c("&bCommon Trinket");
				String rare = m.c("&9Rare Trinket");
				String epic = m.c("&5Epic Trinket");
				String legendary = m.c("&6Legendary Trinket");
				String heroic = m.c("&4Heroic Trinket");
				for(int i = 0; i < 9; i++) {
					if(items[i].hasItemMeta()) {
						String[] name = ChatColor.stripColor(items[i].getItemMeta().getDisplayName()).split(" ");
						if(items[i].getItemMeta().getDisplayName().equals(common) ||(name.length ==3  && name[0].equals("Common") && name[2].equals("Trinket")) ) {
							e.getInventory().setResult(rareDust());
							break;
						}
						else if(items[i].getItemMeta().getDisplayName().equals(rare) ||(name.length ==3  && name[0].equals("Rare") && name[2].equals("Trinket"))) {
							e.getInventory().setResult(epicDust());
							break;
						}
						else if(items[i].getItemMeta().getDisplayName().equals(epic) ||(name.length ==3  && name[0].equals("Epic") && name[2].equals("Trinket"))) {
							e.getInventory().setResult(legDust());
							break;
						}
						else if(items[i].getItemMeta().getDisplayName().equals(legendary) ||(name.length ==3  && name[0].equals("Legendary") && name[2].equals("Trinket"))) {
							e.getInventory().setResult(herDust());
							break;
						}
						else if(items[i].getItemMeta().getDisplayName().equals(heroic) ||(name.length ==3  && name[0].equals("Heroic") && name[2].equals("Trinket"))) {
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
			
			
			
			if(e.getRecipe().getResult().equals(commonTrinket(1))) {
				List<String> lore = new ArrayList<String>();
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
					return;
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
						return;
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
							return;
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
								return;
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
									return;
								} else if(goodToCraft == false) {
									e.getInventory().setResult(null);
								}
								
							}
						}
					}
				}
			}
			 
		}
		
	}
	
	/*@EventHandler
	public void Craft(CraftItemEvent e) {
		
		
		if(e.getCurrentItem().equals(commonTrinket(1))) {
			ItemStack[] items = e.getInventory().getMatrix();
			if(items[0].getAmount() >1) {
				items[0].setAmount(items[0].getAmount() -1);
			} else {
				items[0].setType(Material.AIR);
			}
			if(items[1].getAmount() >1) {
				items[1].setAmount(items[1].getAmount() -1);
			} else {
				items[1].setType(Material.AIR);
			}
			if(items[2].getAmount() >1) {
				items[2].setAmount(items[2].getAmount() -1);
			} else {
				items[2].setType(Material.AIR);
			}
			if(items[3].getAmount() >1) {
				items[3].setAmount(items[3].getAmount() -1);
			} else {
				items[3].setType(Material.AIR);
			}
			if(items[4].getAmount() >1) {
				items[4].setAmount(items[4].getAmount() -1);
			} else {
				items[4].setType(Material.AIR);
			}
			if(items[5].getAmount() >1) {
				items[5].setAmount(items[5].getAmount() -1);
			} else {
				items[5].setType(Material.AIR);
			}
			if(items[6].getAmount() >1) {
				items[6].setAmount(items[6].getAmount() -1);
			} else {
				items[6].setType(Material.AIR);
			}
			if(items[7].getAmount() >1) {
				items[7].setAmount(items[7].getAmount() -1);
			} else {
				items[7].setType(Material.AIR);
			}
			if(items[8].getAmount() >1) {
				items[8].setAmount(items[8].getAmount() -1);
			} else {
				items[8].setType(Material.AIR);
			}
		}else {
			
		}
	}*/
	
	public void openTrinket(Player p, int rarity) {
		Random r = new Random();
		
		
		if(rarity == 1) {
			int rint = r.nextInt(4);
			if(rint == 0) {
				int min = 3;
				int max = 10;
				 int Sell = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&bCommon Sell Trinket"));
				lore.add(m.c("&b"+(Sell)+ "% &7Sell Boost"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				p.getInventory().addItem(trinket);
				
			} else if(rint == 1) {
				int min = 2;
				int max = 10;
				 int XP = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&bCommon XP Trinket"));
				lore.add(m.c("&b"+(XP)+ "% &7Chance for Double XP"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 2) {
				int min = 2;
				int max = 5;
				 int Key = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&bCommon KeyFortune Trinket"));
				lore.add(m.c("&b"+(Key)+ "% &7Chance for Double Keys"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 3) {
				int min = 5;
				int max = 10;
				 int Luck = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&bCommon Lucky Trinket"));
				lore.add(m.c("&b"+(Luck)+ "% &7Additional Luck"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			}
		} else if(rarity == 2) {
			int rint = r.nextInt(4);
			if(rint == 0) {
				int min = 10;
				int max = 18;
				 int Sell = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&9Rare Sell Trinket"));
				lore.add(m.c("&9"+(Sell)+ "% &7Sell Boost"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				p.getInventory().addItem(trinket);
				
			} else if(rint == 1) {
				int min = 10;
				int max = 17;
				 int XP = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&9Rare XP Trinket"));
				lore.add(m.c("&9"+(XP)+ "% &7Chance for Double XP"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 2) {
				int min = 5;
				int max = 8;
				 int Key = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&9Rare KeyFortune Trinket"));
				lore.add(m.c("&9"+(Key)+ "% &7Chance for Double Keys"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 3) {
				int min = 10;
				int max = 15;
				 int Luck = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&9Rare Lucky Trinket"));
				lore.add(m.c("&9"+(Luck)+ "% &7Additional Luck"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			}
		} else if(rarity == 3) {
			int rint = r.nextInt(4);
			if(rint == 0) {
				int min = 18;
				int max = 25;
				 int Sell = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&5Epic Sell Trinket"));
				lore.add(m.c("&5"+(Sell)+ "% &7Sell Boost"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				p.getInventory().addItem(trinket);
				
			} else if(rint == 1) {
				int min = 17;
				int max = 25;
				 int XP = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&5Epic XP Trinket"));
				lore.add(m.c("&5"+(XP)+ "% &7Chance for Double XP"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 2) {
				int min = 8;
				int max = 11;
				 int Key = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&5Epic KeyFortune Trinket"));
				lore.add(m.c("&5"+(Key)+ "% &7Chance for Double Keys"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 3) {
				int min = 15;
				int max = 20;
				 int Luck = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&5Epic Lucky Trinket"));
				lore.add(m.c("&5"+(Luck)+ "% &7Additional Luck"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			}
		} else if(rarity == 4) {
			int rint = r.nextInt(4);
			if(rint == 0) {
				int min = 25;
				int max = 40;
				 int Sell = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&6Legendary Sell Trinket"));
				lore.add(m.c("&6"+(Sell)+ "% &7Sell Boost"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				p.getInventory().addItem(trinket);
				
			} else if(rint == 1) {
				int min = 25;
				int max = 35;
				 int XP = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&6Legendary XP Trinket"));
				lore.add(m.c("&6"+(XP)+ "% &7Chance for Double XP"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 2) {
				int min = 11;
				int max = 14;
				 int Key = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&6Legendary KeyFortune Trinket"));
				lore.add(m.c("&6"+(Key)+ "% &7Chance for Double Keys"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 3) {
				int min = 20;
				int max = 25;
				 int Luck = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&6Legendary Lucky Trinket"));
				lore.add(m.c("&6"+(Luck)+ "% &7Additional Luck"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			}
		} else if(rarity == 5) {
			int rint = r.nextInt(4);
			if(rint == 0) {
				int min = 45;
				int max = 75;
				 int Sell = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&4Heroic Sell Trinket"));
				lore.add(m.c("&4"+(Sell)+ "% &7Sell Boost"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				p.getInventory().addItem(trinket);
				
			} else if(rint == 1) {
				int min = 50;
				int max = 100;
				 int XP = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&4Heroic XP Trinket"));
				lore.add(m.c("&4"+(XP)+ "% &7Chance for Double XP"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 2) {
				int min = 14;
				int max = 20;
				 int Key = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&4Heroic KeyFortune Trinket"));
				lore.add(m.c("&4"+(Key)+ "% &7Chance for Double Keys"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
				tm.setLore(lore);
				trinket.setItemMeta(tm);
				lore.clear();
				
				
				p.getInventory().addItem(trinket);
				
			} else if(rint == 3) {
				int min = 25;
				int max = 35;
				 int Luck = r.nextInt(max-min)+min;
				ArrayList<String> lore = new ArrayList<String>();
				ItemStack trinket = new ItemStack(Material.EMERALD);
				ItemMeta tm = trinket.getItemMeta();
				tm.setDisplayName(m.c("&4Heroic Lucky Trinket"));
				lore.add(m.c("&4"+(Luck)+ "% &7Additional Luck"));
				lore.add(m.c("&7&oDrag onto an item to apply"));
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
				openTrinket(p, 1);
				if(amount == 1) {
					p.setItemInHand(null);
				} else {
					p.getItemInHand().setAmount(amount-1);
				}
			} else if(p.getItemInHand().equals(rareTrinket(amount))) {
				openTrinket(p, 2);
				if(amount == 1) {
					p.setItemInHand(null);
				} else {
					p.getItemInHand().setAmount(amount-1);
				}
			} else if(p.getItemInHand().equals(epicTrinket(amount))) {
				openTrinket(p, 3);
				if(amount == 1) {
					p.setItemInHand(null);
				} else {
					p.getItemInHand().setAmount(amount-1);
				}
			} else if(p.getItemInHand().equals(legTrinket(amount))) {
				openTrinket(p, 4);
				if(amount == 1) {
					p.setItemInHand(null);
				} else {
					p.getItemInHand().setAmount(amount-1);
				}
			} else if(p.getItemInHand().equals(herTrinket(amount))) {
				openTrinket(p, 5);
				if(amount == 1) {
					p.setItemInHand(null);
				} else {
					p.getItemInHand().setAmount(amount-1);
				}
			}
		}
			
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void dragTrinket(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		
		if(e.getCurrentItem() == null) return;
		if (e.getCurrentItem().getType().equals(Material.AIR))
		      return; 
		
		if(e.getClick().equals(ClickType.RIGHT)) {
			if(!e.getClickedInventory().equals(p.getInventory())) return;
			List<String> lore = e.getCurrentItem().getItemMeta().getLore();
			int x;
			for(x = 0; x < lore.size(); x++) {
				String s = lore.get(x);
				String[] v = s.split(" ");
				if(v[0].equals(m.c("&bTrinket:"))) {
					e.setCancelled(true);
					ItemStack i = new ItemStack(Material.EMERALD);
					ItemMeta im = i.getItemMeta();
					String[] ss = ChatColor.stripColor(s).split(" ");
					if(ss[2].equals("Sell")) {
						im.setDisplayName(m.c("&bCommon Sell Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&b"+ss[1]+" &7"+ss[2]+" &7"+ss[3]));
						loree.add(m.c("&7&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					} else if(ss.length > 4 && ss[5].equals("XP")) {
						im.setDisplayName(m.c("&bCommon XP Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&b"+ss[1]+" &7"+ss[2]+" &7"+ss[3]+" &7"+ss[4]+" &7"+ss[5]));
						loree.add(m.c("&7&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					} else if(ss.length > 4 && ss[5].equals("Keys")) {
						im.setDisplayName(m.c("&bCommon KeyFortune Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&b"+ss[1]+" &7"+ss[2]+" &7"+ss[3]+" &7"+ss[4]+" &7"+ss[5]));
						loree.add(m.c("&7&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					} else if(ss[3].equals("Luck")) {
						im.setDisplayName(m.c("&bCommon Lucky Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&b"+ss[1]+" &7"+ss[2]+" &7"+ss[3]));
						loree.add(m.c("&7&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					}
					if(e.getCursor() != null) {
						p.getInventory().addItem(e.getCursor().clone());
						e.setCursor(null);
					}
					e.setCursor(i);
					lore.remove(x);
					ItemStack clone = e.getCurrentItem().clone();
					ItemMeta cm = clone.getItemMeta();
					cm.setLore(lore);
					clone.setItemMeta(cm);
					p.getInventory().setItem(e.getSlot(), clone);
					p.updateInventory();
				} else if(v[0].equals(m.c("&9Trinket:"))) {
					e.setCancelled(true);
					ItemStack i = new ItemStack(Material.EMERALD);
					ItemMeta im = i.getItemMeta();
					String[] ss = ChatColor.stripColor(s).split(" ");
					if(ss[2].equals("Sell")) {
						im.setDisplayName(m.c("&9Rare Sell Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&9"+ss[1]+" &7"+ss[2]+" &7"+ss[3]));
						loree.add(m.c("&7&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					} else if(ss.length > 4 && ss[5].equals("XP")) {
						im.setDisplayName(m.c("&9Rare XP Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&9"+ss[1]+" &7"+ss[2]+" &7"+ss[3]+" &7"+ss[4]+" &7"+ss[5]));
						loree.add(m.c("&7&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					} else if(ss.length > 4 && ss[5].equals("Keys")) {
						im.setDisplayName(m.c("&9Rare KeyFortune Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&9"+ss[1]+" &7"+ss[2]+" &7"+ss[3]+" &7"+ss[4]+" &7"+ss[5]));
						loree.add(m.c("&7&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					} else if(ss[3].equals("Luck")) {
						im.setDisplayName(m.c("&9Rare Lucky Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&b"+ss[1]+" &7"+ss[2]+" &7"+ss[3]));
						loree.add(m.c("&9&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					}
					if(e.getCursor() != null) {
						p.getInventory().addItem(e.getCursor().clone());
						e.setCursor(null);
					}
					e.setCursor(i);
					lore.remove(x);
					ItemStack clone = e.getCurrentItem().clone();
					ItemMeta cm = clone.getItemMeta();
					cm.setLore(lore);
					clone.setItemMeta(cm);
					p.getInventory().setItem(e.getSlot(), clone);
					p.updateInventory();
				} else if(v[0].equals(m.c("&5Trinket:"))) {
					e.setCancelled(true);
					ItemStack i = new ItemStack(Material.EMERALD);
					ItemMeta im = i.getItemMeta();
					String[] ss = ChatColor.stripColor(s).split(" ");
					if(ss[2].equals("Sell")) {
						im.setDisplayName(m.c("&5Epic Sell Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&5"+ss[1]+" &7"+ss[2]+" &7"+ss[3]));
						loree.add(m.c("&7&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					} else if(ss.length > 4 && ss[5].equals("XP")) {
						im.setDisplayName(m.c("&5Epic XP Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&5"+ss[1]+" &7"+ss[2]+" &7"+ss[3]+" &7"+ss[4]+" &7"+ss[5]));
						loree.add(m.c("&7&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					} else if(ss.length > 4 && ss[5].equals("Keys")) {
						im.setDisplayName(m.c("&5Epic KeyFortune Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&5"+ss[1]+" &7"+ss[2]+" &7"+ss[3]+" &7"+ss[4]+" &7"+ss[5]));
						loree.add(m.c("&7&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					} else if(ss[3].equals("Luck")) {
						im.setDisplayName(m.c("&5Epic Lucky Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&b"+ss[1]+" &7"+ss[2]+" &7"+ss[3]));
						loree.add(m.c("&5&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					}
					if(e.getCursor() != null) {
						p.getInventory().addItem(e.getCursor().clone());
						e.setCursor(null);
					}
					e.setCursor(i);
					lore.remove(x);
					ItemStack clone = e.getCurrentItem().clone();
					ItemMeta cm = clone.getItemMeta();
					cm.setLore(lore);
					clone.setItemMeta(cm);
					p.getInventory().setItem(e.getSlot(), clone);
					p.updateInventory();
				} else if(v[0].equals(m.c("&6Trinket:"))) {
					e.setCancelled(true);
					ItemStack i = new ItemStack(Material.EMERALD);
					ItemMeta im = i.getItemMeta();
					String[] ss = ChatColor.stripColor(s).split(" ");
					if(ss[2].equals("Sell")) {
						im.setDisplayName(m.c("&6Legendary Sell Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&6"+ss[1]+" &7"+ss[2]+" &7"+ss[3]));
						loree.add(m.c("&7&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					} else if(ss.length > 4 && ss[5].equals("XP")) {
						im.setDisplayName(m.c("&6Legendary XP Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&6"+ss[1]+" &7"+ss[2]+" &7"+ss[3]+" &7"+ss[4]+" &7"+ss[5]));
						loree.add(m.c("&7&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					} else if(ss.length > 4 && ss[5].equals("Keys")) {
						im.setDisplayName(m.c("&6Legendary KeyFortune Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&6"+ss[1]+" &7"+ss[2]+" &7"+ss[3]+" &7"+ss[4]+" &7"+ss[5]));
						loree.add(m.c("&7&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					} else if(ss[3].equals("Luck")) {
						im.setDisplayName(m.c("&6Legendary Lucky Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&b"+ss[1]+" &7"+ss[2]+" &7"+ss[3]));
						loree.add(m.c("&6&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					}
					if(e.getCursor() != null) {
						p.getInventory().addItem(e.getCursor().clone());
						e.setCursor(null);
					}
					e.setCursor(i);
					lore.remove(x);
					ItemStack clone = e.getCurrentItem().clone();
					ItemMeta cm = clone.getItemMeta();
					cm.setLore(lore);
					clone.setItemMeta(cm);
					p.getInventory().setItem(e.getSlot(), clone);
					p.updateInventory();
				} else if(v[0].equals(m.c("&4Trinket:"))) {
					e.setCancelled(true);
					ItemStack i = new ItemStack(Material.EMERALD);
					ItemMeta im = i.getItemMeta();
					String[] ss = ChatColor.stripColor(s).split(" ");
					if(ss[2].equals("Sell")) {
						im.setDisplayName(m.c("&4Heroic Sell Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&4"+ss[1]+" &7"+ss[2]+" &7"+ss[3]));
						loree.add(m.c("&7&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					} else if(ss.length > 4 && ss[5].equals("XP")) {
						im.setDisplayName(m.c("&4Heroic XP Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&4"+ss[1]+" &7"+ss[2]+" &7"+ss[3]+" &7"+ss[4]+" &7"+ss[5]));
						loree.add(m.c("&7&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					} else if(ss.length > 4 && ss[5].equals("Keys")) {
						im.setDisplayName(m.c("&4Heroic KeyFortune Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&4"+ss[1]+" &7"+ss[2]+" &7"+ss[3]+" &7"+ss[4]+" &7"+ss[5]));
						loree.add(m.c("&7&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					} else if(ss[3].equals("Luck")) {
						im.setDisplayName(m.c("&4Heroic Lucky Trinket"));
						ArrayList<String> loree = new ArrayList<String>();
						loree.add(m.c("&b"+ss[1]+" &7"+ss[2]+" &7"+ss[3]));
						loree.add(m.c("&4&oDrag onto an item to apply"));
						im.setLore(loree);
						i.setItemMeta(im);
					}
					if(e.getCursor() != null) {
						p.getInventory().addItem(e.getCursor().clone());
						e.setCursor(null);
					}
					e.setCursor(i);
					lore.remove(x);
					ItemStack clone = e.getCurrentItem().clone();
					ItemMeta cm = clone.getItemMeta();
					cm.setLore(lore);
					clone.setItemMeta(cm);
					p.getInventory().setItem(e.getSlot(), clone);
					p.updateInventory();
				}
			}
		}
		
		if(e.getCursor().getType() != Material.EMERALD) return;
		if (e.getCursor() == null)
		      return; 
		    if (e.getRawSlot() == e.getSlot())
		      return; 
		    if (!e.getCursor().hasItemMeta())
		      return; 
		    if (!e.getCursor().getItemMeta().hasLore())
		      return; 
			if(e.getClick().equals(ClickType.LEFT)) {
				if(!e.getClickedInventory().equals(p.getInventory())) return;
				List<String> ilore = e.getCurrentItem().getItemMeta().getLore();
				int count = 0;
				for(int i = 0; i < ilore.size(); i++) {
					String s = ilore.get(i);
					String[] v = ChatColor.stripColor(s).split(" ");
					if(v[0].equals(m.c("Trinket:"))) {
						count += 1;
					}
				}
				if(!p.hasPermission("trinket.extra")) {
				if(count >= 3) {
					p.sendMessage(m.c("&cYou can only apply 3 Trinkets at a time"));
					e.setCancelled(true);
					return;
				}
				} else {
					if(count >= 4) {
						p.sendMessage(m.c("&cYou can only apply 4 Trinkets at a time"));
						e.setCancelled(true);
						return;
					}
					}
				
					if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&bCommon Sell Trinket"))) {
						List<String> tlore = e.getCursor().getItemMeta().getLore();
								if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
									p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
									return;
								}
								ItemStack pi = e.getCurrentItem().clone();
								ItemMeta pim = pi.getItemMeta();
								List<String> pilore = pim.getLore();
								pilore.add(m.c("&bTrinket: "+ tlore.get(0)));
								pim.setLore(pilore);
								pi.setItemMeta(pim);
								p.getInventory().setItem(e.getSlot(), pi);
								p.updateInventory();
								e.setCursor(null);
								e.setCancelled(true);
					} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&bCommon XP Trinket"))) {
						List<String> tlore = e.getCursor().getItemMeta().getLore();
						if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
							p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
							return;
						}
							ItemStack pi = e.getCurrentItem().clone();
							ItemMeta pim = pi.getItemMeta();
							List<String> pilore = pim.getLore();
							pilore.add(m.c("&bTrinket: "+ tlore.get(0)));
							pim.setLore(pilore);
							pi.setItemMeta(pim);
							p.getInventory().setItem(e.getSlot(), pi);
							p.updateInventory();
							e.setCursor(null);
							e.setCancelled(true);
					} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&bCommon KeyFortune Trinket"))) {
						List<String> tlore = e.getCursor().getItemMeta().getLore();
						if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
							p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
							return;
						}
							ItemStack pi = e.getCurrentItem().clone();
							ItemMeta pim = pi.getItemMeta();
							List<String> pilore = pim.getLore();
							pilore.add(m.c("&bTrinket: "+ tlore.get(0)));
							pim.setLore(pilore);
							pi.setItemMeta(pim);
							p.getInventory().setItem(e.getSlot(), pi);
							p.updateInventory();
							e.setCursor(null);
							e.setCancelled(true);
					} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&bCommon Lucky Trinket"))) {
						List<String> tlore = e.getCursor().getItemMeta().getLore();
						if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
							p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
							return;
						}
							ItemStack pi = e.getCurrentItem().clone();
							ItemMeta pim = pi.getItemMeta();
							List<String> pilore = pim.getLore();
							pilore.add(m.c("&bTrinket: "+ tlore.get(0)));
							pim.setLore(pilore);
							pi.setItemMeta(pim);
							p.getInventory().setItem(e.getSlot(), pi);
							p.updateInventory();
							e.setCursor(null);
							e.setCancelled(true);
					} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&9Rare Sell Trinket"))) {
						List<String> tlore = e.getCursor().getItemMeta().getLore();
						if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
							p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
							return;
						}
						ItemStack pi = e.getCurrentItem().clone();
						ItemMeta pim = pi.getItemMeta();
						List<String> pilore = pim.getLore();
						pilore.add(m.c("&9Trinket: "+ tlore.get(0)));
						pim.setLore(pilore);
						pi.setItemMeta(pim);
						p.getInventory().setItem(e.getSlot(), pi);
						p.updateInventory();
						e.setCursor(null);
						e.setCancelled(true);
			} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&9Rare XP Trinket"))) {
				List<String> tlore = e.getCursor().getItemMeta().getLore();
				if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
					p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
					return;
				}
					ItemStack pi = e.getCurrentItem().clone();
					ItemMeta pim = pi.getItemMeta();
					List<String> pilore = pim.getLore();
					pilore.add(m.c("&9Trinket: "+ tlore.get(0)));
					pim.setLore(pilore);
					pi.setItemMeta(pim);
					p.getInventory().setItem(e.getSlot(), pi);
					p.updateInventory();
					e.setCursor(null);
					e.setCancelled(true);
			} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&9Rare KeyFortune Trinket"))) {
				List<String> tlore = e.getCursor().getItemMeta().getLore();
				if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
					p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
					return;
				}
					ItemStack pi = e.getCurrentItem().clone();
					ItemMeta pim = pi.getItemMeta();
					List<String> pilore = pim.getLore();
					pilore.add(m.c("&9Trinket: "+ tlore.get(0)));
					pim.setLore(pilore);
					pi.setItemMeta(pim);
					p.getInventory().setItem(e.getSlot(), pi);
					p.updateInventory();
					e.setCursor(null);
					e.setCancelled(true);
			} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&9Rare Lucky Trinket"))) {
				List<String> tlore = e.getCursor().getItemMeta().getLore();
				if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
					p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
					return;
				}
					ItemStack pi = e.getCurrentItem().clone();
					ItemMeta pim = pi.getItemMeta();
					List<String> pilore = pim.getLore();
					pilore.add(m.c("&9Trinket: "+ tlore.get(0)));
					pim.setLore(pilore);
					pi.setItemMeta(pim);
					p.getInventory().setItem(e.getSlot(), pi);
					p.updateInventory();
					e.setCursor(null);
					e.setCancelled(true);
			}else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&5Epic Sell Trinket"))) {
				List<String> tlore = e.getCursor().getItemMeta().getLore();
				if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
					p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
					return;
				}
				ItemStack pi = e.getCurrentItem().clone();
				ItemMeta pim = pi.getItemMeta();
				List<String> pilore = pim.getLore();
				pilore.add(m.c("&5Trinket: "+ tlore.get(0)));
				pim.setLore(pilore);
				pi.setItemMeta(pim);
				p.getInventory().setItem(e.getSlot(), pi);
				p.updateInventory();
				e.setCursor(null);
				e.setCancelled(true);
	} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&5Epic XP Trinket"))) {
		List<String> tlore = e.getCursor().getItemMeta().getLore();
		if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
			p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
			return;
		}
			ItemStack pi = e.getCurrentItem().clone();
			ItemMeta pim = pi.getItemMeta();
			List<String> pilore = pim.getLore();
			pilore.add(m.c("&5Trinket: "+ tlore.get(0)));
			pim.setLore(pilore);
			pi.setItemMeta(pim);
			p.getInventory().setItem(e.getSlot(), pi);
			p.updateInventory();
			e.setCursor(null);
			e.setCancelled(true);
	} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&5Epic KeyFortune Trinket"))) {
		List<String> tlore = e.getCursor().getItemMeta().getLore();
		if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
			p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
			return;
		}
			ItemStack pi = e.getCurrentItem().clone();
			ItemMeta pim = pi.getItemMeta();
			List<String> pilore = pim.getLore();
			pilore.add(m.c("&5Trinket: "+ tlore.get(0)));
			pim.setLore(pilore);
			pi.setItemMeta(pim);
			p.getInventory().setItem(e.getSlot(), pi);
			p.updateInventory();
			e.setCursor(null);
			e.setCancelled(true);
	} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&5Epic Lucky Trinket"))) {
		List<String> tlore = e.getCursor().getItemMeta().getLore();
		if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
			p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
			return;
		}
			ItemStack pi = e.getCurrentItem().clone();
			ItemMeta pim = pi.getItemMeta();
			List<String> pilore = pim.getLore();
			pilore.add(m.c("&5Trinket: "+ tlore.get(0)));
			pim.setLore(pilore);
			pi.setItemMeta(pim);
			p.getInventory().setItem(e.getSlot(), pi);
			p.updateInventory();
			e.setCursor(null);
			e.setCancelled(true);
	} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&6Legendary Sell Trinket"))) {
		List<String> tlore = e.getCursor().getItemMeta().getLore();
		if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
			p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
			return;
		}
		ItemStack pi = e.getCurrentItem().clone();
		ItemMeta pim = pi.getItemMeta();
		List<String> pilore = pim.getLore();
		pilore.add(m.c("&6Trinket: "+ tlore.get(0)));
		pim.setLore(pilore);
		pi.setItemMeta(pim);
		p.getInventory().setItem(e.getSlot(), pi);
		p.updateInventory();
		e.setCursor(null);
		e.setCancelled(true);
} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&6Legendary XP Trinket"))) {
List<String> tlore = e.getCursor().getItemMeta().getLore();
if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
	p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
	return;
}
	ItemStack pi = e.getCurrentItem().clone();
	ItemMeta pim = pi.getItemMeta();
	List<String> pilore = pim.getLore();
	pilore.add(m.c("&6Trinket: "+ tlore.get(0)));
	pim.setLore(pilore);
	pi.setItemMeta(pim);
	p.getInventory().setItem(e.getSlot(), pi);
	p.updateInventory();
	e.setCursor(null);
	e.setCancelled(true);
} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&6Legendary KeyFortune Trinket"))) {
List<String> tlore = e.getCursor().getItemMeta().getLore();
if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
	p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
	return;
}
	ItemStack pi = e.getCurrentItem().clone();
	ItemMeta pim = pi.getItemMeta();
	List<String> pilore = pim.getLore();
	pilore.add(m.c("&6Trinket: "+ tlore.get(0)));
	pim.setLore(pilore);
	pi.setItemMeta(pim);
	p.getInventory().setItem(e.getSlot(), pi);
	p.updateInventory();
	e.setCursor(null);
	e.setCancelled(true);
} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&6Legendary Lucky Trinket"))) {
List<String> tlore = e.getCursor().getItemMeta().getLore();
if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
	p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
	return;
}
	ItemStack pi = e.getCurrentItem().clone();
	ItemMeta pim = pi.getItemMeta();
	List<String> pilore = pim.getLore();
	pilore.add(m.c("&6Trinket: "+ tlore.get(0)));
	pim.setLore(pilore);
	pi.setItemMeta(pim);
	p.getInventory().setItem(e.getSlot(), pi);
	p.updateInventory();
	e.setCursor(null);
	e.setCancelled(true);
}else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&4Heroic Sell Trinket"))) {
	List<String> tlore = e.getCursor().getItemMeta().getLore();
	if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
		p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
		return;
	}
	ItemStack pi = e.getCurrentItem().clone();
	ItemMeta pim = pi.getItemMeta();
	List<String> pilore = pim.getLore();
	pilore.add(m.c("&4Trinket: "+ tlore.get(0)));
	pim.setLore(pilore);
	pi.setItemMeta(pim);
	p.getInventory().setItem(e.getSlot(), pi);
	p.updateInventory();
	e.setCursor(null);
	e.setCancelled(true);
} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&4Heroic XP Trinket"))) {
List<String> tlore = e.getCursor().getItemMeta().getLore();
if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
return;
}
ItemStack pi = e.getCurrentItem().clone();
ItemMeta pim = pi.getItemMeta();
List<String> pilore = pim.getLore();
pilore.add(m.c("&4Trinket: "+ tlore.get(0)));
pim.setLore(pilore);
pi.setItemMeta(pim);
p.getInventory().setItem(e.getSlot(), pi);
p.updateInventory();
e.setCursor(null);
e.setCancelled(true);
} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&4Heroic KeyFortune Trinket"))) {
List<String> tlore = e.getCursor().getItemMeta().getLore();
if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
return;
}
ItemStack pi = e.getCurrentItem().clone();
ItemMeta pim = pi.getItemMeta();
List<String> pilore = pim.getLore();
pilore.add(m.c("&4Trinket: "+ tlore.get(0)));
pim.setLore(pilore);
pi.setItemMeta(pim);
p.getInventory().setItem(e.getSlot(), pi);
p.updateInventory();
e.setCursor(null);
e.setCancelled(true);
} else if(e.getCursor().getItemMeta().getDisplayName().equals(m.c("&4Heroic Lucky Trinket"))) {
List<String> tlore = e.getCursor().getItemMeta().getLore();
if(!e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
p.sendMessage(m.c("&cThis Trinket can only be applied to &bPickaxe&c!"));
return;
}
ItemStack pi = e.getCurrentItem().clone();
ItemMeta pim = pi.getItemMeta();
List<String> pilore = pim.getLore();
pilore.add(m.c("&4Trinket: "+ tlore.get(0)));
pim.setLore(pilore);
pi.setItemMeta(pim);
p.getInventory().setItem(e.getSlot(), pi);
p.updateInventory();
e.setCursor(null);
e.setCancelled(true);
}
						
					}
			
			}
			
		
		
	}
	
    
   
	
	
	


