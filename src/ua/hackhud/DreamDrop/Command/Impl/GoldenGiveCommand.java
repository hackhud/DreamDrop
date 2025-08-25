package ua.hackhud.DreamDrop.Command.Impl;

import noppes.npcs.side.server.bukkit.listeners.utils.Converter;
import org.bukkit.Bukkit;
import ua.hackhud.DreamDrop.Command.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ua.hackhud.DreamDrop.Transformation.GoldTransformation;
import ua.hackhud.DreamDrop.Util.MessageUtils;

public class GoldenGiveCommand extends BaseCommand {

    private final GoldTransformation goldTransformation = new GoldTransformation();

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!validateSender(sender)) return true;

        validateArgsLength(args, 2, "Использование: " + getUsage());

        Player target = getOnlinePlayer(args[0]);
        String storageName = args[1];

        if (args.length == 3) {
            return giveGoldenFromSimpleStorage(sender, target, storageName, args[2]);
        } else {
            return giveGoldenFromWeightedStorage(sender, target, storageName);
        }
    }

    private boolean giveGoldenFromSimpleStorage(CommandSender sender, Player target, String storageName, String idArg) {
        validateSimpleStorageExists(storageName);

        int itemId = parseInteger(idArg, "Айди должен быть целым числом!");
        ItemStack item = getItemFromSimpleStorage(storageName, itemId);
        ItemStack goldenItem = transformToGoldenItem(item);

        giveItemToPlayer(target, goldenItem);
        MessageUtils.sendMessage(sender, PREFIX + "Золотой предмет выдан игроку '"
                + target.getName() + "' из хранилища '" + storageName
                + "' с айди '" + itemId + "'!");
        return true;
    }

    private boolean giveGoldenFromWeightedStorage(CommandSender sender, Player target, String storageName) {
        validateWeightedStorageExists(storageName);

        ItemStack item = getRandomItemFromWeightedStorage(storageName);
        ItemStack goldenItem = transformToGoldenItem(item);

        giveItemToPlayer(target, goldenItem);
        MessageUtils.sendMessage(sender, PREFIX + "Золотой предмет выдан игроку '"
                + target.getName() + "' из хранилища '" + storageName + "'");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "box all " + item.getTypeId() + " 0 #FFA900Золотой_короб &7Игрок " + target.getName() + " получает " + item.getItemMeta().getDisplayName() + "&7!");
        return true;
    }

    private ItemStack transformToGoldenItem(ItemStack item) {
        try {
            return Converter.toBukkitItemStackMirror(goldTransformation.transformRPGItemStackMirror(item));
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при преобразовании предмета в золотой предмет", e);
        }
    }

    @Override
    public String getUsage() {
        return "/dreamdrop goldengive <player> <storage> [id]";
    }

    @Override
    public String getDescription() {
        return "Выдать золотой предмет игроку";
    }
}
