package chain.Listeners;

import chain.Utils.ActionBarAPI;
import chain.Utils.LocationAPI;
import chain.Utils.T_Config;
import chain.sChain;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

public class Events implements Listener {

    @EventHandler(priority =  EventPriority.HIGHEST)
    void DeathEvent(PlayerDeathEvent e) {
        if (!(e.getEntity().getPlayer() instanceof Player || e.getEntity().getKiller() instanceof Player)) return;

        Player victim = e.getEntity().getPlayer();
        Player attacker = e.getEntity().getKiller();

        if (!sChain.onChain.contains(victim.getName())) return;

        e.setDroppedExp(0);

        if (sChain.Config.ClearDrops)
            e.getDrops().clear();

        for (Player death : Bukkit.getOnlinePlayers())
            ActionBarAPI.sendActionBar(sChain.Config.DeathMessage.replace("{assassino}", attacker.getName()).replace("{vitima}", victim.getName()), death);

        e.setDeathMessage(null);
    }

    @EventHandler
    void RespawnEvent(PlayerRespawnEvent e) {
        Player p = e.getPlayer();

        if (sChain.onChain.contains(p.getName())) {
            playerTeleportExitChain(p);
            sChain.onChain.remove(p.getName());
        }
    }

    @EventHandler
    void QuitEvent(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        for (Player quit : Bukkit.getOnlinePlayers()) {
            if (sChain.onChain.contains(p.getName())) {
                p.getInventory().clear();
                p.getInventory().setArmorContents(null);
                ActionBarAPI.sendActionBar(sChain.Config.QuitMessage.replace("{jogador}", p.getName()), quit);
                sChain.onChain.remove(p.getName());
            }
        }
    }

    @EventHandler
    void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (sChain.onChain.contains(p.getName())) {
            if (!e.getMessage().equalsIgnoreCase("chain")) return;
            for (String commands : sChain.Config.CommandsReleased) {
                if (!e.getMessage().contains(commands)) {
                    e.setCancelled(true);
                    p.sendMessage(sChain.Config.CommandChain);
                }
            }
        }
    }
    @EventHandler
    void DropItemEvent(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        if (sChain.onChain.contains(p.getName())) {
            e.setCancelled(true);
            p.sendMessage(sChain.Config.DropMessage);
        }
    }
    @EventHandler
    void DamageItem(PlayerItemDamageEvent e) {
        Player p = e.getPlayer();
        if (sChain.onChain.contains(p.getName())) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    void BreakBlock(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (sChain.onChain.contains(p.getName())) {
            e.setCancelled(true);
            p.sendMessage(sChain.Config.BlockBreak);
        }
    }

    @EventHandler
    void RegenHealth(EntityRegainHealthEvent e) {
        if (sChain.Config.RegenHealth) {
            e.setCancelled(true);
        }
    }

    public void playerTeleportExitChain(Player p) {
        T_Config locationFile = sChain.Instance.locations;
        FileConfiguration locationConfig = locationFile.getConfig();

        Location locationExitChain = LocationAPI.locationUnserializer(locationConfig.getString("Locais.Chain.Saida"));

        p.teleport(locationExitChain);
    }
}