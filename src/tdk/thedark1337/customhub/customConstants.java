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
