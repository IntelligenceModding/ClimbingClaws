package de.artemis.climbingclaws.client;

import de.artemis.climbingclaws.ClimbingClaws;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class ClientConfig {
    public static final ModConfigSpec SPEC;

    private static final ModConfigSpec.BooleanValue SHOW_WALL_SPRING_COOLDOWN_OVERLAY;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        SHOW_WALL_SPRING_COOLDOWN_OVERLAY = builder
                .comment("Shows the Wall Spring cooldown overlay on Climbing Claws item stacks. This only affects local rendering.")
                .translation(ClimbingClaws.MOD_ID + ".configuration.client.show_wall_spring_cooldown_overlay")
                .define("showWallSpringCooldownOverlay", true);

        SPEC = builder.build();
    }

    private ClientConfig() {
    }

    public static boolean showWallSpringCooldownOverlay() {
        return SHOW_WALL_SPRING_COOLDOWN_OVERLAY.get();
    }
}
