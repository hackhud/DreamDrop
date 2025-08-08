package ua.hackhud.DreamDrop.Enum;

import java.util.Arrays;

public enum AttributeType {
    // Основные характеристики
    HEALTH("здоровья", "§c", false),
    HEALTH_PERCENT("здоровья", "§c", true),
    REGEN("регенерации", "§d", false),
    REGEN_PERCENT("регенерации", "§d", true),
    SPEED("к скорости передвижения", "§b", true),
    ARMOR("к дополнительной защите", "§5", false),
    ARMOR_PERCENT("к дополнительной защите", "§5", true),
    DAMAGE("Урона", "§9", false),
    DAMAGE_PERCENT("Урона", "§c", true),
    PVE_ARMOR("к защите от существ", "§5", true),
    PVE_DAMAGE("к урону по существам", "§9", false),
    PVE_DAMAGE_PERCENT("к урону по существам", "§9", true),
    CLEAN_PVE_DAMAGE("к урону по существaм", "§6", false),

    // Критические характеристики
    CRIT_DAMAGE("к критическому урону", "§3", false),
    CRIT_DAMAGE_PERCENT("к критическому урону", "§c", true),
    CRIT_CHANCE("к шансу критического урона", "§3", true),
    MULTIPLE_CRIT_CHANCE("к множественному шансу крит. удара", "§3", true),
    TOTAL_CRIT_CHANCE("к общему шансу критического удара", "§3", true),

    // PVE характеристики
    PVE_CRIT_DAMAGE("к критическому PVE урону", "§e", false),
    PVE_CRIT_DAMAGE_PERCENT("к критическому PVE урону", "§e", true),
    PVE_CRIT_CHANCE("к шансу критического PVE урона", "§e", true),
    PVE_CRIT_MULTIPLE_CHANCE("к множественному PVE шансу крит. удара", "§e", true),
    PVE_CRIT_TOTAL_CHANCE("к общему PVE шансу критического удара", "§e", true),

    // Луки и дальний бой
    BOW_DAMAGE("к силе выстрела", "§9", false),
    BOW_DAMAGE_PERCENT("к силе выстрела", "§9", true),
    UNSTABLE_BOW_POWER("к нестабильной силе выстрела", "§5", true),

    // Особые способности
    EVASION("к шансу уклонения", "§2", true),
    ACCURACY("к точности", "§2", true),
    BLOCK_DAMAGE("к блокировке урона", "§5", true),
    REFLECTION("к отражению урона", "§e", false),
    REFLECTION_CHANCE("к шансу отражения", "§e", true),
    VAMPIRISM("к вампиризму", "§4", true),
    DOUBLE_PVP("к шансу двойного урона (PVP)", "§c", true),
    DOUBLE_PVE("к шансу двойного урона (PVE)", "§6", true),
    NPC_DROP_CHANCE("к дополнительному шансу", "§6", true),

    // MCMMO навыки
    MCMMO("к прокачке всех статистик mcMMO", "§e", true),
    MCMMO_AXE("к прокачке \"Владение топором\"", "§e", true),
    MCMMO_SWORD("к прокачке \"Владение мечем\"", "§e", true),
    MCMMO_ACROBATICS("к прокачке \"Акробатика\"", "§e", true),
    MCMMO_BOW("к прокачке \"Стрельба из лука\"", "§e", true),
    MCMMO_CHANCE_COUNTER_ATTACK("к шансу контр-атаки с меча MCMMO", "§3", true),
    MCMMO_CHANCE_BLEEDING("к шансу кровотечения с меча MCMMO", "§3", true),
    MCMMO_CHANCE_BOW_SHOCK("к шансу шокирующего выстрела с лука MCMMO", "§a", true),
    MCMMO_CHANCE_CRIT("к шансу критического удара с топора MCMMO", "§3", true),

    // Зелья и эффекты
    POTION_MULTIPLE("к длительности положительных зелий", "§c", true),
    POTION_MULTIPLE_NEGATIVE("к длительности негативных зелий", "§c", true),
    POWER_HEAL("к силе положительных зелий", "§2", true),
    INSTANT_DAMAGE_PROTECTION("к защите от моментального урона", "§4", true),
    DAMAGE_HEALTH_NPC("к урону от здоровья существа", "§c", true),
    DAMAGE_HEALTH_NPC_MAX("к урону от максимального здоровья существа", "§c", true),

    // Прочность и ремонт
    ANTI_DMG_CHANCE("к отмене слома предмета", "§6", true),
    TOTAL_ANTI_DMG_CHANCE("к устойчивости комплекта", "§6", true),
    SHIELD_DAMAGE_SPEED("к скорости поломки щита", "§c", true),
    SHIELD_PENETRATION("к пробитию щита", "§4", true),
    ARMOR_DAMAGE("к скорости поломки брони", "§6", true),
    ADDITIONAL_DURABILITY("к дополнительной прочности", "§6", true),
    AUTO_REPAIR("к шансу реконструкции", "§6", true),
    AUTO_REPAIR_TOTAL("к шансу общей реконструкции", "§6", true),

    // Нестабильные характеристики
    UNSTABLE_ACCURACY("к нестабильной точности", "§5", true),
    UNSTABLE_DAMAGE_PVE("к нестабильному урону по сущностям", "§5", true),
    UNSTABLE_DAMAGE_PVP("к нестабильному Урону", "§5", true),
    UNSTABLE_DEFENSE("к нестабильной дополнительной защите", "§5", true),
    UNSTABLE_REGENERATION("к нестабильной регенерации", "§5", true);

    public final String lore;
    public final String color;
    public final boolean isPercent;

    AttributeType(String lore, String color, boolean isPercent) {
        this.lore = lore;
        this.color = color;
        this.isPercent = isPercent;
    }

    public static AttributeType match(String color, String lorePart, boolean isPercent) {
        if (lorePart == null) return null;

        String cleanLore = lorePart.trim();
        return Arrays.stream(values())
                .filter(type -> type.isPercent == isPercent &&
                        cleanLore.equalsIgnoreCase(type.lore))
                .findFirst()
                .orElse(null);
    }
}