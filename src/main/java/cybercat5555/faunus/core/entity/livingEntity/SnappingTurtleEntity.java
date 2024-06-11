package cybercat5555.faunus.core.entity.livingEntity;

import cybercat5555.faunus.core.entity.ai.goals.BuryIntoFloor;
import cybercat5555.faunus.core.entity.ai.goals.TerritorialSelectorGoal;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class SnappingTurtleEntity extends PathAwareEntity implements GeoEntity {
    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public SnappingTurtleEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }


    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 35f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 1f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.1f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1f);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    protected void initGoals() {
        goalSelector.add(0, new SnappingTurtleAttack(this, 0.5D, false));
        goalSelector.add(1, new BuryIntoFloor(this, Blocks.MUD));
        goalSelector.add(1, new SwimGoal(this));
        goalSelector.add(3, new WanderAroundGoal(this, 0.5D));
        targetSelector.add(0, new RevengeGoal(this));
        targetSelector.add(1, new TerritorialSelectorGoal<>(this, LivingEntity.class, true, false,
                target -> (this.squaredDistanceTo(target) < 2.0D && this.isBuried())));
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle", 5, this::idleAnimController));
    }

    protected <E extends SnappingTurtleEntity> PlayState idleAnimController(final AnimationState<E> event) {
        return PlayState.CONTINUE;
    }

    public boolean isBuried() {
        return isOnGround() && !getWorld().getBlockState(getBlockPos().down()).isOf(Blocks.MUD);
    }

    static class SnappingTurtleAttack extends MeleeAttackGoal {
        private static final int ATTACK_COOLDOWN = 20;
        private int attackCooldown;

        public SnappingTurtleAttack(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
        }

        @Override
        protected void attack(LivingEntity target, double squaredDistance) {
            if (this.mob instanceof SnappingTurtleEntity && ((SnappingTurtleEntity) this.mob).isBuried() &&
                this.attackCooldown-- <= 0 && squaredDistance < 2.0D) {
                this.mob.tryAttack(target);
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 60, 2));

                this.attackCooldown = ATTACK_COOLDOWN;
            }
        }
    }
}
