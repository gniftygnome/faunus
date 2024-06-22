package cybercat5555.faunus.core.entity.ai.goals;

import cybercat5555.faunus.core.entity.MateEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public class MateGoal extends Goal {

    private static final TargetPredicate VALID_MATE_PREDICATE = TargetPredicate.createNonAttackable().setBaseMaxDistance(8.0).ignoreVisibility();
    protected final PathAwareEntity animal;
    private final Class<? extends PathAwareEntity> entityClass;
    protected final World world;
    @Nullable
    protected PathAwareEntity mate;
    private int timer;
    private final double speed;

    public MateGoal(PathAwareEntity animal, double speed) {
        this(animal, speed, animal.getClass());
    }

    public MateGoal(PathAwareEntity animal, double speed, Class<? extends PathAwareEntity> entityClass) {
        this.animal = animal;
        this.world = animal.getWorld();
        this.entityClass = entityClass;
        this.speed = speed;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        if (!((MateEntity) this.animal).isInLove()) {
            return false;
        }
        this.mate = this.findMate();
        return this.mate != null;
    }

    @Override
    public boolean shouldContinue() {
        return this.mate != null && this.mate.isAlive() && ((MateEntity) this.mate).isInLove() && this.timer < 60;
    }

    @Override
    public void stop() {
        this.mate = null;
        this.timer = 0;
    }

    @Override
    public void tick() {
        this.animal.getNavigation().startMovingTo(this.mate, this.speed);
        ++this.timer;
        if (this.timer >= this.getTickCount(60) && this.animal.squaredDistanceTo(this.mate) < 9.0) {
            this.breed();
        }
    }

    @Nullable
    private PathAwareEntity findMate() {
        List<? extends PathAwareEntity> list = this.world.getTargets(this.entityClass, VALID_MATE_PREDICATE, this.animal, this.animal.getBoundingBox().expand(8.0));
        double maxDistance = Double.MAX_VALUE;

        PathAwareEntity PathAwareEntity = null;
        for (PathAwareEntity PathAwareEntity2 : list) {
            if (!((MateEntity) this.animal).canBreedWith(PathAwareEntity2) || !(this.animal.squaredDistanceTo(PathAwareEntity2) < maxDistance))
                continue;

            PathAwareEntity = PathAwareEntity2;
            maxDistance = this.animal.squaredDistanceTo(PathAwareEntity2);
        }

        return PathAwareEntity;
    }

    protected void breed() {
        ((MateEntity) this.animal).breed((ServerWorld) this.world, this.mate);
    }
}
