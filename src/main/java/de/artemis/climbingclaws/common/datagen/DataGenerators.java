package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

public final class DataGenerators {
    private static final RegistrySetBuilder DATAPACK_REGISTRY_BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, de.artemis.climbingclaws.common.registry.ModEnchantments::bootstrap);

    private DataGenerators() {
    }

    public static void gatherServerData(GatherDataEvent.Server event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        var datapackProvider = new DatapackBuiltinEntriesProvider(
                packOutput,
                event.getLookupProvider(),
                DATAPACK_REGISTRY_BUILDER,
                Set.of(ClimbingClaws.MOD_ID)
        );
        event.addProvider(datapackProvider);
        event.addProvider(new ModRecipeProvider.Runner(packOutput, event.getLookupProvider()));
        event.addProvider(new ModItemTagProvider(packOutput, event.getLookupProvider()));
        event.addProvider(new ModEnchantmentTagProvider(packOutput, datapackProvider.getRegistryProvider()));
        event.addProvider(ModLootTableProvider.create(packOutput, datapackProvider.getRegistryProvider()));
        event.addProvider(new ModGlobalLootModifierProvider(packOutput, datapackProvider.getRegistryProvider()));
        event.addProvider(new ModAdvancementProvider(packOutput, datapackProvider.getRegistryProvider()));
    }

    public static void gatherClientData(GatherDataEvent.Client event) {
        event.addProvider(new ModLanguageProvider(event.getGenerator().getPackOutput(), "en_us"));
    }
}
