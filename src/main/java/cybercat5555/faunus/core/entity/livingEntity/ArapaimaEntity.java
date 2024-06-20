package cybercat5555.faunus.core.entity.livingEntity;

import cybercat5555.faunus.core.EntityRegistry;
import cybercat5555.faunus.core.ItemRegistry;
import cybercat5555.faunus.core.entity.BreedableEntity;
import cybercat5555.faunus.core.entity.FeedableEntity;
import cybercat5555.faunus.core.entity.ai.goals.RamGoal;
import cybercat5555.faunus.util.FaunusID;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
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

public class ArapaimaEntity extends SchoolingFishEntity implements GeoEntity, FeedableEntity, BreedableEntity {
    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation SWIM_ANIM = RawAnimation.begin().thenLoop("swimming");
    protected static final RawAnimation FLOP_ANIM = RawAnimation.begin().thenLoop("flopping");
    protected static final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenLoop("attack");
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    private static final int MAX_LOVE_TICKS = 600;
    private static final int MAX_BREED_COOLDOWN = 2400;

    private int loveTicks;
    private boolean hasBeenFed;
    private int breedCooldown;


    public ArapaimaEntity(EntityType<? extends SchoolingFishEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(1, new RamGoal(this, 4d, 0.3f));
        goalSelector.add(2, new EscapeDangerGoal(this, 1.25d));
    }


    public static DefaultAttributeContainer.Builder createMobAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 24f)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.35f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6f)
                .add(EntityAttributes.GENERIC_ATTACK_KNOCKBACK, 0.3f)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, 1.5f);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack handItem = player.getStackInHand(hand);
        feedEntity(player, handItem);

        return super.interactMob(player, hand);
    }

    @Override
    protected void applyDamage(DamageSource source, float amount) {
        if (source.getSource() instanceof ArrowEntity) {
            amount *= 0.5F;
        }

        super.applyDamage(source, amount);
    }

    @Override
    public ItemStack getBucketItem() {
        return new ItemStack(ItemRegistry.ARAPAIMA_BUCKET);
    }

    @Override
    protected SoundEvent getFlopSound() {
        return SoundEvents.ENTITY_COD_FLOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle", 5, this::idleAnimController));
    }

    protected <E extends ArapaimaEntity> PlayState idleAnimController(final AnimationState<E> state) {
        if (!isSubmergedInWater()) {
            state.setAndContinue(FLOP_ANIM);
        } else if (state.isMoving()) {
            state.setAndContinue(isAttacking() ? ATTACK_ANIM : SWIM_ANIM);
        } else {
            state.setAndContinue(IDLE_ANIM);
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void tick() {
        super.tick();
        breed();
    }

    @Override
    public void feedEntity(PlayerEntity player, ItemStack stack) {
        if (canFedWithItem(stack)) {
            loveTicks += MAX_LOVE_TICKS;
            hasBeenFed = true;

            if(!player.isCreative() && !player.isSpectator()){
                stack.decrement(1);
            }
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
    public TagKey<Item> getBreedingItemsTag() {
        return TagKey.of(RegistryKeys.ITEM, FaunusID.content("arapaima_breeding_items"));
    }

    @Override
    public void breed() {
        if (breedCooldown > 0) {
            breedCooldown--;
        }

        if (loveTicks >= 0 && breedCooldown <= 0) {
            loveTicks--;
            findMate();

            if (isNearMate()) {
                createChild();
                breedCooldown = MAX_BREED_COOLDOWN;
            }
        }
    }

    @Override
    public boolean isInLove() {
        return loveTicks > 0;
    }

    @Override
    public void findMate() {
        ArrayList<ArapaimaEntity> entities = (ArrayList<ArapaimaEntity>) this.getWorld().getEntitiesByClass(
                ArapaimaEntity.class,
                this.getBoundingBox().expand(8.0D),
                entity -> entity != this && entity.loveTicks > 0
        );

        if (!entities.isEmpty() && !this.isNearMate() && this.getNavigation().getCurrentPath() == null) {
            this.getNavigation().startMovingTo(entities.get(0), 1.0D);
        }
    }

    @Override
    public boolean isNearMate() {
        ArrayList<ArapaimaEntity> entities = (ArrayList<ArapaimaEntity>) this.getWorld().getEntitiesByClass(
                ArapaimaEntity.class,
                this.getBoundingBox().expand(2.0D),
                entity -> entity != this && entity.loveTicks > 0
        );

        return entities.size() > 0;
    }

    @Override
    public void createChild() {
        ArapaimaEntity child = EntityRegistry.ARAPAIMA.create(this.getWorld());

        if (child != null) {
            child.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
            this.getWorld().spawnEntity(child);
        }
    }
}
