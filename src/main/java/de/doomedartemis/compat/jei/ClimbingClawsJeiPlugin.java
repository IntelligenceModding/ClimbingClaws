package de.doomedartemis.compat.jei;

import de.doomedartemis.ClimbingClaws;
import de.doomedartemis.common.registry.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

@JeiPlugin
public class ClimbingClawsJeiPlugin implements IModPlugin {
    private static final Identifier PLUGIN_UID = Identifier.fromNamespaceAndPath(ClimbingClaws.MOD_ID, "jei_plugin");

    @Override
    public Identifier getPluginUid() {
        return PLUGIN_UID;
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(
                ModItems.CLIMBING_CLAWS,
                Component.translatable("jei.climbingclaws.climbing_claws")
        );
    }
}
