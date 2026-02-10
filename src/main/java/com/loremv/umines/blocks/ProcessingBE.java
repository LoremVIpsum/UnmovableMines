package com.loremv.umines.blocks;

import com.loremv.umines.UnmovableMines;
import com.loremv.umines.UnmovableMinesUtil;
import com.loremv.umines.items.ItemWithChemical;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ProcessingBE extends BlockEntity {

    private ListTag processedOres;

    public ProcessingBE(BlockPos pos, BlockState state) {
        super(UnmovableMines.PROCESSOR_BE.get(), pos, state);
    }


    @Override
    protected void saveAdditional(CompoundTag tag) {
        if (processedOres == null) {
            processedOres = new ListTag();
        }
        tag.put("processedOres", processedOres);
        super.saveAdditional(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        processedOres = (ListTag) tag.get("processedOres");
    }

    public ListTag getProcessedOres() {
        return processedOres;

    }

    public void setProcessedOres(ListTag processedOres) {
        this.processedOres = processedOres;
        setChanged();
    }

    private boolean processesOreType(String query) {
        for (int l = 0; l < getProcessedOres().size(); l++) {
            if (getProcessedOres().get(l).getAsString().equals(query)) {
                return true;
            }
        }
        return false;
    }

    public void ensureOresAreChosen(Level world) {
        if (getProcessedOres() != null && !getProcessedOres().isEmpty()) {
            return;
        }
        ListTag ores = new ListTag();
        for (var ore : UnmovableMinesUtil.pickRandom(
                UnmovableMines.getDynamicContentManager().getOreNames(),
                world.random.nextInt(1, 5),
                world.random
        )) {
            ores.add(StringTag.valueOf(ore));
        }
        this.setProcessedOres(ores);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, ProcessingBE be) {
        if (!(world.getDayTime() % 200L == 0L)) {
            return;
        }

        be.ensureOresAreChosen(world);

        if (!(world.getBlockEntity(pos.above()) instanceof Container in)) return;
        if (!(world.getBlockEntity(pos.below()) instanceof Container out)) return;

        ItemStack targetStack = null;
        ItemWithChemical targetChemicalItem = null;

        for (var i = 0; i < in.getContainerSize(); i++) {
            if (!(in.getItem(i).getItem() instanceof ItemWithChemical chemical)) continue;
            if (!be.processesOreType(chemical.getElementName())) continue;
            targetStack = in.getItem(i);
            targetChemicalItem = chemical;
            break;
        }

        if (targetStack == null) {
            return;
        }

        ItemStack outputStack = targetChemicalItem.getNextDrop().item().copy();

        for (int i = 0; i < out.getContainerSize(); i++) {
            var outSlot = out.getItem(i);
            if (outSlot.isStackable() && outSlot.getCount() < outSlot.getMaxStackSize() && ItemStack.isSameItemSameTags(outSlot, outputStack)) {
                targetStack.shrink(1);
                outSlot.grow(1);
                break;
            } else if (outSlot.isEmpty()) {
                targetStack.shrink(1);
                out.setItem(i, outputStack);
                break;
            }
        }
    }
}