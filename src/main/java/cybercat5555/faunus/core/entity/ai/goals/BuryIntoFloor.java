package cybercat5555.faunus.core.entity.ai.goals;

import net.minecraft.block.Block;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;

public class BuryIntoFloor extends Goal {
    private final PathAwareEntity mob;
    private final Block blockToBuryInto;

    public BuryIntoFloor(PathAwareEntity mob) {
        this.mob = mob;
        this.blockToBuryInto = null;
    }

    public BuryIntoFloor(PathAwareEntity mob, Block blockToBuryInto) {
        this.mob = mob;
        this.blockToBuryInto = blockToBuryInto;
    }


    @Override
    public boolean canStart() {
        return this.mob.isOnGround() && !this.mob.getWorld().getBlockState(this.mob.getBlockPos().down()).isOf(this.blockToBuryInto);
    }

    @Override
    public void start() {

        for(int x = -4; x <= 4; x++) {
            for(int z = -4; z <= 4; z++) {
                for(int y = -1; y <= 1; y++) {
                    BlockPos pos = this.mob.getBlockPos().add(x, y, z);

                    if (this.mob.getWorld().getBlockState(pos).isOf(this.blockToBuryInto)) {
                        this.mob.getNavigation().startMovingTo(pos.toCenterPos().getX(), pos.getY(), pos.toCenterPos().getZ(), 0.5D);

                        break;
                    }
                }
            }
        }
    }

    @Override
    public void tick() {
        boolean isAboveWantedBlock = this.mob.getWorld().getBlockState(this.mob.getBlockPos().down()).isOf(this.blockToBuryInto);

        if (isAboveWantedBlock) {
            this.mob.getNavigation().stop();
        }

        super.tick();
    }
}