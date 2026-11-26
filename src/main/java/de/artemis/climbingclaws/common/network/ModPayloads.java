package de.artemis.climbingclaws.common.network;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.event.ClimbingClawsClimbHandler;
import net.minecraft.resources.ResourceLocation;
import java.util.function.Supplier;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.simple.SimpleChannel;

public final class ModPayloads {
    private static final String PROTOCOL_VERSION = "2";

    public static SimpleChannel CHANNEL;

    private ModPayloads() {
    }

    public static void register() {
        CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(ClimbingClaws.MOD_ID, "main"),
                () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

        CHANNEL.messageBuilder(ClimbingBurstPayload.class, 0, NetworkDirection.PLAY_TO_SERVER)
                .encoder(ClimbingBurstPayload::encode)
                .decoder(ClimbingBurstPayload::decode)
                .consumerMainThread(ModPayloads::handleClimbingBurst)
                .add();

        CHANNEL.messageBuilder(WallSpringCooldownPayload.class, 1, NetworkDirection.PLAY_TO_CLIENT)
                .encoder(WallSpringCooldownPayload::encode)
                .decoder(WallSpringCooldownPayload::decode)
                .consumerMainThread(ModPayloads::handleWallSpringCooldown)
                .add();

        CHANNEL.messageBuilder(ClimbingInputPayload.class, 2, NetworkDirection.PLAY_TO_SERVER)
                .encoder(ClimbingInputPayload::encode)
                .decoder(ClimbingInputPayload::decode)
                .consumerMainThread(ModPayloads::handleClimbingInput)
                .add();
    }

    private static void handleClimbingInput(ClimbingInputPayload payload, Supplier<NetworkEvent.Context> context) {
        if (context.get().getSender() != null) {
            ClimbingClawsClimbHandler.setClimbingInput(context.get().getSender(), payload.moving());
        }
    }

    private static void handleClimbingBurst(ClimbingBurstPayload payload, Supplier<NetworkEvent.Context> context) {
        if (context.get().getSender() != null) {
            ClimbingClawsClimbHandler.activateBurst(context.get().getSender());
        }
    }

    private static void handleWallSpringCooldown(WallSpringCooldownPayload payload, Supplier<NetworkEvent.Context> context) {
        ClimbingClawsClimbHandler.syncClientWallSpringCooldown(payload.ticks());
    }
}
