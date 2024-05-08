package cybercat5555.faunus.core;

import cybercat5555.faunus.Faunus;
import cybercat5555.faunus.core.effect.LeechingEffect;
import cybercat5555.faunus.core.effect.StinkyEffect;
import cybercat5555.faunus.core.effect.RancidEffect;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static cybercat5555.faunus.Faunus.MODID;

public final class EffectStatusRegistry {
    public static StatusEffect STINKY_EFFECT = new StinkyEffect();
    public static StatusEffect RANCID_EFFECT = new RancidEffect();
    public static StatusEffect LEECHING_EFFECT = new LeechingEffect();


    public static void init() {
        Faunus.LOG.info("Registering effect status for " + MODID);

        Registry.register(Registries.STATUS_EFFECT, new Identifier(MODID, "rancid"), RANCID_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(MODID, "stinky"), STINKY_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(MODID, "leeching"), LEECHING_EFFECT);
    }
}
