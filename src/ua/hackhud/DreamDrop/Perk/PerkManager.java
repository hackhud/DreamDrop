package ua.hackhud.DreamDrop.Perk;

import ua.hackhud.DreamDrop.Entity.RPGItemStack;
import ua.hackhud.DreamDrop.Util.RandomUtils;

import java.util.*;
import java.util.stream.Collectors;

public class PerkManager {

    private static final Random RANDOM = new Random();

    public static List<Perk> getApplicablePerks(RPGItemStack item) {
        return PerkRegistry.getAll().stream()
                .filter(perk -> perk.canApplyTo(item))
                .collect(Collectors.toList());
    }

    public static Number getPerkAmount() {
        return RandomUtils.getSkewedRandom(1, 3, 2.0, 1.0);
    }

    public static void rollPerks(RPGItemStack item) {
        int amount = getPerkAmount().intValue();
        List<Perk> selected = new ArrayList<>();
        Set<Perk> used = new HashSet<>();

        for (int i = 0; i < amount; i++) {
            List<Perk> applicable = getApplicablePerks(item).stream()
                    .filter(perk -> !used.contains(perk))
                    .collect(Collectors.toList());

            if (applicable.isEmpty()) break;

            Perk chosen = rollWeightedPerk(applicable);
            chosen.applyTo(item);

            selected.add(chosen);
            used.add(chosen);
        }
    }

    private static Perk rollWeightedPerk(List<Perk> perks) {
        int totalWeight = perks.stream().mapToInt(Perk::getBaseWeight).sum();
        if (totalWeight <= 0) return perks.get(0);

        int roll = RANDOM.nextInt(totalWeight);
        int acc = 0;

        for (Perk perk : perks) {
            acc += perk.getBaseWeight();
            if (roll < acc) return perk;
        }

        return perks.get(0);
    }
}
