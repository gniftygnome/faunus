package cybercat5555.faunus.core.entity.livingEntity.variant;

import cybercat5555.faunus.util.FaunusColor;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.BiomeKeys;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public enum BirdVariant {
    VIOLACEOUS_EUPHONIA(0, BirdPatterns.COUNTERSHADED, BiomeKeys.JUNGLE.getValue(), FaunusColor.PURPLE, FaunusColor.YELLOW, FaunusColor.NONE, false),

    WHITE_WINGED_COTINGA(1, BirdPatterns.CAPED, BiomeKeys.JUNGLE.getValue(), FaunusColor.PURPLE, FaunusColor.WHITE, FaunusColor.NONE, false),

    BLACK_HEADED_BERRYEATER(2, BirdPatterns.HOODED, BiomeKeys.JUNGLE.getValue(), FaunusColor.LIME, FaunusColor.DARK_GRAY, FaunusColor.NONE, false),

    YELLOW_WINGED_CACIQUE(3, BirdPatterns.CAPED, BiomeKeys.JUNGLE.getValue(), FaunusColor.DARK_GRAY, FaunusColor.YELLOW, FaunusColor.NONE, true),

    YUCATAN_JAY(4, BirdPatterns.CAPED, BiomeKeys.JUNGLE.getValue(), FaunusColor.DARK_GRAY, FaunusColor.CYAN, FaunusColor.NONE, true),

    ALTAMIRA_ORIOLE(5, BirdPatterns.CAPED, BiomeKeys.JUNGLE.getValue(), FaunusColor.ORANGE, FaunusColor.DARK_GRAY, FaunusColor.NONE, false),

    HONEYCREEPER(6, BirdPatterns.CAPED, BiomeKeys.JUNGLE.getValue(), FaunusColor.BLUE, FaunusColor.DARK_GRAY, FaunusColor.NONE, false),

    FIERY_THROATED_FRUITEATER(7, BirdPatterns.COLLARED, BiomeKeys.JUNGLE.getValue(), FaunusColor.GREEN, FaunusColor.ORANGE, FaunusColor.NONE,false),

    SCARLET_THROATED_TANGER(8, BirdPatterns.COLLARED, BiomeKeys.JUNGLE.getValue(), FaunusColor.DARK_GRAY, FaunusColor.RED, FaunusColor.NONE, false),

    CITROLIN_TROGON(9, BirdPatterns.COLLARED, BiomeKeys.JUNGLE.getValue(), FaunusColor.GRAY, FaunusColor.WHITE, FaunusColor.YELLOW, false);


    private static final BirdVariant[] BY_ID = Arrays.stream(values()).
            sorted(Comparator.comparingInt(BirdVariant::getId)).toArray(BirdVariant[]::new);

    private final int id;
    private final BirdPatterns pattern;
    private final Identifier biome;
    private final int primaryColor;
    private final int secondaryColor;
    private final int bellyColor;
    private final boolean renderCrest;

    private BirdVariant(int id, BirdPatterns pattern, Identifier biome, int primaryColor, int secondaryColor, int bellyColor, boolean renderCrest) {
        this.id = id;
        this.pattern = pattern;
        this.biome = biome;
        this.primaryColor = primaryColor;
        this.secondaryColor = secondaryColor;
        this.bellyColor = bellyColor;
        this.renderCrest = renderCrest;
    }

    public int getId() {
        return id;
    }

    public BirdPatterns getPattern() {
        return pattern;
    }

    public Identifier getBiome() {
        return biome;
    }

    public float[] getPrimaryColor() {
        return new float[]{
                ((primaryColor >> 16) & 0xFF) / 255f,
                ((primaryColor >> 8) & 0xFF) / 255f,
                (primaryColor & 0xFF) / 255f
        };
    }

    public float[] getSecondaryColor() {
        return new float[]{
                ((secondaryColor >> 16) & 0xFF) / 255f,
                ((secondaryColor >> 8) & 0xFF) / 255f,
                (secondaryColor & 0xFF) / 255f
        };
    }

    public float[] getBellyColor() {
        return new float[]{
                ((bellyColor >> 16) & 0xFF) / 255f,
                ((bellyColor >> 8) & 0xFF) / 255f,
                (bellyColor & 0xFF) / 255f
        };
    }

    public boolean shouldRenderCrest() {
        return renderCrest;
    }

    public boolean shouldRenderBelly() {
        return bellyColor != 0;
    }

    public static BirdVariant byBiome(Identifier biome) {
        List<BirdVariant> variantsInBiome = Arrays.stream(BirdVariant.values())
                .filter(variant -> variant.getBiome().equals(biome))
                .toList();

        if (variantsInBiome.isEmpty()) {
            return VIOLACEOUS_EUPHONIA;
        }

        Random random = new Random();
        return variantsInBiome.get(random.nextInt(variantsInBiome.size()));
    }


    public static BirdVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
