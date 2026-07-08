package de.artemis.climbingclaws.common.registry;

import de.artemis.climbingclaws.ClimbingClaws;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

public final class ModLootTables {
    public static final ResourceKey<LootTable> CLIMBING_CLAWS_VILLAGE_SMITH = chest("injections/climbing_claws_village_smith");
    public static final ResourceKey<LootTable> CLIMBING_CLAWS_EXPLORATION = chest("injections/climbing_claws_exploration");
    public static final ResourceKey<LootTable> CLIMBING_CLAWS_STRONGHOLD = chest("injections/climbing_claws_stronghold");
    public static final ResourceKey<LootTable> CLIMBING_CLAWS_TRIAL_CHAMBERS = chest("injections/climbing_claws_trial_chambers");
    public static final ResourceKey<LootTable> CLIMBING_CLAWS_ANCIENT_CITY = chest("injections/climbing_claws_ancient_city");

    public static final ResourceKey<LootTable> WALL_SPRING_DUNGEON = chest("injections/wall_spring_dungeon");
    public static final ResourceKey<LootTable> WALL_SPRING_LIBRARY = chest("injections/wall_spring_library");
    public static final ResourceKey<LootTable> WALL_SPRING_WOODLAND = chest("injections/wall_spring_woodland");
    public static final ResourceKey<LootTable> WALL_SPRING_ANCIENT_CITY = chest("injections/wall_spring_ancient_city");
    public static final ResourceKey<LootTable> WALL_SPRING_TRIAL_REWARD = chest("injections/wall_spring_trial_reward");
    public static final ResourceKey<LootTable> WALL_SPRING_TRIAL_REWARD_OMINOUS = chest("injections/wall_spring_trial_reward_ominous");
    public static final ResourceKey<LootTable> CANOPY_GRIP_DUNGEON = chest("injections/canopy_grip_dungeon");
    public static final ResourceKey<LootTable> CANOPY_GRIP_LIBRARY = chest("injections/canopy_grip_library");
    public static final ResourceKey<LootTable> CANOPY_GRIP_WOODLAND = chest("injections/canopy_grip_woodland");
    public static final ResourceKey<LootTable> CANOPY_GRIP_ANCIENT_CITY = chest("injections/canopy_grip_ancient_city");
    public static final ResourceKey<LootTable> CANOPY_GRIP_TRIAL_REWARD = chest("injections/canopy_grip_trial_reward");

    private ModLootTables() {
    }

    private static ResourceKey<LootTable> chest(String path) {
        return ResourceKey.create(
                Registries.LOOT_TABLE,
                ResourceLocation.fromNamespaceAndPath(ClimbingClaws.MOD_ID, "chests/" + path)
        );
    }
}
