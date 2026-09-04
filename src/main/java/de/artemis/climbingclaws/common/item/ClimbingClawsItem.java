package de.artemis.climbingclaws.common.item;

import de.artemis.climbingclaws.common.config.ClimbingClawsConfig;
import de.artemis.climbingclaws.common.registry.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class ClimbingClawsItem extends ShieldItem {
    public ClimbingClawsItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        if (!ClimbingClawsConfig.enableClimbing() || !ClimbingClawsConfig.isHandUseAllowed(usedHand)) {
            return InteractionResult.PASS;
        }

        return ItemUtils.startUsingInstantly(level, player, usedHand);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        return supportsClimbingClawsEnchantment(enchantment);
    }

    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        return supportsClimbingClawsEnchantment(enchantment);
    }

    public static boolean supportsClimbingClawsEnchantment(Holder<Enchantment> enchantment) {
        return enchantment.is(ModEnchantments.WALL_SPRING)
                || enchantment.is(ModEnchantments.CANOPY_GRIP)
                || enchantment.is(Enchantments.EFFICIENCY)
                || enchantment.is(Enchantments.SHARPNESS)
                || enchantment.is(Enchantments.FIRE_ASPECT)
                || enchantment.is(Enchantments.UNBREAKING)
                || enchantment.is(Enchantments.MENDING);
    }
}
