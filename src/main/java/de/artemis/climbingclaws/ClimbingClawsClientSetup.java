package de.artemis.climbingclaws;

import de.artemis.climbingclaws.client.ClientConfig;
import de.artemis.climbingclaws.client.ClientModEvents;
import de.artemis.climbingclaws.client.ClimbingClawsConfigScreens;
import de.artemis.climbingclaws.common.event.ClimbingClawsTooltipHandler;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.ModLoadingContext;

final class ClimbingClawsClientSetup {
    private ClimbingClawsClientSetup() {
    }

    static void init(ModLoadingContext context, IEventBus modEventBus) {
        context.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        context.registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> ClimbingClawsConfigScreens.create(parent))
        );
        modEventBus.addListener(ClientModEvents::onClientSetup);
        modEventBus.addListener(ClientModEvents::onRegisterItemDecorations);
        MinecraftForge.EVENT_BUS.addListener(ClientModEvents::onClientTick);
        MinecraftForge.EVENT_BUS.addListener(ClimbingClawsTooltipHandler::onItemTooltip);
    }
}
