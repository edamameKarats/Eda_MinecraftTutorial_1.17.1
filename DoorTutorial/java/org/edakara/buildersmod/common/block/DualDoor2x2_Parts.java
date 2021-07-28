package org.edakara.buildersmod.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.edakara.buildersmod.common.state.DoorBlockStateProperties;
import org.edakara.buildersmod.common.state.DualDoorPart2x2;

import java.util.HashMap;

import static org.edakara.buildersmod.common.StaticFunctions.*;

public class DualDoor2x2_Parts extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final EnumProperty<DualDoorPart2x2> DUALPART2X2 = DoorBlockStateProperties.DUAL_DOOR_PART_2X2;
    public static final BooleanProperty OPENED = BlockStateProperties.OPEN;
    private static final VoxelShape NORTH_CLOSE = Block.box(0, 0, 15, 16, 16, 16);
    private static final VoxelShape SOUTH_CLOSE = Block.box(0, 0, 0, 16, 16, 1);
    private static final VoxelShape EAST_CLOSE = Block.box(0, 0, 0, 1, 16, 16);
    private static final VoxelShape WEST_CLOSE = Block.box(15, 0, 0, 16, 16, 16);
    private static final VoxelShape NORTH_OPEN = Block.box(15, 0, 0, 16, 16, 16);
    private static final VoxelShape SOUTH_OPEN = Block.box(0, 0, 0, 1, 16, 16);
    private static final VoxelShape EAST_OPEN = Block.box(0, 0, 15, 16, 16, 16);
    private static final VoxelShape WEST_OPEN = Block.box(0, 0, 0, 16, 16, 1);

    public DualDoor2x2_Parts() {
        this(Properties.of(Material.WOOD, MaterialColor.WOOD).requiresCorrectToolForDrops().strength(5.0F));
    }

    public DualDoor2x2_Parts(Properties props) {
        super(props);
        this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(DUALPART2X2, DualDoorPart2x2.CO_12);
    }

    public void neighborChanged(BlockState p_220069_1_, Level p_220069_2_, BlockPos p_220069_3_, Block p_220069_4_, BlockPos p_220069_5_, boolean p_220069_6_) {
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        HashMap<String, BlockPos> positions = getDualDoor2X2Positions(blockPos, blockState);
        BlockPos rootPos = positions.get("co_11");
        BlockState rootState = level.getBlockState(rootPos);
        return level.getBlockState(rootPos).getBlock().use(rootState, level, rootPos, player, hand, blockHitResult);
    }

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter iBlockReader, BlockPos blockPos, CollisionContext iSelectionContext) {
        Direction direction = blockState.getValue(FACING);
        DualDoorPart2x2 parts = blockState.getValue(DUALPART2X2);
        switch (parts) {
            case CO_12:
                if (!blockState.getValue(OPENED)) {
                    switch (direction) {
                        case NORTH:
                            return NORTH_CLOSE;
                        case SOUTH:
                            return SOUTH_CLOSE;
                        case EAST:
                            return EAST_CLOSE;
                        default:
                            return WEST_CLOSE;
                    }
                } else {
                    switch (direction) {
                        case NORTH:
                            return NORTH_OPEN;
                        case SOUTH:
                            return SOUTH_OPEN;
                        case EAST:
                            return EAST_OPEN;
                        default:
                            return WEST_OPEN;
                    }
                }
            default:
                if (!blockState.getValue(OPENED)) {
                    switch (direction) {
                        case NORTH:
                            return NORTH_CLOSE;
                        case SOUTH:
                            return SOUTH_CLOSE;
                        case EAST:
                            return EAST_CLOSE;
                        default:
                            return WEST_CLOSE;
                    }
                } else {
                    switch (direction) {
                        case NORTH:
                            return EAST_CLOSE;
                        case SOUTH:
                            return WEST_CLOSE;
                        case EAST:
                            return SOUTH_CLOSE;
                        default:
                            return NORTH_CLOSE;
                    }
                }
        }
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        HashMap<String, BlockPos> positions = getDualDoor2X2Positions(pos, state);
        BlockPos rootPos = positions.get("co_11");
        BlockState rootState = worldIn.getBlockState(rootPos);
        worldIn.getBlockState(rootPos).getBlock().playerWillDestroy(worldIn, rootPos, rootState, player);
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        super.createBlockStateDefinition(p_206840_1_);
        p_206840_1_.add(FACING, DUALPART2X2, OPENED);
    }
}