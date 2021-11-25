package chain.Listeners;

import chain.ConfigManager;
import chain.Utils.ActionBarAPI;
import chain.Utils.LocationAPI;
import chain.Utils.T_Config;
import chain.sChain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Events implements Listener {
    public Events(final JavaPlugin plugin){
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler(priority =  EventPriority.HIGHEST)
    void DeathEvent(PlayerDeathEvent e) {
        Player victim = e.getEntity().getPlayer(), attacker = e.getEntity().getKiller();

        if (!(victim instanceof Player || attacker instanceof Player)) return;

        if (!sChain.getOnChain().contains(victim.getName())) return;

        e.setDroppedExp(0);

        ConfigManager conf = sChain.getConfigManager();

        if (conf.ClearDrops)
            e.getDrops().clear();

        for (Player death : Bukkit.getOnlinePlayers())
            ActionBarAPI.sendActionBar(conf.DeathMessage.replace("{assassino}", attacker.getName()).replace("{vitima}", victim.getName()), death);

        e.setDeathMessage(null);
    }
    @EventHandler
    void RespawnEvent(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        String name = p.getName();
        if (sChain.getOnChain().contains(name)) {
            playerTeleportExitChain(p);
            sChain.getOnChain().remove(name);
        }
    }
    @EventHandler
    void QuitEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        String name = p.getPlayer().getName();
        for (Player quit : Bukkit.getOnlinePlayers()) {
            if (sChain.getOnChain().contains(name)) {
                p.getInventory().clear();
                p.getInventory().setArmorContents(null);
                ActionBarAPI.sendActionBar(sChain.getConfigManager().QuitMessage.replace("{jogador}", name), quit);
                sChain.getOnChain().remove(name);
            }
        }
    }
    @EventHandler
    void onCommand(PlayerCommandPreprocessEvent e) {
        String msg = e.getMessage();
        if (!msg.equalsIgnoreCase("chain"))
            return;
        Player p = e.getPlayer();
        String name = p.getName();
        if(!sChain.getOnChain().contains(name))
            return;
        for (String commands : sChain.getConfigManager().CommandsReleased) {
            if (!msg.contains(commands)) {
                e.setCancelled(true);
                p.sendMessage(sChain.getConfigManager().CommandChain);
                break;
            }
        }
    }
    @EventHandler
    void DropItemEvent(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (sChain.getOnChain().contains(p.getName())) {
            e.setCancelled(true);
            p.sendMessage(sChain.getConfigManager().DropMessage);
        }
    }
    @EventHandler
    void DamageItem(PlayerItemDamageEvent e) {
        if (sChain.getOnChain().contains(e.getPlayer().getName()))
            e.setCancelled(true);
    }
    @EventHandler
    void BreakBlock(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (sChain.getOnChain().contains(p.getName())) {
            e.setCancelled(true);
            p.sendMessage(sChain.getConfigManager().BlockBreak);
        }
    }
    @EventHandler
    void RegenHealth(EntityRegainHealthEvent e) {
        if (sChain.getConfigManager().RegenHealth)
            e.setCancelled(true);
    }
    public void playerTeleportExitChain(Player p) {
        T_Config locationFile = sChain.getLocations();
        FileConfiguration locationConfig = locationFile.getConfig();
        Location locationExitChain = LocationAPI.Unserializer(locationConfig.getString("Locais.Chain.Saida"));
        p.teleport(locationExitChain);
    }
}