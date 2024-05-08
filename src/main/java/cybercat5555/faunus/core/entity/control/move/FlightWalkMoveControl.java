package cybercat5555.faunus.core.entity.control.move;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;

public class FlightWalkMoveControl extends MoveControl {
    private final int maxPitchChange;
    private final boolean noGravity;
    private MoveType type;

    public FlightWalkMoveControl(MobEntity entity, int maxPitchChange, boolean noGravity) {
        super(entity);
        this.maxPitchChange = maxPitchChange;
        this.noGravity = noGravity;
    }

    public void changeMovementType(MoveType type) {
        this.type = type;
    }

    public void tick() {
        System.out.println(state);

        if (this.state == State.MOVE_TO) {
            handleMoveState();
        } else {
            handleOtherStates();
        }
    }

    private void handleMoveState() {
        this.state = State.WAIT;
        this.entity.setNoGravity(true);
        double xDistance = this.targetX - this.entity.getX();
        double yDistance = this.targetY - this.entity.getY();
        double zDistance = this.targetZ - this.entity.getZ();

        double squaredDistance = xDistance * xDistance + yDistance * yDistance + zDistance * zDistance;

        if (squaredDistance < 2.500000277905201E-7) {
            stopMovement();
            return;
        }

        handleYawRotation(xDistance, zDistance);
        handleMovement(xDistance, yDistance, zDistance);
    }

    private void handleOtherStates() {
        if (!this.noGravity) {
            this.entity.setNoGravity(false);
        }

        this.entity.setUpwardSpeed(0.0F);
        this.entity.setForwardSpeed(0.0F);
    }

    private void handleYawRotation(double xDistance, double zDistance){
        float newYaw = (float) (MathHelper.atan2(zDistance, xDistance) * 57.2957763671875) - 90.0F;
        this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), newYaw, 90.0F));
    }

    private void handlePitchRotation(double yDistance,  double distanceXZ){
        float newPitch = (float) (-(MathHelper.atan2(yDistance, distanceXZ) * 57.2957763671875));
        this.entity.setPitch(this.wrapDegrees(this.entity.getPitch(), newPitch, (float) this.maxPitchChange));
    }

    private void handleMovement(double xDistance, double yDistance, double zDistance){
        double distanceXZ = Math.sqrt(xDistance * xDistance + zDistance * zDistance);
        float speed = (float) (entity.isOnGround() ?
                this.speed * entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) :
                this.speed * entity.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED));

        speed = type.equals(MoveType.FLY) ? speed : entity.isOnGround() ? -speed : 0;
        this.entity.setMovementSpeed(speed);

        if (type.equals(MoveType.FLY) && Math.abs(yDistance) > 9.999999747378752E-6 || Math.abs(distanceXZ) > 9.999999747378752E-6) {
            handlePitchRotation(yDistance, distanceXZ);
            this.entity.setUpwardSpeed(yDistance > 0.0 ? speed : -speed);
        }

        System.out.println(speed);
    }

    private void stopMovement() {
        this.entity.setUpwardSpeed(0.0F);
        this.entity.setForwardSpeed(0.0F);
    }

    public enum MoveType {
        FLY,
        WALK
    }
}
