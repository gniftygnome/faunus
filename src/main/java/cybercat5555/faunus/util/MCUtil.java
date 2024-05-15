package cybercat5555.faunus.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

import java.util.List;
import java.util.Optional;

public class MCUtil {

    /**
     * Get the world surface height (WORLD_SURFACE_WG, means that it counts the world as first being generated)  at the given position.
     * @param world The world where the position is located.
     * @param pos Position of the surface we want to check.
     * @return The height of the world surface at the given position.
     */
    public static int getWorldSurface(World world, BlockPos pos) {
        return world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ()) - 1;
    }

    public static boolean isUndeadEntity(Class<?> entityClass) {
        return getUndeadEntityClasses().contains(entityClass);
    }

    public static boolean isBossEntity(Class<?> entityClass) {
        return getBossEntityClasses().contains(entityClass);
    }

    public static List<Class<?>> getUndeadEntityClasses() {
        return List.of(
                SkeletonEntity.class, SkeletonHorseEntity.class,
                ZombieEntity.class, ZombieHorseEntity.class, ZombieVillagerEntity.class,
                HuskEntity.class, DrownedEntity.class,
                ZombifiedPiglinEntity.class);
    }

    public static List<Class<?>> getBossEntityClasses() {
        return List.of(EnderDragonEntity.class, WitherEntity.class, ElderGuardianEntity.class);
    }

    public static Vec3d getRunAwayPos(MobEntity mob, LivingEntity effectEntity, double multiplier) {
        return mob.getPos().subtract(effectEntity.getPos()).normalize().multiply(multiplier).add(mob.getPos());
    }

    public static boolean containsEffect(LivingEntity entity, StatusEffect statusEffect) {
        for(StatusEffectInstance effect : entity.getStatusEffects()) {
            if(effect.getEffectType().equals(statusEffect)) {
                return true;
            }
        }

        return false;
    }


    public static Entity getEntityLookinAt(Entity rayTraceEntity, double distance) {
        double playerRotX = rayTraceEntity.getRotationVector().getX();
        double playerRotY = rayTraceEntity.getRotationVector().getY();
        Vec3d startPos = rayTraceEntity.getEyePos();
        double f2 = Math.cos(-playerRotY * ((float) Math.PI / 180F) - (float) Math.PI);
        double f3 = Math.sin(-playerRotY * ((float) Math.PI / 180F) - (float) Math.PI);
        double f4 = -Math.cos(-playerRotX * ((float) Math.PI / 180F));
        double additionY = Math.sin(-playerRotX * ((float) Math.PI / 180F));
        double additionX = f3 * f4;
        double additionZ = f2 * f4;
        double d0 = distance;
        Vec3d endVec = startPos.add((additionX * d0), (additionY * d0), (additionZ * d0));

        Box startEndBox = new Box(startPos, endVec);
        Entity entity = null;

        for (Entity entity1 : rayTraceEntity.getWorld().getOtherEntities(rayTraceEntity, startEndBox, (val) -> true)) {
            Box aabb = entity1.getBoundingBox().expand(5);
            Optional<Vec3d> optional = aabb.raycast(startPos, endVec);
            if (aabb.contains(startPos)) {
                if (d0 >= 0.0D) {
                    entity = entity1;
                    startPos = optional.orElse(startPos);
                    d0 = 0.0D;
                }
            } else if (optional.isPresent()) {
                Vec3d vec31 = optional.get();
                double d1 = startPos.distanceTo(vec31);
                if (d1 < d0 || d0 == 0.0D) {
                    if (entity1.getRootVehicle() == rayTraceEntity.getRootVehicle()) {
                        if (d0 == 0.0D) {
                            entity = entity1;
                            startPos = vec31;
                        }
                    } else {
                        entity = entity1;
                        startPos = vec31;
                        d0 = d1;
                    }
                }
            }
        }

        return entity;
    }
}
