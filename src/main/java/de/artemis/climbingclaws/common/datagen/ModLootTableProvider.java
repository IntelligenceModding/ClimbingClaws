package de.artemis.climbingclaws.common.datagen;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

public final class ModLootTableProvider {
    private ModLootTableProvider() {
    }

    public static LootTableProvider create(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        return new LootTableProvider(
                output,
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(ModChestLootSubProvider::new, LootContextParamSets.CHEST)),
                registriesFuture
        );
    }
}
