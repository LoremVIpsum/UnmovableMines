package com.loremv.umines.blocks;


import com.loremv.umines.OreUtils;
import com.loremv.umines.UnmovableMines;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class MiningBlock extends BlockWithEntity {
    public MiningBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {

        if(hand==Hand.MAIN_HAND && !world.isClient)
        {
            MiningBE miningBE = (MiningBE) world.getBlockEntity(pos);
            if(miningBE.getMinedOre().equals("empty"))
            {
                Random random = Random.create(pos.asLong());
                String s = OreUtils.keys.get(random.nextInt(OreUtils.keys.size())).toLowerCase();
                miningBE.setMinedOre(s);
            }

            Style style = Style.EMPTY.withHoverEvent(
                    new HoverEvent(HoverEvent.Action.SHOW_ITEM,
                            new HoverEvent.ItemStackContent(OreUtils.REGISTRY.get(miningBE.getMinedOre()).getDefaultStack())));

            player.sendMessage(Text.empty().setStyle(style).append("This mine is for "+miningBE.getMinedOre()));


        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new MiningBE(pos,state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, UnmovableMines.INFORMATION_BLOCK_ENTITY, MiningBE::tick);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(2,0,2,14,16,14);
    }
}
