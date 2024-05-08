package cybercat5555.faunus;

import cybercat5555.faunus.renderer.EntityRendererRegistry;
import net.fabricmc.api.ClientModInitializer;

public class FaunusClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		EntityRendererRegistry.init();
	}
}