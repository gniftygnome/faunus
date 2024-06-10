package cybercat5555.faunus.core.entity.ai.goals;

import cybercat5555.faunus.core.entity.BiteGrabEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;

public class BiteGrabGoal extends Goal implements HungerMeter {
    public static final int MAX_GRAB_TIME = 100;
    public static final float MAX_HUNGER = 100;
    protected MobEntity mob;
    protected boolean isGrabbing = false;
    protected float initHealth;
    protected int timeToGrabAgain = 0;
    protected int hunger = 0;
    protected int timeToAttackAgain = 0;
    protected boolean targetWasAlive;


    public BiteGrabGoal(MobEntity mob) {
        this.mob = mob;
    }

    @Override
    public void tick() {
        if (mob.getTarget() == null) return;
        this.mob.getLookControl().lookAt(this.mob.getTarget(), 30.0f, 30.0f);

        if (isGrabbing) {
            if (shouldDrop(this.mob.getTarget())) {
                this.mob.getTarget().stopRiding();
                this.isGrabbing = false;
            } else {
                float chance = this.mob.getRandom().nextFloat();

                if (chance < 0.1f && chance > 0.025f) {
                    grabDamage(this.mob.getTarget());
                } else if (chance < 0.005f) {
                    rollDamage(this.mob.getTarget());
                }
            }
        } else {
            double maxDistance = this.mob.getWidth() * 2.0f * (this.mob.getWidth() * 2.0f);
            double distanceTo = this.mob.squaredDistanceTo(this.mob.getTarget().getX(), this.mob.getTarget().getY(), this.mob.getTarget().getZ());
            double speed = distanceTo > maxDistance && distanceTo < 16.0 ? 1.33 : 0.6;
            double attackSpeed = (float)this.mob.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED) * 40.0;
            double attackDistance = this.mob.getWidth() * 6.0f + this.mob.getTarget().getWidth();

            boolean isCloseEnoughToAttack = distanceTo < attackDistance;
            boolean hasPassedEnoughTimeToAttack = timeToAttackAgain > attackSpeed;

            boolean canAttack = isCloseEnoughToAttack && hasPassedEnoughTimeToAttack;

            this.mob.getNavigation().startMovingTo(this.mob.getTarget(), speed);
            timeToGrabAgain++;
            timeToAttackAgain++;

            if (canAttack && mob.tryAttack(this.mob.getTarget()) && timeToGrabAgain > MAX_GRAB_TIME) {
                isGrabbing = tryGrab(this.mob.getTarget());
                timeToGrabAgain = 0;
                timeToAttackAgain = 0;
            }
        }


        if (targetWasAlive && mob.getTarget().isDead()) {
            increaseHunger(-(MAX_HUNGER / 10));
        }
    }

    private void grabDamage(LivingEntity target) {
        target.damage(this.mob.getDamageSources().generic(), (float) this.mob.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));
    }

    private void rollDamage(LivingEntity target) {
        ((BiteGrabEntity) this.mob).setPerformDeathRoll(true);

        target.damage(this.mob.getDamageSources().generic(), (float) this.mob.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) * 2.0f);
    }

    private boolean tryGrab(LivingEntity target) {
        float grabChance = 1f;
        if (this.mob.getRandom().nextFloat() < grabChance) {
            target.startRiding(this.mob, true);
            this.initHealth = this.mob.getHealth();
            findWater();

            ((BiteGrabEntity) this.mob).setGrabbing(true);
            return true;
        }

        return false;
    }

    private void findWater() {
        Vec3i blockPos = null;

        Iterable<BlockPos> iterable = BlockPos.iterate(MathHelper.floor(this.mob.getX() - 20.0), MathHelper.floor(this.mob.getY() - 20.0), MathHelper.floor(this.mob.getZ() - 20.0), MathHelper.floor(this.mob.getX() + 20.0), this.mob.getBlockY(), MathHelper.floor(this.mob.getZ() + 20.0));
        for (BlockPos blockPos2 : iterable) {
            if (!this.mob.getWorld().getFluidState(blockPos2).isIn(FluidTags.WATER)) continue;
            blockPos = blockPos2;
            break;
        }

        if (blockPos != null) {
            this.mob.getMoveControl().moveTo(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0);
        }
    }

    private boolean shouldDrop(LivingEntity target) {
        if (target.isDead() || !this.mob.canSee(target) || this.mob.getHealth() < initHealth * 0.5f) {
            ((BiteGrabEntity) this.mob).setGrabbing(true);

            return true;
        }

        return false;
    }

    @Override
    public boolean canStart() {
        return this.mob.getTarget() != null || this.mob.getLastAttacker() != null;
    }


    @Override
    public boolean shouldContinue() {
        if (mob.getTarget() == null || !mob.getTarget().isAlive() || this.mob.squaredDistanceTo(this.mob.getTarget()) > 225.0) {
            return false;
        }

        return !this.mob.getNavigation().isIdle() || this.canStart();
    }

    @Override
    public void start() {
        if(this.mob.getLastAttacker() != null){
            this.mob.setTarget(this.mob.getLastAttacker());
        }

        this.mob.getNavigation().startMovingTo(this.mob.getTarget(), 3f);

        super.start();
    }

    @Override
    public void stop() {
        this.mob.setTarget(null);
        this.mob.getNavigation().stop();
    }

    @Override
    public void increaseHunger(float hunger) {

    }

    @Override
    public boolean doesHaveHunger() {
        return hunger > MAX_HUNGER / 2;
    }
}