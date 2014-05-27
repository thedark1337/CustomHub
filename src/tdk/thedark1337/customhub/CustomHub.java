package tdk.thedark1337.customhub;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;



public class CustomHub extends JavaPlugin {

	public CustomHub plugin;
	private customConfig config;
	public timedTeleport timedTeleport;
	private final CustomHubListener listener = new CustomHubListener(this);
	private FileConfiguration config2;

	@Override
	public void onDisable() {
		config.save();
		config = null;
		try {
			config2.save(new File(customConstants.getHubConfigPath()));
		} catch (IOException ex) {
		}
		config2 = null;
	}

	@Override
	public void onEnable() {
		plugin = this;
		customConstants.init(this);
		getServer().getPluginManager().registerEvents(listener, this);
		config = new customConfig();
		config.load();
		config2 = YamlConfiguration.loadConfiguration(new File(customConstants
				.getHubConfigPath()));
		try {
			config2.save(new File(customConstants.getHubConfigPath()));
		} catch (IOException ex) {
		}
		getLogger().log(
				Level.INFO,
				"Running customHub version " + getDescription().getVersion()
						+ " Created by thedark1337");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String commandLabel, String[] args) {
		if (command.getName().equalsIgnoreCase("hub")) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (sender.hasPermission("customhub.reload")) {
						reloadConfig();
						sender.sendMessage(ChatColor.AQUA
								+ " Hub config reloaded");
						return true;
					} else {
						sender.sendMessage(ChatColor.RED
								+ "Error you do not have permission to reload.");
						return true;
					}
				} else if (args[0].equalsIgnoreCase("help")) {
					if (sender.hasPermission("customhub.help")) {
						sender.sendMessage(ChatColor.AQUA
								+ "======Commands======");
						if (sender.hasPermission("customhub.sethub")) {
							sender.sendMessage(ChatColor.YELLOW
									+ "/sethub  - Sets the main server hub");
						}
						if (sender.hasPermission("customhub.hub")) {
							sender.sendMessage(ChatColor.YELLOW
									+ "/hub   -  Teleports you to the main server hub.");
						}
						sender.sendMessage(ChatColor.AQUA
								+ "==================");
					}
					return true;
				}
			}
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ " Non Players can't be teleported.");
				return true;
			} else {
				if (getHubLocation() == null) {
					getLogger().log(Level.SEVERE, "Error hub can't be null.");
				}
				timedTeleport = new timedTeleport(this, (Player) sender,
						(Location) getHubLocation(), (int) getConfig().getInt(
								"settings.delay"));
				return true;
			}
		}
		if (command.getName().equalsIgnoreCase("sethub")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(ChatColor.RED
						+ " Non Players can't set hub.");
				return true;
			}
			if (sender.hasPermission("customhub.sethub")) {
				Player player = (Player) sender;
				setHub(player);
				return true;
			}
		}
		return false;
	}

	public Location getHubLocation() {
		World world = getServer().getWorld(
				config2.getString("hub-location.world"));
		if (world == null) {
			world = getServer().getWorld("hubWorld") != null ? getServer()
					.getWorld("hubWorld") : getServer().getWorlds().get(0);
		}

		return new Location(world, config2.getDouble("hub-location.x"),
				config2.getDouble("hub-location.y"),
				config2.getDouble("hub-location.z"),
				(float) config2.getDouble("hub-location.yaw"),
				(float) config2.getDouble("hub-location.pitch"));
	}

	public void setHub(Player player) throws NullPointerException {
		if (player == null) {
			throw new NullPointerException("Player can't be null.");
		}
		Location loc = player.getLocation();
		if (loc != null) {
			config2.set("hub-location.world", loc.getWorld().getName());
			config2.set("hub-location.x", loc.getX());
			config2.set("hub-location.y", loc.getY());
			config2.set("hub-location.z", loc.getZ());
			config2.set("hub-location.yaw", loc.getYaw());
			config2.set("hub-location.pitch", loc.getPitch());
			try {
				config2.save(new File(customConstants.getHubConfigPath()));
			} catch (IOException ex) {
			}
			loc.getWorld().setSpawnLocation(loc.getBlockX(), loc.getBlockY(),
					loc.getBlockZ());

			player.sendMessage(ChatColor.DARK_AQUA + "hub set at " + "World: "
					+ loc.getWorld().getName() + ": X:" + loc.getBlockX()
					+ " Y:" + loc.getBlockY() + " Z: " + loc.getBlockZ());
		} else {
			throw new NullPointerException("Location can't be null.");
		}
	}

}