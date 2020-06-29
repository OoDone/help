package lmao.me.back;


import java.io.File;
import java.io.IOException;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public final class ConfigHandler {
	private File file;
	private FileConfiguration config;
	private Main2 main;
	private OfflinePlayer player;
	public ConfigHandler(Main2 main, OfflinePlayer player) {
		this.player = player;
		this.main = main;
		loadConfig();
	}
	
	public FileConfiguration getFileConfig() {
		return config;
	}
	public File getFile() {
		return file;
	}
	public OfflinePlayer getPlayer() {
		return player;
	}
	public void saveConfig() {
		try {
			config.save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			return;
			}
	}
	public void loadConfig() {
        file = new File(main.getDataFolder() + File.separator + "userdata", player.getUniqueId().toString() + ".yml");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
            config = YamlConfiguration.loadConfiguration(file);
    }
	public void deleteConfig() {
		file.delete();
	}
}