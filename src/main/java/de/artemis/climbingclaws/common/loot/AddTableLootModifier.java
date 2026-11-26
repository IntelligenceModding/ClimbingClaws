package de.artemis.climbingclaws.common.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;

public final class AddTableLootModifier extends LootModifier {
    public static final MapCodec<AddTableLootModifier> CODEC = RecordCodecBuilder.mapCodec(instance -> codecStart(instance)
            .and(ResourceKey.codec(Registries.LOOT_TABLE).fieldOf("table").forGetter(modifier -> modifier.table))
            .apply(instance, AddTableLootModifier::new));

    private final ResourceKey<LootTable> table;

    public AddTableLootModifier(LootItemCondition[] conditions, ResourceKey<LootTable> table) {
        super(conditions);
        this.table = table;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        // Roll the injection table without reapplying Forge modifiers for the original chest.
        context.getResolver()
                .get(Registries.LOOT_TABLE, table)
                .map(Holder.Reference::value)
                .ifPresent(lootTable -> lootTable.getRandomItemsRaw(context,
                        LootTable.createStackSplitter(context.getLevel(), generatedLoot::add)));
        return generatedLoot;
    }

    @Override
    public MapCodec<? extends net.minecraftforge.common.loot.IGlobalLootModifier> codec() {
        return CODEC;
    }
}
