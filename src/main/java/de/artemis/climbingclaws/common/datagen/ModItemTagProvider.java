package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ClimbingClaws.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        var climbingClaws = ModItems.CLIMBING_CLAWS.get().builtInRegistryHolder().key();

        tag(ItemTags.WEAPON_ENCHANTABLE)
                .add(climbingClaws);

        tag(ItemTags.SHARP_WEAPON_ENCHANTABLE)
                .add(climbingClaws);

        tag(ItemTags.FIRE_ASPECT_ENCHANTABLE)
                .add(climbingClaws);

        tag(ItemTags.DURABILITY_ENCHANTABLE)
                .add(climbingClaws);

        tag(ItemTags.MINING_ENCHANTABLE)
                .add(climbingClaws);

        tag(ItemTags.MELEE_WEAPON_ENCHANTABLE)
                .add(climbingClaws);
    }
}
