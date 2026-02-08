package com.loremv.umines.blocks;

import com.loremv.umines.OreUtils;
import com.loremv.umines.UnmovableMines;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class ProcessingBlock extends BaseEntityBlock {
    public ProcessingBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return null;
    }


    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, UnmovableMines.PROCESSOR_BE.get(), ProcessingBE::tick);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return ProcessingBlock.box(2,0,2,14,16,14);
    }
    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return ProcessingBlock.box(2, 0, 2, 14, 16, 14);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return ProcessingBlock.box(2, 0, 2, 14, 16, 14);
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(!world.isClientSide && hand==InteractionHand.MAIN_HAND)
        {
            ProcessingBE be = (ProcessingBE) world.getBlockEntity(pos);
            player.sendSystemMessage(Component.empty().append("This station can process:"));
            for (int i = 0; i < be.getProcessedOres().size(); i++) {
                //UnmovableMines.LOGGER.info(be.getProcessedOres().getString(i));
                Style style = Style.EMPTY.withHoverEvent(
                        new HoverEvent(HoverEvent.Action.SHOW_ITEM,
                                new HoverEvent.ItemStackInfo(OreUtils.REGISTRY.get(be.getProcessedOres().getString(i).toLowerCase()).getDefaultInstance())));

                player.sendSystemMessage(Component.empty().setStyle(style).append(be.getProcessedOres().getString(i)));
            }
        }
        return super.useItemOn(stack, state, world, pos, player, hand, hitResult);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ProcessingBE(blockPos, blockState);
    }
}
