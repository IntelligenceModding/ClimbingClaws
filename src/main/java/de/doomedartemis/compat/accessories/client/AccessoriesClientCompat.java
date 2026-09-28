package de.doomedartemis.compat.accessories.client;

import de.doomedartemis.ClimbingClaws;
import de.doomedartemis.compat.accessories.AccessoriesCompat;
import de.doomedartemis.common.registry.ModItems;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import net.minecraft.resources.Identifier;

public final class AccessoriesClientCompat {
    public static final String ACCESSORIES_MOD_ID = AccessoriesCompat.ACCESSORIES_MOD_ID;

    private AccessoriesClientCompat() {
    }

    public static void registerRenderers() {
        AccessoriesRendererRegistry.bindItemToRenderer(
                ModItems.CLIMBING_CLAWS,
                Identifier.fromNamespaceAndPath(ClimbingClaws.MOD_ID, "climbing_claws"),
                ClimbingClawsAccessoryRenderer::new
        );
    }
}
