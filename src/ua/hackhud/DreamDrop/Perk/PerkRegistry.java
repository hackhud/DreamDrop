package ua.hackhud.DreamDrop.Perk;

import ua.hackhud.DreamDrop.Perk.Perks.RarityPerks.*;
import ua.hackhud.DreamDrop.Perk.Perks.SpecialPerks.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerkRegistry {

    private static final List<Perk> REGISTERED = new ArrayList<>();

    public static void register(Perk perk) {
        REGISTERED.add(perk);
    }

    public static List<Perk> getAll() {
        return Collections.unmodifiableList(REGISTERED);
    }

    public static void init() {
        register(new VitalityPerk(10, "HEALTH"));
        register(new UndeadPerk(10, "PVE_DAMAGE"));
        register(new SunderPerk(10, "BOW_DAMAGE"));
        register(new SpeedPerk(10, "SPEED"));
        register(new RegenPerk(10, "REGEN"));
        register(new ParryPerk(10, "EVASION"));
        register(new FortitudePerk(10, "ARMOR"));
        register(new DamagePerk(10, "DAMAGE"));
        register(new CriticalPerk(10, "CRITICAL_DAMAGE"));
        register(new AccuracyPerk(9, "ACCURACY"));
        register(new MasteryPerk(9, "MCMMO"));
        register(new DurabilityPerk(8, "ADDITIONAL_DURABILITY"));
        register(new MultiplicityPerk(8, "MULTIPLE_CHANCE_CRIT"));
        register(new ResistancePerk(8, "TOTAL_ANTI_DAMAGE_CHANCE"));
        register(new UndeadCriticalyPerk(7, "PVE_CRITICAL_DAMAGE"));
        register(new PenetratingHitPerk(7, "SHIELD_PENETRATION"));
        register(new DoubleDamagePerk(7, "DOUBLE_DAMAGE"));
        register(new DevastatingHitPerk(6, "NPC_HEALTH_MAX_DAMAGE"));
        register(new VulnerabilityPerk(5, "PVE_ARMOR-PVE_ARMOR"));
        register(new StabilityPerk(4, "CRIT_CHANCE=100"));
        register(new AbsoluteHitPerk(4, "CRIT_DAMAGE -> CRIT_DAMAGE"));
        register(new IntegrityPerk(3, "PVE_DAMAGE -> CLEAN_PVE_DAMAGE"));
        register(new PowerfulHitPerk(3, "CRIT_DAMAGE*2, CRIT_CHANCE/2"));
        register(new ArmadilloPerk(3, "ARMOR*2, SPEED-10, EVASION-10"));
        register(new AbsorptionPerk(3, "HEALTH+REGEN, REGEN-REGEN"));
        register(new EnergyExchangePerk(3, "ADDITIONAL_DURABILITY-ADDITIONAL_DURABILITY"));
        register(new LuckPerk(5, "NPC_DROP_CHANCE"));
        register(new AcuityPerk(3, "BOW_DAMAGE+CRIT_DAMAGE"));
        register(new EmptinessPerk(3, "HEALTH->DAMAGE"));
        register(new GhostProtectionPerk(3, "ARMOR+->BLOCK_ARMOR /4"));
        register(new ControlPerk(3, "EVASION->ANTI_DAMAGE_TOTAL"));
        register(new BaitPerk(3, "ARMOR-> EVASION+SPEED"));
        register(new TacticlePerk(3, "BOW_DAMAGE*2"));
        register(new ResonancePerk(2, "ALL-ATRIBBUTES ROLLED"));
    }
}
