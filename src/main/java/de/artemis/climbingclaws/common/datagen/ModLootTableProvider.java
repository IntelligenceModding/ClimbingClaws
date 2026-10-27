package de.artemis.climbingclaws.common.datagen;

import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;

public final class ModLootTableProvider {
    private ModLootTableProvider() {
    }

    public static LootTableProvider create() {
        return new LootTableProvider(
                Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(ModChestLootSubProvider::new, LootContextParamSets.CHEST))
        );
    }
}
