package ua.hackhud.DreamDrop.Util;

import net.minecraft.server.v1_6_R3.NBTTagCompound;
import net.minecraft.server.v1_6_R3.NBTTagList;
import noppes.npcs.side.server.bukkit.listeners.utils.BukkitItemStack;
import ua.hackhud.DreamDrop.Enum.ItemType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemTypeDetector {

    private static final short PROTECTION_ID = 0;
    private static final short SHARPNESS_ID = 16;
    private static final short SMITE_ID = 17;
    private static final short POWER_ID = 48;
    private static final short UNBREAKING_ID = 34;
    private static final short KNOCKBACK_ID = 19;

    public static ItemType getItemType(BukkitItemStack itemStack) {
        if (itemStack == null) return ItemType.OTHER;

        Object nbtObj = itemStack.getNbt();
        Set<Short> enchantIds = extractEnchantmentIds(nbtObj);

        List<String> lore = itemStack.getLore();

        if (enchantIds.contains(PROTECTION_ID)) {
            return ItemType.ARMOR;
        }

        if (!enchantIds.isEmpty() && allShieldEnchantments(enchantIds)) {
            return ItemType.SHIELD;
        }

        if (enchantIds.contains(SHARPNESS_ID) || enchantIds.contains(SMITE_ID) || enchantIds.contains(KNOCKBACK_ID)) {
            return ItemType.WEAPON;
        }

        if (enchantIds.contains(POWER_ID)) {
            return ItemType.BOW;
        }

        if ((lore != null) && enchantIds.isEmpty()) {
            for (String line : lore) {
                if (line.startsWith("{icon178}.png §7Слот для бижутерии:")) {
                    return ItemType.BAUBLES;
                }
            }
        }

        if (lore != null) {
            for (String line : lore) {
                if ("{cash}.png §7Валюта".equals(line)) {
                    return ItemType.CURRENCY;
                }
            }
        }

        return ItemType.OTHER;
    }

    private static Set<Short> extractEnchantmentIds(Object nbtObj) {
        Set<Short> ids = new HashSet<>();
        if (!(nbtObj instanceof NBTTagCompound)) return ids;

        NBTTagCompound compound = (NBTTagCompound) nbtObj;
        if (!compound.hasKey("tag")) return ids;

        NBTTagCompound tag = compound.getCompound("tag");
        if (!tag.hasKey("ench")) return ids;

        NBTTagList enchList = tag.getList("ench");
        for (int i = 0; i < enchList.size(); i++) {
            Object entry = enchList.get(i);
            if (entry instanceof NBTTagCompound) {
                NBTTagCompound ench = (NBTTagCompound) entry;
                ids.add(ench.getShort("id"));
            }
        }

        return ids;
    }

    private static boolean allShieldEnchantments(Set<Short> ids) {
        if (ids.isEmpty()) return false;
        if (ids.size() == 1) {
            return ids.contains(UNBREAKING_ID);
        }
        if (ids.size() == 2) {
            return ids.contains(UNBREAKING_ID) && ids.contains(KNOCKBACK_ID);
        }
        return false;
    }
}
