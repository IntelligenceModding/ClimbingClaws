package de.artemis.climbingclaws.common.registry;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.item.ClimbingClawsItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ClimbingClaws.MOD_ID);
    private static final int CLIMBING_CLAWS_DURABILITY = 384;
    private static final float CLIMBING_CLAWS_ATTACK_DAMAGE = 3.0F;
    private static final float CLIMBING_CLAWS_ATTACK_SPEED = -2.4F;

    public static final RegistryObject<Item> CLIMBING_CLAWS = ITEMS.register("climbing_claws",
            () -> new ClimbingClawsItem(new Item.Properties().durability(CLIMBING_CLAWS_DURABILITY),
                    CLIMBING_CLAWS_ATTACK_DAMAGE, CLIMBING_CLAWS_ATTACK_SPEED));

    private ModItems() {
    }

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
