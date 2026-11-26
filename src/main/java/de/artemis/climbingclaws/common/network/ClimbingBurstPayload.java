package de.artemis.climbingclaws.common.network;

import net.minecraft.network.FriendlyByteBuf;

public final class ClimbingBurstPayload {
    public static final ClimbingBurstPayload INSTANCE = new ClimbingBurstPayload();

    private ClimbingBurstPayload() {
    }

    public static void encode(ClimbingBurstPayload payload, FriendlyByteBuf buf) {
    }

    public static ClimbingBurstPayload decode(FriendlyByteBuf buf) {
        return INSTANCE;
    }
}
