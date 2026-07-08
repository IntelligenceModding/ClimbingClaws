package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

public final class DataGenerators {
    private static final RegistrySetBuilder DATAPACK_REGISTRY_BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, de.artemis.climbingclaws.common.registry.ModEnchantments::bootstrap);

    private DataGenerators() {
    }

    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        var lookupProvider = event.getLookupProvider();

        var datapackProvider = new DatapackBuiltinEntriesProvider(
                packOutput,
                lookupProvider,
                DATAPACK_REGISTRY_BUILDER,
                Set.of(ClimbingClaws.MOD_ID)
        );
        generator.addProvider(event.includeServer(), datapackProvider);
        generator.addProvider(event.includeServer(), new ModRecipeProvider(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), new ModItemTagProvider(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEnchantmentTagProvider(packOutput, datapackProvider.getRegistryProvider(), existingFileHelper));
        generator.addProvider(event.includeServer(), ModLootTableProvider.create(packOutput, datapackProvider.getRegistryProvider()));
        generator.addProvider(event.includeServer(), new ModGlobalLootModifierProvider(packOutput, datapackProvider.getRegistryProvider()));
        generator.addProvider(event.includeServer(), new ModAdvancementProvider(packOutput, datapackProvider.getRegistryProvider(), existingFileHelper));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new ModLanguageProvider(packOutput, "en_us"));
    }
}
