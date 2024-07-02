package cybercat5555.faunus;

import cybercat5555.faunus.core.BlockRegistry;
import cybercat5555.faunus.renderer.EntityRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class FaunusClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        EntityRendererRegistry.init();

        BlockRenderLayerMap.INSTANCE.putBlock(BlockRegistry.ARAPAIMA_EGG, RenderLayer.getCutout());
    }
}