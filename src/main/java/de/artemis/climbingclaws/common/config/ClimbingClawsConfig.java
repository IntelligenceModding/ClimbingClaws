package de.artemis.climbingclaws.common.config;

import de.artemis.climbingclaws.ClimbingClaws;
import net.minecraft.world.InteractionHand;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class ClimbingClawsConfig {
    public static final ModConfigSpec COMMON_SPEC;

    private static final ModConfigSpec.BooleanValue ENABLE_CLIMBING;
    private static final ModConfigSpec.BooleanValue ALLOW_MAIN_HAND_USE;
    private static final ModConfigSpec.BooleanValue ALLOW_OFF_HAND_USE;
    private static final ModConfigSpec.BooleanValue ENABLE_WALL_CLIMBING;
    private static final ModConfigSpec.BooleanValue ENABLE_CEILING_CLIMBING;
    private static final ModConfigSpec.BooleanValue ENABLE_HANGING;
    private static final ModConfigSpec.BooleanValue ENABLE_CONTROLLED_DESCENT;
    private static final ModConfigSpec.BooleanValue ENABLE_CANOPY_GRIP_EFFECT;

    private static final ModConfigSpec.DoubleValue SIDE_CLIMB_SPEED;
    private static final ModConfigSpec.DoubleValue CEILING_CLIMB_SPEED;
    private static final ModConfigSpec.DoubleValue CEILING_HOLD_SPEED;
    private static final ModConfigSpec.DoubleValue EFFICIENCY_SPEED_BONUS;
    private static final ModConfigSpec.DoubleValue HORIZONTAL_VELOCITY_LIMIT;
    private static final ModConfigSpec.DoubleValue FALL_SPEED_LIMIT_WHILE_ATTACHED;

    private static final ModConfigSpec.BooleanValue ENABLE_WALL_SPRING;
    private static final ModConfigSpec.BooleanValue ALLOW_WALL_SPRING_WHILE_SNEAKING;
    private static final ModConfigSpec.DoubleValue WALL_SPRING_LEVEL_ONE_BOOST;
    private static final ModConfigSpec.DoubleValue WALL_SPRING_LEVEL_TWO_BOOST;
    private static final ModConfigSpec.IntValue WALL_SPRING_COOLDOWN_TICKS;

    private static final ModConfigSpec.BooleanValue ENABLE_DURABILITY_DAMAGE;
    private static final ModConfigSpec.IntValue CLIMBING_DAMAGE_AMOUNT;
    private static final ModConfigSpec.IntValue ACTIVE_CLIMB_DAMAGE_INTERVAL_TICKS;
    private static final ModConfigSpec.IntValue CLING_DAMAGE_INTERVAL_TICKS;
    private static final ModConfigSpec.IntValue WALL_SPRING_DAMAGE_AMOUNT;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        builder
                .translation(commonSectionKey("general"))
                .push("general");
        ENABLE_CLIMBING = builder
                .comment("Master switch for Climbing Claws traversal. The item remains usable as a weapon when this is false.")
                .translation(commonConfigKey("general", "enable_climbing"))
                .define("enableClimbing", true);
        ALLOW_MAIN_HAND_USE = builder
                .comment("Allows Climbing Claws traversal when the claws are used from the main hand.")
                .translation(commonConfigKey("general", "allow_main_hand_use"))
                .define("allowMainHandUse", true);
        ALLOW_OFF_HAND_USE = builder
                .comment("Allows Climbing Claws traversal when the claws are used from the off hand.")
                .translation(commonConfigKey("general", "allow_off_hand_use"))
                .define("allowOffHandUse", true);
        ENABLE_WALL_CLIMBING = builder
                .comment("Allows climbing vertical full-block surfaces.")
                .translation(commonConfigKey("general", "enable_wall_climbing"))
                .define("enableWallClimbing", true);
        ENABLE_CEILING_CLIMBING = builder
                .comment("Allows clinging to and moving along block undersides.")
                .translation(commonConfigKey("general", "enable_ceiling_climbing"))
                .define("enableCeilingClimbing", true);
        ENABLE_HANGING = builder
                .comment("Allows players to hang in place while the claws are raised and no movement key is pressed.")
                .translation(commonConfigKey("general", "enable_hanging"))
                .define("enableHanging", true);
        ENABLE_CONTROLLED_DESCENT = builder
                .comment("Allows sneaking while attached to a surface to descend in a controlled way.")
                .translation(commonConfigKey("general", "enable_controlled_descent"))
                .define("enableControlledDescent", true);
        ENABLE_CANOPY_GRIP_EFFECT = builder
                .comment("Allows the Canopy Grip enchantment to latch onto partial collision surfaces such as leaves.")
                .translation(commonConfigKey("general", "enable_canopy_grip_effect"))
                .define("enableCanopyGripEffect", true);
        builder.pop();

        builder
                .translation(commonSectionKey("movement"))
                .push("movement");
        SIDE_CLIMB_SPEED = builder
                .comment("Base upward speed while climbing a vertical wall.")
                .translation(commonConfigKey("movement", "side_climb_speed"))
                .defineInRange("sideClimbSpeed", 0.065D, 0.0D, 1.0D);
        CEILING_CLIMB_SPEED = builder
                .comment("Base upward/hold speed while moving against a ceiling.")
                .translation(commonConfigKey("movement", "ceiling_climb_speed"))
                .defineInRange("ceilingClimbSpeed", 0.03D, 0.0D, 1.0D);
        CEILING_HOLD_SPEED = builder
                .comment("Base upward/hold speed while clinging to a ceiling without movement input.")
                .translation(commonConfigKey("movement", "ceiling_hold_speed"))
                .defineInRange("ceilingHoldSpeed", 0.01D, 0.0D, 1.0D);
        EFFICIENCY_SPEED_BONUS = builder
                .comment("Additional climb speed added for each Efficiency enchantment level.")
                .translation(commonConfigKey("movement", "efficiency_speed_bonus"))
                .defineInRange("efficiencySpeedBonus", 0.0125D, 0.0D, 0.25D);
        HORIZONTAL_VELOCITY_LIMIT = builder
                .comment("Maximum horizontal velocity retained while attached to a surface.")
                .translation(commonConfigKey("movement", "horizontal_velocity_limit"))
                .defineInRange("horizontalVelocityLimit", 0.15D, 0.0D, 2.0D);
        FALL_SPEED_LIMIT_WHILE_ATTACHED = builder
                .comment("Maximum downward velocity retained while attached before claw movement is applied.")
                .translation(commonConfigKey("movement", "fall_speed_limit_while_attached"))
                .defineInRange("fallSpeedLimitWhileAttached", 0.15D, 0.0D, 2.0D);
        builder.pop();

        builder
                .translation(commonSectionKey("wall_spring"))
                .push("wallSpring");
        ENABLE_WALL_SPRING = builder
                .comment("Allows the Wall Spring enchantment to launch players while attached to a valid surface.")
                .translation(commonConfigKey("wall_spring", "enable"))
                .define("enableWallSpring", true);
        ALLOW_WALL_SPRING_WHILE_SNEAKING = builder
                .comment("Allows Wall Spring to activate while the player is sneaking.")
                .translation(commonConfigKey("wall_spring", "allow_while_sneaking"))
                .define("allowWhileSneaking", false);
        WALL_SPRING_LEVEL_ONE_BOOST = builder
                .comment("Upward velocity added by Wall Spring I.")
                .translation(commonConfigKey("wall_spring", "level_one_boost"))
                .defineInRange("levelOneBoost", 0.75D, 0.0D, 5.0D);
        WALL_SPRING_LEVEL_TWO_BOOST = builder
                .comment("Upward velocity added by Wall Spring II.")
                .translation(commonConfigKey("wall_spring", "level_two_boost"))
                .defineInRange("levelTwoBoost", 1.05D, 0.0D, 5.0D);
        WALL_SPRING_COOLDOWN_TICKS = builder
                .comment("Cooldown in ticks after a Wall Spring activation. 20 ticks is one second.")
                .translation(commonConfigKey("wall_spring", "cooldown_ticks"))
                .defineInRange("cooldownTicks", 200, 0, 20 * 60 * 10);
        builder.pop();

        builder
                .translation(commonSectionKey("durability"))
                .push("durability");
        ENABLE_DURABILITY_DAMAGE = builder
                .comment("Allows traversal and Wall Spring to damage Climbing Claws.")
                .translation(commonConfigKey("durability", "enable_damage"))
                .define("enableDamage", true);
        CLIMBING_DAMAGE_AMOUNT = builder
                .comment("Durability damage applied during normal climbing or clinging intervals.")
                .translation(commonConfigKey("durability", "climbing_damage_amount"))
                .defineInRange("climbingDamageAmount", 1, 0, 100);
        ACTIVE_CLIMB_DAMAGE_INTERVAL_TICKS = builder
                .comment("Ticks between durability damage while actively climbing.")
                .translation(commonConfigKey("durability", "active_climb_damage_interval_ticks"))
                .defineInRange("activeClimbDamageIntervalTicks", 10, 1, 20 * 60);
        CLING_DAMAGE_INTERVAL_TICKS = builder
                .comment("Ticks between durability damage while attached but not actively climbing.")
                .translation(commonConfigKey("durability", "cling_damage_interval_ticks"))
                .defineInRange("clingDamageIntervalTicks", 20, 1, 20 * 60);
        WALL_SPRING_DAMAGE_AMOUNT = builder
                .comment("Durability damage applied immediately when Wall Spring activates.")
                .translation(commonConfigKey("durability", "wall_spring_damage_amount"))
                .defineInRange("wallSpringDamageAmount", 1, 0, 100);
        builder.pop();

        COMMON_SPEC = builder.build();
    }

    private ClimbingClawsConfig() {
    }

    public static boolean enableClimbing() {
        return ENABLE_CLIMBING.get();
    }

    public static boolean isHandUseAllowed(InteractionHand hand) {
        return hand == InteractionHand.MAIN_HAND ? ALLOW_MAIN_HAND_USE.get() : ALLOW_OFF_HAND_USE.get();
    }

    public static boolean enableWallClimbing() {
        return ENABLE_WALL_CLIMBING.get();
    }

    public static boolean enableCeilingClimbing() {
        return ENABLE_CEILING_CLIMBING.get();
    }

    public static boolean enableHanging() {
        return ENABLE_HANGING.get();
    }

    public static boolean enableControlledDescent() {
        return ENABLE_CONTROLLED_DESCENT.get();
    }

    public static boolean enableCanopyGripEffect() {
        return ENABLE_CANOPY_GRIP_EFFECT.get();
    }

    public static double sideClimbSpeed() {
        return SIDE_CLIMB_SPEED.get();
    }

    public static double ceilingClimbSpeed() {
        return CEILING_CLIMB_SPEED.get();
    }

    public static double ceilingHoldSpeed() {
        return CEILING_HOLD_SPEED.get();
    }

    public static double efficiencySpeedBonus() {
        return EFFICIENCY_SPEED_BONUS.get();
    }

    public static double horizontalVelocityLimit() {
        return HORIZONTAL_VELOCITY_LIMIT.get();
    }

    public static double fallSpeedLimitWhileAttached() {
        return FALL_SPEED_LIMIT_WHILE_ATTACHED.get();
    }

    public static boolean enableWallSpring() {
        return ENABLE_WALL_SPRING.get();
    }

    public static boolean allowWallSpringWhileSneaking() {
        return ALLOW_WALL_SPRING_WHILE_SNEAKING.get();
    }

    public static double wallSpringLevelOneBoost() {
        return WALL_SPRING_LEVEL_ONE_BOOST.get();
    }

    public static double wallSpringLevelTwoBoost() {
        return WALL_SPRING_LEVEL_TWO_BOOST.get();
    }

    public static int wallSpringCooldownTicks() {
        return WALL_SPRING_COOLDOWN_TICKS.get();
    }

    public static boolean enableDurabilityDamage() {
        return ENABLE_DURABILITY_DAMAGE.get();
    }

    public static int climbingDamageAmount() {
        return CLIMBING_DAMAGE_AMOUNT.get();
    }

    public static int activeClimbDamageIntervalTicks() {
        return ACTIVE_CLIMB_DAMAGE_INTERVAL_TICKS.get();
    }

    public static int clingDamageIntervalTicks() {
        return CLING_DAMAGE_INTERVAL_TICKS.get();
    }

    public static int wallSpringDamageAmount() {
        return WALL_SPRING_DAMAGE_AMOUNT.get();
    }

    private static String commonConfigKey(String section, String key) {
        return ClimbingClaws.MOD_ID + ".configuration.common." + section + "." + key;
    }

    private static String commonSectionKey(String section) {
        return ClimbingClaws.MOD_ID + ".configuration.common." + section;
    }
}
