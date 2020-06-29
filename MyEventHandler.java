package lmao.me.back;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.earth2me.essentials.Essentials;
import net.md_5.bungee.api.ChatColor;

import java.text.DecimalFormat;

public class MyEventHandler implements Listener {
	public Main2 main;
	public Essentials essentials;
	public MyEventHandler(Plugin plugin, Main2 main, Essentials essentials) {
		this.main = main;
		this.essentials = essentials;
	}
	long cooldown = 6;
	long cdminutes = cooldown * 60;
	long cdhours = cdminutes * 60;
	

	@EventHandler
	public void onClick(PlayerInteractEvent e) {
		if (e.getPlayer().getItemInHand().equals(Material.PAPER)) {return; }
		try {if (!e.getItem().getItemMeta().getDisplayName().contains(ChatColor.translateAlternateColorCodes('&',"/back voucher"))) {return;}
		ItemStack item = e.getPlayer().getItemInHand();
		if (!item.getItemMeta().getLore().contains(ChatColor.translateAlternateColorCodes('&',"&aThis item is a /back voucher"))) {return; }
		 if(Cooldown.isCooling(e.getPlayer().getName(), "back")) {
       Cooldown.coolDurMessage(e.getPlayer(), "back");
       return;
   }
		 e.getPlayer().teleport(essentials.getUser(e.getPlayer()).getLastLocation());
		 e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Teleporting..."));
		 if (e.getPlayer().getInventory().getItemInHand().getAmount() > 1) {
			 e.getPlayer().getInventory().getItemInHand().setAmount(e.getPlayer().getInventory().getItemInHand().getAmount()-1);
		 } else {e.getPlayer().getInventory().remove(GiveBackVoucherCommand.GetItem()); }
		 //e.getPlayer().getInventory().getItemInHand().setAmount(e.getPlayer().getInventory().getItemInHand().getAmount()-1);
		 Cooldown.add(e.getPlayer().getName(), "back", cdhours, System.currentTimeMillis(), main);
		 //e.getPlayer().getInventory().remove(GiveBackVoucherCommand.GetItem());
		 //GiveBackVoucherCommand.GetItem().setAmount(item.getAmount() - 1);
		}
			catch (Exception ex){System.out.println(ex);}
	}
	
	
	public static double trim(double untrimmeded, int decimal) {
    String format = "#.#";

    for(int i = 1; i < decimal; i++) {
        format = format + "#";
    }
    DecimalFormat twoDec = new DecimalFormat(format);
    return Double.valueOf(twoDec.format(untrimmeded)).doubleValue();
} 
	public static enum TimeUnit {
  BEST,
  DAYS,
  HOURS,
  MINUTES,
  SECONDS,
}
  public static double convert(long time, TimeUnit unit, int decPoint) {
    if(unit == TimeUnit.BEST) {
        if(time < 60000L) unit = TimeUnit.SECONDS;
        else if(time < 3600000L) unit = TimeUnit.MINUTES;
        else if(time < 86400000L) unit = TimeUnit.HOURS;
        else unit = TimeUnit.DAYS;
    }
    if(unit == TimeUnit.SECONDS) return trim(time / 1000.0D, decPoint);
    if(unit == TimeUnit.MINUTES) return trim(time / 60000.0D, decPoint);
    if(unit == TimeUnit.HOURS) return trim(time / 3600000.0D, decPoint);
    if(unit == TimeUnit.DAYS) return trim(time / 86400000.0D, decPoint);
    return trim(time, decPoint);
}
}
