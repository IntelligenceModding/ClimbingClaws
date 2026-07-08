package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ClimbingClaws.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Climbing Claws uses a hand-authored item model in src/main/resources.
    }
}
