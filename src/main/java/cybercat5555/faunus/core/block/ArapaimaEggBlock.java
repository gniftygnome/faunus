package cybercat5555.faunus.core.block;

import cybercat5555.faunus.core.BlockRegistry;
import cybercat5555.faunus.core.EntityRegistry;
import cybercat5555.faunus.core.ItemRegistry;
import cybercat5555.faunus.core.entity.livingEntity.ArapaimaEntity;
import cybercat5555.faunus.core.entity.livingEntity.YacareEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TurtleEggBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import org.jetbrains.annotations.Nullable;

public class ArapaimaEggBlock extends TurtleEggBlock {

    public ArapaimaEggBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(EGGS, 4).with(HATCH, 0));
    }

    private void tryBreakEgg(World world, BlockState state, BlockPos pos, Entity entity, int inverseChance) {
        if (!this.breaksEgg(world, entity)) {
            return;
        }

        if (!world.isClient && world.random.nextInt(inverseChance) == 0 && state.isOf(BlockRegistry.YACARE_EGG)) {
            this.breakEgg(world, pos, state);
        }
    }

    private void breakEgg(World world, BlockPos pos, BlockState state) {
        world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7f, 0.9f + world.random.nextFloat() * 0.2f);
        world.breakBlock(pos, false);
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());

        if (blockState.isOf(this)) {
            return blockState.with(EGGS, 4);
        }

        return getDefaultState();
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (TurtleEggBlock.isSandBelow(world, pos)) {
            int i = state.get(HATCH);
            if (i < 2) {
                world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_CRACK, SoundCategory.BLOCKS, 0.7f, 0.9f + random.nextFloat() * 0.2f);
                world.setBlockState(pos, state.with(HATCH, i + 1), Block.NOTIFY_LISTENERS);
            } else {
                world.playSound(null, pos, SoundEvents.ENTITY_TURTLE_EGG_HATCH, SoundCategory.BLOCKS, 0.7f, 0.9f + random.nextFloat() * 0.2f);
                world.removeBlock(pos, false);
                world.syncWorldEvent(WorldEvents.BLOCK_BROKEN, pos, Block.getRawIdFromState(state));
                ArapaimaEntity arapaima = EntityRegistry.ARAPAIMA.create(world);
                if (arapaima == null) return;

                arapaima.refreshPositionAndAngles((double) pos.getX() + 0.3 + 0.2, pos.getY(), (double) pos.getZ() + 0.3, 0.0f, 0.0f);
                world.spawnEntity(arapaima);
            }
        }
    }

    private boolean breaksEgg(World world, Entity entity) {
        if (entity instanceof YacareEntity || entity instanceof BatEntity) {
            return false;
        }
        if (entity instanceof LivingEntity) {
            return entity instanceof PlayerEntity || world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING);
        }
        return false;
    }

    @Override
    public Item asItem() {
        return ItemRegistry.ARAPAIMA_EGG;
    }
}
