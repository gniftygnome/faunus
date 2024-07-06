package cybercat5555.faunus;

import cybercat5555.faunus.common.EventManager;
import cybercat5555.faunus.common.config.MobSpawningConfig;
import cybercat5555.faunus.common.config.SpawnHandler;
import cybercat5555.faunus.core.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

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

        configHandler();
        eventHandler();
        SpawnHandler.addSpawn();
    }

    public void eventHandler() {
        EventManager.onAttack();
    }

    public void configHandler() {
        Path configPath = FabricLoader.getInstance().getConfigDir();
        MobSpawningConfig.init(configPath);
    }
}