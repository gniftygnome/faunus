package cybercat5555.faunus.core.entity.ai.goals;

import cybercat5555.faunus.core.entity.livingEntity.CapuchinEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class HangTreeGoal extends WanderAroundGoal {
    private boolean isHanging;
    private BlockPos hangingSpot;

    public HangTreeGoal(PathAwareEntity mob, double speed) {
        super(mob, speed);
    }

    @Override
    public boolean canStart() {
        Vec3d vec3d;
        if (this.mob.hasPassengers() || ((CapuchinEntity) this.mob).isSitting()) {
            return false;
        }
        if (!this.ignoringChance) {
            int startChance = this.mob.getRandom().nextInt(WanderAroundGoal.toGoalTicks(this.chance));

            if (startChance > 10) {
                return false;
            }
        }
        if ((vec3d = this.getWanderTarget()) == null) {
            return false;
        }
        this.targetX = vec3d.x;
        this.targetY = vec3d.y;
        this.targetZ = vec3d.z;
        this.ignoringChance = false;

        return true;
    }

    @Override
    public void tick() {
        if (!isHanging) {
            findStarterHangingSpot();

            if (hangingSpot != null) {
                moveToHangingSpot();
            }
        }

        hangingTreeHandler();
    }

    private void hangingTreeHandler() {
        boolean hasOwner = this.mob instanceof TameableEntity tameable && tameable.getOwner() != null;
        boolean isFollowingOwner = false;

        if (hasOwner) {
            isFollowingOwner = ((TameableEntity) this.mob).getOwner() instanceof PlayerEntity owner && owner.getPos().squaredDistanceTo(this.mob.getPos()) < 16;
        }

        if (!isFollowingOwner && isHangingTree(this.mob.getBlockPos().up())) {
            Vec3d wanderTarget = NoPenaltyTargeting.find(this.mob, 4, 1);

            if (this.mob.getNavigation().getTargetPos() != null) {
                double distanceX = targetX - this.mob.getX();
                double distanceZ = targetZ - this.mob.getZ();
                double distance = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);

                boolean isMovingToTarget = distance > 1D;
                this.mob.setVelocity(this.mob.getVelocity().multiply(0.5, 1.0, 0.5));
                this.mob.setNoGravity(true);
                isHanging = true;

                if (wanderTarget != null && !isMovingToTarget) {
                    wanderTarget = new BlockPos((int) wanderTarget.x, (int) wanderTarget.y, (int) wanderTarget.z).toCenterPos();
                    int startChance = this.mob.getRandom().nextInt(WanderAroundGoal.toGoalTicks(this.chance));
                    boolean shouldWander = startChance < 5;

                    if (shouldWander && isHangingTree(wanderTarget.add(0, 2, 0))) {

                        targetX = wanderTarget.x;
                        targetY = wanderTarget.y + 2;
                        targetZ = wanderTarget.z;
                    }
                } else if (isMovingToTarget) {
                    // Move to wander target with setVelocity
                    double x = distanceX / distance;
                    double z = distanceZ / distance;
                    BlockPos nextPos = new BlockPos((int) (this.mob.getX() + x), (int) this.mob.getY(), (int) (this.mob.getZ() + z));

                    if (isHangingTree(nextPos.up())) {
                        this.mob.setVelocity(x * 0.05, 0.0, z * 0.05);
                        targetX = this.mob.getX();
                        targetY = this.mob.getY();
                        targetZ = this.mob.getZ();
                    }
                }
            }
        } else {
            this.mob.setNoGravity(false);
            isHanging = false;
        }
    }


    private void moveToHangingSpot() {
        if (this.mob.getWorld().getBlockState(hangingSpot).isAir()) {
            isHanging = false;
            hangingSpot = null;
            return;
        }

        if (!isHangingTree(this.mob.getBlockPos().up()) && hangingSpot != null) {
            double verticalDistance = hangingSpot.getY() - this.mob.getY();

            if (verticalDistance > 3) {
                isHanging = false;
                hangingSpot = null;
                return;
            }


            // Move this.entity to treePos
            this.mob.getNavigation().startMovingTo(hangingSpot.getX(), hangingSpot.getY(), hangingSpot.getZ(), 1.0);
            double horizontalDistance = Math.sqrt(hangingSpot.getSquaredDistance(this.mob.getX(), hangingSpot.getY(), this.mob.getZ()));

            if (horizontalDistance <= 1.5 && verticalDistance <= 3 &&
                    mob.isOnGround() && !isHanging) {
                // Jump from ground to tree block
                jumpTo(hangingSpot);
            }
        }
    }

    private void jumpTo(BlockPos hangingSpot) {
        double distanceX = hangingSpot.toCenterPos().getX() - this.mob.getBlockPos().toCenterPos().getX();
        double distanceZ = hangingSpot.toCenterPos().getZ() - this.mob.getBlockPos().toCenterPos().getZ();
        double distance = Math.sqrt(distanceX * distanceX + distanceZ * distanceZ);

        double x = distanceX / distance;
        double z = distanceZ / distance;

        this.mob.setVelocity(x * 0.5, 0.6, z * 0.5);
    }


    private void findStarterHangingSpot() {
        BlockPos pos = mob.getBlockPos();
        BlockPos treePos = hangingSpot != null ? hangingSpot : findNearestTree(pos, 8);

        if (treePos != null) {
            Vec3d treeVec = treePos.toCenterPos();
            boolean hasTwoBlockSpace = this.mob.getWorld().getBlockState(treePos.down(2)).isAir();
            boolean isHigherThanMob = treeVec.y > this.mob.getY();

            if (hasTwoBlockSpace && isHigherThanMob) {
                this.mob.getNavigation().startMovingTo(treeVec.x, treeVec.y, treeVec.z, 1.0);
                hangingSpot = treePos;
            }
        } else {
            hangingSpot = null;
        }
    }

    private BlockPos findNearestTree(BlockPos pos, int radius) {
        for (int xPos = -radius; xPos < radius; xPos++) {
            for (int yPos = -radius; yPos < radius; yPos++) {
                for (int zPos = -radius; zPos < radius; zPos++) {
                    BlockPos blockPos = pos.add(xPos, yPos, zPos);

                    if (this.mob.getWorld().getBlockState(blockPos).getBlock().getTranslationKey().contains("leave")
                            && this.mob.getWorld().getBlockState(blockPos.down()).isAir()) {
                        return blockPos;
                    }
                }
            }
        }

        return null;
    }

    private boolean isHangingTree(BlockPos pos) {
        return this.mob.getWorld().getBlockState(pos).getBlock().getTranslationKey().contains("leave");
    }

    private boolean isHangingTree(Vec3d pos) {
        BlockPos blockPos = new BlockPos((int) pos.x, (int) pos.y, (int) pos.z);

        return this.mob.getWorld().getBlockState(blockPos).getBlock().getTranslationKey().contains("leave");
    }

    @Override
    public void stop() {
        this.mob.getNavigation().stop();
        super.stop();
    }

    @Override
    public boolean shouldContinue() {
        return !((CapuchinEntity) this.mob).isSitting();
    }
}
