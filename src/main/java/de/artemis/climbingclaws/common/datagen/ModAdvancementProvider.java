package de.artemis.climbingclaws.common.datagen;

import java.util.List;
import net.minecraft.data.advancements.AdvancementProvider;

public final class ModAdvancementProvider extends AdvancementProvider {
    public ModAdvancementProvider() {
        super(List.of(ModAdvancementSubProvider::new));
    }
}
