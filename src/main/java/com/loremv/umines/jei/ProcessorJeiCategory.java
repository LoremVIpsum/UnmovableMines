package com.loremv.umines.jei;

import com.loremv.umines.UnmovableMines;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ProcessorJeiCategory implements IRecipeCategory<IProcessorRecipe> {
    private final IDrawable icon;
    private final IDrawable slot;
    private final IDrawable arrow;

    public ProcessorJeiCategory(IGuiHelper helper) {
        this.icon = helper.createDrawableIngredient(
                VanillaTypes.ITEM_STACK,
                new ItemStack(UnmovableMines.PROCESSING_BLOCK_ITEM.get())
        );
        this.slot = helper.getSlotDrawable();
        this.arrow = helper.getRecipeArrow();
    }

    @Override
    public @NotNull RecipeType<IProcessorRecipe> getRecipeType() {
        return UnmovableMinesJeiPlugin.PROCESSOR_RECIPE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.literal("Ore Processor"); // TODO: Use localised string
    }

    @Override
    public void draw(
            @NotNull IProcessorRecipe recipe,
            @NotNull IRecipeSlotsView recipeSlotsView,
            @NotNull GuiGraphics graphics,
            double mouseX,
            double mouseY
    ) {
        IRecipeCategory.super.draw(recipe, recipeSlotsView, graphics, mouseX, mouseY);
        for (int i = 0; i < 6; i++) {
            int col = i % 3;
            int row = i / 3;
            slot.draw(graphics, 69 + (col * 19), 2 + (row * 19));
            arrow.draw(graphics, 36, 12);
            slot.draw(graphics, 9, 11);
        }
    }

    @Override
    public int getWidth() {
        return 137;
    }

    @Override
    public int getHeight() {
        return 40;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(@NotNull IRecipeLayoutBuilder builder, @NotNull IProcessorRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 10, 12)
                .addIngredients(recipe.getInput());

        List<ItemStack> outputs = recipe.getOutput();
        for (int i = 0; i < outputs.size(); i++) {
            int col = i % 3;
            int row = i / 3;
            builder.addSlot(RecipeIngredientRole.OUTPUT, 70 + (col * 19), 3 + (row * 19))
                    .addItemStack(outputs.get(i));
        }
    }
}
