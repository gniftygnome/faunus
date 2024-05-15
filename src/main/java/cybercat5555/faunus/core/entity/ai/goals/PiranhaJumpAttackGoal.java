package cybercat5555.faunus.core.entity.ai.goals;

import cybercat5555.faunus.core.entity.entityBehaviour.PiranhaEntity;
import cybercat5555.faunus.util.MCUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class PiranhaJumpAttackGoal extends MeleeAttackGoal {

    /**
     * Constructor for the PiranhaJumpAttackGoal.
     * @param piranha The piranha entity.
     * @param speed The speed of the piranha. The higher the speed, the faster the piranha will move.
     * @param pauseWhenMobIdle If true, the piranha will pause when the target is not moving.
     */
    public PiranhaJumpAttackGoal(PiranhaEntity piranha, double speed, boolean pauseWhenMobIdle) {
        super(piranha, speed, pauseWhenMobIdle);
    }

    public void tick() {
        super.tick();

        if (mob.getRandom().nextInt(20) == 0) {
            Entity target = mob.getTarget();
            int distanceToWater = target != null ? (int) (target.getY() - MCUtil.getWorldSurface(target.getWorld(), target.getBlockPos())) : -1;

            if(distanceToWater >= 0 && distanceToWater < 4) {
                this.jumpOutOfWater();
            }
        }
    }

    /**
     * Make the piranha jump out of the water.
     * The piranha will jump out of the water and move towards the target.
     */
    private void jumpOutOfWater() {
        boolean inWater = mob.getWorld().getFluidState(mob.getBlockPos()).isIn(FluidTags.WATER);
        Vec3d velocity = mob.getVelocity();

        if (!inWater) {
            mob.playSound(SoundEvents.ENTITY_DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        if (velocity.y * velocity.y < 0.029999999329447746 && mob.getPitch() != 0.0F) {
            mob.setPitch(MathHelper.lerpAngleDegrees(0.2F, mob.getPitch(), 0.0F));
        } else if (velocity.length() > 9.999999747378752E-6) {
            double d = velocity.horizontalLength();
            double e = Math.atan2(-velocity.y, d) * 57.2957763671875;
            mob.setPitch((float) e);
        }

        mob.getMoveControl().moveTo(mob.getX(), mob.getY() + 0.5D, mob.getZ(), 1.0D);
        mob.setVelocity(velocity.x, 0.4D, velocity.z);
    }

    /**
     * Start executing the goal in case the target is not in water.
     * @return true if the target is not in water, false otherwise.
     */
    @Override
    public boolean canStart() {
        return mob.getTarget() != null && !mob.getTarget().isTouchingWater();
    }

    /**
     * Keep executing the goal in case the target is not in water.
     * @return true if the target is not in water, false otherwise. In case the target is in water, the goal is finished.
     */
    @Override
    public boolean shouldContinue() {
        return mob.getTarget() != null && super.shouldContinue();
    }
}
