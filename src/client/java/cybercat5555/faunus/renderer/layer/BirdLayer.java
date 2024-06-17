package cybercat5555.faunus.renderer.layer;

import cybercat5555.faunus.core.entity.livingEntity.SongbirdEntity;
import cybercat5555.faunus.core.entity.livingEntity.variant.BirdVariant;
import cybercat5555.faunus.util.FaunusID;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class BirdLayer extends GeoRenderLayer<SongbirdEntity> {

    private static final Identifier BELLY_TEXTURE = FaunusID.content("textures/entity/songbird/songbird_belly.png");
    private static final Identifier CAPED_TEXTURE = FaunusID.content("textures/entity/songbird/songbird_caped.png");
    private static final Identifier COLLARED_TEXTURE = FaunusID.content("textures/entity/songbird/songbird_collared.png");
    private static final Identifier COUNTERSHADED_TEXTURE = FaunusID.content("textures/entity/songbird/songbird_countershaded.png");
    private static final Identifier HOODED_TEXTURE = FaunusID.content("textures/entity/songbird/songbird_hooded.png");
    private static final Identifier MASKED_TEXTURE = FaunusID.content("textures/entity/songbird/songbird_masked.png");
    private static final Identifier STRIPEWING_TEXTURE = FaunusID.content("textures/entity/songbird/songbird_stripewing.png");

    public BirdLayer(GeoRenderer<SongbirdEntity> entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack poseStack, SongbirdEntity songbird, BakedGeoModel bakedModel, RenderLayer renderType, VertexConsumerProvider bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        BirdVariant variant = songbird.getBirdVariant();

        Identifier texture = switch (variant) {
            default -> throw new IncompatibleClassChangeError();
            case NONE -> getRenderer().getTextureLocation(songbird);
            case BELLY -> BELLY_TEXTURE;
            case CAPED -> CAPED_TEXTURE;
            case COLLARED -> COLLARED_TEXTURE;
            case COUNTERSHADED -> COUNTERSHADED_TEXTURE;
            case HOODED -> HOODED_TEXTURE;
            case MASKED -> MASKED_TEXTURE;
            case STRIPEWING -> STRIPEWING_TEXTURE;
        };

        RenderLayer layer = RenderLayer.getEntityTranslucent(texture);

        getRenderer().reRender(
                getDefaultBakedModel(songbird),
                poseStack,
                bufferSource,
                songbird,
                layer,
                bufferSource.getBuffer(layer),
                partialTick,
                packedLight,
                OverlayTexture.DEFAULT_UV,
                packedOverlay,
                1, 1, 1);
    }
}
