package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.KeyTagProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends KeyTagProvider<Item> {
    private static final ResourceKey<Item> CLIMBING_CLAWS = ResourceKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(ClimbingClaws.MOD_ID, "climbing_claws")
    );

    public ModItemTagProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider
    ) {
        super(output, Registries.ITEM, lookupProvider, ClimbingClaws.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ItemTags.SHARP_WEAPON_ENCHANTABLE)
                .add(CLIMBING_CLAWS);

        tag(ItemTags.FIRE_ASPECT_ENCHANTABLE)
                .add(CLIMBING_CLAWS);

        tag(ItemTags.DURABILITY_ENCHANTABLE)
                .add(CLIMBING_CLAWS);

        tag(ItemTags.MINING_ENCHANTABLE)
                .add(CLIMBING_CLAWS);
    }
}
