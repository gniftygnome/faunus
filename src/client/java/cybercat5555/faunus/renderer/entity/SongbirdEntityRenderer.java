package cybercat5555.faunus.renderer.entity;

import cybercat5555.faunus.core.entity.livingEntity.SongbirdEntity;
import cybercat5555.faunus.core.entity.livingEntity.variant.BirdPatterns;
import cybercat5555.faunus.core.entity.livingEntity.variant.BirdVariant;
import cybercat5555.faunus.util.FaunusID;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SongbirdEntityRenderer extends GeoEntityRenderer<SongbirdEntity> {
    private static final Identifier BELLY_TEXTURE = FaunusID.content("textures/entity/songbird/songbird_belly.png");
    private static final Identifier CAPED_TEXTURE = FaunusID.content("textures/entity/songbird/songbird_caped.png");
    private static final Identifier COLLARED_TEXTURE = FaunusID.content("textures/entity/songbird/songbird_collared.png");
    private static final Identifier COUNTERSHADED_TEXTURE = FaunusID.content("textures/entity/songbird/songbird_countershaded.png");
    private static final Identifier HOODED_TEXTURE = FaunusID.content("textures/entity/songbird/songbird_hooded.png");
    private static final Identifier MASKED_TEXTURE = FaunusID.content("textures/entity/songbird/songbird_masked.png");
    private static final Identifier STRIPEWING_TEXTURE = FaunusID.content("textures/entity/songbird/songbird_stripewing.png");


    public SongbirdEntityRenderer(Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<SongbirdEntity>(FaunusID.content("songbird"), true)
                .withAltTexture(FaunusID.content("songbird/songbird_base")));
    }

    @Override
    public void renderRecursively(MatrixStack poseStack, SongbirdEntity songbird, GeoBone bone, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        BirdVariant variant = songbird.getBirdVariant();
        BirdPatterns pattern = songbird.getBirdPattern();
        float[] color = variant.getPrimaryColor();
        RenderLayer layer = switch (pattern) {
            case NONE -> RenderLayer.getEntityTranslucent(getTextureLocation(songbird));
            case BELLY -> RenderLayer.getEntityTranslucent(BELLY_TEXTURE);
            case CAPED -> RenderLayer.getEntityTranslucent(CAPED_TEXTURE);
            case COLLARED -> RenderLayer.getEntityTranslucent(COLLARED_TEXTURE);
            case COUNTERSHADED -> RenderLayer.getEntityTranslucent(COUNTERSHADED_TEXTURE);
            case HOODED -> RenderLayer.getEntityTranslucent(HOODED_TEXTURE);
            case MASKED -> RenderLayer.getEntityTranslucent(MASKED_TEXTURE);
            case STRIPEWING -> RenderLayer.getEntityTranslucent(STRIPEWING_TEXTURE);
        };

        if (bone.getName().equals("crest") && !variant.shouldRenderCrest()) {
            return;
        } else if (bone.getName().equals("belly") && !variant.shouldRenderCrest()) {
            return;
        } else {

            switch (pattern) {
                case CAPED ->
                        color = getColorBasedOnCondition(variant, bone.getName().contains("tail") || bone.getName().contains("Wing"));
                case COLLARED -> color = getColorBasedOnCondition(variant, bone.getName().contains("throat"));
                case COUNTERSHADED -> color = getColorBasedOnCondition(variant, bone.getName().contains("belly"));
                case HOODED -> color = getColorBasedOnCondition(variant, bone.getName().contains("head"));
                case MASKED -> color = getColorBasedOnCondition(variant, bone.getName().contains("face"));
                case STRIPEWING ->
                        color = getColorBasedOnCondition(variant, bone.getName().contains("belly") || bone.getName().contains("Wing"));
            }

            red = color[0];
            green = color[1];
            blue = color[2];

            super.renderRecursively(poseStack, songbird, bone, renderType, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
        }
    }


    private float[] getColorBasedOnCondition(BirdVariant variant, boolean condition) {
        if (condition) {
            return new float[]{variant.getSecondaryColor()[0], variant.getSecondaryColor()[1], variant.getSecondaryColor()[2]};
        } else {
            return new float[]{variant.getPrimaryColor()[0], variant.getPrimaryColor()[1], variant.getPrimaryColor()[2]};
        }
    }
}