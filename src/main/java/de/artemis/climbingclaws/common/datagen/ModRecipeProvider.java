package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.common.registry.ModItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;

public class ModRecipeProvider extends RecipeProvider.Runner {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public String getName() {
        return "Climbing Claws Recipes";
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            protected void buildRecipes() {
                shaped(RecipeCategory.TOOLS, ModItems.CLIMBING_CLAWS.get())
                        .pattern("I I")
                        .pattern("ILI")
                        .pattern(" I ")
                        .define('I', Items.IRON_NUGGET)
                        .define('L', Items.LEATHER)
                        .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                        .save(this.output);
            }
        };
    }
}
