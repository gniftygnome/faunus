package cybercat5555.faunus.core;

import cybercat5555.faunus.Faunus;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class SoundRegistry {
    public static final SoundEvent QUETZAL_IDLE = register("entity.quetzal.idle");

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
