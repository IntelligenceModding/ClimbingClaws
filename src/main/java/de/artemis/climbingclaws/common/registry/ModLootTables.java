package de.artemis.climbingclaws.common.registry;

import de.artemis.climbingclaws.ClimbingClaws;
import net.minecraft.resources.ResourceLocation;

public final class ModLootTables {
    public static final ResourceLocation CLIMBING_CLAWS_VILLAGE_SMITH = chest("injections/climbing_claws_village_smith");
    public static final ResourceLocation CLIMBING_CLAWS_EXPLORATION = chest("injections/climbing_claws_exploration");
    public static final ResourceLocation CLIMBING_CLAWS_STRONGHOLD = chest("injections/climbing_claws_stronghold");
    public static final ResourceLocation CLIMBING_CLAWS_ANCIENT_CITY = chest("injections/climbing_claws_ancient_city");

    public static final ResourceLocation WALL_SPRING_DUNGEON = chest("injections/wall_spring_dungeon");
    public static final ResourceLocation WALL_SPRING_LIBRARY = chest("injections/wall_spring_library");
    public static final ResourceLocation WALL_SPRING_WOODLAND = chest("injections/wall_spring_woodland");
    public static final ResourceLocation WALL_SPRING_ANCIENT_CITY = chest("injections/wall_spring_ancient_city");
    public static final ResourceLocation CANOPY_GRIP_DUNGEON = chest("injections/canopy_grip_dungeon");
    public static final ResourceLocation CANOPY_GRIP_LIBRARY = chest("injections/canopy_grip_library");
    public static final ResourceLocation CANOPY_GRIP_WOODLAND = chest("injections/canopy_grip_woodland");
    public static final ResourceLocation CANOPY_GRIP_ANCIENT_CITY = chest("injections/canopy_grip_ancient_city");

    private ModLootTables() {
    }

    private static ResourceLocation chest(String path) {
        return new ResourceLocation(ClimbingClaws.MOD_ID, "chests/" + path);
    }
}
