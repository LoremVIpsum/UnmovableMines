package com.loremv.umines.jei;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public interface IProcessorRecipe {
    Ingredient getInput();
    List<ItemStack> getOutput();
}
