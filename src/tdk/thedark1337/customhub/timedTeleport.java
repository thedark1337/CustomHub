package tdk.thedark1337.customhub;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.Plugin;

public class timedTeleport implements Runnable {
	private Player p = null;
	private Location l = null;
	private Plugin CustomHub;
	private double initX;
	private double initY;
	private double initZ;
	private double afterX;
	private double afterY;
	private double afterZ;
	private long seconds;
	private long delay;
	private boolean canMove;
	private boolean bypass;
	private int task_id = -1;
	private long startTime = System.currentTimeMillis();

	public timedTeleport(Plugin plugin, Player p, Location l, long ticks)
			throws IllegalArgumentException {
		if (p == null)
			throw new IllegalArgumentException("Player must not be null.");
		if (l == null)
			throw new IllegalArgumentException("Location must not be null.");
		this.p = p;
		this.l = l;
		this.seconds = ticks;
		this.delay = (seconds * 1000);
		this.initX = Math.round(p.getLocation().getX());
		this.initY = Math.round(p.getLocation().getY());
		this.initZ = Math.round(p.getLocation().getZ());
		this.CustomHub = plugin;
		this.canMove = CustomHub.getConfig().getBoolean("settings.canMove");
		this.bypass = p.hasPermission("customhub.bypassdelay");

		task_id = CustomHub.getServer().getScheduler()
				.runTaskTimer(plugin, this, 20, 20).getTaskId();
	}

	public void run() {
		Location loc = p.getLocation();
		if (bypass) {
			if (!canMove(loc)) {
				cancelTask(true);
			} else {
				teleport();
			}
		} else {
			final long now = System.currentTimeMillis();
			if (now < startTime + delay) {
				if (canMove(loc)) {
			        sendMsg(p, ChatColor.AQUA, "[CustomHub] Teleporting in: " + seconds-- + " seconds");
				} else {
					cancelTask(true);
				}
			} else {
				if (!canMove(loc)) {
					cancelTask(true);
				} else {
					teleport();
				}
			}
		}
	}

	private void teleport() {
		this.p.teleport(this.l, TeleportCause.PLUGIN);
		cancelTask(false);
	}

	private boolean canMove(Location loc) {
		afterX = Math.round(loc.getX());
		afterY = Math.round(loc.getY());
		afterZ = Math.round(loc.getZ());
		if (!canMove && (initX != afterX || initY != afterY || initZ != afterZ)) {
			return false;
		} else {
			return true;
		}
	}
    public void sendMsg(Player player, ChatColor color, String msg) {
		player.sendMessage(color+ msg);
    }
	public void cancelTask(boolean notify) {
		if (task_id == -1) {
			return;
		}
		try {
			CustomHub.getServer().getScheduler().cancelTask(task_id);
			if (notify) {
			sendMsg(p, ChatColor.DARK_RED,"[CustomHub] Teleporting canceled due to movement");
		}
		}finally {
			task_id = -1;
		}
	}
}