package cybercat5555.faunus.core.entity.control.move;

import cybercat5555.faunus.util.MCUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.PathNodeMaker;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;

import java.util.Random;

public class FlightWalkMoveControl extends MoveControl {
    private final int maxPitchChange;
    private final boolean noGravity;
    private MoveType type;
    private State previousState;

    public FlightWalkMoveControl(MobEntity entity, int maxPitchChange, boolean noGravity) {
        super(entity);
        this.maxPitchChange = maxPitchChange;
        this.noGravity = noGravity;
    }

    public void changeMovementType(MoveType type) {
        this.type = type;
    }

    public void tick() {
        if (previousState == State.WAIT && this.state == State.MOVE_TO) {
            startMovement();
        }
        previousState = this.state;

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

        if (squaredDistance < 1) {
            stopMovement();
            return;
        }

        handleYawRotation(xDistance, zDistance);

        if (type.equals(MoveType.WALK)) {
            handleWalkMovement(squaredDistance, yDistance);
        } else if (type.equals(MoveType.FLY)) {
            handleFlyMovement(xDistance, yDistance, zDistance);
        }
    }

    private void handleOtherStates() {
        if (!this.noGravity) {
            this.entity.setNoGravity(false);
        }

        this.entity.setUpwardSpeed(0.0F);
        this.entity.setForwardSpeed(0.0F);
    }

    private void handleYawRotation(double xDistance, double zDistance) {
        double squareDistance = xDistance * xDistance + zDistance * zDistance;

        if (squareDistance < 1) {
            float newYaw = (float) (MathHelper.atan2(zDistance, xDistance) * 57.2957763671875) - 90.0F;
            this.entity.setYaw(this.wrapDegrees(this.entity.getYaw(), newYaw, 90.0F));
        }
    }

    private void handlePitchRotation(double yDistance, double distanceXZ) {
        float newPitch = (float) (-(MathHelper.atan2(yDistance, distanceXZ) * 57.2957763671875));
        this.entity.setPitch(this.wrapDegrees(this.entity.getPitch(), newPitch, (float) this.maxPitchChange));
    }

    private void handleFlyMovement(double xDistance, double yDistance, double zDistance) {
        double distanceXZ = Math.sqrt(xDistance * xDistance + zDistance * zDistance);
        float speed = (float) (entity.isOnGround() ?
                this.speed * entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) :
                this.speed * entity.getAttributeValue(EntityAttributes.GENERIC_FLYING_SPEED));

        this.entity.setMovementSpeed(speed);

        if (type.equals(MoveType.FLY) && Math.abs(yDistance) > 9.999999747378752E-6 || Math.abs(distanceXZ) > 9.999999747378752E-6) {
            handlePitchRotation(yDistance, distanceXZ);
            this.entity.setUpwardSpeed(yDistance > 0.0 ? speed : -speed);
        }
    }

    private void handleWalkMovement(double squaredDistance, double yDistance) {
        this.state = MoveControl.State.WAIT;
        BlockPos blockPos = this.entity.getBlockPos();
        BlockState blockState = this.entity.getWorld().getBlockState(blockPos);
        VoxelShape voxelShape = blockState.getCollisionShape(this.entity.getWorld(), blockPos);
        double distanceToGround = this.entity.getY() - MCUtil.getWorldSurface(this.entity.getWorld(), this.entity.getBlockPos());
        double stepHeight = this.entity.getStepHeight();
        double entityWidth = Math.max(1.0F, this.entity.getWidth());
        double maxY = voxelShape.getMax(Direction.Axis.Y) + (double) blockPos.getY();

        this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)));

        if (!this.isPosWalkable((float) targetX, (float) targetZ)) {
            this.forwardMovement = 1.0F;
            this.sidewaysMovement = 0.0F;
        }

        if (distanceToGround > 0.5) {
            this.entity.setUpwardSpeed(-0.5F);
        }

        if (yDistance > stepHeight && squaredDistance < entityWidth ||
                (!voxelShape.isEmpty() && this.entity.getY() < maxY &&
                        !blockState.isIn(BlockTags.DOORS) && !blockState.isIn(BlockTags.FENCES))) {
            this.entity.getJumpControl().setActive();
            this.state = MoveControl.State.JUMPING;
        }
    }

    private boolean isPosWalkable(float x, float z) {
        EntityNavigation entityNavigation = this.entity.getNavigation();
        if (entityNavigation != null) {
            PathNodeMaker pathNodeMaker = entityNavigation.getNodeMaker();
            if (pathNodeMaker != null && pathNodeMaker.getDefaultNodeType(this.entity.getWorld(), MathHelper.floor(this.entity.getX() + (double) x), this.entity.getBlockY(), MathHelper.floor(this.entity.getZ() + (double) z)) != PathNodeType.WALKABLE) {
                return false;
            }
        }

        return true;
    }

    private void startMovement() {
        boolean mustFly = new Random().nextFloat() < 0.01F;

        FlightWalkMoveControl.MoveType type = mustFly ? FlightWalkMoveControl.MoveType.FLY : FlightWalkMoveControl.MoveType.WALK;
        changeMovementType(type);
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
