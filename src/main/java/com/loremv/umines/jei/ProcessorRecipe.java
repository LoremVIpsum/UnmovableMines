package com.loremv.umines.jei;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public record ProcessorRecipe(
        Ingredient input,
        List<ItemStack> output
) implements IProcessorRecipe {

    @Override
    public Ingredient getInput() {
        return input;
    }

    @Override
    public List<ItemStack> getOutput() {
        return output;
    }
}
