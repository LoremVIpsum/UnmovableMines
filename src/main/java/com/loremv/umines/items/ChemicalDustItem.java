package com.loremv.umines.items;

import com.loremv.umines.UnmovableMines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChemicalDustItem extends Item {
    public ChemicalDustItem(Properties settings) {
        super(settings);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level p_41422_, List<Component> tooltip, TooltipFlag p_41424_) {

        /*if(stack.hasTag())
        {
            tooltip.add(Component.translatable("item.umines.chemical_dust.short_description"));
        }*/
        super.appendHoverText(stack, p_41422_, tooltip, p_41424_);
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        String material = "";
        if (stack.hasTag()) {
            material = stack.getTag().getString("material");
        }

        if (material.isEmpty()) {
            return super.getName(stack);
        } else {
            return Component.empty()
                    .append(super.getName(stack))
                    .append(Component.literal(" (" + material + ")"));
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        if(!world.isClientSide && user.getItemInHand(hand).is(UnmovableMines.CHEMICAL_DUST_ITEM.get()))
        {
            if(user.hasPermissions(1) && user.isCrouching())
            {
                //OreLoader.setElementItemMap();
                user.sendSystemMessage(Component.empty().append("Reloaded ore list!"));
            }

            int e = user.getItemInHand(hand).getTag().getInt("element");
            /*Item item = OreLoader.ELEMENT_ITEM_MAP.getOrDefault(OreLoader.ELEMENTS.get(e), Items.STICK);
            if(item!=Items.STICK)
            {
                user.setItemInHand(hand,new ItemStack(item,user.getItemInHand(hand).getCount()));
            }*/
        }
        return super.use(world,user,hand);
    }
}
