package ua.hackhud.DreamDrop.Transformation;

import noppes.npcs.side.server.bukkit.listeners.utils.BukkitItemStack;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Main;

import java.util.ArrayList;
import java.util.List;

public class PerkTransformation extends RPGItemTransformation {

    @Override
    public BukkitItemStack transformRPGItemStack(RPGItemStack itemStack) {
        BukkitItemStack bukkitItem = itemStack.getBukkitItemStack();
        List<String> newLore = buildTransformedLore(itemStack);
        bukkitItem.setLore(newLore);
        return bukkitItem;
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
            } else if (line.trim().isEmpty()) {
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