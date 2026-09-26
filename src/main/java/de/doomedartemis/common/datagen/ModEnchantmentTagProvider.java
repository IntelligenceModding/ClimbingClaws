package de.doomedartemis.common.datagen;

import de.doomedartemis.common.registry.ModEnchantments;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;

public final class ModEnchantmentTagProvider extends FabricTagProvider.EnchantmentTagProvider {
    public ModEnchantmentTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider registries) {
        tag(EnchantmentTags.TRADEABLE)
                .add(ModEnchantments.WALL_SPRING)
                .add(ModEnchantments.CANOPY_GRIP);

        tag(EnchantmentTags.IN_ENCHANTING_TABLE)
                .add(ModEnchantments.WALL_SPRING)
                .add(ModEnchantments.CANOPY_GRIP);

        tag(EnchantmentTags.NON_TREASURE)
                .add(ModEnchantments.WALL_SPRING)
                .add(ModEnchantments.CANOPY_GRIP);
    }
}
