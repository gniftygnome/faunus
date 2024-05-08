package cybercat5555.faunus.core.entity.ai.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.Vec3d;

public class RamGoal extends MeleeAttackGoal {
    /**
     * The time the entity will run away after attacking the target.
     */
    private static final int RUN_AWAY_TIME = 20 * 5;
    /**
     * The time the entity has been running away.
     */
    private int runAwayTimer = 0;

    /**
     * If the entity is ramming into the target.
     */
    private boolean ramming = true;
    /**
     * The entity that attacked the entity.
     */
    private LivingEntity attacker;
    /**
     * The speed at which the entity will move. Being 1 means the entity will move at normal speed.
     */
    private final double speed;

    /**
     * The chance that the entity will attack the target. Being 0.1f means 10% chance. So in case of 0 it will never attack, and 1 will always attack.
     */
    private final float attackChance;

    /**
     * Constructor for RamGoal, a goal that makes the entity ram into the target.
     * The entity has a chance to run away after attacking the target.
     *
     * @param entity       The entity that will be performing the goal.
     * @param speed        The speed at which the entity will move. Being 1 means the entity will move at normal speed.
     * @param attackChange The chance that the entity will attack the target. Being 0.1f means 10% chance. So in case of 0 it will never attack, and 1 will always attack.
     */
    public RamGoal(PathAwareEntity entity, double speed, float attackChange) {
        super(entity, speed, false);
        this.speed = speed;
        this.attackChance = attackChange;
    }

    @Override
    public void tick() {
        if (ramming) {
            if (ramEntity(attacker, 1.5d)) {
                ramming = false;
            }
        } else if (runAwayTimer < RUN_AWAY_TIME) {
            moveAway();
            runAwayTimer++;
        } else {
            stop();
        }

        super.tick();
    }

    public boolean ramEntity(LivingEntity target, double distance) {
        if (target != null) {
            mob.getNavigation().startMovingTo(target, speed);

            if (mob.squaredDistanceTo(target) <= distance) {
                attack(target, distance);
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    public void moveAway() {
        Vec3d vec3d = NoPenaltyTargeting.find(this.mob, 5, 4);

        if (vec3d != null) {
            this.mob.getNavigation().startMovingTo(vec3d.x, vec3d.y, vec3d.z, this.speed);
        }
    }

    /**
     * Checks if the goal can start. Starts if the entity has being attacked and a random chance is met.
     *
     * @return True if the goal can start, false otherwise.
     */
    @Override
    public boolean canStart() {
        if (mob.getLastAttacker() != null) {
            float chance = mob.getRandom().nextFloat();
            attacker = mob.getLastAttacker();
            mob.setAttacker(null);
            ramming = chance < attackChance;

            return true;
        }

        return false;
    }

    /**
     * Starts the goal. Sets the target to the last attacker.
     */
    @Override
    public void start() {
        mob.setTarget(mob.getLastAttacker());
    }

    /**
     * Checks if the goal should continue. Continues if the target is not null.
     *
     * @return True if the goal should continue, false otherwise.
     */
    @Override
    public boolean shouldContinue() {
        return attacker != null;
    }

    /**
     * Stops the goal. Sets target to null and stops following player.
     */
    @Override
    public void stop() {
        mob.setTarget(null);
        mob.getNavigation().stop();
        mob.setAttacker(null);
        attacker = null;
    }
}
