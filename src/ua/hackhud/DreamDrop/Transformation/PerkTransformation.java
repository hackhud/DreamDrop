package ua.hackhud.DreamDrop.Transformation;

import noppes.npcs.side.server.bukkit.listeners.utils.BukkitItemStack;
import org.bukkit.inventory.ItemStack;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Enum.ItemType;
import ua.hackhud.DreamDrop.Main;
import ua.hackhud.DreamDrop.Perk.PerkManager;
import ua.hackhud.DreamDrop.Util.ConvertUtils;
import ua.hackhud.DreamDrop.Util.ItemTypeDetector;

import java.util.ArrayList;
import java.util.List;

public class PerkTransformation extends RPGItemTransformation {

    @Override
    public BukkitItemStack transformRPGItemStack(RPGItemStack itemStack) {
        PerkManager.rollPerks(itemStack);
        BukkitItemStack bukkitItem = itemStack.getBukkitItemStack();
        List<String> newLore = buildTransformedLore(itemStack);
        bukkitItem.setLore(newLore);
        return bukkitItem;
    }

    public net.minecraft.item.ItemStack transformRPGItemStackMirror(ItemStack itemStack) {
        BukkitItemStack bukkitItemStack = ConvertUtils.asBukkitItemStackMirror(itemStack, 100);
        if (ItemTypeDetector.getItemType(bukkitItemStack) != ItemType.ARMOR) {
            return ConvertUtils.loadFromBukkitItemStack(bukkitItemStack);
        }
        RPGItemStack rpgItemStack = new RPGItemStack(bukkitItemStack);
        BukkitItemStack bukkitItemStackTransformed = transformRPGItemStack(rpgItemStack);
        return ConvertUtils.loadFromBukkitItemStack(bukkitItemStackTransformed);
    }

    private List<String> buildTransformedLore(RPGItemStack itemStack) {
        List<String> originalLore = itemStack.getBukkitItemStack().getLore();
        List<String> newLore = new ArrayList<>();

        if (originalLore == null || originalLore.isEmpty()) {
            return newLore;
        }

        boolean parsingAttributes = false;
        String attributeStart = Main.getPlugin().getConfigManager().getAttributeStartString();
        String replaceTarget = Main.getPlugin().getConfigManager().getStringToReplace();

        for (String line : originalLore) {
            if (line.startsWith(replaceTarget)) {
                newLore.add(buildPerksLine(itemStack));
                continue;
            }

            if (!parsingAttributes) {
                newLore.add(line);
                if (line.startsWith(attributeStart)) {
                    parsingAttributes = true;
                }
            } else if (line.trim().isEmpty() || line.equals("§7")) {
                appendAttributes(newLore, itemStack);
                newLore.add(line);
                parsingAttributes = false;
            }
        }

        if (parsingAttributes) {
            appendAttributes(newLore, itemStack);
        }

        return newLore;
    }

    private String buildPerksLine(RPGItemStack itemStack) {
        StringBuilder icons = new StringBuilder();
        for (String icon : itemStack.getPerksIcons()) {
            icons.append(" ").append(icon);
        }
        return "{icon2}.png §7Перки:" + icons.toString();
    }

    private void appendAttributes(List<String> loreList, RPGItemStack itemStack) {
        itemStack.getAttributes().forEach((type, value) ->
                loreList.add(value.toString(type))
        );
    }
}