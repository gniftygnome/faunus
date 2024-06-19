package cybercat5555.faunus.core;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static cybercat5555.faunus.Faunus.LOG;
import static cybercat5555.faunus.Faunus.MODID;


public class PotionRegistry {
    public static final Potion SWAMP_CRAWLER = registerPotion("swamp_crawler", new Potion(
            new StatusEffectInstance(StatusEffects.RESISTANCE, 1200),
            new StatusEffectInstance(StatusEffects.WATER_BREATHING, 1200),
            new StatusEffectInstance(StatusEffects.LUCK, 1200),
            new StatusEffectInstance(StatusEffects.HUNGER, 1200),
            new StatusEffectInstance(StatusEffects.WEAKNESS, 1200)));

    public static final Potion THROWABLE_LEECHING = registerPotion("throwable_leeching", new Potion(
            new StatusEffectInstance(EffectStatusRegistry.STOP_HEALING_EFFECT, 1200)));

    public static final Potion DRINKABLE_LEECHING = registerPotion("drinkable_leeching", new Potion(
            new StatusEffectInstance(EffectStatusRegistry.CLEAR_EFFECTS_EFFECT, 10)));


    public static Potion registerPotion(String name, Potion potion) {
        return Registry.register(Registries.POTION, new Identifier(MODID, name), potion);
    }

    public static void init() {
        LOG.info("Registering potions for " + MODID);
    }
}
