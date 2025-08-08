package ua.hackhud.DreamDrop.Entity;

import noppes.npcs.side.server.bukkit.listeners.utils.BukkitItemStack;
import ua.hackhud.DreamDrop.Config.ConfigManager;
import ua.hackhud.DreamDrop.Enum.AttributeType;
import ua.hackhud.DreamDrop.Enum.ItemType;
import ua.hackhud.DreamDrop.Main;
import ua.hackhud.DreamDrop.Util.ItemTypeDetector;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RPGItemStack {
    private final BukkitItemStack bukkitItemStack;
    private final ItemType itemType;
    private final Map<AttributeType, AttributeValue> attributes = new LinkedHashMap<>();
    private final List<String> perksIcons = new ArrayList<>();

    public RPGItemStack(BukkitItemStack itemStack) {
        this.bukkitItemStack = itemStack;
        this.itemType = ItemTypeDetector.getItemType(itemStack);
        parseAttributes(itemStack.getLore());
    }

    public BukkitItemStack getBukkitItemStack() {
        return bukkitItemStack;
    }

    public Map<AttributeType, AttributeValue> getAttributes() {
        return attributes;
    }

    public List<String> getPerksIcons() {
        return perksIcons;
    }

    public Rarity getRarity() {
        return extractRarity(this.bukkitItemStack.getLore());
    }

    public double get(AttributeType type) {
        return attributes.getOrDefault(type, new AttributeValue(0, type.isPercent)).getValue();
    }

    public void set(AttributeType type, double value) {
        attributes.put(type, new AttributeValue(value, type.isPercent));
    }

    public void remove(AttributeType type) {
        attributes.remove(type);
    }

    public boolean hasAttribute(AttributeType type) {
        return attributes.containsKey(type);
    }

    public void multiplyPercent(AttributeType type, double percent) {
        AttributeValue value = attributes.get(type);
        if (value == null) return;
        double factor = 1.0 + (percent / 100.0);
        value.multiply(factor);
    }

    public void multiplyPercentAll(double percent) {
        double factor = 1.0 + (percent / 100.0);
        attributes.values().forEach(attr -> attr.multiply(factor));
    }

    public void replaceAttribute(AttributeType oldType, AttributeType newType, double newValue) {
        if (!attributes.containsKey(oldType) || oldType.equals(newType)) return;

        Map<AttributeType, AttributeValue> replaced = new LinkedHashMap<>();
        AttributeValue newAttr = new AttributeValue(newValue, newType.isPercent);

        attributes.forEach((type, value) ->
                replaced.put(type.equals(oldType) ? newType : type, type.equals(oldType) ? newAttr : value)
        );

        attributes.clear();
        attributes.putAll(replaced);
    }

    public void addIcon(String icon) {
        perksIcons.add(icon);
    }

    private Rarity extractRarity(List<String> loreLines) {
        if (loreLines == null || loreLines.isEmpty()) return null;

        ConfigManager config = Main.getPlugin().getConfigManager();
        return loreLines.stream()
                .filter(Objects::nonNull)
                .map(line -> getMatchingRarity(line, config.getRarities().values()))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    private void parseAttributes(List<String> lores) {
        if (lores == null || lores.isEmpty()) return;

        Pattern pattern = Main.getPlugin().getConfigManager().getAttributePattern();

        for (String line : lores) {
            if (line == null || line.isEmpty() || !line.startsWith("§")) continue;

            Matcher matcher = pattern.matcher(line);
            if (!matcher.matches()) continue;

            extractAttributeFromLine(matcher, line);
        }
    }

    private Rarity getMatchingRarity(String line, Collection<Rarity> rarities) {
        return rarities.stream()
                .filter(rarity -> line.equals(rarity.getRarityLore()))
                .findFirst()
                .orElse(null);
    }

    private void extractAttributeFromLine(Matcher matcher, String originalLine) {
        try {
            String color = matcher.group(1);
            String sign = matcher.group(2);
            double value = Double.parseDouble(matcher.group(3));
            boolean isPercent = !matcher.group(4).isEmpty();
            String lorePart = matcher.group(5);

            if ("-".equals(sign)) value *= -1;

            AttributeType type = AttributeType.match(color, lorePart, isPercent);
            if (type != null) {
                attributes.put(type, new AttributeValue(value, isPercent));
            }
        } catch (Exception e) {
            System.err.println("Failed to parse attribute from lore: " + originalLine);
            e.printStackTrace();
        }
    }
}