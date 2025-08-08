package ua.hackhud.DreamDrop.Entity.Storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import ua.hackhud.DreamDrop.Entity.WeightedItem;
import ua.hackhud.DreamDrop.Main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeightedStorage {

    private final String name;
    private final File file;
    private final List<WeightedItem> items = new ArrayList<>();
    private final Random random = new Random();

    public WeightedStorage(String name) {
        this.name = name;
        this.file = new File(Main.getPlugin().getDataFolder(), "WeightedStorage/" + name + ".yml");

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

    public void addItem(ItemStack item, int weight) {
        items.add(new WeightedItem(item, weight));
        saveToFile();
    }

    public ItemStack getRandomItem() {
        if (items.isEmpty()) return null;

        int totalWeight = items.stream().mapToInt(WeightedItem::getWeight).sum();
        int r = random.nextInt(totalWeight), current = 0;

        for (WeightedItem i : items) {
            current += i.getWeight();
            if (r < current) return i.getItem();
        }
        return null;
    }

    public void removeItem(int id) {
        if (id >= 0 && id < items.size()) {
            items.remove(id);
            saveToFile();
        }
    }

    public void saveToFile() {
        FileConfiguration config = new YamlConfiguration();
        for (int i = 0; i < items.size(); i++) {
            WeightedItem wi = items.get(i);
            config.set("items." + i + ".item", wi.getItem());
            config.set("items." + i + ".weight", wi.getWeight());
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
                ItemStack item = config.getItemStack("items." + key + ".item");
                int weight = config.getInt("items." + key + ".weight");
                if (item != null) {
                    items.add(new WeightedItem(item, weight));
                }
            }
        }
    }

    public String getName() {
        return name;
    }
}
