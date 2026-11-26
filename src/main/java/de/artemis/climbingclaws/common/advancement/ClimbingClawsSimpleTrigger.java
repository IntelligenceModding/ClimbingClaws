package de.artemis.climbingclaws.common.advancement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.ContextAwarePredicate;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public final class ClimbingClawsSimpleTrigger extends SimpleCriterionTrigger<ClimbingClawsSimpleTrigger.TriggerInstance> {
    private final ResourceLocation id;

    public ClimbingClawsSimpleTrigger(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    protected TriggerInstance createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
        return new TriggerInstance(id, player);
    }

    public TriggerInstance criterion() {
        return new TriggerInstance(id, ContextAwarePredicate.ANY);
    }

    public void trigger(ServerPlayer player) {
        trigger(player, instance -> true);
    }

    public static final class TriggerInstance extends AbstractCriterionTriggerInstance {
        private TriggerInstance(ResourceLocation id, ContextAwarePredicate player) {
            super(id, player);
        }
    }
}
