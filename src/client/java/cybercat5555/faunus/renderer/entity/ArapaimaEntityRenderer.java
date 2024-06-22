package cybercat5555.faunus.renderer.entity;

import cybercat5555.faunus.core.entity.livingEntity.ArapaimaEntity;
import cybercat5555.faunus.util.FaunusID;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArapaimaEntityRenderer extends GeoEntityRenderer<ArapaimaEntity> {
    public ArapaimaEntityRenderer(Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(FaunusID.content("arapaima"), true));
    }

    @Override
    public void render(ArapaimaEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
