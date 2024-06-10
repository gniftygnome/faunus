package cybercat5555.faunus.renderer;

import cybercat5555.faunus.core.EntityRegistry;
import cybercat5555.faunus.renderer.entity.*;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;

import static net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.*;

public final class EntityRendererRegistry {
    private EntityRendererRegistry() {
    }

    public static void init() {
        register(EntityRegistry.SONGBIRD, SongbirdEntityRenderer::new);
        register(EntityRegistry.CAPUCHIN, CapuchinEntityRenderer::new);
        register(EntityRegistry.TAPIR, TapirEntityRenderer::new);
        register(EntityRegistry.CONSTRICTOR, ConstrictorEntityRenderer::new);
        register(EntityRegistry.QUETZAL, QuetzalEntityRenderer::new);
        register(EntityRegistry.HOATZIN, HoatzinEntityRenderer::new);
        register(EntityRegistry.PIRANHA, PiranhaEntityRenderer::new);
        register(EntityRegistry.ARAPAIMA, ArapaimaEntityRenderer::new);
        register(EntityRegistry.SNAPPING_TURTLE, SnappingTurtleEntityRenderer::new);
        register(EntityRegistry.CRAYFISH, CrayfishEntityRenderer::new);
        register(EntityRegistry.LEECH, LeechEntityRenderer::new);
        register(EntityRegistry.YACARE, YacareEntityRenderer::new);
        register(EntityRegistry.YACARE_MANEATER, YacareManEaterEntityRenderer::new);
        register(EntityRegistry.COCOA_BEAN_PROJECTILE, FlyingItemEntityRenderer::new);
    }
}
