package de.artemis.climbingclaws.common.network;

import net.minecraft.network.FriendlyByteBuf;

public record ClimbingInputPayload(boolean moving) {
    public static void encode(ClimbingInputPayload payload, FriendlyByteBuf buffer) {
        buffer.writeBoolean(payload.moving());
    }

    public static ClimbingInputPayload decode(FriendlyByteBuf buffer) {
        return new ClimbingInputPayload(buffer.readBoolean());
    }
}
