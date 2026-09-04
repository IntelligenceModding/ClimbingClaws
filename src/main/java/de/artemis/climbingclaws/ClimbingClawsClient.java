package de.artemis.climbingclaws;

import de.artemis.climbingclaws.client.ClientConfig;
import de.artemis.climbingclaws.client.ClientModEvents;
import de.artemis.climbingclaws.client.ClimbingClawsConfigScreens;
import de.artemis.climbingclaws.common.event.ClimbingClawsTooltipHandler;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = ClimbingClaws.MOD_ID, dist = Dist.CLIENT)
public class ClimbingClawsClient {
    public ClimbingClawsClient(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (IConfigScreenFactory) ClimbingClawsConfigScreens::create);
        modEventBus.addListener(ClientModEvents::onClientSetup);
        modEventBus.addListener(ClientModEvents::onRegisterItemDecorations);
        NeoForge.EVENT_BUS.addListener(ClientModEvents::onClientTick);
        NeoForge.EVENT_BUS.addListener(ClimbingClawsTooltipHandler::onItemTooltip);
    }
}
