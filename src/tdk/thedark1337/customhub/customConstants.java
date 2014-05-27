package tdk.thedark1337.customhub;

import java.io.File;

public class customConstants {
	public static void init(CustomHub plugin) {
		pluginFolder = plugin.getDataFolder() + File.separator;
	}

	private static String pluginFolder;

	public static String getPluginFolder() {
		return pluginFolder;
	}

	// config
	public static String getConfigPath() {
		return getPluginFolder() + "config.yml";
	}

	// hubConfig
	public static String getHubConfigPath() {
		return getPluginFolder() + "hub.yml";
	}
}