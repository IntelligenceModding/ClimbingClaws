package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.registry.ModItems;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

public final class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
    private static final ResourceKey<Item> CLIMBING_CLAWS = ResourceKey.create(
            Registries.ITEM,
            Identifier.fromNamespaceAndPath(ClimbingClaws.MOD_ID, "climbing_claws")
    );
    private static final ResourceKey<Item> IRON_INGOT = ResourceKey.create(
            Registries.ITEM,
            Identifier.withDefaultNamespace("iron_ingot")
    );
    private static final ResourceKey<Item> IRON_NUGGET = ResourceKey.create(
            Registries.ITEM,
            Identifier.withDefaultNamespace("iron_nugget")
    );

    public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        builder(ItemTags.SHARP_WEAPON_ENCHANTABLE)
                .add(CLIMBING_CLAWS);

        builder(ItemTags.FIRE_ASPECT_ENCHANTABLE)
                .add(CLIMBING_CLAWS);

        builder(ItemTags.DURABILITY_ENCHANTABLE)
                .add(CLIMBING_CLAWS);

        builder(ItemTags.MINING_ENCHANTABLE)
                .add(CLIMBING_CLAWS);

        builder(ItemTags.MELEE_WEAPON_ENCHANTABLE)
                .add(CLIMBING_CLAWS);

        builder(ModItems.CLIMBING_CLAWS_REPAIR_MATERIALS)
                .add(IRON_INGOT)
                .add(IRON_NUGGET);
    }
}
