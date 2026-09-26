package de.doomedartemis.compat.accessories.client;

import de.doomedartemis.compat.accessories.AccessoriesCompat;
import de.doomedartemis.common.registry.ModItems;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;

public final class AccessoriesClientCompat {
    public static final String ACCESSORIES_MOD_ID = AccessoriesCompat.ACCESSORIES_MOD_ID;

    private AccessoriesClientCompat() {
    }

    public static void registerRenderers() {
        AccessoriesRendererRegistry.registerRenderer(ModItems.CLIMBING_CLAWS, ClimbingClawsAccessoryRenderer::new);
    }
}
