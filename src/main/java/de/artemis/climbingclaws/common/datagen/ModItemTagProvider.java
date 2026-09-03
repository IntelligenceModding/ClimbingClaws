package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            @Nullable ExistingFileHelper existingFileHelper
    ) {
        super(output, lookupProvider, CompletableFuture.completedFuture(tag -> Optional.empty()), ClimbingClaws.MOD_ID, existingFileHelper);
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
