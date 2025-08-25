package ua.hackhud.DreamDrop.Listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import ua.hackhud.DreamDrop.Entity.BagItem;
import ua.hackhud.DreamDrop.Entity.Storage.BagStorage;
import ua.hackhud.DreamDrop.Main;

import java.util.List;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Player player = event.getPlayer();

        ItemStack item = getItemInHand(player);
        String bagName = Main.getPlugin().getStorageManager().findBagNameByItem(item);
        if (bagName != null) {
            BagStorage storage = Main.getPlugin().getStorageManager().getBagStorage(bagName);
            handleBagOpening(player, storage);
        }
    }

    private ItemStack getItemInHand(Player player) {
        ItemStack item = player.getItemInHand();
        if (item == null || item.getType() == Material.AIR) {
            throw new IllegalArgumentException("Возьмите предмет в руку!");
        }
        return item;
    }

    private void handleBagOpening(Player player, BagStorage storage) {
        ItemStack handItem = player.getItemInHand();
        if (handItem.getAmount() > 1) {
            handItem.setAmount(handItem.getAmount() - 1);
        } else {
            player.setItemInHand(null);
        }
        player.updateInventory();

        BagItem drop = storage.getRandomItem();
        ItemStack reward = drop.getItem().clone();

        executeCommands(drop.getCommandList(), player, reward);
        executeCommands(storage.getBaseCommands(), player, reward);
        giveRewardToPlayer(player, reward);
    }

    private void executeCommands(List<String> commands, Player player, ItemStack item) {
        for (String command : commands) {
            String formattedCmd = command.replace("%player%", player.getName())
                    .replace("%itemname%", getItemName(item));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formattedCmd);
        }
    }

    private String getItemName(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
            return item.getItemMeta().getDisplayName();
        }
        return item.getType().toString();
    }

    private void giveRewardToPlayer(Player player, ItemStack reward) {
        if (!player.getInventory().addItem(reward).isEmpty()) {
            player.getWorld().dropItemNaturally(player.getLocation(), reward);
        }
        player.updateInventory();
    }

}
