package de.artemis.climbingclaws.common.registry;

import de.artemis.climbingclaws.ClimbingClaws;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

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
                        output.accept(ModItems.CLIMBING_CLAWS.get());
                        output.accept(createEnchantBook(ModEnchantments.WALL_SPRING.get(), 1));
                        output.accept(createEnchantBook(ModEnchantments.WALL_SPRING.get(), 2));
                        output.accept(createEnchantBook(ModEnchantments.CANOPY_GRIP.get(), 1));
                    })
                    .build()
    );

    private ModCreativeModeTabs() {
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }

    private static ItemStack createEnchantBook(Enchantment enchantment, int level) {
        return EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level));
    }
}
