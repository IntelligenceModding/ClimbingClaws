package de.artemis.climbingclaws;

import de.artemis.climbingclaws.common.config.ClimbingClawsConfig;
import de.artemis.climbingclaws.common.datagen.DataGenerators;
import de.artemis.climbingclaws.common.event.ClimbingClawsAnvilHandler;
import de.artemis.climbingclaws.common.event.ClimbingClawsClimbHandler;
import de.artemis.climbingclaws.common.loot.ModLootModifierSerializers;
import de.artemis.climbingclaws.common.network.ModPayloads;
import de.artemis.climbingclaws.common.registry.ModCriteriaTriggers;
import de.artemis.climbingclaws.common.registry.ModCreativeModeTabs;
import de.artemis.climbingclaws.common.registry.ModItems;
import de.artemis.climbingclaws.common.registry.ModStats;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.api.distmarker.Dist;

@Mod(ClimbingClaws.MOD_ID)
public class ClimbingClaws {
    public static final String MOD_ID = "climbingclaws";

    public ClimbingClaws(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        context.registerConfig(ModConfig.Type.SERVER, ClimbingClawsConfig.SERVER_SPEC);
        ModItems.register(modEventBus);
        ModCreativeModeTabs.register(modEventBus);
        ModLootModifierSerializers.register(modEventBus);
        modEventBus.addListener(DataGenerators::gatherData);
        modEventBus.addListener(ModCriteriaTriggers::register);
        modEventBus.addListener(ModStats::register);
        ModPayloads.register();
        MinecraftForge.EVENT_BUS.addListener(ClimbingClawsAnvilHandler::onAnvilUpdate);
        MinecraftForge.EVENT_BUS.addListener(ClimbingClawsClimbHandler::onPlayerTick);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> ClimbingClawsClientSetup.init(context, modEventBus));
    }
}
