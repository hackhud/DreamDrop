package ua.hackhud.DreamDrop.Entity.Storage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import ua.hackhud.DreamDrop.Entity.BagItem;
import ua.hackhud.DreamDrop.Main;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BagStorage {

    private static final String ITEMS_PATH = "items.";
    private static final String BASE_COMMANDS_PATH = "baseCommands";
    private static final String BAG_ITEM_PATH = "bagItem";

    private final String name;
    private final File file;
    private final List<BagItem> items = new ArrayList<>();
    private final List<String> baseCommands = new ArrayList<>();
    private final Random random = new Random();
    private ItemStack bagItemStack;

    public BagStorage(String name, ItemStack initialBagItemStack) {
        this.name = name;
        this.bagItemStack = initialBagItemStack;
        this.file = new File(Main.getPlugin().getDataFolder(), "BagStorage/" + name + ".yml");

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

    public void addItem(ItemStack item, int weight, List<String> commandList) {
        items.add(new BagItem(item, weight, commandList));
        saveToFile();
    }

    public BagItem getRandomItem() {
        if (items.isEmpty()) return null;

        int totalWeight = items.stream().mapToInt(BagItem::getWeight).sum();
        int randomValue = random.nextInt(totalWeight);
        int current = 0;

        for (BagItem item : items) {
            current += item.getWeight();
            if (randomValue < current) return item;
        }
        return null;
    }

    public void removeItem(int id) {
        if (id >= 0 && id < items.size()) {
            items.remove(id);
            saveToFile();
        }
    }

    public void setBagItem(ItemStack bagItemStack) {
        this.bagItemStack = bagItemStack;
        saveToFile();
    }

    public ItemStack getBagItem() {
        return bagItemStack;
    }

    public void setBaseCommands(List<String> baseCommands) {
        this.baseCommands.clear();
        this.baseCommands.addAll(baseCommands);
        saveToFile();
    }

    public List<String> getBaseCommands() {
        return new ArrayList<>(baseCommands);
    }

    public List<BagItem> getItems() {
        return new ArrayList<>(items);
    }

    public void saveToFile() {
        FileConfiguration config = new YamlConfiguration();

        if (bagItemStack != null) {
            config.set(BAG_ITEM_PATH, bagItemStack);
        }

        if (!baseCommands.isEmpty()) {
            config.set(BASE_COMMANDS_PATH, baseCommands);
        }

        for (int i = 0; i < items.size(); i++) {
            BagItem currentBagItem = items.get(i);
            String path = ITEMS_PATH + i;

            config.set(path + ".item", currentBagItem.getItem());
            config.set(path + ".weight", currentBagItem.getWeight());

            if (currentBagItem.getCommandList() != null && !currentBagItem.getCommandList().isEmpty()) {
                config.set(path + ".commandList", currentBagItem.getCommandList());
            }
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
        baseCommands.clear();

        if (config.contains(BAG_ITEM_PATH)) {
            bagItemStack = config.getItemStack(BAG_ITEM_PATH);
        }

        if (config.contains(BASE_COMMANDS_PATH)) {
            baseCommands.addAll(config.getStringList(BASE_COMMANDS_PATH));
        }

        if (config.contains(ITEMS_PATH)) {
            for (String key : config.getConfigurationSection(ITEMS_PATH).getKeys(false)) {
                ItemStack item = config.getItemStack(ITEMS_PATH + key + ".item");
                int weight = config.getInt(ITEMS_PATH + key + ".weight");
                List<String> commandList = config.getStringList(ITEMS_PATH + key + ".commandList");

                if (item != null) {
                    items.add(new BagItem(item, weight, commandList));
                }
            }
        }
    }

    public String getName() {
        return name;
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