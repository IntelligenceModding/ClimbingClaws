package de.artemis.climbingclaws.common.item;

import de.artemis.climbingclaws.common.config.ClimbingClawsConfig;
import de.artemis.climbingclaws.common.registry.ModEnchantments;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

public class ClimbingClawsItem extends ShieldItem {
    private final Multimap<Attribute, AttributeModifier> attackAttributes;

    public ClimbingClawsItem(Properties properties, double damage, double speed) {
        super(properties);
        attackAttributes = ImmutableMultimap.of(
                Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Claws damage", damage, AttributeModifier.Operation.ADDITION),
                Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Claws speed", speed, AttributeModifier.Operation.ADDITION));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? attackAttributes : super.getDefaultAttributeModifiers(slot);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, net.minecraft.world.entity.player.Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!ClimbingClawsConfig.enableClimbing() || !ClimbingClawsConfig.isHandUseAllowed(usedHand)) {
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
        stack.hurtAndBreak(1, attacker, entity -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return supportsClimbingClawsEnchantment(enchantment) && enchantment != Enchantments.MENDING;
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(Items.IRON_INGOT) || repairCandidate.is(Items.IRON_NUGGET);
    }

    public static boolean supportsClimbingClawsEnchantment(Enchantment enchantment) {
        return enchantment == ModEnchantments.WALL_SPRING.get()
                || enchantment == ModEnchantments.CANOPY_GRIP.get()
                || enchantment == Enchantments.BLOCK_EFFICIENCY
                || enchantment == Enchantments.SHARPNESS
                || enchantment == Enchantments.FIRE_ASPECT
                || enchantment == Enchantments.UNBREAKING
                || enchantment == Enchantments.MENDING;
    }
}
