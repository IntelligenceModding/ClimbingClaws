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
        add("jei.climbingclaws.climbing_claws",
                "Equip the claws in your off-hand and hold right-click to raise them like a shield.\nClimb solid walls and undersides while the claws are raised.\nStop pressing movement to hang in place, or hold sneak to climb back down.\nClimbing uses durability. Unbreaking and Mending help, and Efficiency increases climb speed.\nWall Spring lets you press jump while climbing to burst upward. Level II launches farther.\nCanopy Grip lets the claws latch onto partial surfaces like leaves.\nIn your main hand, the claws also work as a light weapon and support Sharpness and Fire Aspect.");
    }
}
