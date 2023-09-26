package me.dxrk.Main;

public class randomenchant {
	
	/*public void randomEnchant(Player p) {
        Random r = new Random();
        ItemStack i = p.getEquipment().getItemInMainHand().clone();
        ItemMeta im = i.getItemMeta();
        List<String> lore = im.getLore();
        
        
        for(String l : p.getEquipment().getItemInMainHand().getItemMeta().getLore()) {
        
        int enc = r.nextInt(10);
        int x;
          if(enc == 0) {
        	  Enchant = "Explosion";
        	  
        	  
        		  if (ChatColor.stripColor(l).toLowerCase().contains("explosion 1"))  {
        	    for (x = 0; x < lore.size(); x++) {
        	    	String s = lore.get(x);
        	    	if (ChatColor.stripColor(s).contains("Explosion") && happened == false) {
        	    		
        	    		lore.set(x, ChatColor.RED + "Explosion 2");
        	    		happened =true;
        	    	} 
        	    	}
        	    
        	  }else if (ChatColor.stripColor(l).toLowerCase().contains("explosion 2"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Explosion") && happened == false) {
	        	    	
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Explosion 3");
	        	    		happened =true;
	        	    	} 
	        	    	} 
	        	    
	       }else if (ChatColor.stripColor(l).toLowerCase().contains("explosion 3"))  {
        	    for (x = 0; x < lore.size(); x++) {
        	    	String s = lore.get(x);
        	    	if (ChatColor.stripColor(s).contains("Explosion") && happened == false) {
        	    	
        	    		
        	    		lore.set(x, ChatColor.RED + "Explosion 4");
        	    		happened =true;
        	    	} 
        	    	} 
        	    
	       }else if (ChatColor.stripColor(l).toLowerCase().contains("explosion 4"))  {
	    	   for (x = 0; x < lore.size(); x++) {
	    		   String s = lore.get(x);
	    		   if (ChatColor.stripColor(s).contains("Explosion") && happened == false) {
	    	
	    		
	    		lore.set(x, ChatColor.RED + "Explosion 5");
	    		happened =true;
	    	} 
	    	} 
	    
       		}else if (ChatColor.stripColor(l).toLowerCase().contains("explosion 5"))  {
		    	   for (x = 0; x < lore.size(); x++) {
		    		   String s = lore.get(x);
		    		   if (ChatColor.stripColor(s).contains("Explosion") && happened == false) {
    	    	
    	    		
    	    		lore.set(x, ChatColor.RED + "Explosion 6");
    	    		happened =true;
    	    	} 
    	    	} 
    	    
	       		}else if (ChatColor.stripColor(l).toLowerCase().contains("explosion 6"))  {
			    	   for (x = 0; x < lore.size(); x++) {
			    		   String s = lore.get(x);
			    		   if (ChatColor.stripColor(s).contains("Explosion") && happened == false) {
	    	    	
	    	    		
	    	    		lore.set(x, ChatColor.RED + "Explosion 7");
	    	    		happened =true;
	    	    	} 
	    	    	} 
	    	    
		       		}else if (ChatColor.stripColor(l).toLowerCase().contains("explosion 7"))  {
				    	   for (x = 0; x < lore.size(); x++) {
				    		   String s = lore.get(x);
				    		   if (ChatColor.stripColor(s).contains("Explosion") && happened == false) {
		    	    	
		    	    		
		    	    		lore.set(x, ChatColor.RED + "Explosion 8");
		    	    		happened =true;
		    	    	} 
		    	    	} 
		    	    
			       		}else if (ChatColor.stripColor(l).toLowerCase().contains("explosion 8"))  {
					    	   for (x = 0; x < lore.size(); x++) {
					    		   String s = lore.get(x);
					    		   if (ChatColor.stripColor(s).contains("Explosion") && happened == false) {
			    	    	
			    	    		
			    	    		lore.set(x, ChatColor.RED + "Explosion 9");
			    	    		happened =true;
			    	    	} 
			    	    	} 
			    	    
				       		}else if (ChatColor.stripColor(l).toLowerCase().contains("explosion 9"))  {
						    	   for (x = 0; x < lore.size(); x++) {
						    		   String s = lore.get(x);
						    		   if (ChatColor.stripColor(s).contains("Explosion") && happened == false) {
				    	    	
				    	    		
				    	    		lore.set(x, ChatColor.RED + "Explosion 10");
				    	    		happened =true;
				    	    	} 
				    	    	} 
				    	    
					       		}else if (ChatColor.stripColor(l).toLowerCase().contains("explosion 10"))  {
							    	   enc = 1;
					    	    
						       		} else {
					       			if(happened == false) {
					       				lore.add(c("&cExplosion 1"));
					       				happened = true;
					       			}
					       			}
        	  }
          else if(enc == 1) {
        	  Enchant = "Discovery";
        	  
        	  
        	  if (ChatColor.stripColor(l).toLowerCase().contains("discovery 1"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Discovery") && happened == false) {
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Discovery 2");
	        	    		happened =true;
	        	    	} 
	        	    	}
	        	    
	        	  }else if (ChatColor.stripColor(l).toLowerCase().contains("discovery 2"))  {
		        	    for (x = 0; x < lore.size(); x++) {
		        	    	String s = lore.get(x);
		        	    	if (ChatColor.stripColor(s).contains("Discovery") && happened == false) {
		        	    	
		        	    		
		        	    		lore.set(x, ChatColor.RED + "Discovery 3");
		        	    		happened =true;
		        	    	} 
		        	    	} 
		        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("discovery 3"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Discovery") && happened == false) {
	        	    	
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Discovery 4");
	        	    		happened =true;
	        	    	} 
	        	    	} 
	        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("discovery 4"))  {
		    	   for (x = 0; x < lore.size(); x++) {
		    		   String s = lore.get(x);
		    		   if (ChatColor.stripColor(s).contains("Discovery") && happened == false) {
    	    	
    	    		
    	    		lore.set(x, ChatColor.RED + "Discovery 5");
    	    		happened =true;
    	    	} 
    	    	} 
    	    
	       		}else if (ChatColor.stripColor(l).toLowerCase().contains("discovery 5"))  {
			    	   for (x = 0; x < lore.size(); x++) {
			    		   String s = lore.get(x);
			    		   if (ChatColor.stripColor(s).contains("Discovery") && happened == false) {
	    	    	
	    	    		
	    	    		lore.set(x, ChatColor.RED + "Discovery 6");
	    	    		happened =true;
	    	    	} 
	    	    	} 
	    	    
		       		}else if (ChatColor.stripColor(l).toLowerCase().contains("discovery 6"))  {
				    	   for (x = 0; x < lore.size(); x++) {
				    		   String s = lore.get(x);
				    		   if (ChatColor.stripColor(s).contains("Discovery") && happened == false) {
		    	    	
		    	    		
		    	    		lore.set(x, ChatColor.RED + "Discovery 7");
		    	    		happened =true;
		    	    	} 
		    	    	} 
		    	    
			       		}else if (ChatColor.stripColor(l).toLowerCase().contains("discovery 7"))  {
					    	   for (x = 0; x < lore.size(); x++) {
					    		   String s = lore.get(x);
					    		   if (ChatColor.stripColor(s).contains("Discovery") && happened == false) {
			    	    	
			    	    		
			    	    		lore.set(x, ChatColor.RED + "Discovery 8");
			    	    		happened =true;
			    	    	} 
			    	    	} 
			    	    
				       		}else if (ChatColor.stripColor(l).toLowerCase().contains("discovery 8"))  {
						    	   for (x = 0; x < lore.size(); x++) {
						    		   String s = lore.get(x);
						    		   if (ChatColor.stripColor(s).contains("Discovery") && happened == false) {
				    	    	
				    	    		
				    	    		lore.set(x, ChatColor.RED + "Discovery 9");
				    	    		happened =true;
				    	    	} 
				    	    	} 
				    	    
					       		}else if (ChatColor.stripColor(l).toLowerCase().contains("discovery 9"))  {
							    	   for (x = 0; x < lore.size(); x++) {
							    		   String s = lore.get(x);
							    		   if (ChatColor.stripColor(s).contains("Discovery") && happened == false) {
					    	    	
					    	    		
					    	    		lore.set(x, ChatColor.RED + "Discovery 10");
					    	    		happened =true;
					    	    	} 
					    	    	} 
					    	    
						       		}else if (ChatColor.stripColor(l).toLowerCase().contains("discovery 10"))  {
								    	   enc = 1;
						    	    
							       		} else {
						       			if(happened == false) {
						       				lore.add(c("&cDiscovery 1"));
						       				happened = true;
						       			}
						       			}
          	    
        	  
	            
        	  
	            
          }
          else if(enc == 2) {
        	  Enchant = "Encounter";
        	  
        	  if (ChatColor.stripColor(l).toLowerCase().contains("encounter 1"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Encounter") && happened == false) {
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Encounter 2");
	        	    		happened =true;
	        	    	} 
	        	    	}
	        	    
	        	  }else if (ChatColor.stripColor(l).toLowerCase().contains("encounter 2"))  {
		        	    for (x = 0; x < lore.size(); x++) {
		        	    	String s = lore.get(x);
		        	    	if (ChatColor.stripColor(s).contains("Encounter") && happened == false) {
		        	    	
		        	    		
		        	    		lore.set(x, ChatColor.RED + "Encounter 3");
		        	    		happened =true;
		        	    	} 
		        	    	} 
		        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("encounter 3"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Encounter") && happened == false) {
	        	    	
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Encounter 4");
	        	    		happened =true;
	        	    	} 
	        	    	} 
	        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("encounter 4"))  {
		    	   for (x = 0; x < lore.size(); x++) {
		    		   String s = lore.get(x);
		    		   if (ChatColor.stripColor(s).contains("Encounter") && happened == false) {
    	    	
    	    		
    	    		lore.set(x, ChatColor.RED + "Encounter 5");
    	    		happened =true;
    	    	} 
    	    	} 
    	    
	       		}else if (ChatColor.stripColor(l).toLowerCase().contains("encounter 5"))  {
			    	   for (x = 0; x < lore.size(); x++) {
			    		   String s = lore.get(x);
			    		   if (ChatColor.stripColor(s).contains("Encounter") && happened == false) {
	    	    	
	    	    		
	    	    		lore.set(x, ChatColor.RED + "Encounter 6");
	    	    		happened =true;
	    	    	} 
	    	    	} 
	    	    
		       		}else if (ChatColor.stripColor(l).toLowerCase().contains("encounter 6"))  {
				    	   for (x = 0; x < lore.size(); x++) {
				    		   String s = lore.get(x);
				    		   if (ChatColor.stripColor(s).contains("Encounter") && happened == false) {
		    	    	
		    	    		
		    	    		lore.set(x, ChatColor.RED + "Encounter 7");
		    	    		happened =true;
		    	    	} 
		    	    	} 
		    	    
			       		}else if (ChatColor.stripColor(l).toLowerCase().contains("encounter 7"))  {
					    	   for (x = 0; x < lore.size(); x++) {
					    		   String s = lore.get(x);
					    		   if (ChatColor.stripColor(s).contains("Encounter") && happened == false) {
			    	    	
			    	    		
			    	    		lore.set(x, ChatColor.RED + "Encounter 8");
			    	    		happened =true;
			    	    	} 
			    	    	} 
			    	    
				       		}else if (ChatColor.stripColor(l).toLowerCase().contains("encounter 8"))  {
						    	   for (x = 0; x < lore.size(); x++) {
						    		   String s = lore.get(x);
						    		   if (ChatColor.stripColor(s).contains("Encounter") && happened == false) {
				    	    	
				    	    		
				    	    		lore.set(x, ChatColor.RED + "Encounter 9");
				    	    		happened =true;
				    	    	} 
				    	    	} 
				    	    
					       		}else if (ChatColor.stripColor(l).toLowerCase().contains("encounter 9"))  {
							    	   for (x = 0; x < lore.size(); x++) {
							    		   String s = lore.get(x);
							    		   if (ChatColor.stripColor(s).contains("Encounter") && happened == false) {
					    	    	
					    	    		
					    	    		lore.set(x, ChatColor.RED + "Encounter 10");
					    	    		happened =true;
					    	    	} 
					    	    	} 
					    	    
						       		}else if (ChatColor.stripColor(l).toLowerCase().contains("encounter 10"))  {
								    	   enc = 1;
						    	    
							       		} else {
						       			if(happened == false) {
						       				lore.add(c("&cEncounter 1"));
						       				happened = true;
						       			}
						       			}
        	  
        	  
	            
	            
          }
          else if(enc == 3) {
        	  Enchant = "Corrupt";
        	  
        	  if (ChatColor.stripColor(l).toLowerCase().contains("corrupt 1"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Corrupt") && happened == false) {
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Corrupt 2");
	        	    		happened =true;
	        	    	} 
	        	    	}
	        	    
	        	  }else if (ChatColor.stripColor(l).toLowerCase().contains("corrupt 2"))  {
		        	    for (x = 0; x < lore.size(); x++) {
		        	    	String s = lore.get(x);
		        	    	if (ChatColor.stripColor(s).contains("Corrupt") && happened == false) {
		        	    	
		        	    		
		        	    		lore.set(x, ChatColor.RED + "Corrupt 3");
		        	    		happened =true;
		        	    	} 
		        	    	} 
		        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("corrupt 3"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Corrupt") && happened == false) {
	        	    	
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Corrupt 4");
	        	    		happened =true;
	        	    	} 
	        	    	} 
	        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("corrupt 4"))  {
		    	   for (x = 0; x < lore.size(); x++) {
		    		   String s = lore.get(x);
		    		   if (ChatColor.stripColor(s).contains("Corrupt") && happened == false) {
    	    	
    	    		
    	    		lore.set(x, ChatColor.RED + "Corrupt 5");
    	    		happened =true;
    	    	} 
    	    	} 
    	    
	       		}else if (ChatColor.stripColor(l).toLowerCase().contains("corrupt 5"))  {
			    	   for (x = 0; x < lore.size(); x++) {
			    		   String s = lore.get(x);
			    		   if (ChatColor.stripColor(s).contains("Corrupt") && happened == false) {
	    	    	
	    	    		
	    	    		lore.set(x, ChatColor.RED + "Corrupt 6");
	    	    		happened =true;
	    	    	} 
	    	    	} 
	    	    
		       		}else if (ChatColor.stripColor(l).toLowerCase().contains("corrupt 6"))  {
				    	   for (x = 0; x < lore.size(); x++) {
				    		   String s = lore.get(x);
				    		   if (ChatColor.stripColor(s).contains("Corrupt") && happened == false) {
		    	    	
		    	    		
		    	    		lore.set(x, ChatColor.RED + "Corrupt 7");
		    	    		happened =true;
		    	    	} 
		    	    	} 
		    	    
			       		}else if (ChatColor.stripColor(l).toLowerCase().contains("corrupt 7"))  {
					    	   for (x = 0; x < lore.size(); x++) {
					    		   String s = lore.get(x);
					    		   if (ChatColor.stripColor(s).contains("Corrupt") && happened == false) {
			    	    	
			    	    		
			    	    		lore.set(x, ChatColor.RED + "Corrupt 8");
			    	    		happened =true;
			    	    	} 
			    	    	} 
			    	    
				       		}else if (ChatColor.stripColor(l).toLowerCase().contains("corrupt 8"))  {
						    	   for (x = 0; x < lore.size(); x++) {
						    		   String s = lore.get(x);
						    		   if (ChatColor.stripColor(s).contains("Corrupt") && happened == false) {
				    	    	
				    	    		
				    	    		lore.set(x, ChatColor.RED + "Corrupt 9");
				    	    		happened =true;
				    	    	} 
				    	    	} 
				    	    
					       		}else if (ChatColor.stripColor(l).toLowerCase().contains("corrupt 9"))  {
							    	   for (x = 0; x < lore.size(); x++) {
							    		   String s = lore.get(x);
							    		   if (ChatColor.stripColor(s).contains("Corrupt") && happened == false) {
					    	    	
					    	    		
					    	    		lore.set(x, ChatColor.RED + "Corrupt 10");
					    	    		happened =true;
					    	    	} 
					    	    	} 
					    	    
						       		}else if (ChatColor.stripColor(l).toLowerCase().contains("corrupt 10"))  {
								    	   enc = 1;
						    	    
							       		} else {
						       			if(happened == false) {
						       				lore.add(c("&cCorrupt 1"));
						       				happened = true;
						       			}
						       			}
	            
          }
          else if(enc == 4) {
        	  Enchant = "Greed";
        	 
        	  if (ChatColor.stripColor(l).toLowerCase().contains("greed 1"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Greed") && happened == false) {
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Greed 2");
	        	    		happened =true;
	        	    	} 
	        	    	}
	        	    
	        	  }else if (ChatColor.stripColor(l).toLowerCase().contains("greed 2"))  {
		        	    for (x = 0; x < lore.size(); x++) {
		        	    	String s = lore.get(x);
		        	    	if (ChatColor.stripColor(s).contains("Greed") && happened == false) {
		        	    	
		        	    		
		        	    		lore.set(x, ChatColor.RED + "Greed 3");
		        	    		happened =true;
		        	    	} 
		        	    	} 
		        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("greed 3"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Greed") && happened == false) {
	        	    	
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Greed 4");
	        	    		happened =true;
	        	    	} 
	        	    	} 
	        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("greed 4"))  {
		    	   for (x = 0; x < lore.size(); x++) {
		    		   String s = lore.get(x);
		    		   if (ChatColor.stripColor(s).contains("Greed") && happened == false) {
    	    	
    	    		
    	    		lore.set(x, ChatColor.RED + "Greed 5");
    	    		happened =true;
    	    	} 
    	    	} 
    	    
	       		}else if (ChatColor.stripColor(l).toLowerCase().contains("greed 5"))  {
			    	   for (x = 0; x < lore.size(); x++) {
			    		   String s = lore.get(x);
			    		   if (ChatColor.stripColor(s).contains("Greed") && happened == false) {
	    	    	
	    	    		
	    	    		lore.set(x, ChatColor.RED + "Greed 6");
	    	    		happened =true;
	    	    	} 
	    	    	} 
	    	    
		       		}else if (ChatColor.stripColor(l).toLowerCase().contains("greed 6"))  {
				    	   for (x = 0; x < lore.size(); x++) {
				    		   String s = lore.get(x);
				    		   if (ChatColor.stripColor(s).contains("Greed") && happened == false) {
		    	    	
		    	    		
		    	    		lore.set(x, ChatColor.RED + "Greed 7");
		    	    		happened =true;
		    	    	} 
		    	    	} 
		    	    
			       		}else if (ChatColor.stripColor(l).toLowerCase().contains("greed 7"))  {
					    	   for (x = 0; x < lore.size(); x++) {
					    		   String s = lore.get(x);
					    		   if (ChatColor.stripColor(s).contains("Greed") && happened == false) {
			    	    	
			    	    		
			    	    		lore.set(x, ChatColor.RED + "Greed 8");
			    	    		happened =true;
			    	    	} 
			    	    	} 
			    	    
				       		}else if (ChatColor.stripColor(l).toLowerCase().contains("greed 8"))  {
						    	   for (x = 0; x < lore.size(); x++) {
						    		   String s = lore.get(x);
						    		   if (ChatColor.stripColor(s).contains("Greed") && happened == false) {
				    	    	
				    	    		
				    	    		lore.set(x, ChatColor.RED + "Greed 9");
				    	    		happened =true;
				    	    	} 
				    	    	} 
				    	    
					       		}else if (ChatColor.stripColor(l).toLowerCase().contains("greed 9"))  {
							    	   for (x = 0; x < lore.size(); x++) {
							    		   String s = lore.get(x);
							    		   if (ChatColor.stripColor(s).contains("Greed") && happened == false) {
					    	    	
					    	    		
					    	    		lore.set(x, ChatColor.RED + "Greed 10");
					    	    		happened =true;
					    	    	} 
					    	    	} 
					    	    
						       		}else if (ChatColor.stripColor(l).toLowerCase().contains("greed 10"))  {
								    	   enc = 1;
						    	    
							       		} else {
						       			if(happened == false) {
						       				lore.add(c("&cGreed 1"));
						       				happened = true;
						       			}
						       			}
	            
          }
          else if(enc == 5) {
        	  Enchant = "Wave";
        	 
        	  if (ChatColor.stripColor(l).toLowerCase().contains("wave 1"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Wave") && happened == false) {
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Wave 2");
	        	    		happened =true;
	        	    	} 
	        	    	}
	        	    
	        	  }else if (ChatColor.stripColor(l).toLowerCase().contains("wave 2"))  {
		        	    for (x = 0; x < lore.size(); x++) {
		        	    	String s = lore.get(x);
		        	    	if (ChatColor.stripColor(s).contains("Wave") && happened == false) {
		        	    	
		        	    		
		        	    		lore.set(x, ChatColor.RED + "Wave 3");
		        	    		happened =true;
		        	    	} 
		        	    	} 
		        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("wave 3"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Wave") && happened == false) {
	        	    	
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Wave 4");
	        	    		happened =true;
	        	    	} 
	        	    	} 
	        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("wave 4"))  {
		    	   for (x = 0; x < lore.size(); x++) {
		    		   String s = lore.get(x);
		    		   if (ChatColor.stripColor(s).contains("Wave") && happened == false) {
    	    	
    	    		
    	    		lore.set(x, ChatColor.RED + "Wave 5");
    	    		happened =true;
    	    	} 
    	    	} 
    	    
	       		}else if (ChatColor.stripColor(l).toLowerCase().contains("wave 5"))  {
			    	   for (x = 0; x < lore.size(); x++) {
			    		   String s = lore.get(x);
			    		   if (ChatColor.stripColor(s).contains("Wave") && happened == false) {
	    	    	
	    	    		
	    	    		lore.set(x, ChatColor.RED + "Wave 6");
	    	    		happened =true;
	    	    	} 
	    	    	} 
	    	    
		       		}else if (ChatColor.stripColor(l).toLowerCase().contains("wave 6"))  {
				    	   for (x = 0; x < lore.size(); x++) {
				    		   String s = lore.get(x);
				    		   if (ChatColor.stripColor(s).contains("Wave") && happened == false) {
		    	    	
		    	    		
		    	    		lore.set(x, ChatColor.RED + "Wave 7");
		    	    		happened =true;
		    	    	} 
		    	    	} 
		    	    
			       		}else if (ChatColor.stripColor(l).toLowerCase().contains("wave 7"))  {
					    	   for (x = 0; x < lore.size(); x++) {
					    		   String s = lore.get(x);
					    		   if (ChatColor.stripColor(s).contains("Wave") && happened == false) {
			    	    	
			    	    		
			    	    		lore.set(x, ChatColor.RED + "Wave 8");
			    	    		happened =true;
			    	    	} 
			    	    	} 
			    	    
				       		}else if (ChatColor.stripColor(l).toLowerCase().contains("wave 8"))  {
						    	   for (x = 0; x < lore.size(); x++) {
						    		   String s = lore.get(x);
						    		   if (ChatColor.stripColor(s).contains("Wave") && happened == false) {
				    	    	
				    	    		
				    	    		lore.set(x, ChatColor.RED + "Wave 9");
				    	    		happened =true;
				    	    	} 
				    	    	} 
				    	    
					       		}else if (ChatColor.stripColor(l).toLowerCase().contains("wave 9"))  {
							    	   for (x = 0; x < lore.size(); x++) {
							    		   String s = lore.get(x);
							    		   if (ChatColor.stripColor(s).contains("Wave") && happened == false) {
					    	    	
					    	    		
					    	    		lore.set(x, ChatColor.RED + "Wave 10");
					    	    		happened =true;
					    	    	} 
					    	    	} 
					    	    
						       		}else if (ChatColor.stripColor(l).toLowerCase().contains("wave 10"))  {
								    	   enc = 1;
						    	    
							       		} else {
						       			if(happened == false) {
						       				lore.add(c("&cWave 1"));
						       				happened = true;
						       			}
						       			}
	            
          }
          else if(enc == 6) {
        	  Enchant = "TokenFinder";
        	  
        	  if (ChatColor.stripColor(l).toLowerCase().contains("tokenfinder 1"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("TokenFinder") && happened == false) {
	        	    		
	        	    		lore.set(x, ChatColor.RED + "TokenFinder 2");
	        	    		happened =true;
	        	    	} 
	        	    	}
	        	    
	        	  }else if (ChatColor.stripColor(l).toLowerCase().contains("tokenfinder 2"))  {
		        	    for (x = 0; x < lore.size(); x++) {
		        	    	String s = lore.get(x);
		        	    	if (ChatColor.stripColor(s).contains("TokenFinder") && happened == false) {
		        	    	
		        	    		
		        	    		lore.set(x, ChatColor.RED + "TokenFinder 3");
		        	    		happened =true;
		        	    	} 
		        	    	} 
		        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("tokenfinder 3"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("TokenFinder") && happened == false) {
	        	    	
	        	    		
	        	    		lore.set(x, ChatColor.RED + "TokenFinder 4");
	        	    		happened =true;
	        	    	} 
	        	    	} 
	        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("tokenfinder 4"))  {
		    	   for (x = 0; x < lore.size(); x++) {
		    		   String s = lore.get(x);
		    		   if (ChatColor.stripColor(s).contains("TokenFinder") && happened == false) {
    	    	
    	    		
    	    		lore.set(x, ChatColor.RED + "TokenFinder 5");
    	    		happened =true;
    	    	} 
    	    	} 
    	    
	       		}else if (ChatColor.stripColor(l).toLowerCase().contains("tokenfinder 5"))  {
			    	   for (x = 0; x < lore.size(); x++) {
			    		   String s = lore.get(x);
			    		   if (ChatColor.stripColor(s).contains("TokenFinder") && happened == false) {
	    	    	
	    	    		
	    	    		lore.set(x, ChatColor.RED + "TokenFinder 6");
	    	    		happened =true;
	    	    	} 
	    	    	} 
	    	    
		       		}else if (ChatColor.stripColor(l).toLowerCase().contains("tokenfinder 6"))  {
				    	   for (x = 0; x < lore.size(); x++) {
				    		   String s = lore.get(x);
				    		   if (ChatColor.stripColor(s).contains("TokenFinder") && happened == false) {
		    	    	
		    	    		
		    	    		lore.set(x, ChatColor.RED + "TokenFinder 7");
		    	    		happened =true;
		    	    	} 
		    	    	} 
		    	    
			       		}else if (ChatColor.stripColor(l).toLowerCase().contains("tokenfinder 7"))  {
					    	   for (x = 0; x < lore.size(); x++) {
					    		   String s = lore.get(x);
					    		   if (ChatColor.stripColor(s).contains("TokenFinder") && happened == false) {
			    	    	
			    	    		
			    	    		lore.set(x, ChatColor.RED + "TokenFinder 8");
			    	    		happened =true;
			    	    	} 
			    	    	} 
			    	    
				       		}else if (ChatColor.stripColor(l).toLowerCase().contains("tokenfinder 8"))  {
						    	   for (x = 0; x < lore.size(); x++) {
						    		   String s = lore.get(x);
						    		   if (ChatColor.stripColor(s).contains("TokenFinder") && happened == false) {
				    	    	
				    	    		
				    	    		lore.set(x, ChatColor.RED + "TokenFinder 9");
				    	    		happened =true;
				    	    	} 
				    	    	} 
				    	    
					       		}else if (ChatColor.stripColor(l).toLowerCase().contains("tokenfinder 9"))  {
							    	   for (x = 0; x < lore.size(); x++) {
							    		   String s = lore.get(x);
							    		   if (ChatColor.stripColor(s).contains("TokenFinder") && happened == false) {
					    	    	
					    	    		
					    	    		lore.set(x, ChatColor.RED + "TokenFinder 10");
					    	    		happened =true;
					    	    	} 
					    	    	} 
					    	    
						       		}else if (ChatColor.stripColor(l).toLowerCase().contains("tokenfinder 10"))  {
								    	   enc = 1;
						    	    
							       		} else {
						       			if(happened == false) {
						       				lore.add(c("&cTokenFinder 1"));
						       				happened = true;
						       			}
						       			}
	            
          }
          else if(enc == 7) {
        	  Enchant = "Vaporize";
        	  
        	  if (ChatColor.stripColor(l).toLowerCase().contains("vaporize 1"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Vaporize") && happened == false) {
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Vaporize 2");
	        	    		happened =true;
	        	    	} 
	        	    	}
	        	    
	        	  }else if (ChatColor.stripColor(l).toLowerCase().contains("vaporize 2"))  {
		        	    for (x = 0; x < lore.size(); x++) {
		        	    	String s = lore.get(x);
		        	    	if (ChatColor.stripColor(s).contains("Vaporize") && happened == false) {
		        	    	
		        	    		
		        	    		lore.set(x, ChatColor.RED + "Vaporize 3");
		        	    		happened =true;
		        	    	} 
		        	    	} 
		        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("vaporize 3"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Vaporize") && happened == false) {
	        	    	
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Vaporize 4");
	        	    		happened =true;
	        	    	} 
	        	    	} 
	        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("vaporize 4"))  {
		    	   for (x = 0; x < lore.size(); x++) {
		    		   String s = lore.get(x);
		    		   if (ChatColor.stripColor(s).contains("Vaporize") && happened == false) {
    	    	
    	    		
    	    		lore.set(x, ChatColor.RED + "Vaporize 5");
    	    		happened =true;
    	    	} 
    	    	} 
    	    
	       		}else if (ChatColor.stripColor(l).toLowerCase().contains("vaporize 5"))  {
			    	   for (x = 0; x < lore.size(); x++) {
			    		   String s = lore.get(x);
			    		   if (ChatColor.stripColor(s).contains("Vaporize") && happened == false) {
	    	    	
	    	    		
	    	    		lore.set(x, ChatColor.RED + "Vaporize 6");
	    	    		happened =true;
	    	    	} 
	    	    	} 
	    	    
		       		}else if (ChatColor.stripColor(l).toLowerCase().contains("vaporize 6"))  {
				    	   for (x = 0; x < lore.size(); x++) {
				    		   String s = lore.get(x);
				    		   if (ChatColor.stripColor(s).contains("Vaporize") && happened == false) {
		    	    	
		    	    		
		    	    		lore.set(x, ChatColor.RED + "Vaporize 7");
		    	    		happened =true;
		    	    	} 
		    	    	} 
		    	    
			       		}else if (ChatColor.stripColor(l).toLowerCase().contains("vaporize 7"))  {
					    	   for (x = 0; x < lore.size(); x++) {
					    		   String s = lore.get(x);
					    		   if (ChatColor.stripColor(s).contains("Vaporize") && happened == false) {
			    	    	
			    	    		
			    	    		lore.set(x, ChatColor.RED + "Vaporize 8");
			    	    		happened =true;
			    	    	} 
			    	    	} 
			    	    
				       		}else if (ChatColor.stripColor(l).toLowerCase().contains("vaporize 8"))  {
						    	   for (x = 0; x < lore.size(); x++) {
						    		   String s = lore.get(x);
						    		   if (ChatColor.stripColor(s).contains("Vaporize") && happened == false) {
				    	    	
				    	    		
				    	    		lore.set(x, ChatColor.RED + "Vaporize 9");
				    	    		happened =true;
				    	    	} 
				    	    	} 
				    	    
					       		}else if (ChatColor.stripColor(l).toLowerCase().contains("vaporize 9"))  {
							    	   for (x = 0; x < lore.size(); x++) {
							    		   String s = lore.get(x);
							    		   if (ChatColor.stripColor(s).contains("Vaporize") && happened == false) {
					    	    	
					    	    		
					    	    		lore.set(x, ChatColor.RED + "Vaporize 10");
					    	    		happened =true;
					    	    	} 
					    	    	} 
					    	    
						       		}else if (ChatColor.stripColor(l).toLowerCase().contains("vaporize 10"))  {
								    	   enc = 1;
						    	    
							       		} else {
						       			if(happened == false) {
						       				lore.add(c("&cVaporize 1"));
						       				happened = true;
						       			}
						       			}
    	  
	            
	            
          }
          else if(enc == 8) {
        	  Enchant = "Collector";
        	  
        	  if (ChatColor.stripColor(l).toLowerCase().contains("collector 1"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Collector") && happened == false) {
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Collector 2");
	        	    		happened =true;
	        	    	} 
	        	    	}
	        	    
	        	  }else if (ChatColor.stripColor(l).toLowerCase().contains("collector 2"))  {
		        	    for (x = 0; x < lore.size(); x++) {
		        	    	String s = lore.get(x);
		        	    	if (ChatColor.stripColor(s).contains("Collector") && happened == false) {
		        	    	
		        	    		
		        	    		lore.set(x, ChatColor.RED + "Collector 3");
		        	    		happened =true;
		        	    	} 
		        	    	} 
		        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("collector 3"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Collector") && happened == false) {
	        	    	
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Collector 4");
	        	    		happened =true;
	        	    	} 
	        	    	} 
	        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("collector 4"))  {
		    	   for (x = 0; x < lore.size(); x++) {
		    		   String s = lore.get(x);
		    		   if (ChatColor.stripColor(s).contains("Collector") && happened == false) {
    	    	
    	    		
    	    		lore.set(x, ChatColor.RED + "Collector 5");
    	    		happened =true;
    	    	} 
    	    	} 
    	    
	       		}else if (ChatColor.stripColor(l).toLowerCase().contains("collector 5"))  {
			    	   for (x = 0; x < lore.size(); x++) {
			    		   String s = lore.get(x);
			    		   if (ChatColor.stripColor(s).contains("Collector") && happened == false) {
	    	    	
	    	    		
	    	    		lore.set(x, ChatColor.RED + "Collector 6");
	    	    		happened =true;
	    	    	} 
	    	    	} 
	    	    
		       		}else if (ChatColor.stripColor(l).toLowerCase().contains("collector 6"))  {
				    	   for (x = 0; x < lore.size(); x++) {
				    		   String s = lore.get(x);
				    		   if (ChatColor.stripColor(s).contains("Collector") && happened == false) {
		    	    	
		    	    		
		    	    		lore.set(x, ChatColor.RED + "Collector 7");
		    	    		happened =true;
		    	    	} 
		    	    	} 
		    	    
			       		}else if (ChatColor.stripColor(l).toLowerCase().contains("collector 7"))  {
					    	   for (x = 0; x < lore.size(); x++) {
					    		   String s = lore.get(x);
					    		   if (ChatColor.stripColor(s).contains("Collector") && happened == false) {
			    	    	
			    	    		
			    	    		lore.set(x, ChatColor.RED + "Collector 8");
			    	    		happened =true;
			    	    	} 
			    	    	} 
			    	    
				       		}else if (ChatColor.stripColor(l).toLowerCase().contains("collector 8"))  {
						    	   for (x = 0; x < lore.size(); x++) {
						    		   String s = lore.get(x);
						    		   if (ChatColor.stripColor(s).contains("Collector") && happened == false) {
				    	    	
				    	    		
				    	    		lore.set(x, ChatColor.RED + "Collector 9");
				    	    		happened =true;
				    	    	} 
				    	    	} 
				    	    
					       		}else if (ChatColor.stripColor(l).toLowerCase().contains("collector 9"))  {
							    	   for (x = 0; x < lore.size(); x++) {
							    		   String s = lore.get(x);
							    		   if (ChatColor.stripColor(s).contains("Collector") && happened == false) {
					    	    	
					    	    		
					    	    		lore.set(x, ChatColor.RED + "Collector 10");
					    	    		happened =true;
					    	    	} 
					    	    	} 
					    	    
						       		}else if (ChatColor.stripColor(l).toLowerCase().contains("collector 10"))  {
								    	   enc = 1;
						    	    
							       		} else {
						       			if(happened == false) {
						       				lore.add(c("&cCollector 1"));
						       				happened = true;
						       			}
						       			}
    	  
	            
        	  }
        	  else if(enc == 9) {
        	  Enchant = "Research";
        	  
        	  if (ChatColor.stripColor(l).toLowerCase().contains("research 1"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Research") && happened == false) {
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Research 2");
	        	    		happened =true;
	        	    	} 
	        	    	}
	        	    
	        	  }else if (ChatColor.stripColor(l).toLowerCase().contains("research 2"))  {
		        	    for (x = 0; x < lore.size(); x++) {
		        	    	String s = lore.get(x);
		        	    	if (ChatColor.stripColor(s).contains("Research") && happened == false) {
		        	    	
		        	    		
		        	    		lore.set(x, ChatColor.RED + "Research 3");
		        	    		happened =true;
		        	    	} 
		        	    	} 
		        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("research 3"))  {
	        	    for (x = 0; x < lore.size(); x++) {
	        	    	String s = lore.get(x);
	        	    	if (ChatColor.stripColor(s).contains("Research") && happened == false) {
	        	    	
	        	    		
	        	    		lore.set(x, ChatColor.RED + "Research 4");
	        	    		happened =true;
	        	    	} 
	        	    	} 
	        	    
		       }else if (ChatColor.stripColor(l).toLowerCase().contains("research 4"))  {
		    	   for (x = 0; x < lore.size(); x++) {
		    		   String s = lore.get(x);
		    		   if (ChatColor.stripColor(s).contains("Research") && happened == false) {
    	    	
    	    		
    	    		lore.set(x, ChatColor.RED + "Research 5");
    	    		happened =true;
    	    	} 
    	    	} 
    	    
	       		}else if (ChatColor.stripColor(l).toLowerCase().contains("research 5"))  {
			    	   for (x = 0; x < lore.size(); x++) {
			    		   String s = lore.get(x);
			    		   if (ChatColor.stripColor(s).contains("Research") && happened == false) {
	    	    	
	    	    		
	    	    		lore.set(x, ChatColor.RED + "Research 6");
	    	    		happened =true;
	    	    	} 
	    	    	} 
	    	    
		       		}else if (ChatColor.stripColor(l).toLowerCase().contains("research 6"))  {
				    	   for (x = 0; x < lore.size(); x++) {
				    		   String s = lore.get(x);
				    		   if (ChatColor.stripColor(s).contains("Research") && happened == false) {
		    	    	
		    	    		
		    	    		lore.set(x, ChatColor.RED + "Research 7");
		    	    		happened =true;
		    	    	} 
		    	    	} 
		    	    
			       		}else if (ChatColor.stripColor(l).toLowerCase().contains("research 7"))  {
					    	   for (x = 0; x < lore.size(); x++) {
					    		   String s = lore.get(x);
					    		   if (ChatColor.stripColor(s).contains("Research") && happened == false) {
			    	    	
			    	    		
			    	    		lore.set(x, ChatColor.RED + "Research 8");
			    	    		happened =true;
			    	    	} 
			    	    	} 
			    	    
				       		}else if (ChatColor.stripColor(l).toLowerCase().contains("research 8"))  {
						    	   for (x = 0; x < lore.size(); x++) {
						    		   String s = lore.get(x);
						    		   if (ChatColor.stripColor(s).contains("Research") && happened == false) {
				    	    	
				    	    		
				    	    		lore.set(x, ChatColor.RED + "Research 9");
				    	    		happened =true;
				    	    	} 
				    	    	} 
				    	    
					       		}else if (ChatColor.stripColor(l).toLowerCase().contains("research 9"))  {
							    	   for (x = 0; x < lore.size(); x++) {
							    		   String s = lore.get(x);
							    		   if (ChatColor.stripColor(s).contains("Research") && happened == false) {
					    	    	
					    	    		
					    	    		lore.set(x, ChatColor.RED + "Research 10");
					    	    		happened =true;
					    	    	} 
					    	    	} 
					    	    
						       		}else if (ChatColor.stripColor(l).toLowerCase().contains("research 10"))  {
								    	   enc = 1;
						    	    
							       		} else {
						       			if(happened == false) {
						       				lore.add(c("&cResearch 1"));
						       				happened = true;
						       			}
						       			}
    	  
        	  
        	  }
  }
        	  
        
          happened = false;
        
        im.setLore(lore);
        i.setItemMeta(im);
        p.setItemInHand(i);
        p.updateInventory();
        }*/

}
