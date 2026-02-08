package com.loremv.umines.items;

import com.loremv.umines.OreUtils;
import com.loremv.umines.UnmovableMines;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class ChemicalDustItem extends Item {
    public ChemicalDustItem(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(stack.has(DataComponents.CUSTOM_DATA))
        {
            tooltipComponents.add(Component.empty().append(OreUtils.ELEMENTS.get(stack.get(DataComponents.CUSTOM_DATA).copyTag().getInt("element"))));
            //tooltip.add(Text.of("You don't currently have a mod that has this dust or raw ore"));
            //tooltip.add(Text.of("If you do get one, just use this item in the air and it will turn into the correct thing"));
        }
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if(!world.isClientSide && user.getItemInHand(hand).is(UnmovableMines.CHEMICAL_DUST_ITEM))
        {
            if(user.hasPermissions(1) && user.isCrouching())
            {
                OreUtils.setElementItemMap();
                user.sendSystemMessage(Component.empty().append("Reloaded ore list!"));
            }

            int e = user.getItemInHand(hand).get(DataComponents.CUSTOM_DATA).copyTag().getInt("element");
            Item item = OreUtils.ELEMENT_ITEM_MAP.getOrDefault(OreUtils.ELEMENTS.get(e), Items.STICK);
            if(item!=Items.STICK)
            {
                user.setItemInHand(hand,new ItemStack(item,user.getItemInHand(hand).getCount()));
            }
        }
        return super.use(world,user,hand);
    }
}
