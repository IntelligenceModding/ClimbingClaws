package de.doomedartemis.common.registry;

import de.doomedartemis.ClimbingClaws;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

public final class ModCreativeModeTabs {
    public static final CreativeModeTab CLIMBING_CLAWS_TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.CLIMBING_CLAWS))
            .title(Component.translatable("itemGroup.climbingclaws"))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.CLIMBING_CLAWS);
                output.accept(createEnchantBook(parameters.holders(), ModEnchantments.WALL_SPRING, 1));
                output.accept(createEnchantBook(parameters.holders(), ModEnchantments.WALL_SPRING, 2));
                output.accept(createEnchantBook(parameters.holders(), ModEnchantments.CANOPY_GRIP, 1));
            })
            .build();

    private ModCreativeModeTabs() {
    }

    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id("climbing_claws"), CLIMBING_CLAWS_TAB);
    }

    private static ItemStack createEnchantBook(HolderLookup.Provider holders, ResourceKey<Enchantment> enchantmentKey, int level) {
        Holder<Enchantment> enchantment = holders.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(enchantmentKey);
        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
        stack.enchant(enchantment, level);
        return stack;
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(ClimbingClaws.MOD_ID, path);
    }
}
