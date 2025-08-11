package ua.hackhud.DreamDrop.Listener;

import noppes.npcs.EntityNPCInterface;
import noppes.npcs.side.server.bukkit.listeners.NPCDropEvent;
import noppes.npcs.side.server.bukkit.listeners.utils.BukkitItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ua.hackhud.DreamDrop.Main;
import ua.hackhud.DreamDrop.Enum.ItemType;
import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Transformation.PerkTransformation;
import ua.hackhud.DreamDrop.Util.ConvertUtils;
import ua.hackhud.DreamDrop.Util.ItemTypeDetector;

import java.util.List;

public class ItemDropListener implements Listener {

    private static final BukkitItemStack SAPPHIRE = ConvertUtils.asBukkitItemStackMirror(
            Main.getPlugin().getStorageManager().getSimpleStorage("Currency").getItem(0), 100);

    private static final BukkitItemStack RUBINE = ConvertUtils.asBukkitItemStackMirror(
            Main.getPlugin().getStorageManager().getSimpleStorage("Currency").getItem(1), 100);

    @EventHandler
    public void onItemDrop(NPCDropEvent event) {
        final EntityNPCInterface npc = event.getEntityNPCInterface();
        final String title = npc.display.title;
        final int level = getNpcLevel(npc.display.name);
        final List<BukkitItemStack> loot = event.getLoot();

        if (title.contains("Обычный")) {
            addCurrencyDrop(loot, SAPPHIRE, level);
        }

        if (title.contains("Элитный")) {
            addCurrencyDrop(loot, RUBINE, level);
            rollPerksOnLoot(loot);
            //event.setDrop(loot);
        }
    }

    private static int getNpcLevel(String npcName) {
        if (npcName == null || !npcName.startsWith("[") || !npcName.contains("]")) return 0;

        try {
            int end = npcName.indexOf(']');
            return Integer.parseInt(npcName.substring(1, end).trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static void addCurrencyDrop(List<BukkitItemStack> loot, BukkitItemStack item, int level) {
        final int guaranteed = level / 100;
        final int chance = level % 100;

        for (int i = 0; i < guaranteed; i++) {
            loot.add(item);
        }

        if (Math.random() * 100 < chance) {
            loot.add(item);
        }
    }

    private static void rollPerksOnLoot(List<BukkitItemStack> loot) {
        PerkTransformation perkTransformer = new PerkTransformation();

        for (int i = 0; i < loot.size(); i++) {
            BukkitItemStack item = loot.get(i);
            if (item == null) continue;

            ItemType type = ItemTypeDetector.getItemType(item);
            if (type != ItemType.ARMOR) continue;
            if (Math.random() * 100 >= 60) continue;


            RPGItemStack rpgItemStack = new RPGItemStack(item);
            //PerkManager.rollPerks(rpgItemStack);
            loot.set(i, perkTransformer.transformRPGItemStack(rpgItemStack));
        }
    }
}
