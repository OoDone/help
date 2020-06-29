package lmao.me.back;

import java.util.HashMap;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import lmao.me.back.MyEventHandler.TimeUnit;

public class Cooldown {
	public static HashMap<String, AbilityCooldown> cooldownPlayers = new HashMap<String, AbilityCooldown>();
	public static void add(String player, String ability, long seconds, long systime, Main2 main) {
    if(!cooldownPlayers.containsKey(player)) cooldownPlayers.put(player, new AbilityCooldown(player));
    if(isCooling(player, ability)) return;
    cooldownPlayers.get(player).cooldownMap.put(ability, new AbilityCooldown(player, seconds * 1000, System.currentTimeMillis()));
    ConfigHandler config = new ConfigHandler(main, Bukkit.getPlayer(player));
    config.getFileConfig().set(ability, getRemaining(player, "back"));
    config.saveConfig();
}
	public static boolean isCooling(String player, String ability) {
		if(!cooldownPlayers.containsKey(player)) return false;
		if(!cooldownPlayers.get(player).cooldownMap.containsKey(ability)) return false;
		return true;
		}
	public static double getRemaining(String player, String ability) {
    if(!cooldownPlayers.containsKey(player)) return 0.0;
    if(!cooldownPlayers.get(player).cooldownMap.containsKey(ability)) return 0.0;
    return MyEventHandler.convert((cooldownPlayers.get(player).cooldownMap.get(ability).seconds + cooldownPlayers.get(player).cooldownMap.get(ability).systime) - System.currentTimeMillis(), TimeUnit.SECONDS, 1);
}
	public static void coolDurMessage(Player player, String ability) {
    if(player == null) {
        return;
    }
    if(!isCooling(player.getName(), ability)) {
        return;
    }
    double seconds = Math.floor(getRemaining(player.getName(), ability) % 60);
    double minutes = Math.floor((getRemaining(player.getName(), ability) % 3600) / 60);
    double hours = Math.floor(getRemaining(player.getName(), ability) / 3600);
    player.sendMessage(ChatColor.GRAY + ability + " Cooldown " + ChatColor.AQUA + hours + " Hours, " + minutes + " Minutes and " + seconds + " Seconds");
}
	public static void removeCooldown(String player, String ability) {
    if(!cooldownPlayers.containsKey(player)) {
        return;
    }
    if(!cooldownPlayers.get(player).cooldownMap.containsKey(ability)) {
        return;
    }
    cooldownPlayers.get(player).cooldownMap.remove(ability);
    Player cPlayer = Bukkit.getPlayer(player);
    if(player != null) {
        cPlayer.sendMessage(ChatColor.GRAY + "You can now use " + ChatColor.AQUA + ability);
    }
	}
    public static void handleCooldowns(Main2 main) {
      if(cooldownPlayers.isEmpty()) {
          return;
      }
      for(Iterator<String> it = cooldownPlayers.keySet().iterator(); it.hasNext();) {
          String key = it.next();
          ConfigHandler config = new ConfigHandler(main, Bukkit.getPlayer(key));
          config.getFileConfig().set("back", getRemaining(key, "back"));
          config.saveConfig();
          for(Iterator<String> iter = cooldownPlayers.get(key).cooldownMap.keySet().iterator(); iter.hasNext();) {
              String name = iter.next();
              if(getRemaining(key, name) <= 0.0) {
                  removeCooldown(key, name);
                  config.deleteConfig();
              }
          }
      }
  }
}
