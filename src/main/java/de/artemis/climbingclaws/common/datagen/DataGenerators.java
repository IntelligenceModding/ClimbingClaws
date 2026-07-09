package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.Set;

public final class DataGenerators {
    private static final RegistrySetBuilder DATAPACK_REGISTRY_BUILDER = new RegistrySetBuilder()
            .add(Registries.ENCHANTMENT, de.artemis.climbingclaws.common.registry.ModEnchantments::bootstrap);

    private DataGenerators() {
    }

    public static void gatherClientData(GatherDataEvent.Client event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        event.addProvider(new ModLanguageProvider(packOutput, "en_us"));
    }

    public static void gatherServerData(GatherDataEvent.Server event) {
        event.createDatapackRegistryObjects(DATAPACK_REGISTRY_BUILDER, Set.of(ClimbingClaws.MOD_ID));

        PackOutput packOutput = event.getGenerator().getPackOutput();
        var lookupProvider = event.getLookupProvider();

        event.addProvider(new ModRecipeProvider(packOutput, lookupProvider));
        event.addProvider(new ModItemTagProvider(packOutput, lookupProvider));
        event.addProvider(new ModEnchantmentTagProvider(packOutput, lookupProvider));
        event.addProvider(ModLootTableProvider.create(packOutput, lookupProvider));
        event.addProvider(new ModGlobalLootModifierProvider(packOutput, lookupProvider));
        event.addProvider(new ModAdvancementProvider(packOutput, lookupProvider));
    }
}
