package de.doomedartemis.common.registry;

import de.doomedartemis.ClimbingClaws;
import de.doomedartemis.common.advancement.ClimbingClawsSimpleTrigger;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
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
        register(CLIMB_WITH_CLAWS);
        register(HANG_WITH_CLAWS);
        register(CLING_TO_CEILING);
        register(USE_WALL_SPRING);
        register(CLIMB_PARTIAL_SURFACE);
    }

    private static void register(ClimbingClawsSimpleTrigger trigger) {
        Registry.register(BuiltInRegistries.TRIGGER_TYPES, trigger.id(), (CriterionTrigger<?>) trigger);
    }

    private static ClimbingClawsSimpleTrigger trigger(String path) {
        return new ClimbingClawsSimpleTrigger(ResourceLocation.fromNamespaceAndPath(ClimbingClaws.MOD_ID, path));
    }
}
