package cybercat5555.faunus;

import cybercat5555.faunus.common.EventManager;
import cybercat5555.faunus.core.*;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Faunus implements ModInitializer {
    public static final String MODID = "faunus";
    public static final Logger LOG = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        EntityRegistry.init();
        ItemRegistry.init();
        BlockRegistry.init();
        SoundRegistry.init();
        EffectStatusRegistry.init();
        PotionRegistry.init();

        eventHandler();
    }

    public void eventHandler() {
        EventManager.onAttack();
        EventManager.onServerStart();
    }
}