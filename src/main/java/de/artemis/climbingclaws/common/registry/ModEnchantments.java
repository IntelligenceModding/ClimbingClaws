package de.artemis.climbingclaws.common.registry;

import de.artemis.climbingclaws.ClimbingClaws;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public final class ModEnchantments {
    public static final ResourceKey<Enchantment> WALL_SPRING = key("wall_spring");
    public static final ResourceKey<Enchantment> CANOPY_GRIP = key("canopy_grip");

    private ModEnchantments() {
    }

    public static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        HolderSet<Item> climbingClawsOnly = HolderSet.direct(items.getOrThrow(itemKey("climbing_claws")));

        register(
                context,
                WALL_SPRING,
                Enchantment.enchantment(
                        Enchantment.definition(
                                climbingClawsOnly,
                                climbingClawsOnly,
                                4,
                                2,
                                Enchantment.dynamicCost(12, 14),
                                Enchantment.dynamicCost(30, 14),
                                3,
                                EquipmentSlotGroup.OFFHAND
                        )
                )
        );

        register(
                context,
                CANOPY_GRIP,
                Enchantment.enchantment(
                        Enchantment.definition(
                                climbingClawsOnly,
                                climbingClawsOnly,
                                6,
                                1,
                                Enchantment.dynamicCost(8, 0),
                                Enchantment.dynamicCost(24, 0),
                                2,
                                EquipmentSlotGroup.OFFHAND
                        )
                )
        );
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }

    private static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, ResourceLocation.fromNamespaceAndPath(ClimbingClaws.MOD_ID, name));
    }

    private static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(ClimbingClaws.MOD_ID, name));
    }
}
