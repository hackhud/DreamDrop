package ua.hackhud.DreamDrop.Command.Impl;

import noppes.npcs.side.server.bukkit.listeners.utils.Converter;
import ua.hackhud.DreamDrop.Command.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ua.hackhud.DreamDrop.Transformation.PerkTransformation;
import ua.hackhud.DreamDrop.Util.MessageUtils;

public class PerkedGiveCommand extends BaseCommand {

    private final PerkTransformation perkTransformation = new PerkTransformation();

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!validateSender(sender)) return true;

        validateArgsLength(args, 2, "Использование: " + getUsage());

        Player target = getOnlinePlayer(args[0]);
        String storageName = args[1];

        if (args.length == 3) {
            return givePerkedFromSimpleStorage(sender, target, storageName, args[2]);
        } else {
            return givePerkedFromWeightedStorage(sender, target, storageName);
        }
    }

    private boolean givePerkedFromSimpleStorage(CommandSender sender, Player target, String storageName, String idArg) {
        validateSimpleStorageExists(storageName);

        int itemId = parseInteger(idArg, "Айди должен быть целым числом!");
        ItemStack item = getItemFromSimpleStorage(storageName, itemId);
        ItemStack perkedItem = transformToPerkedItem(item);

        giveItemToPlayer(target, perkedItem);
        MessageUtils.sendMessage(sender, PREFIX + "Перк-предмет выдан игроку '" + target.getName() + "' из хранилища '" + storageName + "' с айди '" + itemId + "'!");
        return true;
    }

    private boolean givePerkedFromWeightedStorage(CommandSender sender, Player target, String storageName) {
        validateWeightedStorageExists(storageName);

        ItemStack item = getRandomItemFromWeightedStorage(storageName);
        ItemStack perkedItem = transformToPerkedItem(item);

        giveItemToPlayer(target, perkedItem);
        MessageUtils.sendMessage(sender, PREFIX + "Перк-предмет выдан игроку '" + target.getName() + "' из хранилища '" + storageName + "'");
        return true;
    }

    private ItemStack transformToPerkedItem(ItemStack item) {
        try {
            return Converter.toBukkitItemStackMirror(perkTransformation.transformRPGItemStackMirror(item));
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при преобразовании предмета в перк-предмет", e);
        }
    }

    @Override
    public String getUsage() {
        return "/dreamdrop perkedgive <player> <storage> [id]";
    }

    @Override
    public String getDescription() {
        return "Выдать перк-предмет игроку";
    }
}