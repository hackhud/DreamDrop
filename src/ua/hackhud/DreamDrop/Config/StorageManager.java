package ua.hackhud.DreamDrop.Config;

import ua.hackhud.DreamDrop.Entity.Storage.SimpleStorage;
import ua.hackhud.DreamDrop.Entity.Storage.WeightedStorage;
import ua.hackhud.DreamDrop.Main;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class StorageManager {

    private final Map<String, SimpleStorage> simpleStorages = new HashMap<>();
    private final Map<String, WeightedStorage> weightedStorages = new HashMap<>();
    private final File simpleStorageFolder = new File(Main.getPlugin().getDataFolder(), "SimpleStorage");
    private final File weightedStorageFolder = new File(Main.getPlugin().getDataFolder(), "WeightedStorage");

    public StorageManager() {
        loadSimpleStorages();
        loadWeightedStorages();
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

    public SimpleStorage getSimpleStorage(String name) {
        return simpleStorages.computeIfAbsent(name, SimpleStorage::new);
    }

    public WeightedStorage getWeightedStorage(String name) {
        return weightedStorages.computeIfAbsent(name, WeightedStorage::new);
    }
    public boolean isSimpleStorageExists(String name) {
        return simpleStorages.get(name) != null;
    }
    public boolean isWeightedStorageExists(String name) {
        return weightedStorages.get(name) != null;
    }

    public void saveAll() {
        simpleStorages.values().forEach(SimpleStorage::saveToFile);
        weightedStorages.values().forEach(WeightedStorage::saveToFile);
    }
}
