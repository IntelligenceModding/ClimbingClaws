package de.doomedartemis.common.datagen;

import de.doomedartemis.ClimbingClaws;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

public final class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    private static final ResourceKey<Item> CLIMBING_CLAWS = ResourceKey.create(
            Registries.ITEM,
            ResourceLocation.fromNamespaceAndPath(ClimbingClaws.MOD_ID, "climbing_claws")
    );

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
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
