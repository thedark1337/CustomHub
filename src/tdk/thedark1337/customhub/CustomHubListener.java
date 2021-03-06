/**
 * This file is part of customHub created by thedark1337 version 0.3.6.
 *
 * customHub is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * customHub is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with customHub.  If not, see <http://www.gnu.org/licenses/>.
 */
package tdk.thedark1337.customhub;

import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class CustomHubListener implements Listener {
    private final CustomHub plugin;
    private List<String> worlds;

    public CustomHubListener(CustomHub instance) {
        plugin = instance;
        List<String> list = plugin.getConfig().getStringList("settings.autoflyworld");
        if (list.size() > 0) {
            worlds = list;
            for (int i = 0; i < worlds.size(); i++) {
                worlds.set(i, worlds.get(i).toLowerCase().trim());
            }
        }
    }

    @EventHandler
    public void onPlayerChangeWorld(final PlayerChangedWorldEvent event) {
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            public void run() {
                if (worlds.size() > 0) {
                    Player player = event.getPlayer();
                    String world = player.getWorld().getName().trim().toLowerCase();
                    if (isWorld(world)) {
                        player.setAllowFlight(true);
                        if (!player.isFlying() && player.getGameMode() != GameMode.CREATIVE && player.getAllowFlight()) {
                            player.setFlying(true);
                        }
                    }
                }

            }
        }, 1);
    }

    public boolean isWorld(String world) {
        if (worlds.size() > 0 && world != null) {
            for (String s : worlds) {
                if (world.contains(s)) { return true; }
            }
            return false;
        } else {
            throw new NullPointerException("Either world doesn't exist or config is wrong.");
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if (!player.hasPlayedBefore()) {
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    player.teleport(plugin.getHubLocation(), TeleportCause.PLUGIN);
                }
            }, 1);
        }
    }
}
