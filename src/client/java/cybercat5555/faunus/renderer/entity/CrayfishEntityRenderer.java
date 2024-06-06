package cybercat5555.faunus.renderer.entity;

import cybercat5555.faunus.core.entity.livingEntity.CrayfishEntity;
import cybercat5555.faunus.util.FaunusID;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CrayfishEntityRenderer extends GeoEntityRenderer<CrayfishEntity>
{
	public CrayfishEntityRenderer(Context renderManager)
	{
		super(renderManager, new DefaultedEntityGeoModel<CrayfishEntity>(FaunusID.content("crayfish"), false));

	}
}
