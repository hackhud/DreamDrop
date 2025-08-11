package ua.hackhud.DreamDrop.Transformation;

import noppes.npcs.side.server.bukkit.listeners.utils.BukkitItemStack;
import org.bukkit.inventory.ItemStack;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Main;
import ua.hackhud.DreamDrop.Util.ConvertUtils;
import ua.hackhud.DreamDrop.Util.GradientUtil;

import java.util.ArrayList;
import java.util.List;

public class GoldTransformation extends RPGItemTransformation {

    private static final String ATTRIBUTE_PREFIX = Main.getPlugin().getConfigManager().getAttributeStartString();

    private static final String QUALITY_LINE_PREFIX = "{icon158}.png §7Качество предмета:";
    private static final String FORMATTED_QUALITY_LINE =
            "{icon158}.png §7Качество предмета: #FFA900З#FFB418о#FFBE31л#FFC949о#FFD461т#FFDE7Aо#FFE992е";

    private static final String REPAIR_LINE_PREFIX = "{icon19}.png§7 Ремонт с помощью:";
    private static final String FORMATTED_REPAIR_LINE =
            "{icon19}.png§7 Ремонт с помощью: #FFC949Золотой слиток §7(8 шт.)";

    private static final String COLOR_START = "#FFA900";
    private static final String COLOR_END = "#FFE992";

    @Override
    public BukkitItemStack transformRPGItemStack(RPGItemStack itemStack) {
        itemStack.multiplyPercentAll(50);

        BukkitItemStack bukkitItem = itemStack.getBukkitItemStack();
        String strippedName = GradientUtil.stripHexColors(bukkitItem.getName());
        String coloredName = GradientUtil.setHexName(COLOR_START, COLOR_END, strippedName);
        bukkitItem.setName(coloredName);

        bukkitItem.setLore(buildTransformedLore(itemStack));
        return bukkitItem;
    }

    public net.minecraft.item.ItemStack transformRPGItemStackMirror(ItemStack itemStack) {
        BukkitItemStack bukkitItemStack = ConvertUtils.asBukkitItemStackMirror(itemStack, 100);
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

        for (String line : originalLore) {
            if (line.startsWith(QUALITY_LINE_PREFIX)) {
                newLore.add(FORMATTED_QUALITY_LINE);
                continue;
            }

            if (line.startsWith(REPAIR_LINE_PREFIX)) {
                newLore.add(FORMATTED_REPAIR_LINE);
                continue;
            }

            if (!parsingAttributes) {
                newLore.add(line);
                if (line.startsWith(ATTRIBUTE_PREFIX)) {
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

    private void appendAttributes(List<String> loreList, RPGItemStack itemStack) {
        itemStack.getAttributes().forEach((type, value) ->
                loreList.add(value.toString(type))
        );
    }
}
