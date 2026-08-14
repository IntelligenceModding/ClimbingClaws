package de.artemis.climbingclaws.common.registry;

import de.artemis.climbingclaws.ClimbingClaws;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Supplier;

public final class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ClimbingClaws.MOD_ID);

    @SuppressWarnings("unused")
    public static final Supplier<CreativeModeTab> CLIMBING_CLAWS_TAB = CREATIVE_MODE_TABS.register(
            "climbing_claws",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.CLIMBING_CLAWS.get()))
                    .title(Component.translatable("itemGroup.climbingclaws"))
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.CLIMBING_CLAWS);
                        createEnchantBook(parameters.holders(), ModEnchantments.WALL_SPRING, 1).ifPresent(output::accept);
                        createEnchantBook(parameters.holders(), ModEnchantments.WALL_SPRING, 2).ifPresent(output::accept);
                        createEnchantBook(parameters.holders(), ModEnchantments.CANOPY_GRIP, 1).ifPresent(output::accept);
                    })
                    .build()
    );

    private ModCreativeModeTabs() {
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    private static Optional<ItemStack> createEnchantBook(HolderLookup.Provider holders, net.minecraft.resources.ResourceKey<Enchantment> enchantmentKey, int level) {
        Optional<Holder.Reference<Enchantment>> enchantment = holders.lookup(Registries.ENCHANTMENT)
                .flatMap(enchantments -> enchantments.get(enchantmentKey));
        if (enchantment.isEmpty()) {
            return Optional.empty();
        }

        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
        stack.enchant(enchantment.get(), level);
        return Optional.of(stack);
    }
}
