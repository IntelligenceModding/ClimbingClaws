package de.artemis.climbingclaws;

import de.artemis.climbingclaws.common.config.ClimbingClawsConfig;
import de.artemis.climbingclaws.common.datagen.DataGenerators;
import de.artemis.climbingclaws.common.event.ClimbingClawsAnvilHandler;
import de.artemis.climbingclaws.common.event.ClimbingClawsClimbHandler;
import de.artemis.climbingclaws.common.network.ModPayloads;
import de.artemis.climbingclaws.common.registry.ModCriteriaTriggers;
import de.artemis.climbingclaws.common.registry.ModCreativeModeTabs;
import de.artemis.climbingclaws.common.registry.ModItems;
import de.artemis.climbingclaws.common.registry.ModStats;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;

@Mod(ClimbingClaws.MOD_ID)
public class ClimbingClaws {
    public static final String MOD_ID = "climbingclaws";

    public ClimbingClaws(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, ClimbingClawsConfig.SERVER_SPEC);
        ModItems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        modEventBus.addListener(DataGenerators::gatherData);
        modEventBus.addListener(ModPayloads::register);
        modEventBus.addListener(ModCriteriaTriggers::register);
        modEventBus.addListener(ModStats::register);
        NeoForge.EVENT_BUS.addListener(ClimbingClawsAnvilHandler::onAnvilUpdate);
        NeoForge.EVENT_BUS.addListener(ClimbingClawsClimbHandler::onPlayerTick);
    }
}
