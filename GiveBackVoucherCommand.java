package lmao.me.back;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.avaje.ebeaninternal.server.deploy.generatedproperty.GeneratedInsertTimestamp;

import net.md_5.bungee.api.ChatColor;

	



public class GiveBackVoucherCommand implements CommandExecutor {
	public Main2 main;
	public static ItemStack item = new ItemStack(Material.PAPER, 1);
	public File file;
	public GiveBackVoucherCommand(Main2 main, File file) {
		this.main = main;
	}
	public static ItemStack GetItem() {
		ItemStack item2 = new ItemStack(Material.PAPER, 1);
		ItemMeta meta = item2.getItemMeta();
		List<String> list = new ArrayList<String>();
		list.add(ChatColor.translateAlternateColorCodes('&',"&aThis item is a /back voucher"));
		list.add(ChatColor.translateAlternateColorCodes('&',"&aThere is a 6 hour cooldown!"));
		meta.setLore(list);
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&a&l/back voucher"));
		item2.setItemMeta(meta);
		return item2;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String laber, String[] args) {
		if (cmd.toString().contains("givebackvoucher")) {
			if (sender.hasPermission("blockfront.givebackvoucher")) {
				if (args.length > 0) {
					ItemMeta meta = item.getItemMeta();
					List<String> list = new ArrayList<String>();
					list.add(ChatColor.translateAlternateColorCodes('&',"&aThis item is a /back voucher"));
					list.add(ChatColor.translateAlternateColorCodes('&',"&aThere is a 6 hour cooldown!"));
					meta.setLore(list);
					meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&a&l/back voucher"));
					item.setItemMeta(meta);
					Bukkit.getPlayer(args[0]).getInventory().addItem(item);
				} else {
					ItemMeta meta = item.getItemMeta();
					List<String> list = new ArrayList<String>();
					list.add(ChatColor.translateAlternateColorCodes('&',"&aThis item is a /back voucher"));
					list.add(ChatColor.translateAlternateColorCodes('&',"&aThere is a 6 hour cooldown!"));
					meta.setLore(list);
					meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',"&a&l/back voucher"));
					item.setItemMeta(meta);
					Bukkit.getPlayer(sender.getName()).getInventory().addItem(item);
					for (File file : file.listFiles()) {
						UUID PlayerUUID = UUID.fromString(file.getName().replace(".yml", ""));
						OfflinePlayer player = Bukkit.getOfflinePlayer(PlayerUUID);
						ConfigHandler config = new ConfigHandler(main, player);
						Cooldown.add(config.getPlayer().getName(), "back", config.getFileConfig().getLong("back"), System.currentTimeMillis(), main);
					}
				}
			}
		}
		return true;
	}
}
