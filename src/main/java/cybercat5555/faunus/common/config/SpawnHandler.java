package cybercat5555.faunus.common.config;

import cybercat5555.faunus.core.EntityRegistry;
import cybercat5555.faunus.core.entity.livingEntity.*;
import cybercat5555.faunus.util.MCUtil;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;

import java.util.function.Predicate;

import static cybercat5555.faunus.common.config.MobSpawningConfig.*;

public class SpawnHandler {

    public static void removeSpawn() {
        /* PIG */
        BiomeModifications.create(new Identifier("remove_pig_spawn"))
                .add(
                        ModificationPhase.REMOVALS,
                        BiomeSelectors.tag(BiomeTags.IS_JUNGLE),
                        context -> context.getSpawnSettings().removeSpawns((spawnGroup, spawnEntry) -> spawnEntry.type == EntityType.PIG));

        /* COW */
        BiomeModifications.create(new Identifier("remove_cow_spawn"))
                .add(
                        ModificationPhase.REMOVALS,
                        BiomeSelectors.tag(BiomeTags.IS_JUNGLE),
                        context -> context.getSpawnSettings().removeSpawns((spawnGroup, spawnEntry) -> spawnEntry.type == EntityType.COW));

        /* SHEEP */
        BiomeModifications.create(new Identifier("remove_sheep_spawn"))
                .add(
                        ModificationPhase.REMOVALS,
                        BiomeSelectors.tag(BiomeTags.IS_JUNGLE),
                        context -> context.getSpawnSettings().removeSpawns((spawnGroup, spawnEntry) -> spawnEntry.type == EntityType.SHEEP));
    }

    public static void addSpawn() {
        /* ARAPAIMA */
        Predicate<BiomeSelectionContext> arapaimaSpawnPredicate = BiomeSelectors.includeByKey(MCUtil.getBiomeKeys(ARAPAIMA_BIOME_TAG));

        BiomeModifications.addSpawn(
                MCUtil.getBiomeKeys(ARAPAIMA_BIOME_TAG).isEmpty() ? BiomeSelectors.all() : arapaimaSpawnPredicate,
                SpawnGroup.WATER_CREATURE,
                EntityRegistry.ARAPAIMA,
                ARAPAIMA_SPAWN_WEIGHT, ARAPAIMA_SPAWN_MIN_GROUP, ARAPAIMA_SPAWN_MAX_GROUP
        );

        SpawnRestriction.register(
                EntityRegistry.ARAPAIMA,
                SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.OCEAN_FLOOR,
                ArapaimaEntity::canMobSpawn);

        /* CAPUCHIN */
        Predicate<BiomeSelectionContext> capuchinSpawnPredicate = BiomeSelectors.includeByKey(MCUtil.getBiomeKeys(CAPUCHIN_BIOME_TAG));

        BiomeModifications.addSpawn(
                MCUtil.getBiomeKeys(CAPUCHIN_BIOME_TAG).isEmpty() ? BiomeSelectors.all() : capuchinSpawnPredicate,
                SpawnGroup.AMBIENT,
                EntityRegistry.CAPUCHIN,
                CAPUCHIN_SPAWN_WEIGHT, CAPUCHIN_SPAWN_MIN_GROUP, CAPUCHIN_SPAWN_MAX_GROUP
        );

        SpawnRestriction.register(
                EntityRegistry.CAPUCHIN,
                SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                CapuchinEntity::canMobSpawn);

        /* CRAYFISH */
        Predicate<BiomeSelectionContext> crayfishSpawnPredicate = BiomeSelectors.includeByKey(MCUtil.getBiomeKeys(CRAYFISH_BIOME_TAG));

        BiomeModifications.addSpawn(
                MCUtil.getBiomeKeys(CRAYFISH_BIOME_TAG).isEmpty() ? BiomeSelectors.all() : crayfishSpawnPredicate,
                SpawnGroup.WATER_CREATURE,
                EntityRegistry.CRAYFISH,
                CRAYFISH_SPAWN_WEIGHT, CRAYFISH_SPAWN_MIN_GROUP, CRAYFISH_SPAWN_MAX_GROUP
        );

        SpawnRestriction.register(
                EntityRegistry.CRAYFISH,
                SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.OCEAN_FLOOR,
                CrayfishEntity::canMobSpawn);

        /* HOATZIN */
        Predicate<BiomeSelectionContext> hoatzinSpawnPredicate = BiomeSelectors.includeByKey(MCUtil.getBiomeKeys(HOATZIN_BIOME_TAG));

        BiomeModifications.addSpawn(
                MCUtil.getBiomeKeys(HOATZIN_BIOME_TAG).isEmpty() ? BiomeSelectors.all() : hoatzinSpawnPredicate,
                SpawnGroup.CREATURE,
                EntityRegistry.HOATZIN,
                HOATZIN_SPAWN_WEIGHT, HOATZIN_SPAWN_MIN_GROUP, HOATZIN_SPAWN_MAX_GROUP
        );

        SpawnRestriction.register(
                EntityRegistry.HOATZIN,
                SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                HoatzinEntity::canMobSpawn);

        /* LEECH */
        Predicate<BiomeSelectionContext> leechSpawnPredicate = BiomeSelectors.includeByKey(MCUtil.getBiomeKeys(LEECH_BIOME_TAG));

        BiomeModifications.addSpawn(
                MCUtil.getBiomeKeys(LEECH_BIOME_TAG).isEmpty() ? BiomeSelectors.all() : leechSpawnPredicate,
                SpawnGroup.WATER_CREATURE,
                EntityRegistry.LEECH,
                LEECH_SPAWN_WEIGHT, LEECH_SPAWN_MIN_GROUP, LEECH_SPAWN_MAX_GROUP
        );

        SpawnRestriction.register(
                EntityRegistry.LEECH,
                SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.OCEAN_FLOOR,
                LeechEntity::canMobSpawn);

        /* PIRANHA */
        Predicate<BiomeSelectionContext> piranhaSpawnPredicate = BiomeSelectors.includeByKey(MCUtil.getBiomeKeys(PIRANHA_BIOME_TAG));

        BiomeModifications.addSpawn(
                MCUtil.getBiomeKeys(PIRANHA_BIOME_TAG).isEmpty() ? BiomeSelectors.all() : piranhaSpawnPredicate,
                SpawnGroup.WATER_CREATURE,
                EntityRegistry.PIRANHA,
                PIRANHA_SPAWN_WEIGHT, PIRANHA_SPAWN_MIN_GROUP, PIRANHA_SPAWN_MAX_GROUP
        );

        SpawnRestriction.register(
                EntityRegistry.PIRANHA,
                SpawnRestriction.Location.IN_WATER,
                Heightmap.Type.OCEAN_FLOOR,
                PiranhaEntity::canMobSpawn);

        /* QUETZAL */
        Predicate<BiomeSelectionContext> quetzalSpawnPredicate = BiomeSelectors.includeByKey(MCUtil.getBiomeKeys(QUETZAL_BIOME_TAG));

        BiomeModifications.addSpawn(
                MCUtil.getBiomeKeys(QUETZAL_BIOME_TAG).isEmpty() ? BiomeSelectors.all() : quetzalSpawnPredicate,
                SpawnGroup.CREATURE,
                EntityRegistry.QUETZAL,
                QUETZAL_SPAWN_WEIGHT, QUETZAL_SPAWN_MIN_GROUP, QUETZAL_SPAWN_MAX_GROUP
        );

        SpawnRestriction.register(
                EntityRegistry.QUETZAL,
                SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                QuetzalEntity::canMobSpawn);

        /* SNAPPING_TURTLE */
        Predicate<BiomeSelectionContext> snappingTurtleSpawnPredicate = BiomeSelectors.includeByKey(MCUtil.getBiomeKeys(SNAPPING_TURTLE_BIOME_TAG));

        BiomeModifications.addSpawn(
                MCUtil.getBiomeKeys(SNAPPING_TURTLE_BIOME_TAG).isEmpty() ? BiomeSelectors.all() : snappingTurtleSpawnPredicate,
                SpawnGroup.CREATURE,
                EntityRegistry.SNAPPING_TURTLE,
                SNAPPING_TURTLE_SPAWN_WEIGHT, SNAPPING_TURTLE_SPAWN_MIN_GROUP, SNAPPING_TURTLE_SPAWN_MAX_GROUP
        );

        SpawnRestriction.register(
                EntityRegistry.SNAPPING_TURTLE,
                SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                SnappingTurtleEntity::canMobSpawn);

        /* SONGBIRD */
        Predicate<BiomeSelectionContext> songbirdSpawnPredicate = BiomeSelectors.includeByKey(MCUtil.getBiomeKeys(SONGBIRD_BIOME_TAG));

        BiomeModifications.addSpawn(
                MCUtil.getBiomeKeys(SONGBIRD_BIOME_TAG).isEmpty() ? BiomeSelectors.all() : songbirdSpawnPredicate,
                SpawnGroup.CREATURE,
                EntityRegistry.SONGBIRD,
                SONGBIRD_SPAWN_WEIGHT, SONGBIRD_SPAWN_MIN_GROUP, SONGBIRD_SPAWN_MAX_GROUP
        );

        SpawnRestriction.register(
                EntityRegistry.SONGBIRD,
                SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                SongbirdEntity::canMobSpawn);

        /* TAPIR */
        Predicate<BiomeSelectionContext> tapirSpawnPredicate = BiomeSelectors.includeByKey(MCUtil.getBiomeKeys(TAPIR_BIOME_TAG));

        BiomeModifications.addSpawn(
                MCUtil.getBiomeKeys(TAPIR_BIOME_TAG).isEmpty() ? BiomeSelectors.all() : tapirSpawnPredicate,
                SpawnGroup.CREATURE,
                EntityRegistry.TAPIR,
                TAPIR_SPAWN_WEIGHT, TAPIR_SPAWN_MIN_GROUP, TAPIR_SPAWN_MAX_GROUP
        );

        SpawnRestriction.register(
                EntityRegistry.TAPIR,
                SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                TapirEntity::canMobSpawn);

        /* YACARE */
        Predicate<BiomeSelectionContext> yacareSpawnPredicate = BiomeSelectors.includeByKey(MCUtil.getBiomeKeys(YACARE_BIOME_TAG));

        BiomeModifications.addSpawn(
                MCUtil.getBiomeKeys(YACARE_BIOME_TAG).isEmpty() ? BiomeSelectors.all() : yacareSpawnPredicate,
                SpawnGroup.CREATURE,
                EntityRegistry.YACARE,
                YACARE_SPAWN_WEIGHT, YACARE_SPAWN_MIN_GROUP, YACARE_SPAWN_MAX_GROUP
        );

        SpawnRestriction.register(
                EntityRegistry.YACARE,
                SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                YacareEntity::canMobSpawn);
    }
}
