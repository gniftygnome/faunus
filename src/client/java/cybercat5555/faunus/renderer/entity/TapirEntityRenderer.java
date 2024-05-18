package cybercat5555.faunus.renderer.entity;

import cybercat5555.faunus.core.entity.entityBehaviour.TapirEntity;
import cybercat5555.faunus.util.FaunusID;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;


public class TapirEntityRenderer extends GeoEntityRenderer<TapirEntity> {
    public static final Identifier BABY_TEXTURE = FaunusID.content("textures/entity/tapir_baby.png");

    public TapirEntityRenderer(Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(FaunusID.content("tapir"), true));
    }

    @Override
    public void render(TapirEntity entity, float entityYaw, float partialTick, MatrixStack poseStack, VertexConsumerProvider bufferSource, int packedLight) {
        if (entity.isBaby()) {
            poseStack.scale(0.5F, 0.5F, 0.5F);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    public Identifier getTextureLocation(TapirEntity animatable) {
        return animatable.isBaby() ? BABY_TEXTURE : super.getTextureLocation(animatable);
    }
}
