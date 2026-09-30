package de.artemis.climbingclaws;

import de.artemis.climbingclaws.common.config.ClimbingClawsConfig;
import de.artemis.climbingclaws.common.event.ClimbingClawsClimbHandler;
import de.artemis.climbingclaws.common.event.ClimbingClawsLootEvents;
import de.artemis.climbingclaws.common.network.ModPayloads;
import de.artemis.climbingclaws.common.registry.ModCriteriaTriggers;
import de.artemis.climbingclaws.common.registry.ModCreativeModeTabs;
import de.artemis.climbingclaws.common.registry.ModItems;
import de.artemis.climbingclaws.common.registry.ModStats;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;

public class ClimbingClaws implements ModInitializer {
    public static final String MOD_ID = "climbingclaws";

    @Override
    public void onInitialize() {
        ClimbingClawsConfig.load();
        ModItems.register();
        ModCreativeModeTabs.register();
        ModCriteriaTriggers.register();
        ModStats.register();
        ModPayloads.register();
        ClimbingClawsLootEvents.register();
        ServerTickEvents.START_LEVEL_TICK.register(world -> world.players().forEach(ClimbingClawsClimbHandler::onPlayerTick));
    }
}
