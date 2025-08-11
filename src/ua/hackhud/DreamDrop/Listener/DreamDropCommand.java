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
            case "give":
                handleGive(sender, args);
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
        MessageUtils.sendMessage(sender, "&7/dreamdrop get <weightedStorage> - Получить случайный предмет из взвешенного хранилища");
        MessageUtils.sendMessage(sender, "&7/dreamdrop get <simpleStorage> <id> - Получить предмет по айди из простого хранилища");
        MessageUtils.sendMessage(sender, "&7/dreamdrop add simple <simpleStorage> - Добавить предмет в простое хранилище");
        MessageUtils.sendMessage(sender, "&7/dreamdrop add weighted <weightedstorage> <weight> - Добавить предмет в взвешенное хранилище");
        MessageUtils.sendMessage(sender, "&7/dreamdrop give <player> <weightedStorage> - Выдать игроку случайный предмет из взвешенного хранилища");
        MessageUtils.sendMessage(sender, "&7/dreamdrop give <player> <simpleStorage> <id> - Выдать игроку предмет по айди из простого хранилища");
    }

    private void handleReload(CommandSender sender) {
        Main.getPlugin().getConfigManager().reload();
        Main.getPlugin().getStorageManager().reload();
        MessageUtils.sendMessage(sender, "&e>> DreamDrop: Плагин успешно перезагружен!");
    }

    private void handleGet(CommandSender sender, String[] args) {
        if (args.length < 2) {
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: &7Использование: /dreamdrop get <weightedStorage>");
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: &7Использование: /dreamdrop get <simpleStorage> <id>");
            return;
        }

        Player player = (Player) sender;
        String storageName = args[1];

        if (args.length == 3) {
            if (!storageManager.isSimpleStorageExists(storageName)) {
                MessageUtils.sendMessage(sender, "&c>> DreamDrop: Обычного хранилища с названием '" + storageName + "' не существует!");
                return;
            }

            try {
                int simpleStorageItemId = Integer.parseInt(args[3]);
                ItemStack item = storageManager.getSimpleStorage(storageName).getItem(simpleStorageItemId);
                if (item == null) {
                    MessageUtils.sendMessage(sender, "&c>> DreamDrop: В хранилище '" + storageName + "' нет предмета с айди '" + simpleStorageItemId + "'!");
                }

                player.getInventory().addItem(item);
                MessageUtils.sendMessage(sender, "&e>> DreamDrop: Вы получили предмет из хранилища '" + storageName + "' с айди '" + simpleStorageItemId + "'!");
            } catch (NumberFormatException e) {
                MessageUtils.sendMessage(sender, "&c>> DreamDrop: &7Айди должен быть целым числом!");
            }
            return;
        }

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
        MessageUtils.sendMessage(sender, "&e>> DreamDrop: Вы получили предмет из хранилища '" + storageName + "'");
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

    private void handleGive(CommandSender sender, String[] args) {
        if (args.length < 3) {
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: &7Использование: /dreamdrop give <player> <weightedStorage>");
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: &7Использование: /dreamdrop give <player> <simpleStorage> <id>");
            return;
        }

        Player target = Main.getPlugin().getServer().getPlayer(args[1]);
        if (target == null || !target.isOnline()) {
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: Игрок '" + args[1] + "' не найден или не онлайн!");
            return;
        }

        String storageName = args[2];

        if (args.length == 4) {
            if (!storageManager.isSimpleStorageExists(storageName)) {
                MessageUtils.sendMessage(sender, "&c>> DreamDrop: Обычного хранилища с названием '" + storageName + "' не существует!");
                return;
            }

            try {
                int simpleStorageItemId = Integer.parseInt(args[3]);
                ItemStack item = storageManager.getSimpleStorage(storageName).getItem(simpleStorageItemId);
                if (item == null) {
                    MessageUtils.sendMessage(sender, "&c>> DreamDrop: В хранилище '" + storageName + "' нет предмета с айди '" + simpleStorageItemId + "'!");
                    return;
                }
                target.getInventory().addItem(item);
                MessageUtils.sendMessage(target, "&e>> DreamDrop: Вы получили предмет из хранилища '" + storageName + "' с айди '" + simpleStorageItemId + "'!");
                MessageUtils.sendMessage(sender, "&e>> DreamDrop: Предмет выдан игроку '" + target.getName() + "' из хранилища '" + storageName + "' с айди '" + simpleStorageItemId + "'!");
            } catch (NumberFormatException e) {
                MessageUtils.sendMessage(sender, "&c>> DreamDrop: &7Айди должен быть целым числом!");
            }
            return;
        }

        if (!storageManager.isWeightedStorageExists(storageName)) {
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: Взвешенного хранилища '" + storageName + "' не найдено!");
            return;
        }

        ItemStack item = storageManager.getWeightedStorage(storageName).getRandomItem();
        if (item == null) {
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: В хранилище '" + storageName + "' нет предметов!");
            return;
        }

        target.getInventory().addItem(item);
        MessageUtils.sendMessage(target, "&e>> DreamDrop: Вы получили предмет из хранилища '" + storageName + "'");
        MessageUtils.sendMessage(sender, "&e>> DreamDrop: Предмет выдан игроку '" + target.getName() + "' из хранилища '" + storageName + "'");
    }

    private void handleSimpleStorageAdd(CommandSender sender, String storageName, ItemStack item) {
        SimpleStorage storage = storageManager.getSimpleStorage(storageName);
        storage.addItem(item.clone());
        MessageUtils.sendMessage(sender, "&a>> DreamDrop: Предмет добавлен в простое хранилище '" + storageName + "'");
    }

    private void handleWeightedStorageAdd(CommandSender sender, String[] args, String storageName, ItemStack item) {
        if (args.length < 4) {
            MessageUtils.sendMessage(sender, "&c>> DreamDrop: &7Использование: /dreamdrop add weighted <storage> <weight>");
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