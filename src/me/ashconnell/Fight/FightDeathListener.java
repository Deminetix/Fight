package me.ashconnell.Fight;

import java.util.Iterator;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

public class FightDeathListener extends EntityListener {
	// Can someone please explain what this does exactly?
	public static Fight plugin;
	
	// Can someone please explain what this does exactly?
	public FightDeathListener(Fight instance) {
		plugin = instance;
	}
	
	// Called when player dies
	public void onEntityDeath(EntityDeathEvent event) {
		Entity e = event.getEntity();
		if(e instanceof Player) {
			Player player = (Player)e;			
			
			// Report player death to everyone
			if(plugin.fightUsersTeam.containsKey(player.getName())){
				if(plugin.fightUsersTeam.get(player.getName()) == "red"){
					plugin.tellEveryone(ChatColor.RED + player.getName() + ChatColor.WHITE + " has been killed!");
				}
				else {
					plugin.tellEveryone(ChatColor.BLUE + player.getName() + ChatColor.WHITE + " has been killed!");
				}
				
				// Adjust Team Count
				if(plugin.fightUsersTeam.containsKey(player.getName())){
					if(plugin.fightUsersTeam.get(player.getName()) == "red"){
						plugin.redTeam = plugin.redTeam - 1;
					}
					if(plugin.fightUsersTeam.get(player.getName()) == "blue"){
						plugin.blueTeam = plugin.blueTeam - 1;
					}
				}
				
				// Check if fight has finished
				if((plugin.redTeam > 0 && plugin.blueTeam == 0) || (plugin.redTeam == 0 && plugin.blueTeam > 0)){
					if(plugin.redTeam > 0 && plugin.blueTeam == 0){
						Set<String> set = plugin.fightUsersTeam.keySet();
						Iterator<String> iter = set.iterator();
						while(iter.hasNext()){
							Object o = iter.next();
							Player z = plugin.getServer().getPlayer(o.toString());
							z.teleport(plugin.getCoords("spectator"));
						}
						plugin.tellEveryone(ChatColor.RED + "Red Team are the Champions!");
					}
					else if(plugin.redTeam == 0 && plugin.blueTeam > 0){
						Set<String> set = plugin.fightUsersTeam.keySet();
						Iterator<String> iter = set.iterator();
						while(iter.hasNext()){
							Object o = iter.next();
							Player z = plugin.getServer().getPlayer(o.toString());
							z.teleport(plugin.getCoords("spectator"));
						}
						plugin.tellEveryone(ChatColor.BLUE + "Blue Team are the Champions!");
					}
					plugin.fightInProgress = false;
					plugin.fightUsersTeam.clear();
					plugin.fightUsersClass.clear();
					plugin.cleanSigns();
					plugin.fightSigns.clear();
					plugin.redTeam = 0;
					plugin.blueTeam = 0;
					
				}
			}
		}
	}
}
	