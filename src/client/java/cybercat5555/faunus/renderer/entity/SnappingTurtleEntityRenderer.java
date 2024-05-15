package cybercat5555.faunus.renderer.entity;

import cybercat5555.faunus.core.entity.entityBehaviour.SnappingTurtleEntity;
import cybercat5555.faunus.util.FaunusID;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SnappingTurtleEntityRenderer extends GeoEntityRenderer<SnappingTurtleEntity> {
    public SnappingTurtleEntityRenderer(Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<>(FaunusID.content("snapping_turtle"), true));
    }
}
