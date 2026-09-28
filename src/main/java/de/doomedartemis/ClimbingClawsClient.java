package de.doomedartemis;

import de.doomedartemis.client.ClientConfig;
import de.doomedartemis.client.ClientModEvents;
import de.doomedartemis.compat.accessories.client.AccessoriesClientCompat;
import de.doomedartemis.common.event.ClimbingClawsTooltipHandler;
import de.doomedartemis.common.network.ModPayloads;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.loader.api.FabricLoader;

public class ClimbingClawsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientConfig.load();
        ModPayloads.registerClient();
        ClientTickEvents.START_CLIENT_TICK.register(ClientModEvents::onStartClientTick);
        ClientTickEvents.END_CLIENT_TICK.register(ClientModEvents::onEndClientTick);
        ItemTooltipCallback.EVENT.register(ClimbingClawsTooltipHandler::onItemTooltip);
        if (FabricLoader.getInstance().isModLoaded(AccessoriesClientCompat.ACCESSORIES_MOD_ID)) {
            AccessoriesClientCompat.registerRenderers();
        }
    }
}
