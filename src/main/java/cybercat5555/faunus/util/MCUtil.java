package cybercat5555.faunus.util;

import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockStateRaycastContext;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.List;
import java.util.Optional;

public class MCUtil {

    /**
     * Get the world surface height (WORLD_SURFACE_WG, means that it counts the world as first being generated)  at the given position.
     *
     * @param world The world where the position is located.
     * @param pos   Position of the surface we want to check.
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
        for (StatusEffectInstance effect : entity.getStatusEffects()) {
            if (effect.getEffectType().equals(statusEffect)) {
                return true;
            }
        }

        return false;
    }

    public static boolean canSpawnInBiome(String biomeTag, BiomeSelectionContext ctx) {
        if(biomeTag == null) return true;

        TagKey<Biome> biomeTagKey = MCUtil.biomeTagKeyOf(biomeTag);
        boolean hasTag = ctx.hasTag(biomeTagKey);
        boolean isBiome = ctx.getBiomeKey().getValue().getPath().contains(biomeTag);

        return hasTag || isBiome;
    }

    public static TagKey<Biome> biomeTagKeyOf(String id) {
        return id != null ? TagKey.of(RegistryKeys.BIOME, new Identifier(id)) : null;
    }
}
