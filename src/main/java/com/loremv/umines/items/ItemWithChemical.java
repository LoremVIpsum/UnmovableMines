package com.loremv.umines.items;


import com.loremv.umines.OreUtils;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemWithChemical extends Item {
    private final int[] atomic;
    private final String ores;
    public ItemWithChemical(Settings settings, int[] atoms,String ore) {
        super(settings);
        atomic=atoms;
        ores=ore;
    }

    public int[] getAtomic() {
        return atomic;
    }

    public String getOres() {
        return ores;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);

        for(int atoms: atomic)
        {
            tooltip.add(Text.of(OreUtils.ELEMENTS.get(atoms)));
        }
    }


}
