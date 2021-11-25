package chain;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;


public class ConfigManager {

    public String semPerm;
    public String PlayerChain;
    public String ForaChain;
    public String CommandChain;
    public String DropMessage;
    public String BlockBreak;
    public String ItemsNoInv;

    public Boolean ClearDrops;
    public Boolean RegenHealth;

    public String ChainMessage;
    public String DeathMessage;
    public String QuitMessage;
    public String ChainMessageExit;

    public List<String> CommandsReleased;

    private static FileConfiguration Config = sChain.getInstance().getConfig();

    public void loadConfig() {

        semPerm = Config.getString("SemPermissao").replace("&", "§");
        PlayerChain = Config.getString("NaChain").replace("&", "§");
        ForaChain = sChain.Instance.getConfig().getString("ForaDaChain").replace("&", "ForaDaChain");
        CommandChain = Config.getString("ChainComandosMensagem").replace("&", "§");
        DropMessage = Config.getString("ChainItensDropMensagem").replace("&", "§");
        BlockBreak = Config.getString("QuebrarBlocosNaChain").replace("&", "§");
        ItemsNoInv = Config.getString("ItemsNoInventario").replace("&", "§");

        ClearDrops = Config.getBoolean("LimparDrops");
        RegenHealth = Config.getBoolean("RegerarVida");

        DeathMessage = Config.getString("ChainDeathMensagem").replace("&", "§");
        ChainMessage = Config.getString("ChainMensagem").replace("&", "§");
        QuitMessage = Config.getString("SairDaChain").replace("&", "§");
        ChainMessageExit = Config.getString("ChainSaidaMensagem").replace("&", "§");

        CommandsReleased = Config.getStringList("ComandosLiberadosNaChain");
    }
}