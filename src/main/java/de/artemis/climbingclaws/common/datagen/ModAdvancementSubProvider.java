package de.artemis.climbingclaws.common.datagen;

import de.artemis.climbingclaws.ClimbingClaws;
import de.artemis.climbingclaws.common.registry.ModCriteriaTriggers;
import de.artemis.climbingclaws.common.registry.ModEnchantments;
import de.artemis.climbingclaws.common.registry.ModItems;
import java.util.function.Consumer;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public final class ModAdvancementSubProvider implements ForgeAdvancementProvider.AdvancementGenerator {
    private static final ResourceLocation ROOT_ID = id("root");
    private static final ResourceLocation BACKGROUND = new ResourceLocation("textures/block/cobbled_deepslate.png");

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {
        Advancement root = Advancement.Builder.advancement()
                .display(
                        ModItems.CLIMBING_CLAWS.get(),
                        Component.translatable("advancement.climbingclaws.root.title"),
                        Component.translatable("advancement.climbingclaws.root.description"),
                        BACKGROUND,
                        FrameType.TASK,
                        false,
                        false,
                        false
                )
                .addCriterion("has_climbing_claws", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CLIMBING_CLAWS.get()))
                .save(saver, ROOT_ID, existingFileHelper);

        Advancement suitUp = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        ModItems.CLIMBING_CLAWS.get(),
                        Component.translatable("advancement.climbingclaws.suit_up.title"),
                        Component.translatable("advancement.climbingclaws.suit_up.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("has_climbing_claws", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CLIMBING_CLAWS.get()))
                .save(saver, id("suit_up"), existingFileHelper);

        Advancement wallCrawler = Advancement.Builder.advancement()
                .parent(suitUp)
                .display(
                        ModItems.CLIMBING_CLAWS.get(),
                        Component.translatable("advancement.climbingclaws.wall_crawler.title"),
                        Component.translatable("advancement.climbingclaws.wall_crawler.description"),
                        null,
                        FrameType.TASK,
                        true,
                        true,
                        false
                )
                .addCriterion("climb_with_claws", ModCriteriaTriggers.CLIMB_WITH_CLAWS.criterion())
                .save(saver, id("wall_crawler"), existingFileHelper);

        Advancement holdFast = Advancement.Builder.advancement()
                .parent(wallCrawler)
                .display(
                        ModItems.CLIMBING_CLAWS.get(),
                        Component.translatable("advancement.climbingclaws.hold_fast.title"),
                        Component.translatable("advancement.climbingclaws.hold_fast.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("hang_with_claws", ModCriteriaTriggers.HANG_WITH_CLAWS.criterion())
                .save(saver, id("hold_fast"), existingFileHelper);

        Advancement upsideDown = Advancement.Builder.advancement()
                .parent(wallCrawler)
                .display(
                        ModItems.CLIMBING_CLAWS.get(),
                        Component.translatable("advancement.climbingclaws.upside_down.title"),
                        Component.translatable("advancement.climbingclaws.upside_down.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("cling_to_ceiling", ModCriteriaTriggers.CLING_TO_CEILING.criterion())
                .save(saver, id("upside_down"), existingFileHelper);

        Advancement wallSpring = Advancement.Builder.advancement()
                .parent(wallCrawler)
                .display(
                        EnchantedBookItem.createForEnchantment(new EnchantmentInstance(ModEnchantments.WALL_SPRING.get(), 1)),
                        Component.translatable("advancement.climbingclaws.wall_spring.title"),
                        Component.translatable("advancement.climbingclaws.wall_spring.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("use_wall_spring", ModCriteriaTriggers.USE_WALL_SPRING.criterion())
                .save(saver, id("wall_spring"), existingFileHelper);

        Advancement.Builder canopyBuilder = Advancement.Builder.advancement()
                .parent(wallCrawler)
                .display(
                        EnchantedBookItem.createForEnchantment(new EnchantmentInstance(ModEnchantments.CANOPY_GRIP.get(), 1)),
                        Component.translatable("advancement.climbingclaws.canopy_route.title"),
                        Component.translatable("advancement.climbingclaws.canopy_route.description"),
                        null,
                        FrameType.GOAL,
                        true,
                        true,
                        false
                )
                .addCriterion("climb_partial_surface", ModCriteriaTriggers.CLIMB_PARTIAL_SURFACE.criterion());

        canopyBuilder.save(saver, id("canopy_route"), existingFileHelper);
    }

    private static ResourceLocation id(String path) {
        return new ResourceLocation(ClimbingClaws.MOD_ID, path);
    }
}
