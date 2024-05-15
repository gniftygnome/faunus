package cybercat5555.faunus.mixin;

import cybercat5555.faunus.core.entity.entityBehaviour.YacareManEaterEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin extends LivingEntity {
    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void stopRiding() {
        if (this.getVehicle() != null && this.getVehicle() instanceof YacareManEaterEntity && this.isSneaking() && this.isAlive()) {
            return;
        }

        super.stopRiding();
    }
}
