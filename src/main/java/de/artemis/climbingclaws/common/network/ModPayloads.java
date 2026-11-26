package de.artemis.climbingclaws.common.network;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.event.ClimbingClawsClimbHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.Channel;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.SimpleChannel;

public final class ModPayloads {
    private static final int PROTOCOL_VERSION = 2;

    public static SimpleChannel CHANNEL;

    private ModPayloads() {
    }

    public static void register() {
        CHANNEL = ChannelBuilder.named(ResourceLocation.fromNamespaceAndPath(ClimbingClaws.MOD_ID, "main"))
                .networkProtocolVersion(PROTOCOL_VERSION)
                .acceptedVersions(Channel.VersionTest.exact(PROTOCOL_VERSION))
                .simpleChannel();

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

    private static void handleClimbingInput(ClimbingInputPayload payload, CustomPayloadEvent.Context context) {
        if (context.getSender() != null) {
            ClimbingClawsClimbHandler.setClimbingInput(context.getSender(), payload.moving());
        }
    }

    private static void handleClimbingBurst(ClimbingBurstPayload payload, CustomPayloadEvent.Context context) {
        if (context.getSender() != null) {
            ClimbingClawsClimbHandler.activateBurst(context.getSender());
        }
    }

    private static void handleWallSpringCooldown(WallSpringCooldownPayload payload, CustomPayloadEvent.Context context) {
        ClimbingClawsClimbHandler.syncClientWallSpringCooldown(payload.ticks());
    }
}
