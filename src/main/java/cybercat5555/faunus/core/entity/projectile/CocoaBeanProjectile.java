package cybercat5555.faunus.core.entity.projectile;

import cybercat5555.faunus.core.EntityRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class CocoaBeanProjectile extends ThrownItemEntity {
    private final double damage;

    public CocoaBeanProjectile(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
        this.damage = 1.0D;
    }

    public CocoaBeanProjectile(LivingEntity owner, double damage) {
        super(EntityRegistry.COCOA_BEAN_PROJECTILE, owner, owner.getWorld());
        this.damage = damage;
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (getOwner() != null && entityHitResult.getEntity() instanceof LivingEntity entity) {
            entity.damage(this.getDamageSources().mobAttack((LivingEntity) getOwner()), (float) this.damage);
        }

        this.discard();
    }

    @Override
    protected Item getDefaultItem() {
        return Items.COCOA_BEANS;
    }
}
