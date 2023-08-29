package cybercat5555.faunus.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.SchoolingFishEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class ArapaimaEntity extends SchoolingFishEntity implements GeoEntity
{
		protected static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");

	private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

	public ArapaimaEntity(EntityType<? extends SchoolingFishEntity> entityType, World world)
	{
		super(entityType, world);
	}

	@Override
	public ItemStack getBucketItem()
	{
		return new ItemStack(Items.SALMON);
	}

	@Override
	protected SoundEvent getFlopSound()
	{
		return SoundEvents.ENTITY_CHICKEN_HURT; // again, why not?
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache()
	{
		return geoCache;
	}

	@Override
	public void registerControllers(ControllerRegistrar controllers)
	{
		controllers.add(new AnimationController<>(this, "idle", 5, this::idleAnimController));
	}
	
	protected <E extends ArapaimaEntity> PlayState idleAnimController(final AnimationState<E> event)
	{
		return PlayState.CONTINUE;
	}
}
