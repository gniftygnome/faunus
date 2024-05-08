package cybercat5555.faunus.core;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static cybercat5555.faunus.Faunus.LOG;
import static cybercat5555.faunus.Faunus.MODID;


public class PotionRegistry {
    public static Potion SWAM_CLAWLER_POTION;

    public static Potion registerPotion(String name) {
        return Registry.register(Registries.POTION, new Identifier(MODID, name),
                new Potion(
                        new StatusEffectInstance(StatusEffects.RESISTANCE, 1200),
                        new StatusEffectInstance(StatusEffects.WATER_BREATHING, 1200),
                        new StatusEffectInstance(StatusEffects.LUCK, 1200),
                        new StatusEffectInstance(StatusEffects.HUNGER, 1200),
                        new StatusEffectInstance(StatusEffects.WEAKNESS, 1200)));
    }

    public static void init() {
        LOG.info("Registering potions for " + MODID);

        SWAM_CLAWLER_POTION = registerPotion("swamp_crawler");
    }
}
