package ua.hackhud.DreamDrop.Command.Impl;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ua.hackhud.DreamDrop.Command.BaseCommand;
import org.bukkit.command.CommandSender;
import ua.hackhud.DreamDrop.Entity.Storage.BagStorage;
import ua.hackhud.DreamDrop.Util.MessageUtils;

public class BagCommand extends BaseCommand {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!validateSender(sender)) return true;

        if (!(sender instanceof Player)) {
            MessageUtils.sendMessage(sender, ERROR_PREFIX + "Этой командой может пользоваться лишь игрок!");
            return true;
        }

        Player player = (Player) sender;
        ItemStack itemInHand = getItemInHand(player);

        String commandType = args[0];
        String bagName = args[1];

        switch (commandType) {
            case "create":
                return createNewBagStorage(sender, args, bagName, itemInHand);
            default:
                throw new IllegalArgumentException("Использование: /dreamdrop bag create <name>");
        }
    }

    public ItemStack getItemInHand(Player player) {
        ItemStack item = player.getItemInHand();
        if (item == null || item.getType() == Material.AIR) {
            throw new IllegalArgumentException("Возьмите предмет в руку!");
        }
        return item;
    }

    private boolean createNewBagStorage(CommandSender sender, String[] args, String bagName, ItemStack bagItemStack) {
        validateArgsLength(args, 2, "Использование: /dreamdrop bag create <name>");

        new BagStorage(bagName, bagItemStack);
        MessageUtils.sendMessage(sender, PREFIX + "Хранилище-сумка с названием '" + bagName + "' была успешно создана!");
        return true;
    }

    @Override
    public String getUsage() {
        return "/dreamdrop bag create <name>";
    }

    @Override
    public String getDescription() {
        return "Создать новое хранилище-сумку";
    }
}