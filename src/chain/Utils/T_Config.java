package chain.Utils;

import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class T_Config {
    @Getter private YamlConfiguration config;
    private final JavaPlugin plugin;
    private final String name;
    private File file;
    public T_Config(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;
        reloadConfig();
    }
    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveDefault() {
        config.options().copyDefaults(true);
    }
    public void saveDefaultConfig() {
        if (exist())
            return;
        plugin.saveResource(name, false);
        reloadConfig();
    }
    public void reloadConfig() {
        file = new File(plugin.getDataFolder(),name);
        config = YamlConfiguration.loadConfiguration(file);
    }
    public void deleteConfig() {
        file.delete();
    }
    public boolean exist() {
        return file.exists();
    }
}
