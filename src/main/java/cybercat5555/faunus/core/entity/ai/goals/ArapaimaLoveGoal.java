package cybercat5555.faunus.core.entity.ai.goals;

import cybercat5555.faunus.core.entity.ArapaimaEntity;
import cybercat5555.faunus.core.entity.PiranhaEntity;
import net.minecraft.entity.ai.goal.Goal;

public class ArapaimaLoveGoal extends Goal {
    private final ArapaimaEntity mob;
    private ArapaimaEntity companion;

    /**
     * The Arapaima entity that is executing this goal.
     */
    public ArapaimaLoveGoal(ArapaimaEntity arapaima) {
        super();
        mob = arapaima;
    }

    @Override
    public void tick() {
        super.tick();
        lookForReproduction();
    }

    /**
     * Look for a nearby Arapaima entity to breed with.
     */
    private void lookForReproduction() {
        for (ArapaimaEntity arapaima : mob.getWorld().getEntitiesByClass(ArapaimaEntity.class, mob.getBoundingBox().expand(4.0D, 4.0D, 4.0D), null)) {
            if (arapaima.isInLove() && companion == null) {
                companion = arapaima;
                return;
            }
        }

        if(companion != null) {
            mob.breed(companion);
        }
    }

    /**
     * Execute the goal in case the target has been breed recently.
     *
     * @return true if the target is in love, false otherwise.
     */
    @Override
    public boolean canStart() {
        return mob.isInLove();
    }

    /**
     * Keep executing the goal in case the target has been breed recently.
     *
     * @return true if the target is in love, false otherwise.
     */
    @Override
    public boolean shouldContinue() {
        return mob.isInLove();
    }
}
