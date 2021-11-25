package chain.Utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBarAPI {

    public static void sendActionBar(String text, Player p) {
        sendPacket(new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2),
                p);
    }

    @SuppressWarnings("rawtypes")
    private static void sendPacket(Packet pa, Player p) {
        CraftPlayer cp = (CraftPlayer) p;
        (cp.getHandle()).playerConnection.sendPacket(pa);
    }
}