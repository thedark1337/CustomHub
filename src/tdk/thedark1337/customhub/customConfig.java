package tdk.thedark1337.customhub;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class customConfig {
	private FileConfiguration config;

	public boolean canMove = false;
	public int delay = 5;
	public List <String> afw = null;
	

	public void load() {
		config = YamlConfiguration.loadConfiguration(new File(customConstants
				.getConfigPath()));
		canMove = config.getBoolean("settings.canMove", canMove);
		delay = config.getInt("settings.delay", delay);
		afw = config.getStringList("settings.autoflyworld");
		if (afw.size() == 0) {
			afw.add("example1");
		}
		save();
	}

	public void save() {
		config = new YamlConfiguration();
		config.set("settings.canMove", canMove);
		config.set("settings.delay", delay);
		config.set("settings.autoflyworld", afw);
		try {
			config.save(new File(customConstants.getConfigPath()));
		} catch (IOException ex) {
		}
	}
}
