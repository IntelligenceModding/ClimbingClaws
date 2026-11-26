package de.artemis.climbingclaws.common.loot;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;

public final class AddTableLootModifier extends LootModifier {
    public static final Codec<AddTableLootModifier> CODEC = RecordCodecBuilder.create(instance -> codecStart(instance)
            .and(ResourceLocation.CODEC.fieldOf("table").forGetter(modifier -> modifier.table))
            .apply(instance, AddTableLootModifier::new));

    private final ResourceLocation table;

    public AddTableLootModifier(LootItemCondition[] conditions, ResourceLocation table) {
        super(conditions);
        this.table = table;
    }

    @Override
    @SuppressWarnings("deprecation")
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        // Roll the injection table without reapplying Forge modifiers for the original chest.
        LootTable lootTable = context.getLevel().getServer().getLootData().getLootTable(table);
        lootTable.getRandomItemsRaw(context, LootTable.createStackSplitter(context.getLevel(), generatedLoot::add));
        return generatedLoot;
    }

    @Override
    public Codec<? extends net.minecraftforge.common.loot.IGlobalLootModifier> codec() {
        return CODEC;
    }
}
