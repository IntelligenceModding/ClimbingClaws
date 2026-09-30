package de.doomedartemis.common.registry;

import de.doomedartemis.ClimbingClaws;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import java.util.Optional;

public final class ModCreativeModeTabs {
    public static final CreativeModeTab CLIMBING_CLAWS_TAB = FabricCreativeModeTab.builder()
            .icon(() -> new ItemStack(ModItems.CLIMBING_CLAWS))
            .title(Component.translatable("itemGroup.climbingclaws"))
            .displayItems((parameters, output) -> {
                output.accept(ModItems.CLIMBING_CLAWS);
                createEnchantBook(parameters.holders(), ModEnchantments.WALL_SPRING, 1).ifPresent(output::accept);
                createEnchantBook(parameters.holders(), ModEnchantments.WALL_SPRING, 2).ifPresent(output::accept);
                createEnchantBook(parameters.holders(), ModEnchantments.CANOPY_GRIP, 1).ifPresent(output::accept);
            })
            .build();

    private ModCreativeModeTabs() {
    }

    public static void register() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, id("climbing_claws"), CLIMBING_CLAWS_TAB);
    }

    private static Optional<ItemStack> createEnchantBook(HolderLookup.Provider holders, ResourceKey<Enchantment> enchantmentKey, int level) {
        Optional<Holder.Reference<Enchantment>> enchantment = holders.lookup(Registries.ENCHANTMENT)
                .flatMap(enchantments -> enchantments.get(enchantmentKey));
        if (enchantment.isEmpty()) {
            return Optional.empty();
        }

        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
        stack.enchant(enchantment.get(), level);
        return Optional.of(stack);
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(ClimbingClaws.MOD_ID, path);
    }
}
