package de.artemis.climbingclaws.client;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.core.UnmodifiableConfig.Entry;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;

public final class ClimbingClawsConfigScreens {
    private static final Component EDIT = Component.translatable("neoforge.configuration.uitext.sectiontext");

    private ClimbingClawsConfigScreens() {
    }

    public static Screen create(ModContainer container, Screen parent) {
        return new ConfigurationScreen(container, parent, ClimbingClawsConfigSectionScreen::new);
    }

    private static final class ClimbingClawsConfigSectionScreen extends ConfigurationScreen.ConfigurationSectionScreen {
        private ClimbingClawsConfigSectionScreen(Screen parent, ModConfig.Type type, ModConfig modConfig, Component title) {
            super(parent, type, modConfig, title);
        }

        private ClimbingClawsConfigSectionScreen(
                Context parentContext,
                Screen parent,
                Map<String, Object> valueSpecs,
                String key,
                Set<? extends Entry> entrySet,
                Component title
        ) {
            super(parentContext, parent, valueSpecs, key, entrySet, title);
        }

        @Override
        protected Element createSection(String key, UnmodifiableConfig subconfig, UnmodifiableConfig subsection) {
            if (subconfig.isEmpty()) {
                return null;
            }

            Component label = getTranslationComponent(key);
            Component tooltip = getTooltipComponent(key, null);
            Button button = Button.builder(
                    EDIT,
                    buttonWidget -> minecraft.gui.setScreen(sectionCache.computeIfAbsent(
                            key,
                            sectionKey -> new ClimbingClawsConfigSectionScreen(
                                    context,
                                    this,
                                    subconfig.valueMap(),
                                    key,
                                    subsection.entrySet(),
                                    Component.translatable(getTranslationKey(key))
                            ).rebuild()
                    ))
            )
                    .tooltip(Tooltip.create(tooltip))
                    .width(Button.DEFAULT_WIDTH)
                    .build();

            return new Element(label, tooltip, button, false);
        }
    }
}
