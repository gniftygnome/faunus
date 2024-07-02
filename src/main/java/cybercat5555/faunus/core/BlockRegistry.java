package cybercat5555.faunus.core;

import cybercat5555.faunus.Faunus;
import cybercat5555.faunus.core.block.ArapaimaEggBlock;
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



    public static final Block YACARE_EGG = new ArapaimaEggBlock(FabricBlockSettings.copyOf(Blocks.TURTLE_EGG)
            .ticksRandomly()
            .breakInstantly()
            .nonOpaque()
            .sounds(BlockSoundGroup.STONE));
    public static final Block ARAPAIMA_EGG = new ArapaimaEggBlock(FabricBlockSettings.copyOf(Blocks.GLASS)
            .ticksRandomly()
            .breakInstantly()
            .sounds(BlockSoundGroup.STONE));

    public static void init() {
        Faunus.LOG.info("Registering blocks for " + Faunus.MODID);

        registerBlock("yacare_egg_block", YACARE_EGG);
        registerBlock("arapaima_egg_block", ARAPAIMA_EGG);
    }

    private static void registerBlock(String name, Block block) {
        Registry.register(Registries.BLOCK, FaunusID.content(name), block);
    }


}
