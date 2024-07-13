package cybercat5555.faunus.common;

import cybercat5555.faunus.core.entity.livingEntity.YacareManEaterEntity;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class EventManager {

    public static void onAttackBlock() {
        AttackBlockCallback.EVENT.register((PlayerEntity player, World world, Hand hand, BlockPos pos, Direction direction) -> {
            if (player.getVehicle() != null && player.getVehicle() instanceof YacareManEaterEntity && player.isAlive()) {
                player.attack(player.getVehicle());
            }

            return ActionResult.PASS;
        });
    }

    public static void onAttackEntity() {
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player.getVehicle() != null && player.getVehicle() instanceof YacareManEaterEntity && player.isAlive()) {
                float damage = player.getMainHandStack().isEmpty() ? 2 : player.getMainHandStack().getDamage();
                entity.damage(player.getDamageSources().playerAttack(player), damage);
            }

            return ActionResult.PASS;
        });
    }
}
