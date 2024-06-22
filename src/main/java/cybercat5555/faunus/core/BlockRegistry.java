package cybercat5555.faunus.core;

import cybercat5555.faunus.Faunus;
import cybercat5555.faunus.core.block.YacareEggBlock;
import cybercat5555.faunus.util.FaunusID;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

public class BlockRegistry {
    private BlockRegistry() {
    }


    public static final Block YACARE_EGG = new YacareEggBlock(FabricBlockSettings.copyOf(Blocks.TURTLE_EGG)
            .ticksRandomly()
            .breakInstantly()
            .nonOpaque()
            .sounds(BlockSoundGroup.STONE));

    public static void init() {
        Faunus.LOG.info("Registering blocks for " + Faunus.MODID);

        registerBlock("yacare_egg_block", YACARE_EGG);
    }

    private static void registerBlock(String name, Block block) {
        Registry.register(Registries.BLOCK, FaunusID.content(name), block);
    }


}
