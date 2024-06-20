package cybercat5555.faunus.mixin;

import cybercat5555.faunus.core.entity.livingEntity.CapuchinEntity;
import cybercat5555.faunus.core.entity.livingEntity.LeechEntity;
import cybercat5555.faunus.core.entity.livingEntity.YacareManEaterEntity;
import net.minecraft.entity.Entity;
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

    @Override
    protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
        if (passenger instanceof CapuchinEntity || passenger instanceof LeechEntity) {
            positionUpdater.accept(passenger, this.getX(), this.getY() + this.getHeight() + 0.1D, this.getZ());
        }

        super.updatePassengerPosition(passenger, positionUpdater);
    }
}
