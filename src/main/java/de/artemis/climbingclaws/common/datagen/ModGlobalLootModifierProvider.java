package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.registry.ModLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import de.artemis.climbingclaws.common.loot.AddTableLootModifier;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;

import java.util.List;

public final class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    private static final List<ResourceLocation> VILLAGE_SMITH_TABLES = List.of(
            BuiltInLootTables.VILLAGE_WEAPONSMITH,
            BuiltInLootTables.VILLAGE_TOOLSMITH,
            BuiltInLootTables.VILLAGE_ARMORER
    );
    private static final List<ResourceLocation> EARLY_EXPLORATION_TABLES = List.of(
            BuiltInLootTables.ABANDONED_MINESHAFT,
            BuiltInLootTables.SIMPLE_DUNGEON
    );
    private static final List<ResourceLocation> STRONGHOLD_CHEST_TABLES = List.of(
            BuiltInLootTables.STRONGHOLD_CORRIDOR,
            BuiltInLootTables.STRONGHOLD_CROSSING
    );

    public ModGlobalLootModifierProvider(PackOutput output) {
        super(output, ClimbingClaws.MOD_ID);
    }

    @Override
    protected void start() {
        addForEach("climbing_claws_village_smith", ModLootTables.CLIMBING_CLAWS_VILLAGE_SMITH, VILLAGE_SMITH_TABLES);
        addForEach("climbing_claws_exploration", ModLootTables.CLIMBING_CLAWS_EXPLORATION, EARLY_EXPLORATION_TABLES);
        addForEach("climbing_claws_stronghold", ModLootTables.CLIMBING_CLAWS_STRONGHOLD, STRONGHOLD_CHEST_TABLES);
        add("climbing_claws_ancient_city", modifierFor(BuiltInLootTables.ANCIENT_CITY, ModLootTables.CLIMBING_CLAWS_ANCIENT_CITY));

        add("wall_spring_simple_dungeon", modifierFor(BuiltInLootTables.SIMPLE_DUNGEON, ModLootTables.WALL_SPRING_DUNGEON));
        add("wall_spring_stronghold_library", modifierFor(BuiltInLootTables.STRONGHOLD_LIBRARY, ModLootTables.WALL_SPRING_LIBRARY));
        add("wall_spring_woodland_mansion", modifierFor(BuiltInLootTables.WOODLAND_MANSION, ModLootTables.WALL_SPRING_WOODLAND));
        add("wall_spring_ancient_city", modifierFor(BuiltInLootTables.ANCIENT_CITY, ModLootTables.WALL_SPRING_ANCIENT_CITY));
        add("canopy_grip_simple_dungeon", modifierFor(BuiltInLootTables.SIMPLE_DUNGEON, ModLootTables.CANOPY_GRIP_DUNGEON));
        add("canopy_grip_stronghold_library", modifierFor(BuiltInLootTables.STRONGHOLD_LIBRARY, ModLootTables.CANOPY_GRIP_LIBRARY));
        add("canopy_grip_woodland_mansion", modifierFor(BuiltInLootTables.WOODLAND_MANSION, ModLootTables.CANOPY_GRIP_WOODLAND));
        add("canopy_grip_ancient_city", modifierFor(BuiltInLootTables.ANCIENT_CITY, ModLootTables.CANOPY_GRIP_ANCIENT_CITY));
    }

    private void addForEach(String namePrefix, ResourceLocation injectedTable, List<ResourceLocation> targets) {
        for (ResourceLocation target : targets) {
            add(namePrefix + "_" + target.getPath().replace('/', '_'), modifierFor(target, injectedTable));
        }
    }

    private static AddTableLootModifier modifierFor(ResourceLocation target, ResourceLocation injectedTable) {
        LootItemCondition[] conditions = new LootItemCondition[]{
                LootTableIdCondition.builder(target).build()
        };
        return new AddTableLootModifier(conditions, injectedTable);
    }
}
