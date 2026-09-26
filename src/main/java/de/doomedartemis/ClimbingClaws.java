package de.doomedartemis;

import de.doomedartemis.common.config.ClimbingClawsConfig;
import de.doomedartemis.common.event.ClimbingClawsClimbHandler;
import de.doomedartemis.common.event.ClimbingClawsLootEvents;
import de.doomedartemis.common.network.ModPayloads;
import de.doomedartemis.common.registry.ModCriteriaTriggers;
import de.doomedartemis.common.registry.ModCreativeModeTabs;
import de.doomedartemis.common.registry.ModItems;
import de.doomedartemis.common.registry.ModStats;
import de.doomedartemis.compat.accessories.AccessoriesCompat;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.loader.api.FabricLoader;

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
        if (FabricLoader.getInstance().isModLoaded(AccessoriesCompat.ACCESSORIES_MOD_ID)) {
            AccessoriesCompat.register();
        }
        ServerTickEvents.END_WORLD_TICK.register(world -> world.players().forEach(ClimbingClawsClimbHandler::onPlayerTick));
    }
}
