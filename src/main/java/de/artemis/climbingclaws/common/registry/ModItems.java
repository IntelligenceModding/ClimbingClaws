package de.artemis.climbingclaws.common.registry;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.item.ClimbingClawsItem;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.Repairable;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ClimbingClaws.MOD_ID);
    private static final int CLIMBING_CLAWS_DURABILITY = 384;
    private static final float CLIMBING_CLAWS_ATTACK_DAMAGE = 3.0F;
    private static final float CLIMBING_CLAWS_ATTACK_SPEED = -2.4F;

    public static final DeferredItem<Item> CLIMBING_CLAWS = ITEMS.registerItem("climbing_claws",
            ClimbingClawsItem::new,
            new Item.Properties()
                    .durability(CLIMBING_CLAWS_DURABILITY)
                    .enchantable(14)
                    .component(DataComponents.REPAIRABLE, createRepairableComponent())
                    .attributes(createAttributes()));

    private ModItems() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

    private static ItemAttributeModifiers createAttributes() {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, CLIMBING_CLAWS_ATTACK_DAMAGE, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, CLIMBING_CLAWS_ATTACK_SPEED, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                )
                .build();
    }

    private static Repairable createRepairableComponent() {
        return new Repairable(HolderSet.direct(Items.IRON_INGOT.builtInRegistryHolder(), Items.IRON_NUGGET.builtInRegistryHolder()));
    }
}
