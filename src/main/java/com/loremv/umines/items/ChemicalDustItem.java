package com.loremv.umines.items;

import com.loremv.umines.OreUtils;
import com.loremv.umines.UnmovableMines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChemicalDustItem extends Item {
    public ChemicalDustItem(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> tooltip, TooltipFlag p_41424_) {

        if(stack.hasTag())
        {
            tooltip.add(Component.literal(OreUtils.ELEMENTS.get(stack.getTag().getInt("element"))));
        }
        super.appendHoverText(stack, p_41422_, tooltip, p_41424_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if(!world.isClientSide && user.getItemInHand(hand).is(UnmovableMines.CHEMICAL_DUST_ITEM.get()))
        {
            if(user.hasPermissions(1) && user.isCrouching())
            {
                OreUtils.setElementItemMap();
                user.sendSystemMessage(Component.empty().append("Reloaded ore list!"));
            }

            int e = user.getItemInHand(hand).getTag().getInt("element");
            Item item = OreUtils.ELEMENT_ITEM_MAP.getOrDefault(OreUtils.ELEMENTS.get(e), Items.STICK);
            if(item!=Items.STICK)
            {
                user.setItemInHand(hand,new ItemStack(item,user.getItemInHand(hand).getCount()));
            }
        }
        return super.use(world,user,hand);
    }
}
