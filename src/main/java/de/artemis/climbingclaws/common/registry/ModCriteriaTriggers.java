package de.artemis.climbingclaws.common.registry;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.advancement.ClimbingClawsSimpleTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;

public final class ModCriteriaTriggers {
    public static final ClimbingClawsSimpleTrigger CLIMB_WITH_CLAWS = trigger("climb_with_claws");
    public static final ClimbingClawsSimpleTrigger HANG_WITH_CLAWS = trigger("hang_with_claws");
    public static final ClimbingClawsSimpleTrigger CLING_TO_CEILING = trigger("cling_to_ceiling");
    public static final ClimbingClawsSimpleTrigger USE_WALL_SPRING = trigger("use_wall_spring");
    public static final ClimbingClawsSimpleTrigger CLIMB_PARTIAL_SURFACE = trigger("climb_partial_surface");

    private ModCriteriaTriggers() {
    }

    public static void register() {
        CriteriaTriggers.register(CLIMB_WITH_CLAWS);
        CriteriaTriggers.register(HANG_WITH_CLAWS);
        CriteriaTriggers.register(CLING_TO_CEILING);
        CriteriaTriggers.register(USE_WALL_SPRING);
        CriteriaTriggers.register(CLIMB_PARTIAL_SURFACE);
    }

    private static ClimbingClawsSimpleTrigger trigger(String path) {
        return new ClimbingClawsSimpleTrigger(new ResourceLocation(ClimbingClaws.MOD_ID, path));
    }
}
