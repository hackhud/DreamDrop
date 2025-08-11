package ua.hackhud.DreamDrop.Listener;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ua.hackhud.DreamDrop.Config.StorageManager;
import ua.hackhud.DreamDrop.Entity.Storage.SimpleStorage;
import ua.hackhud.DreamDrop.Entity.Storage.WeightedStorage;
import ua.hackhud.DreamDrop.Main;
import ua.hackhud.DreamDrop.Util.MessageUtils;

public class DreamDropCommand implements CommandExecutor {

    private final StorageManager storageManager = Main.getPlugin().getStorageManager();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!validateSender(sender)) {
            return true;
        }

        if (args.length == 0) {
            sendUsage(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "reload":
                handleReload(sender);
                break;
            case "get":
                handleGet(sender, args);
                break;
            case "add":
                handleAdd(sender, args);
                break;
            default:
                MessageUtils.sendMessage(sender, "&c>> DreamDrop: Неизвестная команда.");
        }

        return true;
    }

    private boolean validateSender(CommandSender sender) {
        if (!(sender instanceof Player)) {
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: Только игрок может использовать эту команду.");
            return false;
        }

        if (!sender.isOp()) {
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: У вас недостаточно прав для использования данной команды!");
            return false;
        }

        return true;
    }

    private void sendUsage(CommandSender sender) {
        MessageUtils.sendMessage(sender, "&e>> DreamDrop: &7Доступные команды:");
        MessageUtils.sendMessage(sender, "&7/dreamdrop reload - Перезагрузить конфигурацию");
        MessageUtils.sendMessage(sender, "&7/dreamdrop get <storageName> - Получить случайный предмет");
        MessageUtils.sendMessage(sender, "&7/dreamdrop add simple <storageName> - Добавить предмет в простое хранилище");
        MessageUtils.sendMessage(sender, "&7/dreamdrop add weighted <storageName> <weight> - Добавить предмет в взвешенное хранилище");
    }

    private void handleReload(CommandSender sender) {
        Main.getPlugin().getConfigManager().reload();
        Main.getPlugin().getStorageManager().reload();
        MessageUtils.sendMessage(sender, "&a>> DreamDrop: Плагин успешно перезагружен!");
    }

    private void handleGet(CommandSender sender, String[] args) {
        if (args.length < 2) {
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: &7Использование: /dreamdrop get <storageName>");
            return;
        }

        Player player = (Player) sender;
        String storageName = args[1];

        if (!storageManager.isWeightedStorageExists(storageName)) {
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: Хранилище '" + storageName + "' не найдено!");
            return;
        }

        ItemStack item = storageManager.getWeightedStorage(storageName).getRandomItem();
        if (item == null) {
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: В хранилище '" + storageName + "' нет предметов!");
            return;
        }

        player.getInventory().addItem(item);
        MessageUtils.sendMessage(sender, "&a>> DreamDrop: Вы получили предмет из хранилища '" + storageName + "'");
    }

    private void handleAdd(CommandSender sender, String[] args) {
        if (args.length < 3) {
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: &7Использование: /dreamdrop add <simple|weighted> <storageName> [weight]");
            return;
        }

        Player player = (Player) sender;
        ItemStack itemInHand = player.getItemInHand();

        if (itemInHand == null || itemInHand.getType() == Material.AIR) {
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: &7Возьмите предмет в руку!");
            return;
        }

        String storageType = args[1].toLowerCase();
        String storageName = args[2];

        switch (storageType) {
            case "simple":
                handleSimpleStorageAdd(sender, storageName, itemInHand);
                break;
            case "weighted":
                handleWeightedStorageAdd(sender, args, storageName, itemInHand);
                break;
            default:
                MessageUtils.sendMessage(sender, "&c>> DreamDrop: &7Тип хранилища должен быть simple или weighted.");
        }
    }

    private void handleSimpleStorageAdd(CommandSender sender, String storageName, ItemStack item) {
        SimpleStorage storage = storageManager.getSimpleStorage(storageName);
        storage.addItem(item.clone());
        MessageUtils.sendMessage(sender, "&a>> DreamDrop: Предмет добавлен в простое хранилище '" + storageName + "'");
    }

    private void handleWeightedStorageAdd(CommandSender sender, String[] args, String storageName, ItemStack item) {
        if (args.length < 4) {
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: &7Использование: /dreamdrop add weighted <storageName> <weight>");
            return;
        }

        try {
            int weight = Integer.parseInt(args[3]);
            if (weight <= 0) {
                MessageUtils.sendMessage(sender, "&c>> DreamDrop: &7Вес должен быть больше нуля!");
                return;
            }

            WeightedStorage storage = storageManager.getWeightedStorage(storageName);
            storage.addItem(item.clone(), weight);
            MessageUtils.sendMessage(sender, "&a>> DreamDrop: Предмет добавлен во взвешенное хранилище '" + storageName + "' с весом " + weight);
        } catch (NumberFormatException e) {
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: &7Вес должен быть целым числом!");
        }
    }
}