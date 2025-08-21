package ua.hackhud.DreamDrop.Command.Impl;

import ua.hackhud.DreamDrop.Command.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ua.hackhud.DreamDrop.Util.MessageUtils;

public class GetCommand extends BaseCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!validateSender(sender)) return true;

        validateArgsLength(args, 1, "Использование: " + getUsage());

        Player player = (Player) sender;
        String storageName = args[0];

        if (args.length == 2) {
            return handleSimpleStorage(player, storageName, args[1]);
        } else {
            return handleWeightedStorage(player, storageName);
        }
    }

    private boolean handleSimpleStorage(Player player, String storageName, String idArg) {
        validateSimpleStorageExists(storageName);

        int itemId = parseInteger(idArg, "Айди должен быть целым числом!");
        ItemStack item = getItemFromSimpleStorage(storageName, itemId);

        giveItemToPlayer(player, item);
        MessageUtils.sendMessage(player, PREFIX + "Вы получили предмет из хранилища '" + storageName + "' с айди '" + itemId + "'!");
        return true;
    }

    private boolean handleWeightedStorage(Player player, String storageName) {
        validateWeightedStorageExists(storageName);

        ItemStack item = getRandomItemFromWeightedStorage(storageName);
        giveItemToPlayer(player, item);
        MessageUtils.sendMessage(player, PREFIX + "Вы получили предмет из хранилища '" + storageName + "'");
        return true;
    }

    @Override
    public String getUsage() {
        return "/dreamdrop get <storage> [id]";
    }

    @Override
    public String getDescription() {
        return "Получить предмет из хранилища";
    }
}