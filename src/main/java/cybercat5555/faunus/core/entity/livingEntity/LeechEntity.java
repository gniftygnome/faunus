package cybercat5555.faunus.core.entity.livingEntity;

import cybercat5555.faunus.core.entity.FeedableEntity;
import cybercat5555.faunus.util.FaunusID;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.util.GeckoLibUtil;

public class LeechEntity extends MobEntity implements GeoEntity, FeedableEntity {

    protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");
    protected static final RawAnimation SUCKING = RawAnimation.begin().thenLoop("sucking");
    protected static final RawAnimation CRAWLING = RawAnimation.begin().thenLoop("crawling");
    protected static final RawAnimation SWIMMING = RawAnimation.begin().thenLoop("swimming");
    protected static final RawAnimation FULL = RawAnimation.begin().thenLoop("full");

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private boolean hasBeenFed = false;

    public LeechEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack handItem = player.getStackInHand(hand);
        feedEntity(player, handItem);

        return super.interactMob(player, hand);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    public void registerControllers(ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "idle", 5, this::idleAnimController));
    }

    protected <E extends LeechEntity> PlayState idleAnimController(final AnimationState<E> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public boolean cannotDespawn() {
        return super.cannotDespawn() || hasBeenFed;
    }

    @Override
    public void feedEntity(PlayerEntity player, ItemStack stack) {
        if (canFedWithItem(stack)) {
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
        return TagKey.of(RegistryKeys.ITEM, FaunusID.content("leech_breeding_items"));
    }
}
