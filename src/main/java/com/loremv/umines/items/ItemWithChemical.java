package com.loremv.umines.items;


import com.loremv.umines.OreUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ItemWithChemical extends Item {
    private final int[] atomic;
    private final String ores;
    public ItemWithChemical(Properties settings, int[] atoms,String ore) {
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        for(int atoms: atomic)
        {
            tooltipComponents.add(Component.empty().append(OreUtils.ELEMENTS.get(atoms)));
        }
    }
}
