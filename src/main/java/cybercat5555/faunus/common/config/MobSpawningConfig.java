package cybercat5555.faunus.common.config;

import com.mojang.datafixers.util.Pair;
import cybercat5555.faunus.Faunus;
import cybercat5555.faunus.util.ConfigRegistry;
import cybercat5555.faunus.util.SimpleConfig;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class MobSpawningConfig {
    public static SimpleConfig config;
    private static ConfigRegistry configRegistry;

    public static int ARAPAIMA_SPAWN_WEIGHT, ARAPAIMA_SPAWN_MIN_GROUP, ARAPAIMA_SPAWN_MAX_GROUP;
    public static String ARAPAIMA_BIOME_TAG;
    public static int CAPUCHIN_SPAWN_WEIGHT, CAPUCHIN_SPAWN_MIN_GROUP, CAPUCHIN_SPAWN_MAX_GROUP;
    public static String CAPUCHIN_BIOME_TAG;
    public static int CONSTRICTOR_SPAWN_WEIGHT, CONSTRICTOR_SPAWN_MIN_GROUP, CONSTRICTOR_SPAWN_MAX_GROUP;
    public static String CONSTRICTOR_BIOME_TAG;
    public static int CRAYFISH_SPAWN_WEIGHT, CRAYFISH_SPAWN_MIN_GROUP, CRAYFISH_SPAWN_MAX_GROUP;
    public static String CRAYFISH_BIOME_TAG;
    public static int HOATZIN_SPAWN_WEIGHT, HOATZIN_SPAWN_MIN_GROUP, HOATZIN_SPAWN_MAX_GROUP;
    public static String HOATZIN_BIOME_TAG;
    public static int LEECH_SPAWN_WEIGHT, LEECH_SPAWN_MIN_GROUP, LEECH_SPAWN_MAX_GROUP;
    public static String LEECH_BIOME_TAG;
    public static int PIRANHA_SPAWN_WEIGHT, PIRANHA_SPAWN_MIN_GROUP, PIRANHA_SPAWN_MAX_GROUP;
    public static String PIRANHA_BIOME_TAG;
    public static int QUETZAL_SPAWN_WEIGHT, QUETZAL_SPAWN_MIN_GROUP, QUETZAL_SPAWN_MAX_GROUP;
    public static String QUETZAL_BIOME_TAG;
    public static int SNAPPING_TURTLE_SPAWN_WEIGHT, SNAPPING_TURTLE_SPAWN_MIN_GROUP, SNAPPING_TURTLE_SPAWN_MAX_GROUP;
    public static String SNAPPING_TURTLE_BIOME_TAG;
    public static int SONGBIRD_SPAWN_WEIGHT, SONGBIRD_SPAWN_MIN_GROUP, SONGBIRD_SPAWN_MAX_GROUP;
    public static String SONGBIRD_BIOME_TAG;
    public static int TAPIR_SPAWN_WEIGHT, TAPIR_SPAWN_MIN_GROUP, TAPIR_SPAWN_MAX_GROUP;
    public static String TAPIR_BIOME_TAG;
    public static int TARANTULA_SPAWN_WEIGHT, TARANTULA_SPAWN_MIN_GROUP, TARANTULA_SPAWN_MAX_GROUP;
    public static String TARANTULA_BIOME_TAG;
    public static int YACARE_SPAWN_WEIGHT, YACARE_SPAWN_MIN_GROUP, YACARE_SPAWN_MAX_GROUP;
    public static String YACARE_BIOME_TAG;
    public static int YACARE_MANEATER_SPAWN_WEIGHT, YACARE_MANEATER_MIN_GROUP, YACARE_MANEATER_MAX_GROUP;
    public static String YACARE_MANEATER_BIOME_TAG;


    public static void init(Path configPath) {
        configRegistry = new ConfigRegistry();
        createConfig();

        config = SimpleConfig.of(configPath + FileSystems.getDefault().getSeparator() + Faunus.MODID + "-mob-spawning-config").provider(configRegistry).request();
        assignConfig();
    }


    private static void createConfig() {
        configRegistry.addComment("Mob Spawning Config");

        configRegistry.addComment("Arapaima");
        configRegistry.addPairData(new Pair<>("arapaima_spawn_weight", 5), "Arapaima spawn weight");
        configRegistry.addPairData(new Pair<>("arapaima_spawn_min_group", 1), "Arapaima spawn min group");
        configRegistry.addPairData(new Pair<>("arapaima_spawn_max_group", 3), "Arapaima spawn max group");
        configRegistry.addPairData(new Pair<>("arapaima_biome_tag", ""), "Arapaima biome tag");

        configRegistry.addComment("Capuchin");
        configRegistry.addPairData(new Pair<>("capuchin_spawn_weight", 5), "Capuchin spawn weight");
        configRegistry.addPairData(new Pair<>("capuchin_spawn_min_group", 1), "Capuchin spawn min group");
        configRegistry.addPairData(new Pair<>("capuchin_spawn_max_group", 3), "Capuchin spawn max group");
        configRegistry.addPairData(new Pair<>("capuchin_biome_tag", "is_jungle"), "Capuchin biome tag");

        configRegistry.addComment("Constrictor");
        configRegistry.addPairData(new Pair<>("constrictor_spawn_weight", 5), "Constrictor spawn weight");
        configRegistry.addPairData(new Pair<>("constrictor_spawn_min_group", 1), "Constrictor spawn min group");
        configRegistry.addPairData(new Pair<>("constrictor_spawn_max_group", 3), "Constrictor spawn max group");
        configRegistry.addPairData(new Pair<>("constrictor_biome_tag", ""), "Constrictor biome tag");

        configRegistry.addComment("Crayfish");
        configRegistry.addPairData(new Pair<>("crayfish_spawn_weight", 5), "Crayfish spawn weight");
        configRegistry.addPairData(new Pair<>("crayfish_spawn_min_group", 1), "Crayfish spawn min group");
        configRegistry.addPairData(new Pair<>("crayfish_spawn_max_group", 3), "Crayfish spawn max group");
        configRegistry.addPairData(new Pair<>("crayfish_biome_tag", "swamp"), "Crayfish biome tag");

        configRegistry.addComment("Hoatzin");
        configRegistry.addPairData(new Pair<>("hoatzin_spawn_weight", 5), "Hoatzin spawn weight");
        configRegistry.addPairData(new Pair<>("hoatzin_spawn_min_group", 1), "Hoatzin spawn min group");
        configRegistry.addPairData(new Pair<>("hoatzin_spawn_max_group", 3), "Hoatzin spawn max group");
        configRegistry.addPairData(new Pair<>("hoatzin_biome_tag", "is_jungle"), "Hoatzin biome tag");

        configRegistry.addComment("Leech");
        configRegistry.addPairData(new Pair<>("leech_spawn_weight", 5), "Leech spawn weight");
        configRegistry.addPairData(new Pair<>("leech_spawn_min_group", 1), "Leech spawn min group");
        configRegistry.addPairData(new Pair<>("leech_spawn_max_group", 3), "Leech spawn max group");
        configRegistry.addPairData(new Pair<>("leech_biome_tag", "swamp"), "Leech biome tag");

        configRegistry.addComment("Piranha");
        configRegistry.addPairData(new Pair<>("piranha_spawn_weight", 5), "Piranha spawn weight");
        configRegistry.addPairData(new Pair<>("piranha_spawn_min_group", 1), "Piranha spawn min group");
        configRegistry.addPairData(new Pair<>("piranha_spawn_max_group", 3), "Piranha spawn max group");
        configRegistry.addPairData(new Pair<>("piranha_biome_tag", "is_river"), "Piranha biome tag");

        configRegistry.addComment("Quetzal");
        configRegistry.addPairData(new Pair<>("quetzal_spawn_weight", 5), "Quetzal spawn weight");
        configRegistry.addPairData(new Pair<>("quetzal_spawn_min_group", 1), "Quetzal spawn min group");
        configRegistry.addPairData(new Pair<>("quetzal_spawn_max_group", 3), "Quetzal spawn max group");
        configRegistry.addPairData(new Pair<>("quetzal_biome_tag", ""), "Quetzal biome tag");

        configRegistry.addComment("Snapping Turtle");
        configRegistry.addPairData(new Pair<>("snapping_turtle_spawn_weight", 5), "Snapping Turtle spawn weight");
        configRegistry.addPairData(new Pair<>("snapping_turtle_spawn_min_group", 1), "Snapping Turtle spawn min group");
        configRegistry.addPairData(new Pair<>("snapping_turtle_spawn_max_group", 3), "Snapping Turtle spawn max group");
        configRegistry.addPairData(new Pair<>("snapping_turtle_biome_tag", "swamp"), "Snapping Turtle biome tag");

        configRegistry.addComment("Songbird");
        configRegistry.addPairData(new Pair<>("songbird_spawn_weight", 5), "Songbird spawn weight");
        configRegistry.addPairData(new Pair<>("songbird_spawn_min_group", 1), "Songbird spawn min group");
        configRegistry.addPairData(new Pair<>("songbird_spawn_max_group", 3), "Songbird spawn max group");
        configRegistry.addPairData(new Pair<>("songbird_biome_tag", "swamp"), "Songbird biome tag");

        configRegistry.addComment("Tapir");
        configRegistry.addPairData(new Pair<>("tapir_spawn_weight", 5), "Tapir spawn weight");
        configRegistry.addPairData(new Pair<>("tapir_spawn_min_group", 1), "Tapir spawn min group");
        configRegistry.addPairData(new Pair<>("tapir_spawn_max_group", 3), "Tapir spawn max group");
        configRegistry.addPairData(new Pair<>("tapir_biome_tag", ""), "Tapir biome tag");

        configRegistry.addComment("Tarantula");
        configRegistry.addPairData(new Pair<>("tarantula_spawn_weight", 5), "Tarantula spawn weight");
        configRegistry.addPairData(new Pair<>("tarantula_spawn_min_group", 1), "Tarantula spawn min group");
        configRegistry.addPairData(new Pair<>("tarantula_spawn_max_group", 3), "Tarantula spawn max group");
        configRegistry.addPairData(new Pair<>("tarantula_biome_tag", ""), "Tarantula biome tag");

        configRegistry.addComment("Yacare");
        configRegistry.addPairData(new Pair<>("yacare_spawn_weight", 5), "Yacare spawn weight");
        configRegistry.addPairData(new Pair<>("yacare_spawn_min_group", 1), "Yacare spawn min group");
        configRegistry.addPairData(new Pair<>("yacare_spawn_max_group", 3), "Yacare spawn max group");
        configRegistry.addPairData(new Pair<>("yacare_biome_tag", "swamp"), "Yacare biome tag");

        configRegistry.addComment("Yacare Maneater");
        configRegistry.addPairData(new Pair<>("yacare_maneater_spawn_weight", 5), "Yacare Maneater spawn weight");
        configRegistry.addPairData(new Pair<>("yacare_maneater_spawn_min_group", 1), "Yacare Maneater spawn min group");
        configRegistry.addPairData(new Pair<>("yacare_maneater_spawn_max_group", 3), "Yacare Maneater spawn max group");
        configRegistry.addPairData(new Pair<>("yacare_maneater_biome_tag", "swamp"), "Yacare Maneater biome tag");
    }

    private static void assignConfig() {
        ARAPAIMA_SPAWN_WEIGHT = Integer.parseInt(config.getOrDefault("arapaima_spawn_weight", "0"));
        ARAPAIMA_SPAWN_MIN_GROUP = Integer.parseInt(config.getOrDefault("arapaima_spawn_min_group", "0"));
        ARAPAIMA_SPAWN_MAX_GROUP = Integer.parseInt(config.getOrDefault("arapaima_spawn_max_group", "0"));
        ARAPAIMA_BIOME_TAG = config.getOrDefault("arapaima_biome_tag", null).isEmpty() ? null : config.getOrDefault("arapaima_biome_tag", null);

        CAPUCHIN_SPAWN_WEIGHT = Integer.parseInt(config.getOrDefault("capuchin_spawn_weight", "0"));
        CAPUCHIN_SPAWN_MIN_GROUP = Integer.parseInt(config.getOrDefault("capuchin_spawn_min_group", "0"));
        CAPUCHIN_SPAWN_MAX_GROUP = Integer.parseInt(config.getOrDefault("capuchin_spawn_max_group", "0"));
        CAPUCHIN_BIOME_TAG = config.getOrDefault("capuchin_biome_tag", null).isEmpty() ? null : config.getOrDefault("capuchin_biome_tag", null);

        CONSTRICTOR_SPAWN_WEIGHT = Integer.parseInt(config.getOrDefault("constrictor_spawn_weight", "0"));
        CONSTRICTOR_SPAWN_MIN_GROUP = Integer.parseInt(config.getOrDefault("constrictor_spawn_min_group", "0"));
        CONSTRICTOR_SPAWN_MAX_GROUP = Integer.parseInt(config.getOrDefault("constrictor_spawn_max_group", "0"));
        CONSTRICTOR_BIOME_TAG = config.getOrDefault("constrictor_biome_tag", null).isEmpty() ? null : config.getOrDefault("constrictor_biome_tag", null);

        CRAYFISH_SPAWN_WEIGHT = Integer.parseInt(config.getOrDefault("crayfish_spawn_weight", "0"));
        CRAYFISH_SPAWN_MIN_GROUP = Integer.parseInt(config.getOrDefault("crayfish_spawn_min_group", "0"));
        CRAYFISH_SPAWN_MAX_GROUP = Integer.parseInt(config.getOrDefault("crayfish_spawn_max_group", "0"));
        CRAYFISH_BIOME_TAG = config.getOrDefault("crayfish_biome_tag", null).isEmpty() ? null : config.getOrDefault("crayfish_biome_tag", null);

        HOATZIN_SPAWN_WEIGHT = Integer.parseInt(config.getOrDefault("hoatzin_spawn_weight", "0"));
        HOATZIN_SPAWN_MIN_GROUP = Integer.parseInt(config.getOrDefault("hoatzin_spawn_min_group", "0"));
        HOATZIN_SPAWN_MAX_GROUP = Integer.parseInt(config.getOrDefault("hoatzin_spawn_max_group", "0"));
        HOATZIN_BIOME_TAG = config.getOrDefault("hoatzin_biome_tag", null).isEmpty() ? null : config.getOrDefault("hoatzin_biome_tag", null);

        LEECH_SPAWN_WEIGHT = Integer.parseInt(config.getOrDefault("leech_spawn_weight", "0"));
        LEECH_SPAWN_MIN_GROUP = Integer.parseInt(config.getOrDefault("leech_spawn_min_group", "0"));
        LEECH_SPAWN_MAX_GROUP = Integer.parseInt(config.getOrDefault("leech_spawn_max_group", "0"));
        LEECH_BIOME_TAG = config.getOrDefault("leech_biome_tag", null).isEmpty() ? null : config.getOrDefault("leech_biome_tag", null);

        PIRANHA_SPAWN_WEIGHT = Integer.parseInt(config.getOrDefault("piranha_spawn_weight", "0"));
        PIRANHA_SPAWN_MIN_GROUP = Integer.parseInt(config.getOrDefault("piranha_spawn_min_group", "0"));
        PIRANHA_SPAWN_MAX_GROUP = Integer.parseInt(config.getOrDefault("piranha_spawn_max_group", "0"));
        PIRANHA_BIOME_TAG = config.getOrDefault("piranha_biome_tag", null).isEmpty() ? null : config.getOrDefault("piranha_biome_tag", null);

        QUETZAL_SPAWN_WEIGHT = Integer.parseInt(config.getOrDefault("quetzal_spawn_weight", "0"));
        QUETZAL_SPAWN_MIN_GROUP = Integer.parseInt(config.getOrDefault("quetzal_spawn_min_group", "0"));
        QUETZAL_SPAWN_MAX_GROUP = Integer.parseInt(config.getOrDefault("quetzal_spawn_max_group", "0"));
        QUETZAL_BIOME_TAG = config.getOrDefault("quetzal_biome_tag", null).isEmpty() ? null : config.getOrDefault("quetzal_biome_tag", null);

        SNAPPING_TURTLE_SPAWN_WEIGHT = Integer.parseInt(config.getOrDefault("snapping_turtle_spawn_weight", "0"));
        SNAPPING_TURTLE_SPAWN_MIN_GROUP = Integer.parseInt(config.getOrDefault("snapping_turtle_spawn_min_group", "0"));
        SNAPPING_TURTLE_SPAWN_MAX_GROUP = Integer.parseInt(config.getOrDefault("snapping_turtle_spawn_max_group", "0"));
        SNAPPING_TURTLE_BIOME_TAG = config.getOrDefault("snapping_turtle_biome_tag", null).isEmpty() ? null : config.getOrDefault("snapping_turtle_biome_tag", null);

        SONGBIRD_SPAWN_WEIGHT = Integer.parseInt(config.getOrDefault("songbird_spawn_weight", "0"));
        SONGBIRD_SPAWN_MIN_GROUP = Integer.parseInt(config.getOrDefault("songbird_spawn_min_group", "0"));
        SONGBIRD_SPAWN_MAX_GROUP = Integer.parseInt(config.getOrDefault("songbird_spawn_max_group", "0"));
        SONGBIRD_BIOME_TAG = config.getOrDefault("songbird_biome_tag", null).isEmpty() ? null : config.getOrDefault("songbird_biome_tag", null);

        TAPIR_SPAWN_WEIGHT = Integer.parseInt(config.getOrDefault("tapir_spawn_weight", "0"));
        TAPIR_SPAWN_MIN_GROUP = Integer.parseInt(config.getOrDefault("tapir_spawn_min_group", "0"));
        TAPIR_SPAWN_MAX_GROUP = Integer.parseInt(config.getOrDefault("tapir_spawn_max_group", "0"));
        TAPIR_BIOME_TAG = config.getOrDefault("tapir_biome_tag", null).isEmpty() ? null : config.getOrDefault("tapir_biome_tag", null);

        TARANTULA_SPAWN_WEIGHT = Integer.parseInt(config.getOrDefault("tarantula_spawn_weight", "0"));
        TARANTULA_SPAWN_MIN_GROUP = Integer.parseInt(config.getOrDefault("tarantula_spawn_min_group", "0"));
        TARANTULA_SPAWN_MAX_GROUP = Integer.parseInt(config.getOrDefault("tarantula_spawn_max_group", "0"));
        TARANTULA_BIOME_TAG = config.getOrDefault("tarantula_biome_tag", null).isEmpty() ? null : config.getOrDefault("tarantula_biome_tag", null);

        YACARE_SPAWN_WEIGHT = Integer.parseInt(config.getOrDefault("yacare_spawn_weight", "0"));
        YACARE_SPAWN_MIN_GROUP = Integer.parseInt(config.getOrDefault("yacare_spawn_min_group", "0"));
        YACARE_SPAWN_MAX_GROUP = Integer.parseInt(config.getOrDefault("yacare_spawn_max_group", "0"));
        YACARE_BIOME_TAG = config.getOrDefault("yacare_biome_tag", null).isEmpty() ? null : config.getOrDefault("yacare_biome_tag", null);

        YACARE_MANEATER_SPAWN_WEIGHT = Integer.parseInt(config.getOrDefault("yacare_maneater_spawn_weight", "0"));
        YACARE_MANEATER_MIN_GROUP = Integer.parseInt(config.getOrDefault("yacare_maneater_spawn_min_group", "0"));
        YACARE_MANEATER_MAX_GROUP = Integer.parseInt(config.getOrDefault("yacare_maneater_spawn_max_group", "0"));
        YACARE_MANEATER_BIOME_TAG = config.getOrDefault("yacare_maneater_biome_tag", null).isEmpty() ? null : config.getOrDefault("yacare_maneater_biome_tag", null);
    }
}
