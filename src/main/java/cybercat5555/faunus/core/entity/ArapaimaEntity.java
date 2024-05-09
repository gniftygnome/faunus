package cybercat5555.faunus.core.entity;

import cybercat5555.faunus.core.EntityRegistry;
import cybercat5555.faunus.core.ItemRegistry;
import cybercat5555.faunus.core.entity.ai.goals.ArapaimaLoveGoal;
import cybercat5555.faunus.core.entity.ai.goals.RamGoal;
import cybercat5555.faunus.util.FaunusID;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
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

public class ArapaimaEntity extends SchoolingFishEntity implements GeoEntity {
    public static final TagKey<Item> BREED_ITEMS = TagKey.of(RegistryKeys.ITEM, FaunusID.content("arapaima_breeding_items"));
    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation SWIM_ANIM = RawAnimation.begin().thenLoop("swimming");
    protected static final RawAnimation FLOP_ANIM = RawAnimation.begin().thenLoop("flopping");
    protected static final RawAnimation ATTACK_ANIM = RawAnimation.begin().thenLoop("attack");
    private static final int MAX_LOVE_TICKS = 6000;

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private int loveTicks = 0;
    private boolean hasBeenFed = false;

    public ArapaimaEntity(EntityType<? extends SchoolingFishEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        goalSelector.add(1, new RamGoal(this, 2.5d, 0.3f));
        goalSelector.add(2, new EscapeDangerGoal(this, 1.25d));
        goalSelector.add(2, new ArapaimaLoveGoal(this));
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack handItem = player.getStackInHand(hand);
        feedEntity(player, handItem);

        return super.interactMob(player, hand);
    }

    /**
     * Breeds the arapaima with the player if the player is holding the breeding item.
     *
     * @param player   The player that is interacting with the arapaima.
     * @param handItem The item that the player is holding. Must be an arapaima breeding item.
     */
    private void feedEntity(PlayerEntity player, ItemStack handItem) {
        if (isBreedingItem(handItem)) {
            hasBeenFed = true;
            handItem.decrement(1);
            loveTicks = MAX_LOVE_TICKS;

            //TODO: Need to make a custom breed code
        }
    }

    /**
     * Breeds the arapaima with another arapaima entity.
     * @param arapaima The arapaima entity to breed with.
     */
    public void breed(ArapaimaEntity arapaima) {
        if (loveTicks > 0 && arapaima.loveTicks > 0) {
            loveTicks = 0;
            arapaima.loveTicks = 0;
            createChild((ServerWorld) getWorld());
        }
    }

    /**
     * Creates a new arapaima entity in the world.
     * @param world The world in which the entity will be created.
     */
    public void createChild(ServerWorld world) {
        EntityRegistry.ARAPAIMA.create(world);
        //TODO: CREATE EGGS INSTEAD OF SPAWNING A NEW ENTITY
    }

    @Override
    protected void applyDamage(DamageSource source, float amount) {
        if (source.getSource() instanceof ArrowEntity) {
            amount *= 0.5F;
        }

        super.applyDamage(source, amount);
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || hasBeenFed;
    }

    public boolean isInLove() {
        return loveTicks > 0;
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(BREED_ITEMS);
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
}
