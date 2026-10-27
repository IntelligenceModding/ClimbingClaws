package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.common.registry.ModEnchantments;
import de.artemis.climbingclaws.common.registry.ModItems;
import de.artemis.climbingclaws.common.registry.ModLootTables;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
import net.minecraft.world.level.storage.loot.entries.SingleEntryContainerBase;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetEnchantmentsFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemDamageFunction;
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProviders;
import net.minecraft.world.level.storage.loot.providers.number.floats.ContextFloatProviders;


public final class ModChestLootSubProvider implements LootTableSubProvider {
    private final LootTableSubProvider.Context output;
    private final Holder<Enchantment> wallSpring;
    private final Holder<Enchantment> canopyGrip;

    public ModChestLootSubProvider(LootTableSubProvider.Context provider) {
        this.output = provider;
        this.wallSpring = provider.lookup(Registries.ENCHANTMENT).getOrThrow(ModEnchantments.WALL_SPRING);
        this.canopyGrip = provider.lookup(Registries.ENCHANTMENT).getOrThrow(ModEnchantments.CANOPY_GRIP);
    }

    @Override
    public void run() {
        output.accept(ModLootTables.CLIMBING_CLAWS_VILLAGE_SMITH, clawsTable(95, 5, 0.45F, 0.80F));
        output.accept(ModLootTables.CLIMBING_CLAWS_EXPLORATION, clawsTable(92, 8, 0.55F, 0.90F));
        output.accept(ModLootTables.CLIMBING_CLAWS_STRONGHOLD, clawsTable(90, 10, 0.65F, 0.95F));
        output.accept(ModLootTables.CLIMBING_CLAWS_TRIAL_CHAMBERS, clawsTable(89, 11, 0.70F, 1.00F));
        output.accept(ModLootTables.CLIMBING_CLAWS_ANCIENT_CITY, clawsTable(86, 14, 0.80F, 1.00F));

        output.accept(ModLootTables.WALL_SPRING_DUNGEON, wallSpringTable(96, 4, 0));
        output.accept(ModLootTables.WALL_SPRING_LIBRARY, wallSpringTable(88, 9, 3));
        output.accept(ModLootTables.WALL_SPRING_WOODLAND, wallSpringTable(93, 5, 2));
        output.accept(ModLootTables.WALL_SPRING_ANCIENT_CITY, wallSpringTable(90, 6, 4));
        output.accept(ModLootTables.WALL_SPRING_TRIAL_REWARD, wallSpringTable(94, 5, 1));
        output.accept(ModLootTables.WALL_SPRING_TRIAL_REWARD_OMINOUS, wallSpringTable(90, 6, 4));

        output.accept(ModLootTables.CANOPY_GRIP_DUNGEON, singleEnchantmentBookTable(this.canopyGrip, 95, 5));
        output.accept(ModLootTables.CANOPY_GRIP_LIBRARY, singleEnchantmentBookTable(this.canopyGrip, 90, 10));
        output.accept(ModLootTables.CANOPY_GRIP_WOODLAND, singleEnchantmentBookTable(this.canopyGrip, 94, 6));
        output.accept(ModLootTables.CANOPY_GRIP_ANCIENT_CITY, singleEnchantmentBookTable(this.canopyGrip, 92, 8));
        output.accept(ModLootTables.CANOPY_GRIP_TRIAL_REWARD, singleEnchantmentBookTable(this.canopyGrip, 95, 5));
    }

    private static LootTable.Builder clawsTable(int emptyWeight, int clawsWeight, float minDurability, float maxDurability) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(EmptyLootItem.emptyItem().setWeight(emptyWeight))
                                .add(clawsEntry(clawsWeight, minDurability, maxDurability))
                );
    }

    private LootTable.Builder wallSpringTable(int emptyWeight, int levelOneWeight, int levelTwoWeight) {
        LootPool.Builder pool = LootPool.lootPool()
                .setRolls(ContextIntProviders.exactly(1))
                .add(EmptyLootItem.emptyItem().setWeight(emptyWeight))
                .add(wallSpringBookEntry(1, levelOneWeight));

        if (levelTwoWeight > 0) {
            pool.add(wallSpringBookEntry(2, levelTwoWeight));
        }

        return LootTable.lootTable().withPool(pool);
    }

    private LootTable.Builder singleEnchantmentBookTable(Holder<Enchantment> enchantment, int emptyWeight, int weight) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ContextIntProviders.exactly(1))
                                .add(EmptyLootItem.emptyItem().setWeight(emptyWeight))
                                .add(singleEnchantmentBookEntry(enchantment, weight))
                );
    }

    private static SingleEntryContainerBase.Builder<?> clawsEntry(int weight, float minDurability, float maxDurability) {
        return LootItem.lootTableItem(ModItems.CLIMBING_CLAWS.get())
                .setWeight(weight)
                .apply(SetItemDamageFunction.setDamage(ContextFloatProviders.between(minDurability, maxDurability)));
    }

    private SingleEntryContainerBase.Builder<?> wallSpringBookEntry(int level, int weight) {
        return LootItem.lootTableItem(Items.BOOK)
                .setWeight(weight)
                .apply(new SetEnchantmentsFunction.Builder().withEnchantment(this.wallSpring, ContextIntProviders.exactly(level)));
    }

    private SingleEntryContainerBase.Builder<?> singleEnchantmentBookEntry(Holder<Enchantment> enchantment, int weight) {
        return LootItem.lootTableItem(Items.BOOK)
                .setWeight(weight)
                .apply(new SetEnchantmentsFunction.Builder().withEnchantment(enchantment, ContextIntProviders.exactly(1)));
    }
}
