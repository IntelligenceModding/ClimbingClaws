package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider
    ) {
        super(output, lookupProvider, CompletableFuture.completedFuture(TagsProvider.TagLookup.<Block>empty()), ClimbingClaws.MOD_ID);
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
    }
}
