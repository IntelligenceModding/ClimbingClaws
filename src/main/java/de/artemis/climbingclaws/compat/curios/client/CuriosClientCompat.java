package de.artemis.climbingclaws.compat.curios.client;

import de.artemis.climbingclaws.common.registry.ModItems;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public final class CuriosClientCompat {
    private CuriosClientCompat() {
    }

    public static void registerRenderers() {
        ICurioRenderer.register(ModItems.CLIMBING_CLAWS.get(), ClimbingClawsCurioRenderer::new);
    }
}
