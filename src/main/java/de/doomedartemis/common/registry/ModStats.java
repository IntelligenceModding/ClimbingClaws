package de.doomedartemis.common.registry;

import de.doomedartemis.ClimbingClaws;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

public final class ModStats {
    public static final ResourceLocation CLIMBING_CLAWS_ONE_CM = id("climbing_claws_one_cm");
    public static final ResourceLocation CLIMBING_CLAWS_DESCEND_ONE_CM = id("climbing_claws_descend_one_cm");
    public static final ResourceLocation CLIMBING_CLAWS_TIME = id("climbing_claws_time");
    public static final ResourceLocation CLIMBING_CLAWS_HANG_TIME = id("climbing_claws_hang_time");
    public static final ResourceLocation WALL_SPRING_USES = id("wall_spring_uses");

    private ModStats() {
    }

    public static void register() {
        register(CLIMBING_CLAWS_ONE_CM, StatFormatter.DISTANCE);
        register(CLIMBING_CLAWS_DESCEND_ONE_CM, StatFormatter.DISTANCE);
        register(CLIMBING_CLAWS_TIME, StatFormatter.TIME);
        register(CLIMBING_CLAWS_HANG_TIME, StatFormatter.TIME);
        register(WALL_SPRING_USES, StatFormatter.DEFAULT);
    }

    private static void register(ResourceLocation id, StatFormatter formatter) {
        Registry.register(BuiltInRegistries.CUSTOM_STAT, id, id);
        Stats.CUSTOM.get(id, formatter);
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(ClimbingClaws.MOD_ID, path);
    }
}
