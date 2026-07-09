package de.artemis.climbingclaws.common.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.predicates.ContextAwarePredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

public final class ClimbingClawsSimpleTrigger extends SimpleCriterionTrigger<ClimbingClawsSimpleTrigger.TriggerInstance> {
    private final Identifier id;

    public ClimbingClawsSimpleTrigger(Identifier id) {
        this.id = id;
    }

    @Override
    public Codec<TriggerInstance> codec() {
        return TriggerInstance.CODEC;
    }

    public Identifier id() {
        return id;
    }

    public Criterion<TriggerInstance> criterion() {
        return createCriterion(new TriggerInstance(Optional.empty()));
    }

    public void trigger(ServerPlayer player) {
        trigger(player, instance -> true);
    }

    public record TriggerInstance(Optional<ContextAwarePredicate> player) implements SimpleInstance {
        public static final Codec<TriggerInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(TriggerInstance::player)
        ).apply(instance, TriggerInstance::new));
    }
}
