package de.artemis.climbingclaws;

import de.artemis.climbingclaws.client.ClientModEvents;
import de.artemis.climbingclaws.common.datagen.DataGenerators;
import de.artemis.climbingclaws.common.event.ClimbingClawsAnvilHandler;
import de.artemis.climbingclaws.common.event.ClimbingClawsClimbHandler;
import de.artemis.climbingclaws.common.event.ClimbingClawsTooltipHandler;
import de.artemis.climbingclaws.common.network.ModPayloads;
import de.artemis.climbingclaws.common.registry.ModCriteriaTriggers;
import de.artemis.climbingclaws.common.registry.ModCreativeModeTabs;
import de.artemis.climbingclaws.common.registry.ModItems;
import de.artemis.climbingclaws.common.registry.ModStats;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;

@Mod(ClimbingClaws.MOD_ID)
public class ClimbingClaws {
    public static final String MOD_ID = "climbingclaws";

    public ClimbingClaws(IEventBus modEventBus) {
        ModItems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        modEventBus.addListener(DataGenerators::gatherClientData);
        modEventBus.addListener(DataGenerators::gatherServerData);
        modEventBus.addListener(ModPayloads::register);
        modEventBus.addListener(ModCriteriaTriggers::register);
        modEventBus.addListener(ModStats::register);
        if (FMLEnvironment.getDist() == Dist.CLIENT) {
            modEventBus.addListener(ClientModEvents::onClientSetup);
            modEventBus.addListener(ClientModEvents::onRegisterItemDecorations);
            NeoForge.EVENT_BUS.addListener(ClientModEvents::onClientTick);
            NeoForge.EVENT_BUS.addListener(ClimbingClawsTooltipHandler::onItemTooltip);
        }
        NeoForge.EVENT_BUS.addListener(ClimbingClawsAnvilHandler::onAnvilUpdate);
        NeoForge.EVENT_BUS.addListener(ClimbingClawsClimbHandler::onPlayerTick);
    }
}
