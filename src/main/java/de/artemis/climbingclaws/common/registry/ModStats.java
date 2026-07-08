package de.artemis.climbingclaws.common.registry;

import de.artemis.climbingclaws.ClimbingClaws;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.neoforged.neoforge.registries.RegisterEvent;

public final class ModStats {
    public static final ResourceLocation CLIMBING_CLAWS_ONE_CM = id("climbing_claws_one_cm");
    public static final ResourceLocation CLIMBING_CLAWS_DESCEND_ONE_CM = id("climbing_claws_descend_one_cm");
    public static final ResourceLocation CLIMBING_CLAWS_TIME = id("climbing_claws_time");
    public static final ResourceLocation CLIMBING_CLAWS_HANG_TIME = id("climbing_claws_hang_time");
    public static final ResourceLocation WALL_SPRING_USES = id("wall_spring_uses");

    private ModStats() {
    }

    public static void register(RegisterEvent event) {
        event.register(Registries.CUSTOM_STAT, helper -> {
            register(helper, CLIMBING_CLAWS_ONE_CM, StatFormatter.DISTANCE);
            register(helper, CLIMBING_CLAWS_DESCEND_ONE_CM, StatFormatter.DISTANCE);
            register(helper, CLIMBING_CLAWS_TIME, StatFormatter.TIME);
            register(helper, CLIMBING_CLAWS_HANG_TIME, StatFormatter.TIME);
            register(helper, WALL_SPRING_USES, StatFormatter.DEFAULT);
        });
    }

    private static void register(RegisterEvent.RegisterHelper<ResourceLocation> helper, ResourceLocation id, StatFormatter formatter) {
        helper.register(id, id);
        Stats.CUSTOM.get(id, formatter);
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(ClimbingClaws.MOD_ID, path);
    }
}
