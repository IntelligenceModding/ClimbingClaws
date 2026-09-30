package de.artemis.climbingclaws;

import de.artemis.climbingclaws.client.ClientConfig;
import de.artemis.climbingclaws.client.ClientModEvents;
import de.artemis.climbingclaws.common.event.ClimbingClawsTooltipHandler;
import de.artemis.climbingclaws.common.network.ModPayloads;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;

public class ClimbingClawsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientConfig.load();
        ModPayloads.registerClient();
        ClientTickEvents.START_CLIENT_TICK.register(ClientModEvents::onStartClientTick);
        ClientTickEvents.END_CLIENT_TICK.register(ClientModEvents::onEndClientTick);
        ItemTooltipCallback.EVENT.register(ClimbingClawsTooltipHandler::onItemTooltip);
    }
}
