package cybercat5555.faunus.core.entity.entityBehaviour;

import cybercat5555.faunus.core.EntityRegistry;
import cybercat5555.faunus.core.entity.BiteGrabEntity;
import cybercat5555.faunus.core.entity.BreedableEntity;
import cybercat5555.faunus.core.entity.FeedableEntity;
import cybercat5555.faunus.core.entity.ai.goals.MeleeHungryGoal;
import cybercat5555.faunus.util.FaunusID;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.ArrayList;

public class YacareEntity extends AnimalEntity implements GeoEntity, FeedableEntity, BreedableEntity {

    protected static final RawAnimation IDLE_LAND_ANIM = RawAnimation.begin().thenLoop("idle_land");
    protected static final RawAnimation IDLE_WATER_ANIM = RawAnimation.begin().thenLoop("idle_water");
    protected static final RawAnimation SWIM_ANIM = RawAnimation.begin().thenLoop("swim");
    protected static final RawAnimation RUSH_WATER_ANIM = RawAnimation.begin().thenLoop("rush_water");
    protected static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");
    protected static final RawAnimation RUSH_LAND_ANIM = RawAnimation.begin().thenLoop("rush_land");
    protected static final RawAnimation DEATH_ROLL_ANIM = RawAnimation.begin().thenLoop("WIP death roll3").thenLoop("idle_land");

    private static final int MAX_LOVE_TICKS = 600;
    private int loveTicks;
    private boolean hasBeenFed;


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
        goalSelector.add(1, new MeleeHungryGoal(this,  isSubmergedInWater() ? 3 : 1.35f, false));
        goalSelector.add(2, new LookAroundGoal(this));
        goalSelector.add(3, new WanderAroundGoal(this, 0.7D));
        targetSelector.add(1, new RevengeGoal(this));
        targetSelector.add(2, new ActiveTargetGoal<>(this, LivingEntity.class, true,
                target -> target instanceof WaterCreatureEntity && target.getBoundingBox().getAverageSideLength() < getBoundingBox().getAverageSideLength()));
    }

    @Override
    public void tick() {
        setAir(getMaxAir());
        breed();

        super.tick();
    }

    @Override
    public boolean onKilledOther(ServerWorld world, LivingEntity other) {
        if(other instanceof PlayerEntity) {
            this.turnIntoManEater();
        }

        return super.onKilledOther(world, other);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack handItem = player.getStackInHand(hand);
        feedEntity(handItem);

        return super.interactMob(player, hand);
    }


    public void turnIntoManEater() {
        YacareManEaterEntity manEater = EntityRegistry.YACARE_MANEATER.create(this.getWorld());

        if(manEater != null) {
            manEater.updatePositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
            this.getWorld().spawnEntity(manEater);
            this.remove(RemovalReason.DISCARDED);
        }
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

    @Override
    public void breed() {
        if (loveTicks >= 0) {
            loveTicks--;
            findMate();

            if (isNearMate()) {
                createChild();
            }
        }
    }

    @Override
    public boolean isInLove() {
        return loveTicks > 0;
    }

    @Override
    public void findMate() {
        ArrayList<YacareEntity> entities = (ArrayList<YacareEntity>) this.getWorld().getEntitiesByClass(
                YacareEntity.class,
                this.getBoundingBox().expand(8.0D),
                entity -> entity != this && entity.isInLove()
        );

        if (!entities.isEmpty() && !this.isNearMate() && this.getNavigation().getCurrentPath() == null) {
            this.getNavigation().startMovingTo(entities.get(0), 1.0D);
        }
    }

    @Override
    public boolean isNearMate() {
        ArrayList<YacareEntity> entities = (ArrayList<YacareEntity>) this.getWorld().getEntitiesByClass(
                YacareEntity.class,
                this.getBoundingBox().expand(2.0D),
                entity -> entity != this && entity.isInLove()
        );

        return entities.size() > 0;
    }


    @Override
    public void createChild() {
        // TODO: Spawn egg
    }

    @Override
    public void feedEntity(ItemStack stack) {
        if (canFedWithItem(stack)) {
            breed();
            hasBeenFed = true;
            stack.decrement(1);
        }
    }

    @Override
    public boolean canFedWithItem(ItemStack stack) {
        return stack.isIn(getBreedingItemsTag());
    }

    @Override
    public boolean hasBeenFed() {
        return hasBeenFed;
    }


    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(getBreedingItemsTag());
    }

    @Override
    public TagKey<Item> getBreedingItemsTag() {
        return TagKey.of(RegistryKeys.ITEM, FaunusID.content("yacare_breeding_items"));
    }
}
