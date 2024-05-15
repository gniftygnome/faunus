package cybercat5555.faunus.renderer.entity;

import cybercat5555.faunus.core.entity.entityBehaviour.ConstrictorEntity;
import cybercat5555.faunus.util.FaunusID;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ConstrictorEntityRenderer extends GeoEntityRenderer<ConstrictorEntity>
{
	public ConstrictorEntityRenderer(Context renderManager)
	{
		super(renderManager, new DefaultedEntityGeoModel<ConstrictorEntity>(FaunusID.content("constrictor"), false));
	}
}
