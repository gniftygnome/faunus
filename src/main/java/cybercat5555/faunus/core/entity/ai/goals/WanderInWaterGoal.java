package cybercat5555.faunus.core.entity.ai.goals;

import net.minecraft.block.Blocks;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

public class WanderInWaterGoal extends MoveToTargetPosGoal {
    private static final int tick = 1200;
    private final PathAwareEntity entity;

    public WanderInWaterGoal(PathAwareEntity entity, double speed) {
        super(entity, entity.isBaby() ? 2.0 : speed, 24);
        this.entity = entity;
        this.lowestY = -1;
    }

    @Override
    public boolean shouldContinue() {
        return !this.entity.isTouchingWater() && this.tryingTime <= 1200 && this.isTargetPos(this.entity.getWorld(), this.targetPos);
    }

    @Override
    public boolean canStart() {
        if (this.entity.isBaby() && !this.entity.isTouchingWater()) {
            return super.canStart();
        }

        return !this.entity.isTouchingWater() && super.canStart();
    }

    @Override
    public boolean shouldResetPath() {
        return this.tryingTime % 160 == 0;
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        return world.getBlockState(pos).isOf(Blocks.WATER);
    }
}