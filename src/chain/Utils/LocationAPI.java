package chain.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationAPI {

	public static String locationSerializer(Location location) {
		String world = location.getWorld().getName();

		double x = location.getX();
		double y = location.getY();
		double z = location.getZ();

		float yaw = location.getYaw();
		float pitch = location.getPitch();

		return world + ";" + x + ";" + y + ";" + z + ";" + yaw + ";" + pitch;
	}

	public static Location locationUnserializer(String split) {
		String[] splitLocation = split.split(";");

		World world = Bukkit.getWorld(splitLocation[0]);

		double x = Double.parseDouble(splitLocation[1]);
		double y = Double.parseDouble(splitLocation[2]);
		double z = Double.parseDouble(splitLocation[3]);

		float yaw = Float.parseFloat(splitLocation[4]);
		float pitch = Float.parseFloat(splitLocation[5]);

		return new Location(world, x, y, z, yaw, pitch);
	}

}
