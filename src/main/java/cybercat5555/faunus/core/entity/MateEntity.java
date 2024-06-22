package cybercat5555.faunus.core.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.world.ServerWorld;

public interface MateEntity {


    boolean isInLove();

    void setInLove(int ticks);

    void resetLoveTicks();

    boolean canBreedWith(LivingEntity mate);

    void breed(ServerWorld world, LivingEntity other);

    void breed(ServerWorld world, LivingEntity other, LivingEntity baby);

    MobEntity createChild(ServerWorld world, LivingEntity other);
}