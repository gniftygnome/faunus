package cybercat5555.faunus.core.entity.livingEntity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum BirdPatterns {
    NONE(0),
    BELLY(1),
    CAPED(2),
    COLLARED(3),
    COUNTERSHADED(4),
    HOODED(5),
    MASKED(6),
    STRIPEWING(7);

    private static final BirdPatterns[] BY_ID = Arrays.stream(values()).
            sorted(Comparator.comparingInt(BirdPatterns::getId)).toArray(BirdPatterns[]::new);
    private final int id;

    private BirdPatterns(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static BirdPatterns byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
