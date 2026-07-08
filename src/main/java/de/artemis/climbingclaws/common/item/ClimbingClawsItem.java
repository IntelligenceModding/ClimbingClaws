package de.artemis.climbingclaws.common.item;

import de.artemis.climbingclaws.common.registry.ModEnchantments;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;

public class ClimbingClawsItem extends ShieldItem {
    public ClimbingClawsItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, net.minecraft.world.entity.player.Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (usedHand != InteractionHand.OFF_HAND) {
            return InteractionResultHolder.pass(stack);
        }

        player.startUsingItem(usedHand);
        return InteractionResultHolder.consume(stack);
    }

    @Override
    public int getEnchantmentValue() {
        return 14;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return true;
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(Items.IRON_INGOT) || repairCandidate.is(Items.IRON_NUGGET);
    }

    @Override
    public boolean supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.is(ModEnchantments.WALL_SPRING) || enchantment.is(ModEnchantments.CANOPY_GRIP)) {
            return true;
        }

        return super.supportsEnchantment(stack, enchantment);
    }

    @Override
    public boolean isPrimaryItemFor(ItemStack stack, Holder<Enchantment> enchantment) {
        if (enchantment.is(ModEnchantments.WALL_SPRING) || enchantment.is(ModEnchantments.CANOPY_GRIP)) {
            return true;
        }

        return super.isPrimaryItemFor(stack, enchantment);
    }
}
