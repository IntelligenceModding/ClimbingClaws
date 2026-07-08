package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.common.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.CLIMBING_CLAWS.get())
                .pattern("I I")
                .pattern("ILI")
                .pattern(" I ")
                .define('I', Items.IRON_NUGGET)
                .define('L', Items.LEATHER)
                .unlockedBy(getHasName(Items.LEATHER), has(Items.LEATHER))
                .save(recipeOutput);
    }
}
