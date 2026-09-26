package de.doomedartemis.client;

import de.doomedartemis.common.config.ClimbingClawsConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class ClimbingClawsConfigScreen extends Screen {
    private static final int ROW_HEIGHT = 24;
    private static final int CONTROL_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 20;

    private final Screen parent;
    private final Draft draft;
    private final List<RenderedLabel> labels = new ArrayList<>();
    private Scope scope = Scope.CLIENT;
    private ServerCategory serverCategory = ServerCategory.GENERAL;
    private int page;
    private Component status = Component.empty();

    public ClimbingClawsConfigScreen(Screen parent) {
        super(Component.translatable("climbingclaws.config.title"));
        this.parent = parent;
        this.draft = new Draft(ClientConfig.snapshot(), ClimbingClawsConfig.snapshot());
    }

    @Override
    protected void init() {
        rebuild();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, 12, 0xFFFFFF);
        guiGraphics.drawCenteredString(this.font, currentSubtitle(), this.width / 2, 34, 0xA0A0A0);
        for (RenderedLabel label : this.labels) {
            guiGraphics.drawString(this.font, label.component(), label.x(), label.y(), 0xE0E0E0);
        }
        if (!this.status.getString().isEmpty()) {
            guiGraphics.drawCenteredString(this.font, this.status, this.width / 2, this.height - 54, 0xA0E0A0);
        }
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.parent);
        }
    }

    private void rebuild() {
        this.clearWidgets();
        this.labels.clear();

        int centerX = this.width / 2;
        int top = 52;
        addScopeButton(centerX - 124, top, Scope.CLIENT);
        addScopeButton(centerX + 4, top, Scope.SERVER);

        if (this.scope == Scope.SERVER) {
            int categoryY = top + 24;
            int startX = centerX - 154;
            addCategoryButton(startX, categoryY, ServerCategory.GENERAL);
            addCategoryButton(startX + 78, categoryY, ServerCategory.MOVEMENT);
            addCategoryButton(startX + 156, categoryY, ServerCategory.WALL_SPRING);
            addCategoryButton(startX + 234, categoryY, ServerCategory.DURABILITY);
        }

        List<Option> options = currentOptions();
        int rowTop = this.scope == Scope.CLIENT ? top + 38 : top + 62;
        int rowsPerPage = Math.max(1, (this.height - rowTop - 76) / ROW_HEIGHT);
        int maxPage = Math.max(0, (options.size() - 1) / rowsPerPage);
        this.page = Math.min(this.page, maxPage);
        int first = this.page * rowsPerPage;
        int last = Math.min(options.size(), first + rowsPerPage);
        for (int i = first; i < last; i++) {
            addOption(options.get(i), rowTop + ((i - first) * ROW_HEIGHT));
        }

        if (maxPage > 0) {
        this.addRenderableWidget(Button.builder(Component.literal("<"), button -> {
                        this.page = Math.max(0, this.page - 1);
                        rebuild();
                    })
                    .bounds(centerX - 66, this.height - 52, 28, BUTTON_HEIGHT)
                    .build());
            this.addRenderableWidget(Button.builder(Component.literal((this.page + 1) + " / " + (maxPage + 1)), button -> {
                    })
                    .bounds(centerX - 34, this.height - 52, 68, BUTTON_HEIGHT)
                    .build()).active = false;
            this.addRenderableWidget(Button.builder(Component.literal(">"), button -> {
                        this.page = Math.min(maxPage, this.page + 1);
                        rebuild();
                    })
                    .bounds(centerX + 38, this.height - 52, 28, BUTTON_HEIGHT)
                    .build());
        }

        this.addRenderableWidget(Button.builder(Component.translatable("climbingclaws.config.save"), button -> save())
                .bounds(centerX - 154, this.height - 28, 96, BUTTON_HEIGHT)
                .build());
        this.addRenderableWidget(Button.builder(Component.translatable("climbingclaws.config.reload"), button -> reload())
                .bounds(centerX - 48, this.height - 28, 96, BUTTON_HEIGHT)
                .build());
        this.addRenderableWidget(Button.builder(Component.translatable("climbingclaws.config.done"), button -> onClose())
                .bounds(centerX + 58, this.height - 28, 96, BUTTON_HEIGHT)
                .build());
    }

    private void addScopeButton(int x, int y, Scope buttonScope) {
        Button button = Button.builder(Component.translatable(buttonScope.titleKey), pressed -> {
                    this.scope = buttonScope;
                    this.page = 0;
                    this.status = Component.empty();
                    rebuild();
                })
                .bounds(x, y, 120, BUTTON_HEIGHT)
                .build();
        button.active = this.scope != buttonScope;
        this.addRenderableWidget(button);
    }

    private void addCategoryButton(int x, int y, ServerCategory category) {
        Button button = Button.builder(Component.literal(category.title), pressed -> {
                    this.serverCategory = category;
                    this.page = 0;
                    this.status = Component.empty();
                    rebuild();
                })
                .bounds(x, y, 74, BUTTON_HEIGHT)
                .build();
        button.active = this.serverCategory != category;
        this.addRenderableWidget(button);
    }

    private void addOption(Option option, int y) {
        int labelX = this.width / 2 - 170;
        int controlX = this.width / 2 + 50;
        Component label = Component.translatable(option.labelKey());
        Component tooltip = Component.translatable(option.labelKey() + ".tooltip");
        this.labels.add(new RenderedLabel(label, labelX, y + 6));

        if (option instanceof BooleanOption booleanOption) {
            Button button = Button.builder(booleanValue(booleanOption.getter().getAsBoolean()), pressed -> {
                        booleanOption.setter().accept(!booleanOption.getter().getAsBoolean());
                        this.status = Component.empty();
                        pressed.setMessage(booleanValue(booleanOption.getter().getAsBoolean()));
                    })
                    .bounds(controlX, y, CONTROL_WIDTH, BUTTON_HEIGHT)
                    .tooltip(Tooltip.create(tooltip))
                    .build();
            this.addRenderableWidget(button);
            return;
        }

        if (option instanceof DoubleOption doubleOption) {
            EditBox editBox = new EditBox(this.font, controlX, y, CONTROL_WIDTH, BUTTON_HEIGHT, label);
            editBox.setValue(formatDouble(doubleOption.getter().getAsDouble()));
            editBox.setTooltip(Tooltip.create(tooltip));
            editBox.setResponder(value -> {
                try {
                    double parsed = Double.parseDouble(value);
                    boolean valid = parsed >= doubleOption.min() && parsed <= doubleOption.max();
                    editBox.setTextColor(valid ? 0xE0E0E0 : 0xFF7070);
                    if (valid) {
                        doubleOption.setter().accept(parsed);
                        this.status = Component.empty();
                    }
                } catch (NumberFormatException ignored) {
                    editBox.setTextColor(0xFF7070);
                }
            });
            this.addRenderableWidget(editBox);
            return;
        }

        if (option instanceof IntOption intOption) {
            EditBox editBox = new EditBox(this.font, controlX, y, CONTROL_WIDTH, BUTTON_HEIGHT, label);
            editBox.setValue(Integer.toString(intOption.getter().getAsInt()));
            editBox.setTooltip(Tooltip.create(tooltip));
            editBox.setResponder(value -> {
                try {
                    int parsed = Integer.parseInt(value);
                    boolean valid = parsed >= intOption.min() && parsed <= intOption.max();
                    editBox.setTextColor(valid ? 0xE0E0E0 : 0xFF7070);
                    if (valid) {
                        intOption.setter().accept(parsed);
                        this.status = Component.empty();
                    }
                } catch (NumberFormatException ignored) {
                    editBox.setTextColor(0xFF7070);
                }
            });
            this.addRenderableWidget(editBox);
        }
    }

    private List<Option> currentOptions() {
        if (this.scope == Scope.CLIENT) {
            return List.of(new BooleanOption(
                    "climbingclaws.configuration.client.show_wall_spring_cooldown_overlay",
                    () -> this.draft.showWallSpringCooldownOverlay,
                    value -> this.draft.showWallSpringCooldownOverlay = value
            ));
        }

        return switch (this.serverCategory) {
            case GENERAL -> List.of(
                    new BooleanOption("climbingclaws.configuration.server.general.enable_climbing", () -> this.draft.enableClimbing, value -> this.draft.enableClimbing = value),
                    new BooleanOption("climbingclaws.configuration.server.general.allow_main_hand_use", () -> this.draft.allowMainHandUse, value -> this.draft.allowMainHandUse = value),
                    new BooleanOption("climbingclaws.configuration.server.general.allow_off_hand_use", () -> this.draft.allowOffHandUse, value -> this.draft.allowOffHandUse = value),
                    new BooleanOption("climbingclaws.configuration.server.general.enable_wall_climbing", () -> this.draft.enableWallClimbing, value -> this.draft.enableWallClimbing = value),
                    new BooleanOption("climbingclaws.configuration.server.general.enable_ceiling_climbing", () -> this.draft.enableCeilingClimbing, value -> this.draft.enableCeilingClimbing = value),
                    new BooleanOption("climbingclaws.configuration.server.general.enable_hanging", () -> this.draft.enableHanging, value -> this.draft.enableHanging = value),
                    new BooleanOption("climbingclaws.configuration.server.general.enable_controlled_descent", () -> this.draft.enableControlledDescent, value -> this.draft.enableControlledDescent = value),
                    new BooleanOption("climbingclaws.configuration.server.general.enable_canopy_grip_effect", () -> this.draft.enableCanopyGripEffect, value -> this.draft.enableCanopyGripEffect = value)
            );
            case MOVEMENT -> List.of(
                    new DoubleOption("climbingclaws.configuration.server.movement.side_climb_speed", () -> this.draft.sideClimbSpeed, value -> this.draft.sideClimbSpeed = value, 0.0D, 1.0D),
                    new DoubleOption("climbingclaws.configuration.server.movement.ceiling_climb_speed", () -> this.draft.ceilingClimbSpeed, value -> this.draft.ceilingClimbSpeed = value, 0.0D, 1.0D),
                    new DoubleOption("climbingclaws.configuration.server.movement.ceiling_hold_speed", () -> this.draft.ceilingHoldSpeed, value -> this.draft.ceilingHoldSpeed = value, 0.0D, 1.0D),
                    new DoubleOption("climbingclaws.configuration.server.movement.efficiency_speed_bonus", () -> this.draft.efficiencySpeedBonus, value -> this.draft.efficiencySpeedBonus = value, 0.0D, 0.25D),
                    new DoubleOption("climbingclaws.configuration.server.movement.horizontal_velocity_limit", () -> this.draft.horizontalVelocityLimit, value -> this.draft.horizontalVelocityLimit = value, 0.0D, 2.0D),
                    new DoubleOption("climbingclaws.configuration.server.movement.fall_speed_limit_while_attached", () -> this.draft.fallSpeedLimitWhileAttached, value -> this.draft.fallSpeedLimitWhileAttached = value, 0.0D, 2.0D)
            );
            case WALL_SPRING -> List.of(
                    new BooleanOption("climbingclaws.configuration.server.wall_spring.enable", () -> this.draft.enableWallSpring, value -> this.draft.enableWallSpring = value),
                    new BooleanOption("climbingclaws.configuration.server.wall_spring.allow_while_sneaking", () -> this.draft.allowWallSpringWhileSneaking, value -> this.draft.allowWallSpringWhileSneaking = value),
                    new DoubleOption("climbingclaws.configuration.server.wall_spring.level_one_boost", () -> this.draft.wallSpringLevelOneBoost, value -> this.draft.wallSpringLevelOneBoost = value, 0.0D, 5.0D),
                    new DoubleOption("climbingclaws.configuration.server.wall_spring.level_two_boost", () -> this.draft.wallSpringLevelTwoBoost, value -> this.draft.wallSpringLevelTwoBoost = value, 0.0D, 5.0D),
                    new IntOption("climbingclaws.configuration.server.wall_spring.cooldown_ticks", () -> this.draft.wallSpringCooldownTicks, value -> this.draft.wallSpringCooldownTicks = value, 0, 20 * 60 * 10)
            );
            case DURABILITY -> List.of(
                    new BooleanOption("climbingclaws.configuration.server.durability.enable_damage", () -> this.draft.enableDurabilityDamage, value -> this.draft.enableDurabilityDamage = value),
                    new IntOption("climbingclaws.configuration.server.durability.climbing_damage_amount", () -> this.draft.climbingDamageAmount, value -> this.draft.climbingDamageAmount = value, 0, 100),
                    new IntOption("climbingclaws.configuration.server.durability.active_climb_damage_interval_ticks", () -> this.draft.activeClimbDamageIntervalTicks, value -> this.draft.activeClimbDamageIntervalTicks = value, 1, 20 * 60),
                    new IntOption("climbingclaws.configuration.server.durability.cling_damage_interval_ticks", () -> this.draft.clingDamageIntervalTicks, value -> this.draft.clingDamageIntervalTicks = value, 1, 20 * 60),
                    new IntOption("climbingclaws.configuration.server.durability.wall_spring_damage_amount", () -> this.draft.wallSpringDamageAmount, value -> this.draft.wallSpringDamageAmount = value, 0, 100)
            );
        };
    }

    private void save() {
        ClientConfig.apply(this.draft.clientSnapshot());
        ClimbingClawsConfig.apply(this.draft.serverSnapshot());
        this.status = Component.translatable("climbingclaws.config.saved");
    }

    private void reload() {
        ClientConfig.load();
        ClimbingClawsConfig.load();
        this.draft.load(ClientConfig.snapshot(), ClimbingClawsConfig.snapshot());
        this.status = Component.translatable("climbingclaws.config.reloaded");
        rebuild();
    }

    private Component currentSubtitle() {
        if (this.scope == Scope.CLIENT) {
            return Component.translatable("climbingclaws.configuration.section.climbingclaws.client.toml.client");
        }
        return Component.translatable("climbingclaws.config.local_server")
                .append(Component.literal(" / " + this.serverCategory.title));
    }

    private static Component booleanValue(boolean value) {
        return Component.literal(value ? "On" : "Off");
    }

    private static String formatDouble(double value) {
        return String.format(Locale.ROOT, "%.4f", value).replaceAll("0+$", "").replaceAll("\\.$", "");
    }

    private enum Scope {
        CLIENT("climbingclaws.config.client"),
        SERVER("climbingclaws.config.server");

        private final String titleKey;

        Scope(String titleKey) {
            this.titleKey = titleKey;
        }
    }

    private enum ServerCategory {
        GENERAL("General"),
        MOVEMENT("Movement"),
        WALL_SPRING("Wall Spring"),
        DURABILITY("Durability");

        private final String title;

        ServerCategory(String title) {
            this.title = title;
        }
    }

    private sealed interface Option permits BooleanOption, DoubleOption, IntOption {
        String labelKey();
    }

    private record BooleanOption(String labelKey, BooleanSupplier getter, Consumer<Boolean> setter) implements Option {
    }

    private record DoubleOption(String labelKey, DoubleSupplier getter, DoubleConsumer setter, double min, double max) implements Option {
    }

    private record IntOption(String labelKey, IntSupplier getter, IntConsumer setter, int min, int max) implements Option {
    }

    private record RenderedLabel(Component component, int x, int y) {
    }

    private static final class Draft {
        boolean showWallSpringCooldownOverlay;
        boolean enableClimbing;
        boolean allowMainHandUse;
        boolean allowOffHandUse;
        boolean enableWallClimbing;
        boolean enableCeilingClimbing;
        boolean enableHanging;
        boolean enableControlledDescent;
        boolean enableCanopyGripEffect;
        double sideClimbSpeed;
        double ceilingClimbSpeed;
        double ceilingHoldSpeed;
        double efficiencySpeedBonus;
        double horizontalVelocityLimit;
        double fallSpeedLimitWhileAttached;
        boolean enableWallSpring;
        boolean allowWallSpringWhileSneaking;
        double wallSpringLevelOneBoost;
        double wallSpringLevelTwoBoost;
        int wallSpringCooldownTicks;
        boolean enableDurabilityDamage;
        int climbingDamageAmount;
        int activeClimbDamageIntervalTicks;
        int clingDamageIntervalTicks;
        int wallSpringDamageAmount;

        private Draft(ClientConfig.Snapshot client, ClimbingClawsConfig.Snapshot server) {
            load(client, server);
        }

        private void load(ClientConfig.Snapshot client, ClimbingClawsConfig.Snapshot server) {
            this.showWallSpringCooldownOverlay = client.showWallSpringCooldownOverlay();
            this.enableClimbing = server.enableClimbing();
            this.allowMainHandUse = server.allowMainHandUse();
            this.allowOffHandUse = server.allowOffHandUse();
            this.enableWallClimbing = server.enableWallClimbing();
            this.enableCeilingClimbing = server.enableCeilingClimbing();
            this.enableHanging = server.enableHanging();
            this.enableControlledDescent = server.enableControlledDescent();
            this.enableCanopyGripEffect = server.enableCanopyGripEffect();
            this.sideClimbSpeed = server.sideClimbSpeed();
            this.ceilingClimbSpeed = server.ceilingClimbSpeed();
            this.ceilingHoldSpeed = server.ceilingHoldSpeed();
            this.efficiencySpeedBonus = server.efficiencySpeedBonus();
            this.horizontalVelocityLimit = server.horizontalVelocityLimit();
            this.fallSpeedLimitWhileAttached = server.fallSpeedLimitWhileAttached();
            this.enableWallSpring = server.enableWallSpring();
            this.allowWallSpringWhileSneaking = server.allowWallSpringWhileSneaking();
            this.wallSpringLevelOneBoost = server.wallSpringLevelOneBoost();
            this.wallSpringLevelTwoBoost = server.wallSpringLevelTwoBoost();
            this.wallSpringCooldownTicks = server.wallSpringCooldownTicks();
            this.enableDurabilityDamage = server.enableDurabilityDamage();
            this.climbingDamageAmount = server.climbingDamageAmount();
            this.activeClimbDamageIntervalTicks = server.activeClimbDamageIntervalTicks();
            this.clingDamageIntervalTicks = server.clingDamageIntervalTicks();
            this.wallSpringDamageAmount = server.wallSpringDamageAmount();
        }

        private ClientConfig.Snapshot clientSnapshot() {
            return new ClientConfig.Snapshot(this.showWallSpringCooldownOverlay);
        }

        private ClimbingClawsConfig.Snapshot serverSnapshot() {
            return new ClimbingClawsConfig.Snapshot(
                    this.enableClimbing,
                    this.allowMainHandUse,
                    this.allowOffHandUse,
                    this.enableWallClimbing,
                    this.enableCeilingClimbing,
                    this.enableHanging,
                    this.enableControlledDescent,
                    this.enableCanopyGripEffect,
                    this.sideClimbSpeed,
                    this.ceilingClimbSpeed,
                    this.ceilingHoldSpeed,
                    this.efficiencySpeedBonus,
                    this.horizontalVelocityLimit,
                    this.fallSpeedLimitWhileAttached,
                    this.enableWallSpring,
                    this.allowWallSpringWhileSneaking,
                    this.wallSpringLevelOneBoost,
                    this.wallSpringLevelTwoBoost,
                    this.wallSpringCooldownTicks,
                    this.enableDurabilityDamage,
                    this.climbingDamageAmount,
                    this.activeClimbDamageIntervalTicks,
                    this.clingDamageIntervalTicks,
                    this.wallSpringDamageAmount
            );
        }
    }
}
