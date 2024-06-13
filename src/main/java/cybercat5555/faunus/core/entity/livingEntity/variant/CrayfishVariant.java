package cybercat5555.faunus.core.entity.livingEntity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum CrayfishVariant {
    DEFAULT(0),
    BLUE(1);

    private static final CrayfishVariant[] BY_ID = Arrays.stream(values()).
            sorted(Comparator.comparingInt(CrayfishVariant::getId)).toArray(CrayfishVariant[]::new);
    private final int id;

    private CrayfishVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static CrayfishVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
