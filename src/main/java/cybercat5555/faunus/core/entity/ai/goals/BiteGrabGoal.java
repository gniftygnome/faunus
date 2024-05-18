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
    public static final float MAX_HUNGER = 100;
    public static final int MAX_GRAB_TIME = 100;
    private float hunger = 0;


    protected MobEntity mob;
    protected LivingEntity target;
    protected boolean isGrabbing = false;
    protected float initHealth;
    protected int timeToGrabAgain = 0;


    public BiteGrabGoal(MobEntity mob) {
        this.mob = mob;
    }


    @Override
    public void tick() {
        if (target == null) return;
        this.mob.getLookControl().lookAt(this.target, 30.0f, 30.0f);
        increaseHunger(0.2f);

        if (isGrabbing) {
            if (shouldDrop(this.target)) {
                this.target.stopRiding();
                this.isGrabbing = false;
            } else {
                float chance = this.mob.getRandom().nextFloat();

                if (chance < 0.1f && chance > 0.025f) {
                    grabDamage(this.target);
                } else if (chance > 0.025f && this.mob.isTouchingWater()) {
                    rollDamage(this.target);
                }
            }
        } else {
            double maxDistance = this.mob.getWidth() * 2.0f * (this.mob.getWidth() * 2.0f);
            double distanceTo = this.mob.squaredDistanceTo(this.target.getX(), this.target.getY(), this.target.getZ());
            double speed = distanceTo > maxDistance && distanceTo < 16.0 ? 1.33 : 0.6;

            this.mob.getNavigation().startMovingTo(this.target, speed);
            timeToGrabAgain++;

            if (distanceTo < 2 && mob.tryAttack(this.target) && timeToGrabAgain > MAX_GRAB_TIME) {
                isGrabbing = tryGrab(this.target);
                timeToGrabAgain = 0;
            }
        }
    }

    private void grabDamage(LivingEntity target) {
        target.damage(this.mob.getDamageSources().generic(), (float) this.mob.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE));

        if (!target.isAlive()) {
            increaseHunger(-(MAX_HUNGER / 10));
        }
    }

    private void rollDamage(LivingEntity target) {
        ((BiteGrabEntity) this.mob).setPerformDeathRoll(true);

        target.damage(this.mob.getDamageSources().generic(), (float) this.mob.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE) * 2.0f);

        if (!target.isAlive()) {
            increaseHunger(-(MAX_HUNGER / 10));
        }
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

        Iterable<BlockPos> iterable = BlockPos.iterate(MathHelper.floor(this.mob.getX() - 2.0), MathHelper.floor(this.mob.getY() - 2.0), MathHelper.floor(this.mob.getZ() - 2.0), MathHelper.floor(this.mob.getX() + 2.0), this.mob.getBlockY(), MathHelper.floor(this.mob.getZ() + 2.0));
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
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity == null || !doesHaveHunger()) {
            increaseHunger(0.2f);
            return false;
        }

        this.target = livingEntity;
        return true;
    }


    @Override
    public boolean shouldContinue() {
        if (target == null || !target.isAlive() || !doesHaveHunger()) {
            return false;
        }

        if (this.mob.squaredDistanceTo(this.target) > 225.0) {
            return false;
        }
        return !this.mob.getNavigation().isIdle() || this.canStart();
    }

    @Override
    public void stop() {
        this.target = null;
        this.mob.getNavigation().stop();
    }


    @Override
    public void increaseHunger(float hunger) {
        this.hunger = Math.max(0, Math.min(MAX_HUNGER, this.hunger + hunger));
    }

    @Override
    public boolean doesHaveHunger() {
        return hunger > (MAX_HUNGER / 2);
    }
}