package chain.Utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class LocationAPI {
	public static Location Unserializer(String serialized) {
		String[] divPoints = serialized.split("; ");
		Location deserialized = new Location(Bukkit.getWorld(divPoints[0]), Double.parseDouble(divPoints[1]), Double.parseDouble(divPoints[2]), Double.parseDouble(divPoints[3]));
		deserialized.setYaw(Float.parseFloat(divPoints[4]));
		deserialized.setPitch(Float.parseFloat(divPoints[5]));
		return deserialized;
	}
	public static String Serializer(Location unserialized) {
			return unserialized.getWorld().getName() + "; " + unserialized.getX() + "; " + unserialized.getY() + "; " + unserialized.getZ() + "; " + unserialized
					.getYaw() + "; " + unserialized.getPitch();
	}
}
