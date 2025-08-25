package ua.hackhud.DreamDrop.Config;

import org.bukkit.inventory.ItemStack;
import ua.hackhud.DreamDrop.Entity.Storage.BagStorage;
import ua.hackhud.DreamDrop.Entity.Storage.SimpleStorage;
import ua.hackhud.DreamDrop.Entity.Storage.WeightedStorage;
import ua.hackhud.DreamDrop.Main;

import java.io.File;
import java.util.*;

public class StorageManager {
    private final Map<String, SimpleStorage> simpleStorages = new HashMap<>();
    private final Map<String, WeightedStorage> weightedStorages = new HashMap<>();
    private final Map<String, BagStorage> bagStorages = new HashMap<>();
    private final File simpleStorageFolder = new File(Main.getPlugin().getDataFolder(), "SimpleStorage");
    private final File weightedStorageFolder = new File(Main.getPlugin().getDataFolder(), "WeightedStorage");
    private final File bagStorageFolder = new File(Main.getPlugin().getDataFolder(), "BagStorage");
    private final Map<String, ItemStack> bagNameToItemMap = new IdentityHashMap<>();

    public StorageManager() {
        loadSimpleStorages();
        loadWeightedStorages();
        loadBagStorages();
    }

    public void reload() {
        simpleStorages.clear();
        weightedStorages.clear();
        bagStorages.clear();
        loadSimpleStorages();
        loadWeightedStorages();
        loadBagStorages();
    }

    private void loadSimpleStorages() {
        if (!simpleStorageFolder.exists()) simpleStorageFolder.mkdirs();
        File[] files = simpleStorageFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;
        for (File file : files) {
            String name = file.getName().replace(".yml", "");
            simpleStorages.put(name, new SimpleStorage(name));
        }
    }

    private void loadWeightedStorages() {
        if (!weightedStorageFolder.exists()) weightedStorageFolder.mkdirs();
        File[] files = weightedStorageFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;
        for (File file : files) {
            String name = file.getName().replace(".yml", "");
            weightedStorages.put(name, new WeightedStorage(name));
        }
    }

    private void loadBagStorages() {
        if (!bagStorageFolder.exists()) bagStorageFolder.mkdirs();
        File[] files = bagStorageFolder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;
        for (File file : files) {
            String name = file.getName().replace(".yml", "");

            ItemStack tempItem = new ItemStack(org.bukkit.Material.AIR);
            bagStorages.put(name, new BagStorage(name, tempItem));
        }

        for (BagStorage storage : bagStorages.values()) {
            bagNameToItemMap.put(storage.getName(), storage.getBagItem());
        }
    }

    public SimpleStorage getSimpleStorage(String name) {
        return simpleStorages.computeIfAbsent(name, SimpleStorage::new);
    }

    public WeightedStorage getWeightedStorage(String name) {
        return weightedStorages.computeIfAbsent(name, WeightedStorage::new);
    }

    public BagStorage getBagStorage(String name) {
        return bagStorages.get(name);
    }

    public ItemStack getBagItemByName(String bagName) {
        return bagNameToItemMap.get(bagName);
    }
    public String findBagNameByItem(ItemStack targetItem) {
        if (targetItem == null) return null;

        for (Map.Entry<String, ItemStack> entry : bagNameToItemMap.entrySet()) {
            if (entry.getValue().isSimilar(targetItem)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public boolean isBagItem(ItemStack item) {
        return findBagNameByItem(item) != null;
    }

    public boolean isSimpleStorageExists(String name) {
        return simpleStorages.containsKey(name);
    }

    public boolean isWeightedStorageExists(String name) {
        return weightedStorages.containsKey(name);
    }

    public boolean isBagStorageExists(String name) {
        return bagStorages.containsKey(name);
    }

    public void saveAll() {
        simpleStorages.values().forEach(SimpleStorage::saveToFile);
        weightedStorages.values().forEach(WeightedStorage::saveToFile);
        bagStorages.values().forEach(BagStorage::saveToFile);
    }
}