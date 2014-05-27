/**
 * This file is part of customHub created by thedark1337 version 0.3.5.
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
