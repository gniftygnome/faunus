package cybercat5555.faunus.core.entity.livingEntity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum BirdVariant {
    NONE(0),
    BELLY(1),
    CAPED(2),
    COLLARED(3),
    COUNTERSHADED(4),
    HOODED(5),
    MASKED(6),
    STRIPEWING(7);

    private static final BirdVariant[] BY_ID = Arrays.stream(values()).
            sorted(Comparator.comparingInt(BirdVariant::getId)).toArray(BirdVariant[]::new);
    private final int id;

    private BirdVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static BirdVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
