package cybercat5555.faunus.common;

import cybercat5555.faunus.common.config.MobSpawningConfig;
import cybercat5555.faunus.core.entity.livingEntity.YacareManEaterEntity;
import cybercat5555.faunus.util.FaunusID;
import cybercat5555.faunus.util.MCUtil;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.biome.SpawnSettings;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import static cybercat5555.faunus.common.config.MobSpawningConfig.*;
import static cybercat5555.faunus.core.EntityRegistry.*;

public class EventManager {

    public static void onAttack() {
        AttackBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) -> {
            if (player.getVehicle() != null && player.getVehicle() instanceof YacareManEaterEntity yacare && player.isAlive()) {
                player.attack(player.getVehicle());
            }

            return ActionResult.PASS;
        });
    }

    public static void onServerStart() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            Path worldPath = Path.of(server.getSavePath(WorldSavePath.ROOT) + FileSystems.getDefault().getSeparator() + "config");

            MobSpawningConfig.init(worldPath);
            applyConfig();
        });
    }

    private static void applyConfig() {

        BiomeModifications.create(FaunusID.content("arapaima_spawn")).add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.tag(MCUtil.biomeTagKeyOf(ARAPAIMA_BIOME_TAG)),
                ctx -> ctx.getSpawnSettings().addSpawn(
                        SpawnGroup.WATER_CREATURE,
                        new SpawnSettings.SpawnEntry(ARAPAIMA, ARAPAIMA_SPAWN_WEIGHT, ARAPAIMA_SPAWN_MIN_GROUP, ARAPAIMA_SPAWN_MAX_GROUP)));

        BiomeModifications.create(FaunusID.content("capuchin_spawn")).add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.tag(MCUtil.biomeTagKeyOf(CAPUCHIN_BIOME_TAG)),
                ctx -> ctx.getSpawnSettings().addSpawn(
                        SpawnGroup.CREATURE,
                        new SpawnSettings.SpawnEntry(CAPUCHIN, CAPUCHIN_SPAWN_WEIGHT, CAPUCHIN_SPAWN_MIN_GROUP, CAPUCHIN_SPAWN_MAX_GROUP)));

        BiomeModifications.create(FaunusID.content("constrictor_spawn")).add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.tag(MCUtil.biomeTagKeyOf(CONSTRICTOR_BIOME_TAG)),
                ctx -> ctx.getSpawnSettings().addSpawn(
                        SpawnGroup.CREATURE,
                        new SpawnSettings.SpawnEntry(CONSTRICTOR, CONSTRICTOR_SPAWN_WEIGHT, CONSTRICTOR_SPAWN_MIN_GROUP, CONSTRICTOR_SPAWN_MAX_GROUP)));

        BiomeModifications.create(FaunusID.content("crayfish_spawn")).add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.tag(MCUtil.biomeTagKeyOf(CRAYFISH_BIOME_TAG)),
                ctx -> ctx.getSpawnSettings().addSpawn(
                        SpawnGroup.WATER_CREATURE,
                        new SpawnSettings.SpawnEntry(CRAYFISH, CRAYFISH_SPAWN_WEIGHT, CRAYFISH_SPAWN_MIN_GROUP, CRAYFISH_SPAWN_MAX_GROUP)));

        BiomeModifications.create(FaunusID.content("hoatzin_spawn")).add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.tag(MCUtil.biomeTagKeyOf(HOATZIN_BIOME_TAG)),
                ctx -> ctx.getSpawnSettings().addSpawn(
                        SpawnGroup.CREATURE,
                        new SpawnSettings.SpawnEntry(HOATZIN, HOATZIN_SPAWN_WEIGHT, HOATZIN_SPAWN_MIN_GROUP, HOATZIN_SPAWN_MAX_GROUP)));

        BiomeModifications.create(FaunusID.content("leech_spawn")).add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.tag(MCUtil.biomeTagKeyOf(LEECH_BIOME_TAG)),
                ctx -> ctx.getSpawnSettings().addSpawn(
                        SpawnGroup.WATER_CREATURE,
                        new SpawnSettings.SpawnEntry(LEECH, LEECH_SPAWN_WEIGHT, LEECH_SPAWN_MIN_GROUP, LEECH_SPAWN_MAX_GROUP)));

        BiomeModifications.create(FaunusID.content("piranha_spawn")).add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.tag(MCUtil.biomeTagKeyOf(PIRANHA_BIOME_TAG)),
                ctx -> ctx.getSpawnSettings().addSpawn(
                        SpawnGroup.WATER_CREATURE,
                        new SpawnSettings.SpawnEntry(PIRANHA, PIRANHA_SPAWN_WEIGHT, PIRANHA_SPAWN_MIN_GROUP, PIRANHA_SPAWN_MAX_GROUP)));

        BiomeModifications.create(FaunusID.content("quetzal_spawn")).add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.tag(MCUtil.biomeTagKeyOf(QUETZAL_BIOME_TAG)),
                ctx -> ctx.getSpawnSettings().addSpawn(
                        SpawnGroup.CREATURE,
                        new SpawnSettings.SpawnEntry(QUETZAL, QUETZAL_SPAWN_WEIGHT, QUETZAL_SPAWN_MIN_GROUP, QUETZAL_SPAWN_MAX_GROUP)));

        BiomeModifications.create(FaunusID.content("snapping_turtle_spawn")).add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.tag(MCUtil.biomeTagKeyOf(SNAPPING_TURTLE_BIOME_TAG)),
                ctx -> ctx.getSpawnSettings().addSpawn(
                        SpawnGroup.CREATURE,
                        new SpawnSettings.SpawnEntry(SNAPPING_TURTLE, SNAPPING_TURTLE_SPAWN_WEIGHT, SNAPPING_TURTLE_SPAWN_MIN_GROUP, SNAPPING_TURTLE_SPAWN_MAX_GROUP)));

        BiomeModifications.create(FaunusID.content("songbird_spawn")).add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.tag(MCUtil.biomeTagKeyOf(SONGBIRD_BIOME_TAG)),
                ctx -> ctx.getSpawnSettings().addSpawn(
                        SpawnGroup.CREATURE,
                        new SpawnSettings.SpawnEntry(SONGBIRD, SONGBIRD_SPAWN_WEIGHT, SONGBIRD_SPAWN_MIN_GROUP, SONGBIRD_SPAWN_MAX_GROUP)));

        BiomeModifications.create(FaunusID.content("tapir_spawn")).add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.tag(MCUtil.biomeTagKeyOf(TAPIR_BIOME_TAG)),
                ctx -> ctx.getSpawnSettings().addSpawn(
                        SpawnGroup.CREATURE,
                        new SpawnSettings.SpawnEntry(TAPIR, TAPIR_SPAWN_WEIGHT, TAPIR_SPAWN_MIN_GROUP, TAPIR_SPAWN_MAX_GROUP)));

        BiomeModifications.create(FaunusID.content("yacare_spawn")).add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.tag(MCUtil.biomeTagKeyOf(YACARE_BIOME_TAG)),
                ctx -> ctx.getSpawnSettings().addSpawn(
                        SpawnGroup.CREATURE,
                        new SpawnSettings.SpawnEntry(YACARE, YACARE_SPAWN_WEIGHT, YACARE_SPAWN_MIN_GROUP, YACARE_SPAWN_MAX_GROUP)));

        BiomeModifications.create(FaunusID.content("yacare_maneater_spawn")).add(
                ModificationPhase.ADDITIONS,
                BiomeSelectors.tag(MCUtil.biomeTagKeyOf(YACARE_BIOME_TAG)),
                ctx -> ctx.getSpawnSettings().addSpawn(
                        SpawnGroup.CREATURE,
                        new SpawnSettings.SpawnEntry(YACARE_MANEATER, YACARE_MANEATER_SPAWN_WEIGHT, YACARE_MANEATER_MIN_GROUP, YACARE_MANEATER_MAX_GROUP)));
    }
}
