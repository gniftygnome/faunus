package cybercat5555.faunus.core.entity.ai.goals;

import cybercat5555.faunus.core.entity.livingEntity.PiranhaEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

import static net.minecraft.util.Util.getRandom;

public class PiranhaAttackGoal extends MeleeAttackGoal {

    /**
     * Constructor for the PiranhaAttackGoal.
     * @param piranha The piranha entity.
     * @param speed The speed of the piranha. The higher the speed, the faster the piranha will move.
     * @param pauseWhenMobIdle If true, the piranha will pause when the target is not moving.
     */
    public PiranhaAttackGoal(PiranhaEntity piranha, double speed, boolean pauseWhenMobIdle) {
        super(piranha, speed, pauseWhenMobIdle);
    }

    /**
     * Start executing the goal in case the target is in water.
     * @return true if the target is in water, false otherwise.
     */
    @Override
    public boolean canStart() {
        return mob.getTarget() != null && mob.getTarget().isTouchingWater() && super.canStart();
    }

    /**
     * Keep executing the goal in case the target is in water.
     * @return true if the target is in water, false otherwise. In case the target is not in water, the goal is finished.
     */
    @Override
    public boolean shouldContinue() {
        return mob.getTarget() != null && mob.getTarget().isTouchingWater() && super.shouldContinue();
    }
}
