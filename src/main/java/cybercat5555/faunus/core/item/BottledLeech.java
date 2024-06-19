package cybercat5555.faunus.core.item;

import cybercat5555.faunus.core.EntityRegistry;
import cybercat5555.faunus.core.entity.livingEntity.CrayfishEntity;
import cybercat5555.faunus.core.entity.livingEntity.LeechEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.PotionItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BottledLeech extends Item {
    public BottledLeech(Settings settings) {
        super(settings);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();

        pos = pos.add(0, 1, 0);
        spawnLeech(world, player, pos);

        return ActionResult.SUCCESS;
    }

    private void spawnLeech(World world, PlayerEntity player, BlockPos pos) {
        consumeItem(player);
        LeechEntity leech = new LeechEntity(EntityRegistry.LEECH, world);
        leech.refreshPositionAndAngles(pos, 0.0F, 0.0F);
        world.spawnEntity(leech);
    }

    private void consumeItem(PlayerEntity player) {
        if (!player.isCreative()) {

            // Replace with bottle item
            player.getStackInHand(Hand.MAIN_HAND).decrement(1);
            player.giveItemStack(Items.GLASS_BOTTLE.getDefaultStack());
        }
    }
}
