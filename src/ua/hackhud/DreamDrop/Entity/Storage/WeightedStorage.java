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

    private static final String ITEMS_PATH = "items.";

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
                boolean fileCreated = file.createNewFile();
                if (fileCreated) {
                    saveToFile();
                }
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
        int randomValue = random.nextInt(totalWeight);
        int current = 0;

        for (WeightedItem weightedItem : items) {
            current += weightedItem.getWeight();
            if (randomValue < current) return weightedItem.getItem();
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
            WeightedItem weightedItem = items.get(i);
            config.set(ITEMS_PATH + i + ".item", weightedItem.getItem());
            config.set(ITEMS_PATH + i + ".weight", weightedItem.getWeight());
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

        if (config.contains(ITEMS_PATH)) {
            for (String key : config.getConfigurationSection(ITEMS_PATH).getKeys(false)) {
                ItemStack item = config.getItemStack(ITEMS_PATH + key + ".item");
                int weight = config.getInt(ITEMS_PATH + key + ".weight");
                if (item != null) {
                    items.add(new WeightedItem(item, weight));
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public List<WeightedItem> getItems() {
        return new ArrayList<>(items);
    }

    public void clearItems() {
        items.clear();
        saveToFile();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }
}