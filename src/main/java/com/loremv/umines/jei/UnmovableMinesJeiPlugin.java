package com.loremv.umines.jei;

import com.loremv.umines.UnmovableMines;
import mezz.jei.api.*;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IModIngredientRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class UnmovableMinesJeiPlugin implements IModPlugin {

    public final static RecipeType<IProcessorRecipe> PROCESSOR_RECIPE_TYPE = RecipeType.create(
            UnmovableMines.MODID,
            "processor",
            IProcessorRecipe.class);

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(UnmovableMines.MODID, "unmovable_mines_jei");
    }

    @Override
    public void registerCategories(@NotNull IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new ProcessorJeiCategory(
                        registration.getJeiHelpers().getGuiHelper()
                )
        );

    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration reg) {
        reg.addRecipeCatalyst(
                new ItemStack(UnmovableMines.PROCESSING_BLOCK_ITEM.get()),
                PROCESSOR_RECIPE_TYPE
        );
    }

    @Override
    public void registerRecipes(@NotNull IRecipeRegistration registration) {
        registration.addRecipes(PROCESSOR_RECIPE_TYPE, ProcessorRecipeProvider.getAll());
    }
}
