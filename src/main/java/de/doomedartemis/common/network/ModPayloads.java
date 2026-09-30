package de.doomedartemis.common.network;

import de.doomedartemis.common.event.ClimbingClawsClimbHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public final class ModPayloads {
    private ModPayloads() {
    }

    public static void register() {
        PayloadTypeRegistry.serverboundPlay().register(ClimbingBurstPayload.TYPE, ClimbingBurstPayload.STREAM_CODEC);
        PayloadTypeRegistry.clientboundPlay().register(WallSpringCooldownPayload.TYPE, WallSpringCooldownPayload.STREAM_CODEC);
        ServerPlayNetworking.registerGlobalReceiver(ClimbingBurstPayload.TYPE,
                (payload, context) -> ClimbingClawsClimbHandler.activateBurst(context.player()));
    }

    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(WallSpringCooldownPayload.TYPE,
                (payload, context) -> ClimbingClawsClimbHandler.syncClientWallSpringCooldown(payload.ticks()));
    }
}
