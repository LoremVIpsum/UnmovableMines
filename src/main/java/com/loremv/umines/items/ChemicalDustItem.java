package com.loremv.umines.items;

import com.loremv.umines.OreUtils;
import com.loremv.umines.UnmovableMines;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChemicalDustItem extends Item {
    public ChemicalDustItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if(stack.hasNbt())
        {
            tooltip.add(Text.of(OreUtils.ELEMENTS.get(stack.getNbt().getInt("element"))));
            //tooltip.add(Text.of("You don't currently have a mod that has this dust or raw ore"));
            //tooltip.add(Text.of("If you do get one, just use this item in the air and it will turn into the correct thing"));
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient && user.getStackInHand(hand).isOf(UnmovableMines.CHEMICAL_DUST))
        {
            int e = user.getStackInHand(hand).getNbt().getInt("element");
            Item item = OreUtils.ELEMENT_ITEM_MAP.getOrDefault(OreUtils.ELEMENTS.get(e), Items.STICK);
            if(item!=Items.STICK)
            {
                user.setStackInHand(hand,new ItemStack(item,user.getStackInHand(hand).getCount()));
            }
        }
        return super.use(world, user, hand);
    }

}
