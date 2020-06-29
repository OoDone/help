package lmao.me.back;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.earth2me.essentials.Essentials;



public class Main2 extends JavaPlugin {
	public Main2 main2 = this;
	public File file = new File(getDataFolder() + File.separator + "userdata");
	@Override
	public void onEnable() {
		System.out.println("xd Enabled");
		Essentials essentials = (com.earth2me.essentials.Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
		Bukkit.getServer().getPluginManager().registerEvents(new MyEventHandler(this, this, essentials), this);
		this.getCommand("givebackvoucher").setExecutor(new GiveBackVoucherCommand(this, file));
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
	     
      @Override
      public void run() {
          Cooldown.handleCooldowns(main2);
      }
  }, 1L, 1L);
		for (File file : file.listFiles()) {
			UUID PlayerUUID = UUID.fromString(file.getName().replace(".yml", ""));
			OfflinePlayer player = Bukkit.getOfflinePlayer(PlayerUUID);
			ConfigHandler config = new ConfigHandler(main2, player);
			Cooldown.add(config.getPlayer().getName(), "back", config.getFileConfig().getLong("back"), System.currentTimeMillis(), main2);
		}
	}
	
	
	@Override
	public void onDisable() {
		System.out.println("xd Disabled!");
	}
}