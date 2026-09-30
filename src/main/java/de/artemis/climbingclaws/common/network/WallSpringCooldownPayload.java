package de.artemis.climbingclaws.common.network;

import de.artemis.climbingclaws.ClimbingClaws;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record WallSpringCooldownPayload(int ticks) implements CustomPacketPayload {
    public static final Type<WallSpringCooldownPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(ClimbingClaws.MOD_ID, "wall_spring_cooldown"));
    public static final StreamCodec<RegistryFriendlyByteBuf, WallSpringCooldownPayload> STREAM_CODEC =
            StreamCodec.composite(ByteBufCodecs.VAR_INT, WallSpringCooldownPayload::ticks, WallSpringCooldownPayload::new);

    @Override
    public Type<WallSpringCooldownPayload> type() {
        return TYPE;
    }
}
