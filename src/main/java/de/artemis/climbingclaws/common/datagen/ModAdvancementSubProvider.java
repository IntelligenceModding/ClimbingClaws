package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.registry.ModCriteriaTriggers;
import de.artemis.climbingclaws.common.registry.ModEnchantments;
import de.artemis.climbingclaws.common.registry.ModItems;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class ModAdvancementSubProvider implements AdvancementProvider.AdvancementGenerator {
    private static final ResourceLocation ROOT_ID = id("root");
    private static final ResourceLocation BACKGROUND = ResourceLocation.withDefaultNamespace("textures/block/cobbled_deepslate.png");

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
        var enchantments = registries.lookupOrThrow(Registries.ENCHANTMENT);

        AdvancementHolder root = Advancement.Builder.advancement()
                .display(
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
                .save(saver, ROOT_ID, existingFileHelper);

        AdvancementHolder suitUp = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.CLIMBING_CLAWS.get(),
                        Component.translatable("advancement.climbingclaws.suit_up.title"),
                        Component.translatable("advancement.climbingclaws.suit_up.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_climbing_claws", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CLIMBING_CLAWS.get()))
                .save(saver, id("suit_up"), existingFileHelper);

        AdvancementHolder wallCrawler = Advancement.Builder.advancement()
                .parent(suitUp)
                .display(
                        ModItems.CLIMBING_CLAWS.get(),
                        Component.translatable("advancement.climbingclaws.wall_crawler.title"),
                        Component.translatable("advancement.climbingclaws.wall_crawler.description"),
                        null,
                        AdvancementType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("climb_with_claws", ModCriteriaTriggers.CLIMB_WITH_CLAWS.criterion())
                .save(saver, id("wall_crawler"), existingFileHelper);

        AdvancementHolder holdFast = Advancement.Builder.advancement()
                .parent(wallCrawler)
                .display(
                        ModItems.CLIMBING_CLAWS.get(),
                        Component.translatable("advancement.climbingclaws.hold_fast.title"),
                        Component.translatable("advancement.climbingclaws.hold_fast.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("hang_with_claws", ModCriteriaTriggers.HANG_WITH_CLAWS.criterion())
                .save(saver, id("hold_fast"), existingFileHelper);

        AdvancementHolder upsideDown = Advancement.Builder.advancement()
                .parent(wallCrawler)
                .display(
                        ModItems.CLIMBING_CLAWS.get(),
                        Component.translatable("advancement.climbingclaws.upside_down.title"),
                        Component.translatable("advancement.climbingclaws.upside_down.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("cling_to_ceiling", ModCriteriaTriggers.CLING_TO_CEILING.criterion())
                .save(saver, id("upside_down"), existingFileHelper);

        AdvancementHolder wallSpring = Advancement.Builder.advancement()
                .parent(wallCrawler)
                .display(
                        createEnchantedBook(enchantments.getOrThrow(ModEnchantments.WALL_SPRING), 1),
                        Component.translatable("advancement.climbingclaws.wall_spring.title"),
                        Component.translatable("advancement.climbingclaws.wall_spring.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("use_wall_spring", ModCriteriaTriggers.USE_WALL_SPRING.criterion())
                .save(saver, id("wall_spring"), existingFileHelper);

        Advancement.Builder canopyBuilder = Advancement.Builder.advancement()
                .parent(wallCrawler)
                .display(
                        createEnchantedBook(enchantments.getOrThrow(ModEnchantments.CANOPY_GRIP), 1),
                        Component.translatable("advancement.climbingclaws.canopy_route.title"),
                        Component.translatable("advancement.climbingclaws.canopy_route.description"),
                        null,
                        AdvancementType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("climb_partial_surface", ModCriteriaTriggers.CLIMB_PARTIAL_SURFACE.criterion());

        canopyBuilder.save(saver, id("canopy_route"), existingFileHelper);
    }

    private static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(ClimbingClaws.MOD_ID, path);
    }

    private static ItemStack createEnchantedBook(Holder<Enchantment> enchantment, int level) {
        ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
        ItemEnchantments.Mutable enchantments = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
        enchantments.set(enchantment, level);
        stack.set(DataComponents.STORED_ENCHANTMENTS, enchantments.toImmutable());
        return stack;
    }
}
