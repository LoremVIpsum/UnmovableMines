package com.loremv.umines.blocks;

import com.loremv.umines.UnmovableMines;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
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

public class MiningBlock extends BaseEntityBlock {
    public MiningBlock(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResult use(
            BlockState blockState,
            Level world,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult blockHitResult
    ) {
        if(hand==InteractionHand.MAIN_HAND && !world.isClientSide)
        {
            var miningBE = (MiningBE) world.getBlockEntity(pos);
            if (miningBE == null) { return InteractionResult.FAIL; }
            if(miningBE.getMinedOre().isEmpty())
            {
                RandomSource random = RandomSource.create(pos.asLong());
                miningBE.chooseMinedOre(random);
            }

            Style style = Style.EMPTY.withHoverEvent(
                    new HoverEvent(HoverEvent.Action.SHOW_ITEM,
                            new HoverEvent.ItemStackInfo(miningBE.getMinedOre().get().getDefaultInstance())));

            player.sendSystemMessage(Component.empty().setStyle(style).append("This mine is for "+miningBE.getMinedOre().get().getElementName()));

        }
        return super.use(blockState,world,pos,player,hand, blockHitResult);
    }

    @Override
    public RenderShape getRenderShape(BlockState p_49232_) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, UnmovableMines.MINING_BE.get(),MiningBE::tick);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return MiningBlock.box(2, 0, 2, 14, 16, 14);
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return MiningBlock.box(2, 0, 2, 14, 16, 14);
    }
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return MiningBlock.box(2, 0, 2, 14, 16, 14);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new MiningBE(blockPos,blockState);
    }
}
