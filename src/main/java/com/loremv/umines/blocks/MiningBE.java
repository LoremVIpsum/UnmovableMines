package com.loremv.umines.blocks;

import com.loremv.umines.UnmovableMines;
import com.loremv.umines.items.ItemWithChemical;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.Random;

public class MiningBE extends BlockEntity {
    private String minedOre = "empty";
    public MiningBE(BlockPos pos, BlockState state) {
        super(UnmovableMines.MINING_BE.get(), pos, state);

    }



    @Override
    protected void saveAdditional(CompoundTag tag) {
        tag.putString("minedOre",minedOre);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
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
                be.chooseMinedOre(world.random);
            }
        }
        if (world.getDayTime()%1000L==0L)
        {
            if (world.getBlockEntity(pos.above()) instanceof Container inventory)
            {
                var toOutput = be.getMinedOre();
                if (toOutput.isEmpty()) { return; }
                for (int i = 0; i < inventory.getContainerSize(); i++) {
                    if(inventory.getItem(i).is(toOutput.get()) && inventory.getItem(i).getCount()<inventory.getItem(i).getMaxStackSize())
                    {
                        inventory.getItem(i).grow(1);
                        break;
                    }
                    else if(inventory.getItem(i).isEmpty())
                    {
                        inventory.setItem(i, toOutput.get().getDefaultInstance());
                        break;
                    }
                }
            }
        }
    }

    public void chooseMinedOre(RandomSource random) {
        var minedOres = UnmovableMines.getDynamicContentManager().getOreNames();
        var randomOre = minedOres.get(random.nextInt(minedOres.size()));
        this.setMinedOre(randomOre);
    }

    public Optional<ItemWithChemical> getMinedOre() {
        return ItemWithChemical.byElementName(minedOre);
    }
}
