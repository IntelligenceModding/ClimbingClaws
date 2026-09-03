package de.artemis.climbingclaws.compat.rei;

import de.artemis.climbingclaws.common.registry.ModItems;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.forge.REIPluginClient;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.minecraft.network.chat.Component;

import java.util.List;

@REIPluginClient
public class ClimbingClawsReiPlugin implements REIClientPlugin {
    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.add(DefaultInformationDisplay.createFromEntries(
                EntryIngredients.of(ModItems.CLIMBING_CLAWS.get()),
                Component.translatable("item.climbingclaws.climbing_claws")
        ).lines(List.of(Component.translatable("rei.climbingclaws.climbing_claws"))));
    }
}
