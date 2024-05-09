package cybercat5555.faunus.core.entity;

import cybercat5555.faunus.core.EntityRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.control.AquaticMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class YacareEntity extends AnimalEntity implements GeoEntity {
    protected static final RawAnimation IDLE_LAND_ANIM = RawAnimation.begin().thenLoop("idle_land");
    protected static final RawAnimation IDLE_WATER_ANIM = RawAnimation.begin().thenLoop("idle_water");
    protected static final RawAnimation SWIM_ANIM = RawAnimation.begin().thenLoop("swim");
    protected static final RawAnimation RUSH_WATER_ANIM = RawAnimation.begin().thenLoop("rush_water");
    protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");
    protected static final RawAnimation RUSH_LAND_ANIM = RawAnimation.begin().thenLoop("rush_land");
    protected static final RawAnimation DEATH_ANIM = RawAnimation.begin().thenLoop("WIP death roll3");


    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public YacareEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new AquaticMoveControl(this, 85, 10, 2F, 1F, true);
    }

    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity other) {
        return EntityRegistry.YACARE.create(world);
    }

    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 24f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.3f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1f);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(1, new MeleeAttackGoal(this,  isSubmergedInWater() ? 3 : 1.35f, false));
        goalSelector.add(2, new LookAroundGoal(this));
        goalSelector.add(3, new WanderAroundGoal(this, 0.7D));
        targetSelector.add(1, new RevengeGoal(this));
        targetSelector.add(2, new ActiveTargetGoal<>(this, LivingEntity.class, true,
                target -> target instanceof WaterCreatureEntity && target.getBoundingBox().getAverageSideLength() < getBoundingBox().getAverageSideLength()));
    }

    @Override
    public void tick() {
        setAir(getMaxAir());

        super.tick();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle", 5, this::idleAnimController));
    }

    protected <E extends YacareEntity> PlayState idleAnimController(final AnimationState<E> event) {
        boolean isAttacking = isAttacking();
        boolean isMoving = event.isMoving();

        if (isSubmergedInWater()) {
            event.setAndContinue(isMoving && isAttacking ? RUSH_WATER_ANIM : isMoving ? SWIM_ANIM : IDLE_WATER_ANIM);
        } else {
            event.setAndContinue(isMoving && isAttacking ? RUSH_LAND_ANIM : isMoving ? WALK_ANIM : IDLE_LAND_ANIM);
        }

        return PlayState.CONTINUE;
    }
}
