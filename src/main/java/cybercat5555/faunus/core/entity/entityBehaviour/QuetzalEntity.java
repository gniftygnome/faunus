package cybercat5555.faunus.core.entity.entityBehaviour;

import cybercat5555.faunus.core.EntityRegistry;
import cybercat5555.faunus.core.SoundRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class QuetzalEntity extends ParrotEntity implements GeoEntity {
    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation IDLE_LOOK_ANIM = RawAnimation.begin().thenPlayXTimes("idle_look_around", 3).thenLoop("idle");
    protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");
    protected static final RawAnimation FLYING_ANIM = RawAnimation.begin().thenLoop("flight");
    protected static final RawAnimation FLYING_UPRIGHT_ANIM = RawAnimation.begin().thenLoop("flight_upright");
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public QuetzalEntity(EntityType<? extends QuetzalEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createQuetzalAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6)
                .add(EntityAttributes.GENERIC_FLYING_SPEED, 0.4f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f);
    }

    @Nullable
    @Override
    public SoundEvent getAmbientSound() {
        return SoundRegistry.QUETZAL_IDLE;
    }

    @Override
    public void playAmbientSound() {
        SoundEvent soundEvent = this.getAmbientSound();

        if (soundEvent != null && this.random.nextInt(100) <= 20) {
            this.playSound(soundEvent, this.getSoundVolume(), this.getSoundPitch());
        }
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle", 10, this::idleAnimController));
    }

    protected <E extends QuetzalEntity> PlayState idleAnimController(final AnimationState<E> state) {
        if (isTouchingWater()) {
            state.setAndContinue(FLYING_UPRIGHT_ANIM);
        } else if (!isOnGround()) {
            state.setAndContinue(getVelocity().getY() > 0.05f ? FLYING_ANIM : FLYING_UPRIGHT_ANIM);
        } else if (state.isMoving() && isOnGround()) {
            state.setAndContinue(WALK_ANIM);
        } else if (!state.isCurrentAnimation(IDLE_LOOK_ANIM)) {
            state.setAndContinue(IDLE_ANIM);
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }


    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity other) {
        return EntityRegistry.QUETZAL.create(world);
    }

    @Override
    public EntityView method_48926() {
        return null;
    }
}
