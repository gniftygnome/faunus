package cybercat5555.faunus.core.entity.ai.goals;

import cybercat5555.faunus.core.entity.livingEntity.CrayfishEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;

public class BuryOnChased extends EscapeDangerGoal {
    private static final int BURY_TIME = 20 * 2;
    private static final int ANIMATION_TIME = 20;
    private int buryTime = 0;
    private int despawnTime = 0;


    public BuryOnChased(CrayfishEntity mob, double speed) {
        super(mob, speed);
    }

    @Override
    protected boolean isInDanger() {
        return super.isInDanger() || this.isChasedByPlayer();
    }

    private boolean isChasedByPlayer() {
        return this.mob.getWorld().getClosestPlayer(this.mob, 10.0D) != null;
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isChasedByPlayer() && buryTime++ >= BURY_TIME) {
            this.bury();
        } else if(!this.isChasedByPlayer()) {
            buryTime = 0;
            despawnTime = 0;
        }
    }

    private void bury() {
        if (!this.mob.isOnGround()) {
            // Move down to the ground
            this.mob.setVelocity(0, -0.1, 0);
        } else {
            ((CrayfishEntity) this.mob).setBuried(true);

            if (despawnTime++ >= ANIMATION_TIME) {
                this.mob.remove(Entity.RemovalReason.DISCARDED);
            }
        }
    }
}