package de.artemis.climbingclaws.common.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.artemis.climbingclaws.ClimbingClaws;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.InteractionHand;

public final class ClimbingClawsConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Values values = new Values();

    private ClimbingClawsConfig() {
    }

    public static void load() {
        Path path = path();
        if (Files.exists(path)) {
            try (Reader reader = Files.newBufferedReader(path)) {
                Values loaded = GSON.fromJson(reader, Values.class);
                if (loaded != null) {
                    values = loaded.normalized();
                    return;
                }
            } catch (IOException ignored) {
            }
        }
        save(path);
    }

    public static Snapshot snapshot() {
        return Snapshot.from(values);
    }

    public static void apply(Snapshot snapshot) {
        values = snapshot.toValues().normalized();
        save(path());
    }

    private static Path path() {
        return FabricLoader.getInstance().getConfigDir().resolve(ClimbingClaws.MOD_ID + ".json");
    }

    private static void save(Path path) {
        try {
            Files.createDirectories(path.getParent());
            try (Writer writer = Files.newBufferedWriter(path)) {
                GSON.toJson(values, writer);
            }
        } catch (IOException ignored) {
        }
    }

    public static boolean enableClimbing() {
        return values.enableClimbing;
    }

    public static boolean isHandUseAllowed(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? values.allowMainHandUse : values.allowOffHandUse;
    }

    public static boolean enableWallClimbing() {
        return values.enableWallClimbing;
    }

    public static boolean enableCeilingClimbing() {
        return values.enableCeilingClimbing;
    }

    public static boolean enableHanging() {
        return values.enableHanging;
    }

    public static boolean enableControlledDescent() {
        return values.enableControlledDescent;
    }

    public static boolean enableCanopyGripEffect() {
        return values.enableCanopyGripEffect;
    }

    public static double sideClimbSpeed() {
        return values.sideClimbSpeed;
    }

    public static double ceilingClimbSpeed() {
        return values.ceilingClimbSpeed;
    }

    public static double ceilingHoldSpeed() {
        return values.ceilingHoldSpeed;
    }

    public static double efficiencySpeedBonus() {
        return values.efficiencySpeedBonus;
    }

    public static double horizontalVelocityLimit() {
        return values.horizontalVelocityLimit;
    }

    public static double fallSpeedLimitWhileAttached() {
        return values.fallSpeedLimitWhileAttached;
    }

    public static boolean enableWallSpring() {
        return values.enableWallSpring;
    }

    public static boolean allowWallSpringWhileSneaking() {
        return values.allowWallSpringWhileSneaking;
    }

    public static double wallSpringLevelOneBoost() {
        return values.wallSpringLevelOneBoost;
    }

    public static double wallSpringLevelTwoBoost() {
        return values.wallSpringLevelTwoBoost;
    }

    public static int wallSpringCooldownTicks() {
        return values.wallSpringCooldownTicks;
    }

    public static boolean enableDurabilityDamage() {
        return values.enableDurabilityDamage;
    }

    public static int climbingDamageAmount() {
        return values.climbingDamageAmount;
    }

    public static int activeClimbDamageIntervalTicks() {
        return values.activeClimbDamageIntervalTicks;
    }

    public static int clingDamageIntervalTicks() {
        return values.clingDamageIntervalTicks;
    }

    public static int wallSpringDamageAmount() {
        return values.wallSpringDamageAmount;
    }

    public record Snapshot(
            boolean enableClimbing,
            boolean allowMainHandUse,
            boolean allowOffHandUse,
            boolean enableWallClimbing,
            boolean enableCeilingClimbing,
            boolean enableHanging,
            boolean enableControlledDescent,
            boolean enableCanopyGripEffect,
            double sideClimbSpeed,
            double ceilingClimbSpeed,
            double ceilingHoldSpeed,
            double efficiencySpeedBonus,
            double horizontalVelocityLimit,
            double fallSpeedLimitWhileAttached,
            boolean enableWallSpring,
            boolean allowWallSpringWhileSneaking,
            double wallSpringLevelOneBoost,
            double wallSpringLevelTwoBoost,
            int wallSpringCooldownTicks,
            boolean enableDurabilityDamage,
            int climbingDamageAmount,
            int activeClimbDamageIntervalTicks,
            int clingDamageIntervalTicks,
            int wallSpringDamageAmount) {
        private static Snapshot from(Values values) {
            return new Snapshot(
                    values.enableClimbing,
                    values.allowMainHandUse,
                    values.allowOffHandUse,
                    values.enableWallClimbing,
                    values.enableCeilingClimbing,
                    values.enableHanging,
                    values.enableControlledDescent,
                    values.enableCanopyGripEffect,
                    values.sideClimbSpeed,
                    values.ceilingClimbSpeed,
                    values.ceilingHoldSpeed,
                    values.efficiencySpeedBonus,
                    values.horizontalVelocityLimit,
                    values.fallSpeedLimitWhileAttached,
                    values.enableWallSpring,
                    values.allowWallSpringWhileSneaking,
                    values.wallSpringLevelOneBoost,
                    values.wallSpringLevelTwoBoost,
                    values.wallSpringCooldownTicks,
                    values.enableDurabilityDamage,
                    values.climbingDamageAmount,
                    values.activeClimbDamageIntervalTicks,
                    values.clingDamageIntervalTicks,
                    values.wallSpringDamageAmount
            );
        }

        private Values toValues() {
            Values result = new Values();
            result.enableClimbing = enableClimbing;
            result.allowMainHandUse = allowMainHandUse;
            result.allowOffHandUse = allowOffHandUse;
            result.enableWallClimbing = enableWallClimbing;
            result.enableCeilingClimbing = enableCeilingClimbing;
            result.enableHanging = enableHanging;
            result.enableControlledDescent = enableControlledDescent;
            result.enableCanopyGripEffect = enableCanopyGripEffect;
            result.sideClimbSpeed = sideClimbSpeed;
            result.ceilingClimbSpeed = ceilingClimbSpeed;
            result.ceilingHoldSpeed = ceilingHoldSpeed;
            result.efficiencySpeedBonus = efficiencySpeedBonus;
            result.horizontalVelocityLimit = horizontalVelocityLimit;
            result.fallSpeedLimitWhileAttached = fallSpeedLimitWhileAttached;
            result.enableWallSpring = enableWallSpring;
            result.allowWallSpringWhileSneaking = allowWallSpringWhileSneaking;
            result.wallSpringLevelOneBoost = wallSpringLevelOneBoost;
            result.wallSpringLevelTwoBoost = wallSpringLevelTwoBoost;
            result.wallSpringCooldownTicks = wallSpringCooldownTicks;
            result.enableDurabilityDamage = enableDurabilityDamage;
            result.climbingDamageAmount = climbingDamageAmount;
            result.activeClimbDamageIntervalTicks = activeClimbDamageIntervalTicks;
            result.clingDamageIntervalTicks = clingDamageIntervalTicks;
            result.wallSpringDamageAmount = wallSpringDamageAmount;
            return result;
        }
    }

    private static final class Values {
        boolean enableClimbing = true;
        boolean allowMainHandUse = true;
        boolean allowOffHandUse = true;
        boolean enableWallClimbing = true;
        boolean enableCeilingClimbing = true;
        boolean enableHanging = true;
        boolean enableControlledDescent = true;
        boolean enableCanopyGripEffect = true;
        double sideClimbSpeed = 0.065D;
        double ceilingClimbSpeed = 0.03D;
        double ceilingHoldSpeed = 0.01D;
        double efficiencySpeedBonus = 0.0125D;
        double horizontalVelocityLimit = 0.15D;
        double fallSpeedLimitWhileAttached = 0.15D;
        boolean enableWallSpring = true;
        boolean allowWallSpringWhileSneaking = false;
        double wallSpringLevelOneBoost = 0.75D;
        double wallSpringLevelTwoBoost = 1.05D;
        int wallSpringCooldownTicks = 200;
        boolean enableDurabilityDamage = true;
        int climbingDamageAmount = 1;
        int activeClimbDamageIntervalTicks = 10;
        int clingDamageIntervalTicks = 20;
        int wallSpringDamageAmount = 1;

        Values normalized() {
            sideClimbSpeed = clamp(sideClimbSpeed, 0.0D, 1.0D);
            ceilingClimbSpeed = clamp(ceilingClimbSpeed, 0.0D, 1.0D);
            ceilingHoldSpeed = clamp(ceilingHoldSpeed, 0.0D, 1.0D);
            efficiencySpeedBonus = clamp(efficiencySpeedBonus, 0.0D, 0.25D);
            horizontalVelocityLimit = clamp(horizontalVelocityLimit, 0.0D, 2.0D);
            fallSpeedLimitWhileAttached = clamp(fallSpeedLimitWhileAttached, 0.0D, 2.0D);
            wallSpringLevelOneBoost = clamp(wallSpringLevelOneBoost, 0.0D, 5.0D);
            wallSpringLevelTwoBoost = clamp(wallSpringLevelTwoBoost, 0.0D, 5.0D);
            wallSpringCooldownTicks = clamp(wallSpringCooldownTicks, 0, 20 * 60 * 10);
            climbingDamageAmount = clamp(climbingDamageAmount, 0, 100);
            activeClimbDamageIntervalTicks = clamp(activeClimbDamageIntervalTicks, 1, 20 * 60);
            clingDamageIntervalTicks = clamp(clingDamageIntervalTicks, 1, 20 * 60);
            wallSpringDamageAmount = clamp(wallSpringDamageAmount, 0, 100);
            return this;
        }

        private static double clamp(double value, double min, double max) {
            return Math.max(min, Math.min(max, value));
        }

        private static int clamp(int value, int min, int max) {
            return Math.max(min, Math.min(max, value));
        }
    }
}
