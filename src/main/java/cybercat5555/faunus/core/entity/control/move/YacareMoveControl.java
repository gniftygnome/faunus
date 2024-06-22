package cybercat5555.faunus.core.entity.control.move;

import cybercat5555.faunus.core.entity.livingEntity.YacareEntity;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.math.MathHelper;

public class YacareMoveControl extends MoveControl {
    private final YacareEntity yacare;

    public YacareMoveControl(YacareEntity yacare) {
        super(yacare);
        this.yacare = yacare;
    }

    private void updateVelocity() {
        if (this.yacare.isTouchingWater()) {
            this.yacare.setVelocity(this.yacare.getVelocity().add(0.0, 0.005, 0.0));
            if (this.yacare.isBaby()) {
                this.yacare.setMovementSpeed(Math.max(this.yacare.getMovementSpeed() / 3.0f, 0.06f));
            }
        } else if (this.yacare.isOnGround()) {
            this.yacare.setMovementSpeed(Math.max(this.yacare.getMovementSpeed() / 2.0f, 0.06f));
        }
    }

    @Override
    public void tick() {
        double f;
        double e;
        this.updateVelocity();
        if (this.state != MoveControl.State.MOVE_TO || this.yacare.getNavigation().isIdle()) {
            this.yacare.setMovementSpeed(0.0f);
            return;
        }
        double d = this.targetX - this.yacare.getX();
        double g = Math.sqrt(d * d + (e = this.targetY - this.yacare.getY()) * e + (f = this.targetZ - this.yacare.getZ()) * f);
        if (g < (double) 1.0E-5f) {
            this.entity.setMovementSpeed(0.0f);
            return;
        }
        e /= g;
        float h = (float) (MathHelper.atan2(f, d) * 57.2957763671875) - 90.0f;
        this.yacare.setYaw(this.wrapDegrees(this.yacare.getYaw(), h, 90.0f));
        this.yacare.bodyYaw = this.yacare.getYaw();
        float i = (float) (this.speed * this.yacare.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED));
        this.yacare.setMovementSpeed(MathHelper.lerp(0.125f, this.yacare.getMovementSpeed(), i));
        this.yacare.setVelocity(this.yacare.getVelocity().add(0.0, (double) this.yacare.getMovementSpeed() * e * 0.1, 0.0));
    }
}