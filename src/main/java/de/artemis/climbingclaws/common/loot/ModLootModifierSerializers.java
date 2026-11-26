package de.artemis.climbingclaws.common.loot;

import de.artemis.climbingclaws.ClimbingClaws;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public final class ModLootModifierSerializers {
    public static final DeferredRegister<com.mojang.serialization.Codec<? extends net.minecraftforge.common.loot.IGlobalLootModifier>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ClimbingClaws.MOD_ID);

    @SuppressWarnings("unused")
    public static final net.minecraftforge.registries.RegistryObject<com.mojang.serialization.Codec<? extends net.minecraftforge.common.loot.IGlobalLootModifier>> ADD_TABLE =
            SERIALIZERS.register("add_table", () -> AddTableLootModifier.CODEC);

    private ModLootModifierSerializers() {
    }

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
