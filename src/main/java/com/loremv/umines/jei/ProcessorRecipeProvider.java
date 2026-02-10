package com.loremv.umines.jei;

import com.google.common.collect.Lists;
import com.loremv.umines.UnmovableMines;
import com.loremv.umines.items.ItemWithChemical;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ProcessorRecipeProvider {

    public static List<IProcessorRecipe> getAll() {
        var recipes = new ArrayList<IProcessorRecipe>();

        for (var input : UnmovableMines.ORE_ITEMS) {
            // Chunk by 6's so it displays well in JEI menu
            Lists.partition(input.get().getOutputItems(), 6).forEach(chunk -> {
                recipes.add(new ProcessorRecipe(
                        Ingredient.of(input.get()),
                        chunk
                ));
            });
        }

        return recipes;
    }
}
