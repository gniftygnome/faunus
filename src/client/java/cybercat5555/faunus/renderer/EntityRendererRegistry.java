package cybercat5555.faunus.renderer;

import cybercat5555.faunus.core.EntityRegistry;
import cybercat5555.faunus.renderer.entity.*;

public final class EntityRendererRegistry {
    private EntityRendererRegistry() {
    }

    public static void init() {
        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(EntityRegistry.SONGBIRD, SongbirdEntityRenderer::new);
        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(EntityRegistry.CAPUCHIN, CapuchinEntityRenderer::new);
        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(EntityRegistry.TAPIR, TapirEntityRenderer::new);
        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(EntityRegistry.CONSTRICTOR, ConstrictorEntityRenderer::new);
        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(EntityRegistry.QUETZAL, QuetzalEntityRenderer::new);
        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(EntityRegistry.HOATZIN, HoatzinEntityRenderer::new);
        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(EntityRegistry.PIRANHA, PiranhaEntityRenderer::new);
        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(EntityRegistry.ARAPAIMA, ArapaimaEntityRenderer::new);
        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(EntityRegistry.SNAPPING_TURTLE, SnappingTurtleEntityRenderer::new);
        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(EntityRegistry.CRAYFISH, CrayfishEntityRenderer::new);
        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(EntityRegistry.LEECH, LeechEntityRenderer::new);
        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(EntityRegistry.YACARE, YacareEntityRenderer::new);
        net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry.register(EntityRegistry.YACARE_MANEATER, YacareManEaterEntityRenderer::new);
    }
}
