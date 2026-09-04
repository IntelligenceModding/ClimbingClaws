package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.registry.ModItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

public class ModItemTagProvider extends IntrinsicHolderTagsProvider<Item> {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, Registries.ITEM, lookupProvider, item -> item.builtInRegistryHolder().key(), ClimbingClaws.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ItemTags.SHARP_WEAPON_ENCHANTABLE)
                .add(ModItems.CLIMBING_CLAWS.get());

        tag(ItemTags.FIRE_ASPECT_ENCHANTABLE)
                .add(ModItems.CLIMBING_CLAWS.get());

        tag(ItemTags.DURABILITY_ENCHANTABLE)
                .add(ModItems.CLIMBING_CLAWS.get());

        tag(ItemTags.MINING_ENCHANTABLE)
                .add(ModItems.CLIMBING_CLAWS.get());

        tag(ItemTags.MELEE_WEAPON_ENCHANTABLE)
                .add(ModItems.CLIMBING_CLAWS.get());
    }
}
