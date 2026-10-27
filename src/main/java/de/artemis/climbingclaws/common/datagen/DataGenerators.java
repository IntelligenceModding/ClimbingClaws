package de.artemis.climbingclaws.common.datagen;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.data.event.GatherDataRegistryEntriesEvent;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;

public final class DataGenerators {
    private DataGenerators() {
    }

    public static void gatherRegistries(GatherDataRegistryEntriesEvent event) {
        if (FMLEnvironment.getDist() != Dist.DEDICATED_SERVER) {
            return;
        }
        event.add(Registries.ENCHANTMENT, de.artemis.climbingclaws.common.registry.ModEnchantments::bootstrap)
                .recipe(ModRecipeProvider::new)
                .add(Registries.LOOT_TABLE, ModLootTableProvider.create())
                .add(Registries.ADVANCEMENT, new ModAdvancementProvider());
    }

    public static void gatherClientData(GatherDataEvent.Client event) {
        event.addProvider(new ModLanguageProvider(event.getGenerator().getPackOutput(), "en_us"));
    }

    public static void gatherServerData(GatherDataEvent.Server event) {
        PackOutput packOutput = event.getGenerator().getPackOutput();
        var lookupProvider = event.getWorldLookupProvider();
        event.addProvider(new ModItemTagProvider(packOutput, lookupProvider));
        event.addProvider(new ModEnchantmentTagProvider(packOutput, lookupProvider));
        event.addProvider(new ModGlobalLootModifierProvider(packOutput, event.getReloadableLookupProvider()));
    }
}
