package de.artemis.climbingclaws.client;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import de.artemis.climbingclaws.common.config.ClimbingClawsConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeConfigSpec.ValueSpec;

/**
 * Hand-built replacement for NeoForge's built-in ConfigurationScreen, which Forge has no equivalent of.
 * Walks the live {@link ForgeConfigSpec} values in parallel with {@link ForgeConfigSpec#getSpec()} (the
 * ValueSpec/comment/range tree) to reproduce the same section/tooltip/editing behavior.
 */
public final class ClimbingClawsConfigScreens {
    private static final int ROW_HEIGHT = 24;
    private static final int ROW_WIDTH = 310;

    private ClimbingClawsConfigScreens() {
    }

    public static Screen create(Screen parent) {
        return new CategoryScreen(parent);
    }

    private static IntegratedServer editableServer(Minecraft minecraft) {
        IntegratedServer server = minecraft.getSingleplayerServer();
        if (server != null && minecraft.player != null
                && minecraft.player.connection.getConnection().isMemoryConnection()
                && server.isSingleplayerOwner(minecraft.player.getGameProfile())
                && !server.isPublished() && ClimbingClawsConfig.SERVER_SPEC.isLoaded()) {
            return server;
        }
        return null;
    }

    private static final class CategoryScreen extends Screen {
        private final Screen parent;

        private CategoryScreen(Screen parent) {
            super(Component.translatable("climbingclaws.configuration.title",
                    Component.translatable("item.climbingclaws.climbing_claws")));
            this.parent = parent;
        }

        @Override
        protected void init() {
            int centerX = width / 2;
            Button serverSettings = addRenderableWidget(Button.builder(
                            Component.translatable("climbingclaws.configuration.section.climbingclaws.server.toml"),
                            button -> {
                                IntegratedServer server = editableServer(minecraft);
                                if (server != null) {
                                    minecraft.setScreen(new ConfigSectionScreen(this, ClimbingClawsConfig.SERVER_SPEC,
                                            server, List.of(), button.getMessage()));
                                }
                            })
                    .bounds(centerX - ROW_WIDTH / 2, height / 2 - 24, ROW_WIDTH, 20)
                    .build());
            serverSettings.active = editableServer(minecraft) != null;
            if (!serverSettings.active) {
                serverSettings.setTooltip(Tooltip.create(Component.translatable(
                        "climbingclaws.configuration.server.unavailable")));
            }
            addRenderableWidget(Button.builder(
                            Component.translatable("climbingclaws.configuration.section.climbingclaws.client.toml"),
                            button -> minecraft.setScreen(new ConfigSectionScreen(
                                    this,
                                    ClientConfig.SPEC,
                                    null,
                                    List.of(),
                                    Component.translatable("climbingclaws.configuration.section.climbingclaws.client.toml"))))
                    .bounds(centerX - ROW_WIDTH / 2, height / 2, ROW_WIDTH, 20)
                    .build());
            addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose())
                    .bounds(centerX - 100, height - 28, 200, 20)
                    .build());
        }

        @Override
        public void onClose() {
            minecraft.setScreen(parent);
        }

        @Override
        public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            renderBackground(guiGraphics, mouseX, mouseY, partialTick);
            super.render(guiGraphics, mouseX, mouseY, partialTick);
            guiGraphics.drawCenteredString(font, title, width / 2, 16, 0xFFFFFF);
        }
    }

    private static final class ConfigSectionScreen extends Screen {
        private final Screen parent;
        private final ForgeConfigSpec spec;
        private final IntegratedServer server;
        private final List<String> path;

        private ConfigSectionScreen(
                Screen parent,
                ForgeConfigSpec spec,
                IntegratedServer server,
                List<String> path,
                Component sectionTitle
        ) {
            super(sectionTitle);
            this.parent = parent;
            this.spec = spec;
            this.server = server;
            this.path = path;
        }

        private boolean canEdit() {
            return spec.isLoaded() && (spec == ClientConfig.SPEC
                    || (server != null && server == editableServer(minecraft)));
        }

        @Override
        public void tick() {
            if (!canEdit()) {
                onClose();
            }
        }

        @Override
        protected void init() {
            if (!canEdit()) {
                return;
            }

            UnmodifiableConfig specs = path.isEmpty() ? spec.getSpec() : spec.getSpec().get(path);
            UnmodifiableConfig values = path.isEmpty() ? spec.getValues() : spec.getValues().get(path);
            RowList rowList = new RowList(minecraft, width, height, 36, height - 36, ROW_HEIGHT);
            for (UnmodifiableConfig.Entry entry : specs.entrySet()) {
                String key = entry.getKey();
                Object rawSpec = entry.getValue();

                if (rawSpec instanceof UnmodifiableConfig) {
                    List<String> sectionPath = new ArrayList<>(path);
                    sectionPath.add(key);
                    Component label = configLabel(spec.getLevelTranslationKey(sectionPath), key);
                    rowList.addRow(new SectionRow(label, button -> minecraft.setScreen(new ConfigSectionScreen(
                            this, spec, server, sectionPath, label))));
                    continue;
                }

                if (!(rawSpec instanceof ValueSpec valueSpec)) {
                    continue;
                }

                String translationKey = valueSpec.getTranslationKey();
                Component label = configLabel(translationKey, key);
                Component tooltip = translationKey != null && I18n.exists(translationKey + ".tooltip")
                        ? Component.translatable(translationKey + ".tooltip")
                        : null;
                rowList.addRow(buildValueRow(values.get(key), label, tooltip, valueSpec));
            }

            addRenderableWidget(rowList);
            addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose())
                    .bounds(width / 2 - 100, height - 28, 200, 20)
                    .build());
        }

        private Row buildValueRow(ConfigValue<?> configValue, Component label, Component tooltip, ValueSpec valueSpec) {
            if (configValue instanceof BooleanValue booleanValue) {
                Checkbox checkbox = Checkbox.builder(Component.empty(), font)
                        .selected(booleanValue.get())
                        .onValueChange((box, value) -> saveValue(booleanValue, value))
                        .build();
                return new ValueRow(label, tooltip, checkbox);
            }

            EditBox editBox = new EditBox(font, 0, 0, 90, 20, label);
            editBox.setValue(String.valueOf(configValue.get()));
            editBox.setResponder(text -> {
                if (configValue instanceof IntValue intValue) {
                    trySetNumeric(intValue, valueSpec, Integer::valueOf, text, editBox);
                } else if (configValue instanceof DoubleValue doubleValue) {
                    trySetNumeric(doubleValue, valueSpec, Double::valueOf, text, editBox);
                }
            });
            return new ValueRow(label, tooltip, editBox);
        }

        private <T extends Number> void trySetNumeric(ConfigValue<T> configValue, ValueSpec valueSpec,
                Function<String, T> parser, String text, EditBox editBox) {
            try {
                T parsed = parser.apply(text);
                if (Double.isFinite(parsed.doubleValue()) && valueSpec.test(parsed)) {
                    saveValue(configValue, parsed);
                    editBox.setTextColor(0xE0E0E0);
                } else {
                    editBox.setTextColor(0xFF5555);
                }
            } catch (NumberFormatException exception) {
                editBox.setTextColor(0xFF5555);
            }
        }

        private <T> void saveValue(ConfigValue<T> configValue, T value) {
            if (!canEdit()) {
                return;
            }
            if (server == null) {
                configValue.set(value);
                spec.save();
                return;
            }
            server.execute(() -> {
                if (canEdit()) {
                    configValue.set(value);
                    spec.save();
                }
            });
        }

        private static Component configLabel(String translationKey, String key) {
            return translationKey == null ? Component.literal(key) : Component.translatableWithFallback(translationKey, key);
        }

        @Override
        public void onClose() {
            minecraft.setScreen(parent);
        }

        @Override
        public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            renderBackground(guiGraphics, mouseX, mouseY, partialTick);
            super.render(guiGraphics, mouseX, mouseY, partialTick);
            guiGraphics.drawCenteredString(font, title, width / 2, 16, 0xFFFFFF);
        }
    }

    private abstract static class Row extends ContainerObjectSelectionList.Entry<Row> {
    }

    private static final class SectionRow extends Row {
        private final Button button;

        private SectionRow(Component label, Button.OnPress onPress) {
            this.button = Button.builder(label, onPress).bounds(0, 0, ROW_WIDTH, 20).build();
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            button.setPosition(left, top);
            button.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        @Override
        public List<? extends net.minecraft.client.gui.narration.NarratableEntry> narratables() {
            return List.of(button);
        }

        @Override
        public List<? extends net.minecraft.client.gui.components.events.GuiEventListener> children() {
            return List.of(button);
        }
    }

    private static final class ValueRow extends Row {
        private final Component label;
        private final Component tooltip;
        private final net.minecraft.client.gui.components.AbstractWidget control;

        private ValueRow(Component label, Component tooltip, net.minecraft.client.gui.components.AbstractWidget control) {
            this.label = label;
            this.tooltip = tooltip;
            this.control = control;
            if (tooltip != null) {
                control.setTooltip(Tooltip.create(tooltip));
            }
        }

        @Override
        public void render(GuiGraphics guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick) {
            guiGraphics.drawString(net.minecraft.client.Minecraft.getInstance().font, label, left, top + 6, 0xFFFFFF);
            control.setPosition(left + width - 100, top);
            control.setWidth(95);
            control.render(guiGraphics, mouseX, mouseY, partialTick);
        }

        @Override
        public List<? extends net.minecraft.client.gui.narration.NarratableEntry> narratables() {
            return List.of(control);
        }

        @Override
        public List<? extends net.minecraft.client.gui.components.events.GuiEventListener> children() {
            return List.of(control);
        }
    }

    private static final class RowList extends ContainerObjectSelectionList<Row> {
        private RowList(Minecraft minecraft, int width, int height, int top, int bottom, int itemHeight) {
            super(minecraft, width, bottom - top, top, itemHeight);
        }

        void addRow(Row row) {
            addEntry(row);
        }

        @Override
        public int getRowWidth() {
            return ROW_WIDTH;
        }
    }
}
