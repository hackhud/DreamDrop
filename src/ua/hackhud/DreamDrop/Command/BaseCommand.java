package ua.hackhud.DreamDrop.Command;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ua.hackhud.DreamDrop.Main;
import ua.hackhud.DreamDrop.Util.MessageUtils;

import java.util.Map;

public abstract class BaseCommand implements Command {

    protected static final String PREFIX = "&e>> DreamDrop: ";
    protected static final String ERROR_PREFIX = "&c>> DreamDrop: ";

    protected final Main plugin = Main.getPlugin();

    protected boolean validateSender(CommandSender sender) {
        if (!sender.isOp()) {
            MessageUtils.sendMessage(sender, ERROR_PREFIX + "У вас недостаточно прав для использования данной команды!");
            return false;
        }
        return true;
    }

    protected void validateArgsLength(String[] args, int minLength, String errorMessage) {
        if (args.length < minLength) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    protected Player getOnlinePlayer(String playerName) {
        Player player = plugin.getServer().getPlayer(playerName);
        if (player == null || !player.isOnline()) {
            throw new IllegalArgumentException("Игрок '" + playerName + "' не найден или не онлайн!");
        }
        return player;
    }

    protected void validateSimpleStorageExists(String storageName) {
        if (!plugin.getStorageManager().isSimpleStorageExists(storageName)) {
            throw new IllegalArgumentException("Обычного хранилища с названием '" + storageName + "' не существует!");
        }
    }

    protected void validateWeightedStorageExists(String storageName) {
        if (!plugin.getStorageManager().isWeightedStorageExists(storageName)) {
            throw new IllegalArgumentException("Взвешенного хранилища '" + storageName + "' не найдено!");
        }
    }

    protected int parseInteger(String value, String errorMessage) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    protected int parsePositiveInteger(String value, String errorMessage) {
        int number = parseInteger(value, errorMessage);
        if (number <= 0) {
            throw new IllegalArgumentException("Вес должен быть больше нуля!");
        }
        return number;
    }

    protected ItemStack getItemFromSimpleStorage(String storageName, int itemId) {
        ItemStack item = plugin.getStorageManager().getSimpleStorage(storageName).getItem(itemId);
        if (item == null) {
            throw new IllegalArgumentException("В хранилище '" + storageName + "' нет предмета с айди '" + itemId + "'!");
        }
        return item;
    }

    protected ItemStack getRandomItemFromWeightedStorage(String storageName) {
        ItemStack item = plugin.getStorageManager().getWeightedStorage(storageName).getRandomItem();
        if (item == null) {
            throw new IllegalArgumentException("В хранилище '" + storageName + "' нет предметов!");
        }
        return item;
    }

    protected ItemStack getItemInHand(Player player) {
        ItemStack item = player.getItemInHand();
        if (item == null || item.getType() == Material.AIR) {
            throw new IllegalArgumentException("Возьмите предмет в руку!");
        }
        return item;
    }

    protected void giveItemToPlayer(Player player, ItemStack item) {
        Map<Integer, ItemStack> leftOver = player.getInventory().addItem(item);

        if (!leftOver.isEmpty()) {
            for (ItemStack leftoverItem : leftOver.values()) {
                Location dropLocation = player.getLocation();
                player.getWorld().dropItemNaturally(dropLocation, leftoverItem);
            }
        }
    }
}