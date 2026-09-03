package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.registry.ModItems;
import net.minecraft.data.PackOutput;

public class ModLanguageProvider extends net.neoforged.neoforge.common.data.LanguageProvider {
    public ModLanguageProvider(PackOutput output, String locale) {
        super(output, ClimbingClaws.MOD_ID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.climbingclaws", "Climbing Claws");
        add(ModItems.CLIMBING_CLAWS.get(), "Climbing Claws");
        add("fml.menu.mods.info.displayname.climbingclaws", "Climbing Claws");
        add("fml.menu.mods.info.description.climbingclaws", "Utility mod that adds hand-held Climbing Claws for wall and ceiling traversal, with enchantments and survival-friendly mobility.");
        add("enchantment.climbingclaws.wall_spring", "Wall Spring");
        add("enchantment.climbingclaws.canopy_grip", "Canopy Grip");
        add("advancement.climbingclaws.root.title", "Climbing Claws");
        add("advancement.climbingclaws.root.description", "Master walls, ceilings, and rough surfaces with a dedicated climbing tool.");
        add("advancement.climbingclaws.suit_up.title", "Suit Up");
        add("advancement.climbingclaws.suit_up.description", "Obtain a pair of Climbing Claws.");
        add("advancement.climbingclaws.wall_crawler.title", "Wall Crawler");
        add("advancement.climbingclaws.wall_crawler.description", "Climb a wall with the Climbing Claws raised.");
        add("advancement.climbingclaws.hold_fast.title", "Hold Fast");
        add("advancement.climbingclaws.hold_fast.description", "Hang in place on a surface without slipping.");
        add("advancement.climbingclaws.upside_down.title", "Upside Down");
        add("advancement.climbingclaws.upside_down.description", "Cling to the underside of a block with the Climbing Claws.");
        add("advancement.climbingclaws.wall_spring.title", "Spring Loaded");
        add("advancement.climbingclaws.wall_spring.description", "Use Wall Spring to burst upward while climbing.");
        add("advancement.climbingclaws.canopy_route.title", "Canopy Route");
        add("advancement.climbingclaws.canopy_route.description", "Use Canopy Grip to climb a partial surface such as leaves.");
        add("stat.climbingclaws.climbing_claws_one_cm", "Distance Climbed with Climbing Claws");
        add("stat.climbingclaws.climbing_claws_descend_one_cm", "Distance Descended with Climbing Claws");
        add("stat.climbingclaws.climbing_claws_time", "Time Spent Climbing with Climbing Claws");
        add("stat.climbingclaws.climbing_claws_hang_time", "Time Spent Hanging with Climbing Claws");
        add("stat.climbingclaws.wall_spring_uses", "Wall Spring Uses");
        add("tooltip.climbingclaws.wall_spring", "Press jump while climbing to launch upward. Has a cooldown.");
        add("tooltip.climbingclaws.canopy_grip", "Lets the claws latch onto partial surfaces like leaves.");
        addConfigSectionTranslation("general", "General");
        addConfigSectionTranslation("movement", "Movement");
        addConfigSectionTranslation("wall_spring", "Wall Spring");
        addConfigSectionTranslation("durability", "Durability");
        addConfigTranslation("general.enable_climbing", "Enable Climbing", "Master switch for Climbing Claws traversal. The item remains usable as a weapon when this is false.");
        addConfigTranslation("general.allow_main_hand_use", "Allow Main Hand Use", "Allows Climbing Claws traversal when the claws are used from the main hand.");
        addConfigTranslation("general.allow_off_hand_use", "Allow Off Hand Use", "Allows Climbing Claws traversal when the claws are used from the off hand.");
        addConfigTranslation("general.enable_wall_climbing", "Enable Wall Climbing", "Allows climbing vertical full-block surfaces.");
        addConfigTranslation("general.enable_ceiling_climbing", "Enable Ceiling Climbing", "Allows clinging to and moving along block undersides.");
        addConfigTranslation("general.enable_hanging", "Enable Hanging", "Allows players to hang in place while the claws are raised and no movement key is pressed.");
        addConfigTranslation("general.enable_controlled_descent", "Enable Controlled Descent", "Allows sneaking while attached to a surface to descend in a controlled way.");
        addConfigTranslation("general.enable_canopy_grip_effect", "Enable Canopy Grip Effect", "Allows the Canopy Grip enchantment to latch onto partial collision surfaces such as leaves.");
        addConfigTranslation("movement.side_climb_speed", "Side Climb Speed", "Base upward speed while climbing a vertical wall.");
        addConfigTranslation("movement.ceiling_climb_speed", "Ceiling Climb Speed", "Base upward/hold speed while moving against a ceiling.");
        addConfigTranslation("movement.ceiling_hold_speed", "Ceiling Hold Speed", "Base upward/hold speed while clinging to a ceiling without movement input.");
        addConfigTranslation("movement.efficiency_speed_bonus", "Efficiency Speed Bonus", "Additional climb speed added for each Efficiency enchantment level.");
        addConfigTranslation("movement.horizontal_velocity_limit", "Horizontal Velocity Limit", "Maximum horizontal velocity retained while attached to a surface.");
        addConfigTranslation("movement.fall_speed_limit_while_attached", "Fall Speed Limit While Attached", "Maximum downward velocity retained while attached before claw movement is applied.");
        addConfigTranslation("wall_spring.enable", "Enable Wall Spring", "Allows the Wall Spring enchantment to launch players while attached to a valid surface.");
        addConfigTranslation("wall_spring.allow_while_sneaking", "Allow Wall Spring While Sneaking", "Allows Wall Spring to activate while the player is sneaking.");
        addConfigTranslation("wall_spring.level_one_boost", "Wall Spring I Boost", "Upward velocity added by Wall Spring I.");
        addConfigTranslation("wall_spring.level_two_boost", "Wall Spring II Boost", "Upward velocity added by Wall Spring II.");
        addConfigTranslation("wall_spring.cooldown_ticks", "Wall Spring Cooldown Ticks", "Cooldown in ticks after a Wall Spring activation. 20 ticks is one second.");
        addConfigTranslation("durability.enable_damage", "Enable Durability Damage", "Allows traversal and Wall Spring to damage Climbing Claws.");
        addConfigTranslation("durability.climbing_damage_amount", "Climbing Damage Amount", "Durability damage applied during normal climbing or clinging intervals.");
        addConfigTranslation("durability.active_climb_damage_interval_ticks", "Active Climb Damage Interval Ticks", "Ticks between durability damage while actively climbing.");
        addConfigTranslation("durability.cling_damage_interval_ticks", "Cling Damage Interval Ticks", "Ticks between durability damage while attached but not actively climbing.");
        addConfigTranslation("durability.wall_spring_damage_amount", "Wall Spring Damage Amount", "Durability damage applied immediately when Wall Spring activates.");
        add("jei.climbingclaws.climbing_claws",
                "Equip the claws in either hand and hold right-click to raise them like a shield.\nClimb solid walls and undersides while the claws are raised.\nStop pressing movement to hang in place, or hold sneak to climb back down.\nClimbing uses durability. Unbreaking and Mending help, and Efficiency increases climb speed.\nWall Spring lets you press jump while climbing to burst upward. Level II launches farther.\nCanopy Grip lets the claws latch onto partial surfaces like leaves.\nIn your main hand, the claws also work as a light weapon and support Sharpness and Fire Aspect.");
        // add("rei.climbingclaws.climbing_claws",
        //         "Equip the claws in either hand and hold right-click to raise them like a shield.\nClimb solid walls and undersides while the claws are raised.\nStop pressing movement to hang in place, or hold sneak to climb back down.\nClimbing uses durability. Unbreaking and Mending help, and Efficiency increases climb speed.\nWall Spring lets you press jump while climbing to burst upward. Level II launches farther.\nCanopy Grip lets the claws latch onto partial surfaces like leaves.\nIn your main hand, the claws also work as a light weapon and support Sharpness and Fire Aspect.");
    }

    private void addConfigSectionTranslation(String path, String label) {
        add("climbingclaws.configuration.common." + path, label);
    }

    private void addConfigTranslation(String path, String label, String tooltip) {
        String key = "climbingclaws.configuration.common." + path;
        add(key, label);
        add(key + ".tooltip", tooltip);
    }
}
