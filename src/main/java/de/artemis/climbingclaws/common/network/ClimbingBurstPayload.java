package de.artemis.climbingclaws.common.network;

import de.artemis.climbingclaws.ClimbingClaws;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record ClimbingBurstPayload() implements CustomPacketPayload {
    public static final ClimbingBurstPayload INSTANCE = new ClimbingBurstPayload();
    public static final Type<ClimbingBurstPayload> TYPE =
            new Type<>(Identifier.fromNamespaceAndPath(ClimbingClaws.MOD_ID, "climbing_burst"));
    public static final StreamCodec<RegistryFriendlyByteBuf, ClimbingBurstPayload> STREAM_CODEC =
            StreamCodec.unit(INSTANCE);

    @Override
    public Type<ClimbingBurstPayload> type() {
        return TYPE;
    }
}
