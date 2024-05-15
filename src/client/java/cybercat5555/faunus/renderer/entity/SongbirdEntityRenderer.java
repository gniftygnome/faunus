package cybercat5555.faunus.renderer.entity;

import cybercat5555.faunus.core.entity.entityBehaviour.SongbirdEntity;
import cybercat5555.faunus.util.FaunusID;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SongbirdEntityRenderer extends GeoEntityRenderer<SongbirdEntity> {
    public SongbirdEntityRenderer(Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<SongbirdEntity>(FaunusID.content("songbird"), true)
                .withAltTexture(FaunusID.content("songbird/songbird_base")));
    }
}
