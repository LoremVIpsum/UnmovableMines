package com.loremv.umines.blocks;


import com.loremv.umines.OreUtils;
import com.loremv.umines.UnmovableMines;
import com.loremv.umines.items.ItemWithChemical;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ProcessingBE extends BlockEntity {

    private ListTag processedOres;
    public ProcessingBE(BlockPos pos, BlockState state) {
        super(UnmovableMines.PROCESSOR_BE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if(processedOres==null)
        {
            processedOres=new ListTag();
        }
        tag.put("processedOres",processedOres);
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        processedOres= (ListTag) tag.get("processedOres");
    }

    public ListTag getProcessedOres() {
        return processedOres;

    }

    public void setProcessedOres(ListTag processedOres) {
        if(OreUtils.ELEMENT_ITEM_MAP==null||OreUtils.ELEMENT_ITEM_MAP.isEmpty())
        {
            OreUtils.setElementItemMap();
        }
        this.processedOres = processedOres;
        setChanged();
    }

    public static void tick(Level world, BlockPos pos, BlockState state, ProcessingBE be)
    {
        if(world.getDayTime()%195L==0L)
        {
            if(be.getProcessedOres()==null || be.getProcessedOres().isEmpty())
            {
                ListTag ores = new ListTag();
                for (int i = 0; i < world.random.nextInt(1,5); i++) {
                    ores.add(StringTag.valueOf(OreUtils.keys.get(world.random.nextInt(OreUtils.keys.size()))));
                }
                be.setProcessedOres(ores);
            }
        }
        if(world.getDayTime()%200L==0L)
        {
            if(world.getBlockEntity(pos.above()) instanceof Container in)
            {
                if(world.getBlockEntity(pos.below()) instanceof Container out)
                {
                    for (int i = 0; i < in.getContainerSize(); i++) {
                        if(in.getItem(i).getItem() instanceof ItemWithChemical chemical)
                        {
                            boolean found = false;
                            for (int l = 0; l < be.getProcessedOres().size(); l++)
                            {
                                if(be.getProcessedOres().getString(l).equals(chemical.getOres()))
                                {
                                    found=true;
                                }
                            }
                            if(!found) break;

                            int[] atomics = chemical.getAtomic();
                            int take = world.random.nextInt(atomics.length)+1;
                            for (int j = 0; j < take; j++)
                            {
                                ItemStack output = OreUtils.ELEMENT_ITEM_MAP.getOrDefault(OreUtils.ELEMENTS.get(atomics[j]), UnmovableMines.CHEMICAL_DUST_ITEM.get()).getDefaultInstance();

                                if(output.is(UnmovableMines.CHEMICAL_DUST_ITEM.get()))
                                {
                                    CompoundTag compound = new CompoundTag();
                                    compound.putInt("element",atomics[j]);
                                    output.set(DataComponents.CUSTOM_DATA, CustomData.of(compound));
                                }

                                for (int k = 0; k < out.getContainerSize(); k++) {
                                    if(out.getItem(k).is(output.getItem()) && out.getItem(k).getCount()<out.getItem(k).getMaxStackSize())
                                    {
                                        if(out.getItem(k).is(UnmovableMines.CHEMICAL_DUST_ITEM.get()))
                                        {
                                            if(out.getItem(k).get(DataComponents.CUSTOM_DATA).copyTag().getInt("element")==output.get(DataComponents.CUSTOM_DATA).copyTag().getInt("element"))
                                            {
                                                in.getItem(i).shrink(1);
                                                out.getItem(k).grow(1);
                                                break;
                                            }
                                        }
                                        else
                                        {
                                            in.getItem(i).shrink(1);
                                            out.getItem(k).grow(1);
                                            break;
                                        }


                                    }
                                    else if(out.getItem(k).isEmpty())
                                    {
                                        in.getItem(i).shrink(1);
                                        out.setItem(k,output);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
