package ua.hackhud.DreamDrop.Entity.Storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import ua.hackhud.DreamDrop.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleStorage {

    private final String name;
    private final File file;
    private final List<ItemStack> items = new ArrayList<>();

    public SimpleStorage(String name) {
        this.name = name;
        this.file = new File(Main.getPlugin().getDataFolder(), "SimpleStorage/" + name + ".yml");

        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                saveToFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loadFromFile();
        }
    }

    public void saveToFile() {
        FileConfiguration config = new YamlConfiguration();
        for (int i = 0; i < items.size(); i++) {
            config.set("items." + i, items.get(i));
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        items.clear();

        if (config.contains("items")) {
            for (String key : config.getConfigurationSection("items").getKeys(false)) {
                ItemStack item = config.getItemStack("items." + key);
                if (item != null) items.add(item);
            }
        }
    }

    public void addItem(ItemStack itemStack) {
        items.add(itemStack);
        saveToFile();
    }

    public ItemStack getItem(int id) {
        if (id < 0 || id >= items.size()) return null;
            return items.get(id);
    }

    public void removeItem(int id) {
        if (id >= 0 && id < items.size()) {
            items.remove(id);
        }
    }

    public String getName() {
        return name;
    }
}
