package chain;

import chain.Commands.Chain;
import chain.Listeners.Events;
import chain.Utils.T_Config;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class sChain extends JavaPlugin {

    public static sChain Instance;

    public static ConfigManager Config;

    public static List<String> onChain = new ArrayList<>();

    public T_Config locations;
    public T_Config kit;

    public void onEnable() {

        Instance = this;
        Config = new ConfigManager();
        saveDefaultConfig();
        locations = new T_Config(this, "locations.yml");
        locations.saveDefaultConfig();
        kit = new T_Config(this, "kit.yml");
        kit.saveDefaultConfig();
        Config.loadConfig();
        registerCommands();
        registerEvents();

        Bukkit.getConsoleSender().sendMessage("§c[sChain] §fCriado por §b" + getDescription().getAuthors());
        Bukkit.getConsoleSender().sendMessage("§c[sChain] §aO plugin §csChain §afoi iniciado com sucesso.");

    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new Events(), this);

    }
    private void registerCommands() {
        Bukkit.getPluginCommand("chain").setExecutor(new Chain());

    }
}
