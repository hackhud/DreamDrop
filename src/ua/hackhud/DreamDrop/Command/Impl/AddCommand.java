package ua.hackhud.DreamDrop.Command.Impl;

import ua.hackhud.DreamDrop.Command.BaseCommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ua.hackhud.DreamDrop.Entity.Storage.BagStorage;
import ua.hackhud.DreamDrop.Entity.Storage.SimpleStorage;
import ua.hackhud.DreamDrop.Entity.Storage.WeightedStorage;
import ua.hackhud.DreamDrop.Util.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class AddCommand extends BaseCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!validateSender(sender)) return true;

        validateArgsLength(args, 2, "Использование: " + getUsage());

        Player player = (Player) sender;
        ItemStack itemInHand = getItemInHand(player);

        String storageType = args[0].toLowerCase();
        String storageName = args[1];

        switch (storageType) {
            case "simple":
                return addToSimpleStorage(sender, storageName, itemInHand);
            case "weighted":
                return addToWeightedStorage(sender, args, storageName, itemInHand);
            case "bag":
                return addToBagStorage(sender, args, storageName, itemInHand);
            default:
                throw new IllegalArgumentException("Тип хранилища должен быть simple, weighted или bag.");
        }
    }

    private boolean addToSimpleStorage(CommandSender sender, String storageName, ItemStack item) {
        SimpleStorage storage = plugin.getStorageManager().getSimpleStorage(storageName);
        storage.addItem(item.clone());
        MessageUtils.sendMessage(sender, PREFIX + "Предмет добавлен в простое хранилище '" + storageName + "'");
        return true;
    }

    private boolean addToWeightedStorage(CommandSender sender, String[] args, String storageName, ItemStack item) {
        validateArgsLength(args, 3, "Использование: /dreamdrop add weighted <storage> <weight>");

        int weight = parsePositiveInteger(args[2], "Вес должен быть целым числом больше нуля!");

        WeightedStorage storage = plugin.getStorageManager().getWeightedStorage(storageName);
        storage.addItem(item.clone(), weight);
        MessageUtils.sendMessage(sender, PREFIX + "Предмет добавлен во взвешенное хранилище '" + storageName + "' с весом " + weight);
        return true;
    }

    private boolean addToBagStorage(CommandSender sender, String[] args, String storageName, ItemStack item) {
        validateArgsLength(args, 3, "Использование: /dreamdrop add bag <storage> <weight>");

        int weight = parsePositiveInteger(args[2], "Вес должен быть целым числом больше нуля!");
        List<String> commandList = new ArrayList<>();
        BagStorage storage = plugin.getStorageManager().getBagStorage(storageName);
        if (storage == null) {
            MessageUtils.sendMessage(sender, ERROR_PREFIX + "Хранилища с именем '" + storageName + "' не существует");
            return true;
        }

        storage.addItem(item.clone(), weight, commandList);
        MessageUtils.sendMessage(sender, PREFIX + "Предмет добавлен в хранилище-сумку '" + storageName + "' с весом " + weight);
        return true;
    }

    public ItemStack getItemInHand(Player player) {
        ItemStack item = player.getItemInHand();
        if (item == null || item.getType() == Material.AIR) {
            throw new IllegalArgumentException("Возьмите предмет в руку!");
        }
        return item;
    }

    @Override
    public String getUsage() {
        return "/dreamdrop add <simple|weighted|bag> <storage> [weight]";
    }

    @Override
    public String getDescription() {
        return "Добавить предмет в хранилище";
    }
}