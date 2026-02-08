package com.loremv.umines.blocks;


import com.loremv.umines.OreUtils;
import com.loremv.umines.UnmovableMines;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class MiningBE extends BlockEntity {
    private String minedOre = "empty";
    public MiningBE(BlockPos pos, BlockState state) {
        super(UnmovableMines.MINING_BE.get(), pos, state);

    }



    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putString("minedOre",minedOre);
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        minedOre=tag.getString("minedOre");
    }

    public void setMinedOre(String minedOre) {
        this.minedOre = minedOre;
        setChanged();
    }

    public static void tick(Level world, BlockPos pos, BlockState state, MiningBE be)
    {
        if(world.getDayTime()%995L==0L)
        {
            if(be.minedOre.equals("empty"))
            {

                String s = OreUtils.keys.get(world.random.nextInt(OreUtils.keys.size())).toLowerCase();
                be.setMinedOre(s);

            }
        }
        if(world.getDayTime()%1000L==0L)
        {
            if(world.getBlockEntity(pos.above()) instanceof Container inventory)
            {
                for (int i = 0; i < inventory.getContainerSize(); i++) {
                    if(inventory.getItem(i).is(OreUtils.REGISTRY.get(be.minedOre)) && inventory.getItem(i).getCount()<inventory.getItem(i).getMaxStackSize())
                    {
                        inventory.getItem(i).grow(1);
                        break;
                    }
                    else if(inventory.getItem(i).isEmpty())
                    {
                        inventory.setItem(i, new ItemStack(OreUtils.REGISTRY.getOrDefault(be.minedOre, Items.COBBLESTONE)));
                        break;
                    }
                }
            }
        }
    }

    public String getMinedOre() {
        return minedOre;
    }
}
