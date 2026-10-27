package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.registry.ModCriteriaTriggers;
import de.artemis.climbingclaws.common.registry.ModEnchantments;
import de.artemis.climbingclaws.common.registry.ModItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.triggers.InventoryChangeTrigger;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.advancements.AdvancementSubProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public final class ModAdvancementSubProvider extends AdvancementSubProvider {
    private static final Identifier ROOT_ID = id("root");
    private static final Identifier BACKGROUND = Identifier.withDefaultNamespace("block/cobbled_deepslate");

    public ModAdvancementSubProvider(BootstrapContext<Advancement> output) {
        super(output);
    }

    @Override
    public void generate() {
        var enchantments = this.output.lookup(Registries.ENCHANTMENT);

        AdvancementHolder root = Advancement.Builder.advancement()
                .rootDisplay(
                        ModItems.CLIMBING_CLAWS.get(),
                        Component.translatable("advancement.climbingclaws.root.title"),
                        Component.translatable("advancement.climbingclaws.root.description"),
                        BACKGROUND,
                        AdvancementType.TASK,
                        false,
                        false,
                        false
                )
                .addCriterion("has_climbing_claws", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CLIMBING_CLAWS.get()))
                .save(this.output, ROOT_ID.toString());

        AdvancementHolder suitUp = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.CLIMBING_CLAWS.get(),
                        Component.translatable("advancement.climbingclaws.suit_up.title"),
                        Component.translatable("advancement.climbingclaws.suit_up.description"),
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_climbing_claws", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CLIMBING_CLAWS.get()))
                .save(this.output, id("suit_up").toString());

        AdvancementHolder wallCrawler = Advancement.Builder.advancement()
                .parent(suitUp)
                .display(
                        ModItems.CLIMBING_CLAWS.get(),
                        Component.translatable("advancement.climbingclaws.wall_crawler.title"),
                        Component.translatable("advancement.climbingclaws.wall_crawler.description"),
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("climb_with_claws", ModCriteriaTriggers.CLIMB_WITH_CLAWS.criterion())
                .save(this.output, id("wall_crawler").toString());

        AdvancementHolder holdFast = Advancement.Builder.advancement()
                .parent(wallCrawler)
                .display(
                        ModItems.CLIMBING_CLAWS.get(),
                        Component.translatable("advancement.climbingclaws.hold_fast.title"),
                        Component.translatable("advancement.climbingclaws.hold_fast.description"),
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("hang_with_claws", ModCriteriaTriggers.HANG_WITH_CLAWS.criterion())
                .save(this.output, id("hold_fast").toString());

        AdvancementHolder upsideDown = Advancement.Builder.advancement()
                .parent(wallCrawler)
                .display(
                        ModItems.CLIMBING_CLAWS.get(),
                        Component.translatable("advancement.climbingclaws.upside_down.title"),
                        Component.translatable("advancement.climbingclaws.upside_down.description"),
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("cling_to_ceiling", ModCriteriaTriggers.CLING_TO_CEILING.criterion())
                .save(this.output, id("upside_down").toString());

        Advancement.Builder.advancement()
                .parent(wallCrawler)
                .display(
                        enchantedBook(enchantments, ModEnchantments.WALL_SPRING),
                        Component.translatable("advancement.climbingclaws.wall_spring.title"),
                        Component.translatable("advancement.climbingclaws.wall_spring.description"),
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("use_wall_spring", ModCriteriaTriggers.USE_WALL_SPRING.criterion())
                .save(this.output, id("wall_spring").toString());

        Advancement.Builder.advancement()
                .parent(wallCrawler)
                .display(
                        enchantedBook(enchantments, ModEnchantments.CANOPY_GRIP),
                        Component.translatable("advancement.climbingclaws.canopy_route.title"),
                        Component.translatable("advancement.climbingclaws.canopy_route.description"),
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("climb_partial_surface", ModCriteriaTriggers.CLIMB_PARTIAL_SURFACE.criterion())
                .save(this.output, id("canopy_route").toString());
    }

    private static ItemStackTemplate enchantedBook(HolderGetter<Enchantment> enchantments, ResourceKey<Enchantment> enchantmentKey) {
        ItemEnchantments.Mutable storedEnchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        storedEnchantments.set(enchantments.getOrThrow(enchantmentKey), 1);
        return new ItemStackTemplate(
                Items.ENCHANTED_BOOK,
                DataComponentPatch.builder()
                        .set(DataComponents.STORED_ENCHANTMENTS, storedEnchantments.toImmutable())
                        .build()
        );
    }

    private static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(ClimbingClaws.MOD_ID, path);
    }
}
