package com.loremv.umines.items;


import com.loremv.umines.OreUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

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
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> tooltip, TooltipFlag p_41424_) {
        super.appendHoverText(stack, p_41422_, tooltip, p_41424_);
        for(int atoms: atomic)
        {
            tooltip.add(Component.literal(OreUtils.ELEMENTS.get(atoms)));
        }
    }
}
