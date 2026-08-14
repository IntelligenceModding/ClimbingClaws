package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.registry.ModLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    private static final List<ResourceKey<LootTable>> VILLAGE_SMITH_TABLES = List.of(
            BuiltInLootTables.VILLAGE_WEAPONSMITH,
            BuiltInLootTables.VILLAGE_TOOLSMITH,
            BuiltInLootTables.VILLAGE_ARMORER
    );
    private static final List<ResourceKey<LootTable>> EARLY_EXPLORATION_TABLES = List.of(
            BuiltInLootTables.ABANDONED_MINESHAFT,
            BuiltInLootTables.SIMPLE_DUNGEON
    );
    private static final List<ResourceKey<LootTable>> STRONGHOLD_CHEST_TABLES = List.of(
            BuiltInLootTables.STRONGHOLD_CORRIDOR,
            BuiltInLootTables.STRONGHOLD_CROSSING
    );
    private static final List<ResourceKey<LootTable>> TRIAL_CHAMBER_CONTAINER_TABLES = List.of(
            BuiltInLootTables.TRIAL_CHAMBERS_SUPPLY,
            BuiltInLootTables.TRIAL_CHAMBERS_CORRIDOR,
            BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION,
            BuiltInLootTables.TRIAL_CHAMBERS_INTERSECTION_BARREL,
            BuiltInLootTables.TRIAL_CHAMBERS_ENTRANCE
    );

    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, ClimbingClaws.MOD_ID);
    }

    @Override
    protected void start() {
        addForEach("climbing_claws_village_smith", ModLootTables.CLIMBING_CLAWS_VILLAGE_SMITH, VILLAGE_SMITH_TABLES);
        addForEach("climbing_claws_exploration", ModLootTables.CLIMBING_CLAWS_EXPLORATION, EARLY_EXPLORATION_TABLES);
        addForEach("climbing_claws_stronghold", ModLootTables.CLIMBING_CLAWS_STRONGHOLD, STRONGHOLD_CHEST_TABLES);
        addForEach("climbing_claws_trial_chambers", ModLootTables.CLIMBING_CLAWS_TRIAL_CHAMBERS, TRIAL_CHAMBER_CONTAINER_TABLES);
        add("climbing_claws_ancient_city", modifierFor(BuiltInLootTables.ANCIENT_CITY, ModLootTables.CLIMBING_CLAWS_ANCIENT_CITY));

        add("wall_spring_simple_dungeon", modifierFor(BuiltInLootTables.SIMPLE_DUNGEON, ModLootTables.WALL_SPRING_DUNGEON));
        add("wall_spring_stronghold_library", modifierFor(BuiltInLootTables.STRONGHOLD_LIBRARY, ModLootTables.WALL_SPRING_LIBRARY));
        add("wall_spring_woodland_mansion", modifierFor(BuiltInLootTables.WOODLAND_MANSION, ModLootTables.WALL_SPRING_WOODLAND));
        add("wall_spring_ancient_city", modifierFor(BuiltInLootTables.ANCIENT_CITY, ModLootTables.WALL_SPRING_ANCIENT_CITY));
        add("wall_spring_trial_reward", modifierFor(BuiltInLootTables.TRIAL_CHAMBERS_REWARD, ModLootTables.WALL_SPRING_TRIAL_REWARD));
        add("wall_spring_trial_reward_ominous", modifierFor(BuiltInLootTables.TRIAL_CHAMBERS_REWARD_OMINOUS, ModLootTables.WALL_SPRING_TRIAL_REWARD_OMINOUS));
        add("canopy_grip_simple_dungeon", modifierFor(BuiltInLootTables.SIMPLE_DUNGEON, ModLootTables.CANOPY_GRIP_DUNGEON));
        add("canopy_grip_stronghold_library", modifierFor(BuiltInLootTables.STRONGHOLD_LIBRARY, ModLootTables.CANOPY_GRIP_LIBRARY));
        add("canopy_grip_woodland_mansion", modifierFor(BuiltInLootTables.WOODLAND_MANSION, ModLootTables.CANOPY_GRIP_WOODLAND));
        add("canopy_grip_ancient_city", modifierFor(BuiltInLootTables.ANCIENT_CITY, ModLootTables.CANOPY_GRIP_ANCIENT_CITY));
        add("canopy_grip_trial_reward", modifierFor(BuiltInLootTables.TRIAL_CHAMBERS_REWARD, ModLootTables.CANOPY_GRIP_TRIAL_REWARD));
    }

    private void addForEach(String namePrefix, ResourceKey<LootTable> injectedTable, List<ResourceKey<LootTable>> targets) {
        for (ResourceKey<LootTable> target : targets) {
            add(namePrefix + "_" + target.identifier().getPath().replace('/', '_'), modifierFor(target, injectedTable));
        }
    }

    private static AddTableLootModifier modifierFor(ResourceKey<LootTable> target, ResourceKey<LootTable> injectedTable) {
        LootItemCondition[] conditions = new LootItemCondition[]{
                LootTableIdCondition.builder(target.identifier()).build()
        };
        return new AddTableLootModifier(conditions, injectedTable);
    }
}
