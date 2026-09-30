package de.artemis.climbingclaws.common.registry;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.item.ClimbingClawsItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public final class ModItems {
    public static final TagKey<Item> CLIMBING_CLAWS_REPAIR_MATERIALS =
            TagKey.create(BuiltInRegistries.ITEM.key(), id("repair_materials/climbing_claws"));
    private static final ResourceKey<Item> CLIMBING_CLAWS_KEY =
            ResourceKey.create(BuiltInRegistries.ITEM.key(), id("climbing_claws"));
    private static final int CLIMBING_CLAWS_DURABILITY = 384;
    private static final float CLIMBING_CLAWS_ATTACK_DAMAGE = 3.0F;
    private static final float CLIMBING_CLAWS_ATTACK_SPEED = -2.4F;

    public static final Item CLIMBING_CLAWS = new ClimbingClawsItem(new Item.Properties()
            .setId(CLIMBING_CLAWS_KEY)
            .durability(CLIMBING_CLAWS_DURABILITY)
            .enchantable(14)
            .repairable(CLIMBING_CLAWS_REPAIR_MATERIALS)
            .attributes(createAttributes()));

    private ModItems() {
    }

    public static void register() {
        Registry.register(BuiltInRegistries.ITEM, CLIMBING_CLAWS_KEY, CLIMBING_CLAWS);
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

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(ClimbingClaws.MOD_ID, path);
    }
}
