package de.artemis.climbingclaws.common.registry;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.advancement.ClimbingClawsSimpleTrigger;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.registries.RegisterEvent;

public final class ModCriteriaTriggers {
    public static final ClimbingClawsSimpleTrigger CLIMB_WITH_CLAWS = trigger("climb_with_claws");
    public static final ClimbingClawsSimpleTrigger HANG_WITH_CLAWS = trigger("hang_with_claws");
    public static final ClimbingClawsSimpleTrigger CLING_TO_CEILING = trigger("cling_to_ceiling");
    public static final ClimbingClawsSimpleTrigger USE_WALL_SPRING = trigger("use_wall_spring");
    public static final ClimbingClawsSimpleTrigger CLIMB_PARTIAL_SURFACE = trigger("climb_partial_surface");

    private ModCriteriaTriggers() {
    }

    public static void register(RegisterEvent event) {
        event.register(Registries.TRIGGER_TYPE, helper -> {
            helper.register(CLIMB_WITH_CLAWS.id(), CLIMB_WITH_CLAWS);
            helper.register(HANG_WITH_CLAWS.id(), HANG_WITH_CLAWS);
            helper.register(CLING_TO_CEILING.id(), CLING_TO_CEILING);
            helper.register(USE_WALL_SPRING.id(), USE_WALL_SPRING);
            helper.register(CLIMB_PARTIAL_SURFACE.id(), CLIMB_PARTIAL_SURFACE);
        });
    }

    private static ClimbingClawsSimpleTrigger trigger(String path) {
        return new ClimbingClawsSimpleTrigger(Identifier.fromNamespaceAndPath(ClimbingClaws.MOD_ID, path));
    }
}
