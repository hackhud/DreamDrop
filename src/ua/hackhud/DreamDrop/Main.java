package ua.hackhud.DreamDrop;

import org.bukkit.plugin.java.JavaPlugin;
import ua.hackhud.DreamDrop.Config.ConfigManager;
import ua.hackhud.DreamDrop.Config.StorageManager;
import ua.hackhud.DreamDrop.Listener.DreamDropCommand;
import ua.hackhud.DreamDrop.Perk.PerkRegistry;
import ua.hackhud.DreamDrop.Listener.ItemDropListener;
import ua.hackhud.DreamDrop.Transformation.GoldTransformation;
import ua.hackhud.DreamDrop.Transformation.PerkTransformation;

public class Main extends JavaPlugin {
    private static Main plugin;
    private ConfigManager configManager;
    private StorageManager storageManager;
    @Override
    public void onEnable() {
        plugin = this;
        getLogger().info("DreamDrop v1.0 Enabled!");
        registerConfig();
        registerStorages();
        PerkRegistry.init();
        getServer().getPluginManager().registerEvents(new ItemDropListener(), this);
        getCommand("dreamdrop").setExecutor(new DreamDropCommand());
    }

    public static Main getPlugin() {
        return plugin;
    }
    public void registerConfig() {
        configManager = new ConfigManager();
    }
    public void registerStorages() { storageManager = new StorageManager(); }
    public ConfigManager getConfigManager() {
        return configManager;
    }
    public StorageManager getStorageManager() {
        return storageManager;
    }
    public GoldTransformation getGoldTransformation() {
        return new GoldTransformation();
    }
    public PerkTransformation getPerkTransformation() {
        return new PerkTransformation();
    }
}
