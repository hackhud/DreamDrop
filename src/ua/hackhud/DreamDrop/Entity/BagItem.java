package ua.hackhud.DreamDrop.Entity;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public class BagItem {

    private final ItemStack item;
    private final int weight;
    private final List<String> commandList;

    public BagItem(ItemStack item, int weight, List<String> commandList) {
        this.item = item;
        this.weight = weight;
        this.commandList = commandList;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getWeight() {
        return weight;
    }

    public List<String> getCommandList() {
        return commandList;
    }
}
