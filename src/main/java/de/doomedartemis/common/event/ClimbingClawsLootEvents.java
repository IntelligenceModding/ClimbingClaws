package de.doomedartemis.common.event;

import de.doomedartemis.common.registry.ModLootTables;
import java.util.List;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;

public final class ClimbingClawsLootEvents {
    private static final List<Injection> INJECTIONS = List.of(
            injection(BuiltInLootTables.VILLAGE_WEAPONSMITH, ModLootTables.CLIMBING_CLAWS_VILLAGE_SMITH),
            injection(BuiltInLootTables.VILLAGE_TOOLSMITH, ModLootTables.CLIMBING_CLAWS_VILLAGE_SMITH),
            injection(BuiltInLootTables.VILLAGE_ARMORER, ModLootTables.CLIMBING_CLAWS_VILLAGE_SMITH),
            injection(BuiltInLootTables.ABANDONED_MINESHAFT, ModLootTables.CLIMBING_CLAWS_EXPLORATION),
            injection(BuiltInLootTables.SIMPLE_DUNGEON, ModLootTables.CLIMBING_CLAWS_EXPLORATION),
            injection(BuiltInLootTables.STRONGHOLD_CORRIDOR, ModLootTables.CLIMBING_CLAWS_STRONGHOLD),
            injection(BuiltInLootTables.STRONGHOLD_CROSSING, ModLootTables.CLIMBING_CLAWS_STRONGHOLD),
            injection(BuiltInLootTables.TRIAL_CHAMBERS_SUPPLY, ModLootTables.CLIMBING_CLAWS_TRIAL_CHAMBERS),
            injection(BuiltInLootTables.TRIAL_CHAMBERS_CORRIDOR, ModLootTables.CLIMBING_CLAWS_TRIAL_CHAMBERS),
            injection(BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION, ModLootTables.CLIMBING_CLAWS_TRIAL_CHAMBERS),
            injection(BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION_BARREL, ModLootTables.CLIMBING_CLAWS_TRIAL_CHAMBERS),
            injection(BuiltInLootTables.TRIAL_CHAMBERS_ENTRANCE, ModLootTables.CLIMBING_CLAWS_TRIAL_CHAMBERS),
            injection(BuiltInLootTables.ANCIENT_CITY, ModLootTables.CLIMBING_CLAWS_ANCIENT_CITY),
            injection(BuiltInLootTables.SIMPLE_DUNGEON, ModLootTables.WALL_SPRING_DUNGEON),
            injection(BuiltInLootTables.STRONGHOLD_LIBRARY, ModLootTables.WALL_SPRING_LIBRARY),
            injection(BuiltInLootTables.WOODLAND_MANSION, ModLootTables.WALL_SPRING_WOODLAND),
            injection(BuiltInLootTables.ANCIENT_CITY, ModLootTables.WALL_SPRING_ANCIENT_CITY),
            injection(BuiltInLootTables.TRIAL_CHAMBERS_REWARD, ModLootTables.WALL_SPRING_TRIAL_REWARD),
            injection(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS, ModLootTables.WALL_SPRING_TRIAL_REWARD_OMINOUS),
            injection(BuiltInLootTables.SIMPLE_DUNGEON, ModLootTables.CANOPY_GRIP_DUNGEON),
            injection(BuiltInLootTables.STRONGHOLD_LIBRARY, ModLootTables.CANOPY_GRIP_LIBRARY),
            injection(BuiltInLootTables.WOODLAND_MANSION, ModLootTables.CANOPY_GRIP_WOODLAND),
            injection(BuiltInLootTables.ANCIENT_CITY, ModLootTables.CANOPY_GRIP_ANCIENT_CITY),
            injection(BuiltInLootTables.TRIAL_CHAMBERS_REWARD, ModLootTables.CANOPY_GRIP_TRIAL_REWARD)
    );

    private ClimbingClawsLootEvents() {
    }

    public static void register() {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            if (!source.isBuiltin()) {
                return;
            }
            for (Injection injection : INJECTIONS) {
                if (injection.target() == key) {
                    tableBuilder.withPool(LootPool.lootPool()
                            .add(NestedLootTable.lootTableReference(injection.injected())));
                }
            }
        });
    }

    private static Injection injection(ResourceKey<LootTable> target, ResourceKey<LootTable> injected) {
        return new Injection(target, injected);
    }

    private record Injection(ResourceKey<LootTable> target, ResourceKey<LootTable> injected) {
    }
}
