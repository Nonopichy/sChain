package chain;

import chain.Commands.Chain;
import chain.Listeners.Events;
import chain.Utils.T_Config;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class sChain extends JavaPlugin {

    @Getter static sChain Instance;
    @Getter static ConfigManager ConfigManager;
    @Getter static List<String> onChain = new ArrayList<>();
    @Getter static T_Config locations;
    @Getter static T_Config kit;

    public void onEnable() {
        Instance = this;
        registerYamls();
        registerCommands();
        registerEvents();
        info();
    }
    private void info(){
        Logger log = Bukkit.getLogger();
        log.info("§c[sChain] §fCriado por §b" + getDescription().getAuthors());
        log.info("§c[sChain] §aO plugin §csChain §afoi iniciado com sucesso.");
    }
    private void registerYamls(){
        ConfigManager = new ConfigManager();
        saveDefaultConfig();
        locations = new T_Config(this, "locations.yml");
        locations.saveDefaultConfig();
        kit = new T_Config(this, "kit.yml");
        kit.saveDefaultConfig();
        ConfigManager.loadConfig();
    }
    private void registerEvents() {
        new Events(this);
    }
    private void registerCommands() {
        Bukkit.getPluginCommand("chain").setExecutor(new Chain());
    }
}
