package cybercat5555.faunus.core.entity;

import cybercat5555.faunus.core.entity.control.move.FlightWalkMoveControl;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FlyGoal;
import net.minecraft.entity.ai.goal.WanderAroundGoal;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.world.EntityView;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class HoatzinEntity extends ParrotEntity implements GeoEntity {
    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation WALKING_ANIM = RawAnimation.begin().thenLoop("walk");
    protected static final RawAnimation FLY_ANIM = RawAnimation.begin().thenLoop("flight");

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public HoatzinEntity(EntityType<? extends ParrotEntity> entityType, World world) {
        super(entityType, world);
        setStepHeight(1.0f);
        moveControl = new FlightWalkMoveControl(this, 90, false);
        ((FlightWalkMoveControl) moveControl).changeMovementType(FlightWalkMoveControl.MoveType.WALK);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle", 5, this::idleAnimController));
    }

    protected <E extends HoatzinEntity> PlayState idleAnimController(final AnimationState<E> state) {
        if (state.isMoving() && isOnGround()) {
            state.setAndContinue(WALKING_ANIM);
        } else if (!isOnGround()) {
            state.setAndContinue(FLY_ANIM);
        } else if (!state.isMoving() && isOnGround()) {
            state.setAndContinue(IDLE_ANIM);
        }

        return PlayState.CONTINUE;
    }

    @Override
    public EntityView method_48926() {
        return null;
    }
}
