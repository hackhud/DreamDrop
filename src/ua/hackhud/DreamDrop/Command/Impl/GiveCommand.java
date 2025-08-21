package ua.hackhud.DreamDrop.Command.Impl;

import ua.hackhud.DreamDrop.Command.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ua.hackhud.DreamDrop.Util.MessageUtils;

public class GiveCommand extends BaseCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!validateSender(sender)) return true;

        validateArgsLength(args, 2, "Использование: " + getUsage());

        Player target = getOnlinePlayer(args[0]);
        String storageName = args[1];

        if (args.length == 3) {
            return giveFromSimpleStorage(sender, target, storageName, args[2]);
        } else {
            return giveFromWeightedStorage(sender, target, storageName);
        }
    }

    private boolean giveFromSimpleStorage(CommandSender sender, Player target, String storageName, String idArg) {
        validateSimpleStorageExists(storageName);

        int itemId = parseInteger(idArg, "Айди должен быть целым числом!");
        ItemStack item = getItemFromSimpleStorage(storageName, itemId);

        giveItemToPlayer(target, item);
        MessageUtils.sendMessage(sender, PREFIX + "Предмет выдан игроку '" + target.getName() + "' из хранилища '" + storageName + "' с айди '" + itemId + "'!");
        return true;
    }

    private boolean giveFromWeightedStorage(CommandSender sender, Player target, String storageName) {
        validateWeightedStorageExists(storageName);

        ItemStack item = getRandomItemFromWeightedStorage(storageName);
        giveItemToPlayer(target, item);
        MessageUtils.sendMessage(sender, PREFIX + "Предмет выдан игроку '" + target.getName() + "' из хранилища '" + storageName + "'");
        return true;
    }

    @Override
    public String getUsage() {
        return "/dreamdrop give <player> <storage> [id]";
    }

    @Override
    public String getDescription() {
        return "Выдать предмет игроку";
    }
}