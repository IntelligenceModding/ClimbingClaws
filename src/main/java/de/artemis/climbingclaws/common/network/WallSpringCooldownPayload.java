package de.artemis.climbingclaws.common.network;

import net.minecraft.network.FriendlyByteBuf;

public record WallSpringCooldownPayload(int ticks) {
    public static void encode(WallSpringCooldownPayload payload, FriendlyByteBuf buf) {
        buf.writeVarInt(payload.ticks());
    }

    public static WallSpringCooldownPayload decode(FriendlyByteBuf buf) {
        return new WallSpringCooldownPayload(buf.readVarInt());
    }
}
