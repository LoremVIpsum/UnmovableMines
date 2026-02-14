package com.loremv.umines.blocks;

import com.loremv.umines.UnmovableMines;
import com.loremv.umines.items.ItemWithChemical;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.Style;
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
import org.slf4j.Logger;

public class ProcessingBlock extends BaseEntityBlock {
    private static final Logger LOGGER = LogUtils.getLogger();
    public ProcessingBlock(Properties settings) {
        super(settings);
    }


    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, UnmovableMines.PROCESSOR_BE.get(), ProcessingBE::tick);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return ProcessingBlock.box(2,0,2,14,16,14);
    }
    @Override
    public VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return ProcessingBlock.box(2, 0, 2, 14, 16, 14);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return ProcessingBlock.box(2, 0, 2, 14, 16, 14);
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }


    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult p_60508_) {
        if(!world.isClientSide && hand==InteractionHand.MAIN_HAND)
        {
            ProcessingBE be = (ProcessingBE) world.getBlockEntity(pos);
            if (be == null) { return InteractionResult.FAIL; }
            be.ensureOresAreChosen(world);
            player.sendSystemMessage(Component.empty().append("This station can process:"));
            for (int i = 0; i < be.getProcessedOres().size(); i++) {
                var item = ItemWithChemical.byElementName(be.getProcessedOres().get(i).getAsString());
                if (item.isEmpty()) {
                    LOGGER.warn("No item found for processed ore {}", be.getProcessedOres().get(i));
                } else {
                    Style style = Style.EMPTY.withHoverEvent(
                            new HoverEvent(HoverEvent.Action.SHOW_ITEM,
                                    new HoverEvent.ItemStackInfo(item.get().getDefaultInstance())));
                    player.sendSystemMessage(Component.empty()
                                                      .setStyle(style)
                                                      .append(be.getProcessedOres().getString(i)));
                }



            }
        }
        return super.use(state,world,pos,player,hand, p_60508_);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ProcessingBE(blockPos, blockState);
    }
}
