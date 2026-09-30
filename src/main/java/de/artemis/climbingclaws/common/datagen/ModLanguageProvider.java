package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.registry.ModEnchantments;
import de.artemis.climbingclaws.common.registry.ModItems;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

public final class ModLanguageProvider extends FabricLanguageProvider {
    private static final String CLIMBING_CLAWS_INFO = "Equip the claws in either hand and hold right-click to raise them like a shield.\n"
            + "Climb solid walls and undersides while the claws are raised.\n"
            + "Stop pressing movement to hang in place, or hold sneak to climb back down.\n"
            + "Climbing uses durability. Unbreaking and Mending help, and Efficiency increases climb speed.\n"
            + "Wall Spring lets you press jump while climbing to burst upward. Level II launches farther.\n"
            + "Canopy Grip lets the claws latch onto partial surfaces like leaves.\n"
            + "In your main hand, the claws also work as a light weapon and support Sharpness and Fire Aspect.";

    public ModLanguageProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder builder) {
        builder.add("itemGroup.climbingclaws", "Climbing Claws");
        builder.add(ModItems.CLIMBING_CLAWS, "Climbing Claws");
        builder.add("modmenu.nameTranslation.climbingclaws", "Climbing Claws");
        builder.add("modmenu.descriptionTranslation.climbingclaws", "Utility mod that adds hand-held Climbing Claws for wall and ceiling traversal, with enchantments and survival-friendly mobility.");
        builder.add("climbingclaws.link.discord", "Discord");
        builder.add("climbingclaws.link.github", "GitHub");
        builder.add("climbingclaws.link.youtube", "YouTube");
        builder.add("climbingclaws.config.title", "Climbing Claws Configuration");
        builder.add("climbingclaws.config.reload", "Reload Config");
        builder.add("climbingclaws.config.done", "Done");
        builder.add("climbingclaws.config.save", "Save");
        builder.add("climbingclaws.config.client", "Client");
        builder.add("climbingclaws.config.server", "Server");
        builder.add("climbingclaws.config.local_server", "Local server settings");
        builder.add("climbingclaws.config.saved", "Saved client and local server config.");
        builder.add("climbingclaws.config.reloaded", "Reloaded from config files.");
        builder.add("climbingclaws.configuration.title", "%s Configuration");
        addLegacyConfigSectionTranslation(builder, "general", "General");
        addLegacyConfigSectionTranslation(builder, "movement", "Movement");
        addLegacyConfigSectionTranslation(builder, "wall_spring", "Wall Spring");
        addLegacyConfigSectionTranslation(builder, "durability", "Durability");
        builder.add("climbingclaws.configuration.section.climbingclaws.client.toml", "Client Settings");
        builder.add("climbingclaws.configuration.section.climbingclaws.client.toml.title", "Client Settings");
        builder.add("climbingclaws.configuration.section.climbingclaws.client.toml.client", "Client Settings");
        builder.add("climbingclaws.configuration.section.climbingclaws.server.toml", "Server Settings");
        builder.add("climbingclaws.configuration.section.climbingclaws.server.toml.title", "Server Settings");
        builder.add("climbingclaws.configuration.section.climbingclaws.server.toml.server", "Server Settings");
        builder.addEnchantment(ModEnchantments.WALL_SPRING, "Wall Spring");
        builder.addEnchantment(ModEnchantments.CANOPY_GRIP, "Canopy Grip");
        builder.add("advancement.climbingclaws.root.title", "Climbing Claws");
        builder.add("advancement.climbingclaws.root.description", "Master walls, ceilings, and rough surfaces with a dedicated climbing tool.");
        builder.add("advancement.climbingclaws.suit_up.title", "Suit Up");
        builder.add("advancement.climbingclaws.suit_up.description", "Obtain a pair of Climbing Claws.");
        builder.add("advancement.climbingclaws.wall_crawler.title", "Wall Crawler");
        builder.add("advancement.climbingclaws.wall_crawler.description", "Climb a wall with the Climbing Claws raised.");
        builder.add("advancement.climbingclaws.hold_fast.title", "Hold Fast");
        builder.add("advancement.climbingclaws.hold_fast.description", "Hang in place on a surface without slipping.");
        builder.add("advancement.climbingclaws.upside_down.title", "Upside Down");
        builder.add("advancement.climbingclaws.upside_down.description", "Cling to the underside of a block with the Climbing Claws.");
        builder.add("advancement.climbingclaws.wall_spring.title", "Spring Loaded");
        builder.add("advancement.climbingclaws.wall_spring.description", "Use Wall Spring to burst upward while climbing.");
        builder.add("advancement.climbingclaws.canopy_route.title", "Canopy Route");
        builder.add("advancement.climbingclaws.canopy_route.description", "Use Canopy Grip to climb a partial surface such as leaves.");
        builder.add("stat.climbingclaws.climbing_claws_one_cm", "Distance Climbed with Climbing Claws");
        builder.add("stat.climbingclaws.climbing_claws_descend_one_cm", "Distance Descended with Climbing Claws");
        builder.add("stat.climbingclaws.climbing_claws_time", "Time Spent Climbing with Climbing Claws");
        builder.add("stat.climbingclaws.climbing_claws_hang_time", "Time Spent Hanging with Climbing Claws");
        builder.add("stat.climbingclaws.wall_spring_uses", "Wall Spring Uses");
        builder.add("tooltip.climbingclaws.wall_spring", "Press jump while climbing to launch upward. Has a cooldown.");
        builder.add("tooltip.climbingclaws.canopy_grip", "Lets the claws latch onto partial surfaces like leaves.");
        addConfigTranslation(builder, "enable_climbing", "Enable Climbing", "Master switch for Climbing Claws traversal. The item remains usable as a weapon when this is false.");
        addConfigTranslation(builder, "allow_main_hand_use", "Allow Main Hand Use", "Allows Climbing Claws traversal when the claws are used from the main hand.");
        addConfigTranslation(builder, "allow_off_hand_use", "Allow Off Hand Use", "Allows Climbing Claws traversal when the claws are used from the off hand.");
        addConfigTranslation(builder, "enable_wall_climbing", "Enable Wall Climbing", "Allows climbing vertical full-block surfaces.");
        addConfigTranslation(builder, "enable_ceiling_climbing", "Enable Ceiling Climbing", "Allows clinging to and moving along block undersides.");
        addConfigTranslation(builder, "enable_hanging", "Enable Hanging", "Allows players to hang in place while the claws are raised and no movement key is pressed.");
        addConfigTranslation(builder, "enable_controlled_descent", "Enable Controlled Descent", "Allows sneaking while attached to a surface to descend in a controlled way.");
        addConfigTranslation(builder, "enable_canopy_grip_effect", "Enable Canopy Grip Effect", "Allows the Canopy Grip enchantment to latch onto partial collision surfaces such as leaves.");
        addConfigTranslation(builder, "side_climb_speed", "Side Climb Speed", "Base upward speed while climbing a vertical wall.");
        addConfigTranslation(builder, "ceiling_climb_speed", "Ceiling Climb Speed", "Base upward/hold speed while moving against a ceiling.");
        addConfigTranslation(builder, "ceiling_hold_speed", "Ceiling Hold Speed", "Base upward/hold speed while clinging to a ceiling without movement input.");
        addConfigTranslation(builder, "efficiency_speed_bonus", "Efficiency Speed Bonus", "Additional climb speed added for each Efficiency enchantment level.");
        addConfigTranslation(builder, "horizontal_velocity_limit", "Horizontal Velocity Limit", "Maximum horizontal velocity retained while attached to a surface.");
        addConfigTranslation(builder, "fall_speed_limit_while_attached", "Fall Speed Limit While Attached", "Maximum downward velocity retained while attached before claw movement is applied.");
        addConfigTranslation(builder, "enable_wall_spring", "Enable Wall Spring", "Allows the Wall Spring enchantment to launch players while attached to a valid surface.");
        addConfigTranslation(builder, "allow_wall_spring_while_sneaking", "Allow Wall Spring While Sneaking", "Allows Wall Spring to activate while the player is sneaking.");
        addConfigTranslation(builder, "wall_spring_level_one_boost", "Wall Spring I Boost", "Upward velocity added by Wall Spring I.");
        addConfigTranslation(builder, "wall_spring_level_two_boost", "Wall Spring II Boost", "Upward velocity added by Wall Spring II.");
        addConfigTranslation(builder, "wall_spring_cooldown_ticks", "Wall Spring Cooldown Ticks", "Cooldown in ticks after a Wall Spring activation. 20 ticks is one second.");
        addConfigTranslation(builder, "enable_durability_damage", "Enable Durability Damage", "Allows traversal and Wall Spring to damage Climbing Claws.");
        addConfigTranslation(builder, "climbing_damage_amount", "Climbing Damage Amount", "Durability damage applied during normal climbing or clinging intervals.");
        addConfigTranslation(builder, "active_climb_damage_interval_ticks", "Active Climb Damage Interval Ticks", "Ticks between durability damage while actively climbing.");
        addConfigTranslation(builder, "cling_damage_interval_ticks", "Cling Damage Interval Ticks", "Ticks between durability damage while attached but not actively climbing.");
        addConfigTranslation(builder, "wall_spring_damage_amount", "Wall Spring Damage Amount", "Durability damage applied immediately when Wall Spring activates.");
        addLegacyConfigTranslation(builder, "general.enable_climbing", "Enable Climbing", "Master switch for Climbing Claws traversal. The item remains usable as a weapon when this is false.");
        addLegacyConfigTranslation(builder, "general.allow_main_hand_use", "Allow Main Hand Use", "Allows Climbing Claws traversal when the claws are used from the main hand.");
        addLegacyConfigTranslation(builder, "general.allow_off_hand_use", "Allow Off Hand Use", "Allows Climbing Claws traversal when the claws are used from the off hand.");
        addLegacyConfigTranslation(builder, "general.enable_wall_climbing", "Enable Wall Climbing", "Allows climbing vertical full-block surfaces.");
        addLegacyConfigTranslation(builder, "general.enable_ceiling_climbing", "Enable Ceiling Climbing", "Allows clinging to and moving along block undersides.");
        addLegacyConfigTranslation(builder, "general.enable_hanging", "Enable Hanging", "Allows players to hang in place while the claws are raised and no movement key is pressed.");
        addLegacyConfigTranslation(builder, "general.enable_controlled_descent", "Enable Controlled Descent", "Allows sneaking while attached to a surface to descend in a controlled way.");
        addLegacyConfigTranslation(builder, "general.enable_canopy_grip_effect", "Enable Canopy Grip Effect", "Allows the Canopy Grip enchantment to latch onto partial collision surfaces such as leaves.");
        addLegacyConfigTranslation(builder, "movement.side_climb_speed", "Side Climb Speed", "Base upward speed while climbing a vertical wall.");
        addLegacyConfigTranslation(builder, "movement.ceiling_climb_speed", "Ceiling Climb Speed", "Base upward/hold speed while moving against a ceiling.");
        addLegacyConfigTranslation(builder, "movement.ceiling_hold_speed", "Ceiling Hold Speed", "Base upward/hold speed while clinging to a ceiling without movement input.");
        addLegacyConfigTranslation(builder, "movement.efficiency_speed_bonus", "Efficiency Speed Bonus", "Additional climb speed added for each Efficiency enchantment level.");
        addLegacyConfigTranslation(builder, "movement.horizontal_velocity_limit", "Horizontal Velocity Limit", "Maximum horizontal velocity retained while attached to a surface.");
        addLegacyConfigTranslation(builder, "movement.fall_speed_limit_while_attached", "Fall Speed Limit While Attached", "Maximum downward velocity retained while attached before claw movement is applied.");
        addLegacyConfigTranslation(builder, "wall_spring.enable", "Enable Wall Spring", "Allows the Wall Spring enchantment to launch players while attached to a valid surface.");
        addLegacyConfigTranslation(builder, "wall_spring.allow_while_sneaking", "Allow Wall Spring While Sneaking", "Allows Wall Spring to activate while the player is sneaking.");
        addLegacyConfigTranslation(builder, "wall_spring.level_one_boost", "Wall Spring I Boost", "Upward velocity added by Wall Spring I.");
        addLegacyConfigTranslation(builder, "wall_spring.level_two_boost", "Wall Spring II Boost", "Upward velocity added by Wall Spring II.");
        addLegacyConfigTranslation(builder, "wall_spring.cooldown_ticks", "Wall Spring Cooldown Ticks", "Cooldown in ticks after a Wall Spring activation. 20 ticks is one second.");
        addLegacyConfigTranslation(builder, "durability.enable_damage", "Enable Durability Damage", "Allows traversal and Wall Spring to damage Climbing Claws.");
        addLegacyConfigTranslation(builder, "durability.climbing_damage_amount", "Climbing Damage Amount", "Durability damage applied during normal climbing or clinging intervals.");
        addLegacyConfigTranslation(builder, "durability.active_climb_damage_interval_ticks", "Active Climb Damage Interval Ticks", "Ticks between durability damage while actively climbing.");
        addLegacyConfigTranslation(builder, "durability.cling_damage_interval_ticks", "Cling Damage Interval Ticks", "Ticks between durability damage while attached but not actively climbing.");
        addLegacyConfigTranslation(builder, "durability.wall_spring_damage_amount", "Wall Spring Damage Amount", "Durability damage applied immediately when Wall Spring activates.");
        builder.add("climbingclaws.configuration.client.show_wall_spring_cooldown_overlay", "Show Wall Spring Cooldown Overlay");
        builder.add("climbingclaws.configuration.client.show_wall_spring_cooldown_overlay.tooltip", "Shows the Wall Spring cooldown overlay on Climbing Claws item stacks.");
        builder.add("jei.climbingclaws.climbing_claws", CLIMBING_CLAWS_INFO);
    }

    @Override
    public String getName() {
        return "Climbing Claws Languages";
    }

    private static void addConfigTranslation(TranslationBuilder builder, String path, String label, String tooltip) {
        String key = ClimbingClaws.MOD_ID + ".configuration." + path;
        builder.add(key, label);
        builder.add(key + ".tooltip", tooltip);
    }

    private static void addLegacyConfigSectionTranslation(TranslationBuilder builder, String path, String label) {
        builder.add(ClimbingClaws.MOD_ID + ".configuration.server." + path, label);
    }

    private static void addLegacyConfigTranslation(TranslationBuilder builder, String path, String label, String tooltip) {
        String key = ClimbingClaws.MOD_ID + ".configuration.server." + path;
        builder.add(key, label);
        builder.add(key + ".tooltip", tooltip);
    }
}
