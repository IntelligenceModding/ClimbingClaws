package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.registry.ModEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EnchantmentTagsProvider;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModEnchantmentTagProvider extends EnchantmentTagsProvider {
    public ModEnchantmentTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ClimbingClaws.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
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
