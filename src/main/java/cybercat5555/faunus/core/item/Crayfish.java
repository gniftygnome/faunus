package cybercat5555.faunus.core.item;

import cybercat5555.faunus.core.EntityRegistry;
import cybercat5555.faunus.core.entity.livingEntity.CrayfishEntity;
import cybercat5555.faunus.core.entity.livingEntity.variant.CrayfishVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Crayfish extends Item {
    private final CrayfishVariant VARIANT;

    public Crayfish(Settings settings, CrayfishVariant variant) {
        super(settings);
        this.VARIANT = variant;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();

        pos = pos.add(0, 1, 0);
        spawnCrayfish(world, player, pos);

        return ActionResult.SUCCESS;
    }

    private void spawnCrayfish(World world, PlayerEntity player, BlockPos pos) {
        consumeItem(player);
        CrayfishEntity crayfish = new CrayfishEntity(EntityRegistry.CRAYFISH, world);
        crayfish.setVariant(VARIANT);
        crayfish.refreshPositionAndAngles(pos, 0.0F, 0.0F);
        world.spawnEntity(crayfish);
    }

    private void consumeItem(PlayerEntity player) {
        if (!player.isCreative()) {
            player.getStackInHand(Hand.MAIN_HAND).decrement(1);
        }
    }
}
