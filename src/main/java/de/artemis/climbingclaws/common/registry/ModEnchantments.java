package de.artemis.climbingclaws.common.registry;

import de.artemis.climbingclaws.ClimbingClaws;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModEnchantments {
    private static final DeferredRegister<Enchantment> ENCHANTMENTS =
            DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, ClimbingClaws.MOD_ID);
    private static final EnchantmentCategory CLAWS =
            EnchantmentCategory.create("climbingclaws_claws", item -> item == ModItems.CLIMBING_CLAWS.get());

    public static final RegistryObject<Enchantment> WALL_SPRING =
            ENCHANTMENTS.register("wall_spring", () -> new ClawsEnchantment(Enchantment.Rarity.RARE, 2, 12, 14, 18));
    public static final RegistryObject<Enchantment> CANOPY_GRIP =
            ENCHANTMENTS.register("canopy_grip", () -> new ClawsEnchantment(Enchantment.Rarity.UNCOMMON, 1, 8, 0, 16));

    private ModEnchantments() {
    }

    public static void register(IEventBus eventBus) {
        ENCHANTMENTS.register(eventBus);
    }

    private static final class ClawsEnchantment extends Enchantment {
        private final int maxLevel;
        private final int baseCost;
        private final int levelCost;
        private final int costRange;

        private ClawsEnchantment(Rarity rarity, int maxLevel, int baseCost, int levelCost, int costRange) {
            super(rarity, CLAWS, new EquipmentSlot[]{EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
            this.maxLevel = maxLevel;
            this.baseCost = baseCost;
            this.levelCost = levelCost;
            this.costRange = costRange;
        }

        @Override
        public int getMaxLevel() {
            return maxLevel;
        }

        @Override
        public int getMinCost(int level) {
            return baseCost + (level - 1) * levelCost;
        }

        @Override
        public int getMaxCost(int level) {
            return getMinCost(level) + costRange;
        }
    }
}
