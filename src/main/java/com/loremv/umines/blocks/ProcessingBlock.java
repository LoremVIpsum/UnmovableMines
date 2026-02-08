package com.loremv.umines.blocks;

import com.loremv.umines.OreUtils;
import com.loremv.umines.UnmovableMines;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ProcessingBlock extends BlockWithEntity {
    public ProcessingBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ProcessingBE(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, UnmovableMines.PROCESSING_BLOCK_ENTITY,ProcessingBE::tick);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(2,0,2,14,16,14);
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient && hand==Hand.MAIN_HAND)
        {
            ProcessingBE be = (ProcessingBE) world.getBlockEntity(pos);
            player.sendMessage(Text.of("This station can process:"));
            for (int i = 0; i < be.getProcessedOres().size(); i++) {
                //UnmovableMines.LOGGER.info(be.getProcessedOres().getString(i));
                Style style = Style.EMPTY.withHoverEvent(
                        new HoverEvent(HoverEvent.Action.SHOW_ITEM,
                                new HoverEvent.ItemStackContent(OreUtils.REGISTRY.get(be.getProcessedOres().getString(i).toLowerCase()).getDefaultStack())));

                player.sendMessage(Text.empty().setStyle(style).append(be.getProcessedOres().getString(i)));
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }
}
