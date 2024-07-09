package cybercat5555.faunus.core;

import cybercat5555.faunus.Faunus;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundRegistry {
    public static final SoundEvent QUETZAL_IDLE_L1 = register("entity.quetzal.idle_long_1");
    public static final SoundEvent QUETZAL_IDLE_L2 = register("entity.quetzal.idle_long_2");
    public static final SoundEvent QUETZAL_IDLE_M1 = register("entity.quetzal.idle_medium_1");
    public static final SoundEvent QUETZAL_IDLE_M2 = register("entity.quetzal.idle_medium_2");
    public static final SoundEvent QUETZAL_IDLE_S1 = register("entity.quetzal.idle_short_1");
    public static final SoundEvent QUETZAL_IDLE_S2 = register("entity.quetzal.idle_short_2");

    public static final SoundEvent CAPUCHIN_ANGRY_1 = register("entity.capuchin.capuchin_angry_1");
    public static final SoundEvent CAPUCHIN_ANGRY_2 = register("entity.capuchin.capuchin_angry_2");
    public static final SoundEvent CAPUCHIN_ANGRY_3 = register("entity.capuchin.capuchin_angry_3");
    public static final SoundEvent CAPUCHIN_HURT_1 = register("entity.capuchin.capuchin_hurt_1");
    public static final SoundEvent CAPUCHIN_HURT_2 = register("entity.capuchin.capuchin_hurt_2");
    public static final SoundEvent CAPUCHIN_HURT_3 = register("entity.capuchin.capuchin_hurt_3");
    public static final SoundEvent CAPUCHIN_IDLE_1 = register("entity.capuchin.capuchin_idle_1");
    public static final SoundEvent CAPUCHIN_IDLE_2 = register("entity.capuchin.capuchin_idle_2");
    public static final SoundEvent CAPUCHIN_TAMED_IDLE_1 = register("entity.capuchin.capuchin_tamed_idle_1");
    public static final SoundEvent CAPUCHIN_TAMED_IDLE_2 = register("entity.capuchin.capuchin_tamed_idle_2");
    public static final SoundEvent CAPUCHIN_TAMED_IDLE_3 = register("entity.capuchin.capuchin_tamed_idle_3");
    public static final SoundEvent CAPUCHIN_TAMED_IDLE_4 = register("entity.capuchin.capuchin_tamed_idle_4");

    public static final SoundEvent TROPICAL_BIRD_AMBIANCE_1 = register("entity.tropical_bird.tropical_bird_ambiance_1");
    public static final SoundEvent TROPICAL_BIRD_AMBIANCE_2 = register("entity.tropical_bird.tropical_bird_ambiance_2");
    public static final SoundEvent TROPICAL_BIRD_AMBIANCE_3 = register("entity.tropical_bird.tropical_bird_ambiance_3");
    public static final SoundEvent TROPICAL_BIRD_AMBIANCE_4 = register("entity.tropical_bird.tropical_bird_ambiance_4");
    public static final SoundEvent TROPICAL_BIRD_AMBIANCE_5 = register("entity.tropical_bird.tropical_bird_ambiance_5");
    public static final SoundEvent TROPICAL_BIRD_AMBIANCE_6 = register("entity.tropical_bird.tropical_bird_ambiance_6");
    public static final SoundEvent TROPICAL_BIRD_AMBIANCE_7 = register("entity.tropical_bird.tropical_bird_ambiance_7");
    public static final SoundEvent TROPICAL_BIRD_AMBIANCE_8 = register("entity.tropical_bird.tropical_bird_ambiance_8");
    public static final SoundEvent TROPICAL_BIRD_HURT_1 = register("entity.tropical_bird.tropical_bird_hurt_1");
    public static final SoundEvent TROPICAL_BIRD_HURT_2 = register("entity.tropical_bird.tropical_bird_hurt_2");


    /**
     * Register a sound event with the given name.
     * @param name The name of the sound event.
     * @return The sound event.
     */
    private static SoundEvent register(String name) {
        Identifier id = new Identifier(Faunus.MODID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void init() {
        Faunus.LOG.info("Registering sounds for " + Faunus.MODID);
    }
}
