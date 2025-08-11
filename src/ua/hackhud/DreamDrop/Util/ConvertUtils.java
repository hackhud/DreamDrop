package ua.hackhud.DreamDrop.Util;

import noppes.npcs.side.server.bukkit.listeners.utils.BukkitItemStack;
import noppes.npcs.side.server.bukkit.listeners.utils.Converter;
import org.bukkit.inventory.ItemStack;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;

import java.lang.reflect.Method;

public class ConvertUtils {
    public static Object asNMSCopy(ItemStack original) {
        try {
            Class<?> craftItemStackClass = Class.forName("org.bukkit.craftbukkit.v1_6_R3.inventory.CraftItemStack");
            Method asNMSCopyMethod = craftItemStackClass.getDeclaredMethod("asNMSCopy", ItemStack.class);
            return asNMSCopyMethod.invoke(null, original);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static net.minecraft.item.ItemStack loadFromBukkitItemStack(BukkitItemStack itemStack) {
        return Converter.loadFromBukkitItemStack(itemStack);
    }
    public static BukkitItemStack asBukkitItemStack(net.minecraft.item.ItemStack original, double chance) {
        return Converter.getBukkitItemStack(original, chance);
    }
    public static BukkitItemStack asBukkitItemStackMirror(ItemStack itemStack, double chance) {
        return ConvertUtils.asBukkitItemStack((net.minecraft.item.ItemStack) ConvertUtils.asNMSCopy(itemStack), chance);
    }

    public static RPGItemStack asRPGItemStack(BukkitItemStack itemStack) {
        return new RPGItemStack(itemStack);
    }

    public static RPGItemStack asRPGItemStackMirror(net.minecraft.item.ItemStack itemStack) {
        return new RPGItemStack(asBukkitItemStack(itemStack, 100));
    }
}
