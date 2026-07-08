package de.artemis.climbingclaws.common.network;

import de.artemis.climbingclaws.common.event.ClimbingClawsClimbHandler;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public final class ModPayloads {
    private static final String NETWORK_VERSION = "1";

    private ModPayloads() {
    }

    public static void register(RegisterPayloadHandlersEvent event) {
        event.registrar(NETWORK_VERSION)
                .playToServer(ClimbingBurstPayload.TYPE, ClimbingBurstPayload.STREAM_CODEC, ModPayloads::handleClimbingBurst)
                .playToClient(WallSpringCooldownPayload.TYPE, WallSpringCooldownPayload.STREAM_CODEC, ModPayloads::handleWallSpringCooldown);
    }

    private static void handleClimbingBurst(ClimbingBurstPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> ClimbingClawsClimbHandler.activateBurst(context.player()));
    }

    private static void handleWallSpringCooldown(WallSpringCooldownPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> ClimbingClawsClimbHandler.syncClientWallSpringCooldown(payload.ticks()));
    }
}
