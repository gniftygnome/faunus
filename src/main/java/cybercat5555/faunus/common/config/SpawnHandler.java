package cybercat5555.faunus.common.config;

import cybercat5555.faunus.common.tags.FaunusBiomeTags;
import cybercat5555.faunus.core.EntityRegistry;
import cybercat5555.faunus.core.entity.livingEntity.*;
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
        Predicate<BiomeSelectionContext> arapaimaSpawnPredicate = BiomeSelectors.tag(FaunusBiomeTags.SPAWNS_ARAPAIMA);

        BiomeModifications.addSpawn(
                arapaimaSpawnPredicate,
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
        Predicate<BiomeSelectionContext> capuchinSpawnPredicate = BiomeSelectors.tag(FaunusBiomeTags.SPAWNS_CAPUCHIN);

        BiomeModifications.addSpawn(
                capuchinSpawnPredicate,
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
        Predicate<BiomeSelectionContext> crayfishSpawnPredicate = BiomeSelectors.tag(FaunusBiomeTags.SPAWNS_CRAYFISH);

        BiomeModifications.addSpawn(
                crayfishSpawnPredicate,
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
        Predicate<BiomeSelectionContext> hoatzinSpawnPredicate = BiomeSelectors.tag(FaunusBiomeTags.SPAWNS_HOATZIN);

        BiomeModifications.addSpawn(
                hoatzinSpawnPredicate,
                SpawnGroup.CREATURE,
                EntityRegistry.HOATZIN,
                HOATZIN_SPAWN_WEIGHT, HOATZIN_SPAWN_MIN_GROUP, HOATZIN_SPAWN_MAX_GROUP
        );

        SpawnRestriction.register(
                EntityRegistry.HOATZIN,
                SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                HoatzinEntity::canMobSpawn);

        /* IGUANA */
        BiomeModifications.addSpawn(
                BiomeSelectors.tag(FaunusBiomeTags.SPAWNS_IGUANA),
                SpawnGroup.CREATURE,
                EntityRegistry.IGUANA,
                IGUANA_SPAWN_WEIGHT, IGUANA_SPAWN_MIN_GROUP, IGUANA_SPAWN_MAX_GROUP
        );

        SpawnRestriction.register(
                EntityRegistry.IGUANA,
                SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING,
                IguanaEntity::canMobSpawn);

        /* LEECH */
        Predicate<BiomeSelectionContext> leechSpawnPredicate = BiomeSelectors.tag(FaunusBiomeTags.SPAWNS_LEECH);

        BiomeModifications.addSpawn(
                leechSpawnPredicate,
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
        Predicate<BiomeSelectionContext> piranhaSpawnPredicate = BiomeSelectors.tag(FaunusBiomeTags.SPAWNS_PIRANHA);

        BiomeModifications.addSpawn(
                piranhaSpawnPredicate,
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
        Predicate<BiomeSelectionContext> quetzalSpawnPredicate = BiomeSelectors.tag(FaunusBiomeTags.SPAWNS_QUETZAL);

        BiomeModifications.addSpawn(
                quetzalSpawnPredicate,
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
        Predicate<BiomeSelectionContext> snappingTurtleSpawnPredicate = BiomeSelectors.tag(FaunusBiomeTags.SPAWNS_SNAPPING_TURTLE);

        BiomeModifications.addSpawn(
                snappingTurtleSpawnPredicate,
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
        Predicate<BiomeSelectionContext> songbirdSpawnPredicate = BiomeSelectors.tag(FaunusBiomeTags.SPAWNS_SONGBIRD);

        BiomeModifications.addSpawn(
                songbirdSpawnPredicate,
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
        Predicate<BiomeSelectionContext> tapirSpawnPredicate = BiomeSelectors.tag(FaunusBiomeTags.SPAWNS_TAPIR);

        BiomeModifications.addSpawn(
                tapirSpawnPredicate,
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
        Predicate<BiomeSelectionContext> yacareSpawnPredicate = BiomeSelectors.tag(FaunusBiomeTags.SPAWNS_YACARE);

        BiomeModifications.addSpawn(
                yacareSpawnPredicate,
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
