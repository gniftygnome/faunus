package cybercat5555.faunus.renderer.entity;

import com.google.common.collect.Maps;
import cybercat5555.faunus.core.entity.livingEntity.CrayfishEntity;
import cybercat5555.faunus.core.entity.livingEntity.variant.CrayfishVariant;
import cybercat5555.faunus.util.FaunusID;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib.model.DefaultedEntityGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

public class CrayfishEntityRenderer extends GeoEntityRenderer<CrayfishEntity> {

	public static final Map<CrayfishVariant, Identifier> TEXTURES = Util.make(Maps.newEnumMap(CrayfishVariant.class), (map) -> {
		map.put(CrayfishVariant.DEFAULT, FaunusID.content("textures/entity/crayfish.png"));
		map.put(CrayfishVariant.BLUE, FaunusID.content("textures/entity/crayfish_blue.png"));
	});

    public CrayfishEntityRenderer(Context renderManager) {
        super(renderManager, new DefaultedEntityGeoModel<CrayfishEntity>(FaunusID.content("crayfish"), false));
    }

	@Override
	public Identifier getTextureLocation(CrayfishEntity entity) {
		return TEXTURES.get(entity.getVariant());
	}
}
