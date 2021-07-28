package org.edakara.buildersmod.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.edakara.buildersmod.common.state.DoorBlockStateProperties;
import org.edakara.buildersmod.common.state.DualDoorPart2x2;
import org.edakara.buildersmod.core.init.BlockInit;
import static org.edakara.buildersmod.common.StaticFunctions.*;

import javax.annotation.Nullable;
import java.util.HashMap;


public class DualDoor2x2 extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final EnumProperty<DualDoorPart2x2> DUALPART2X2 = DoorBlockStateProperties.DUAL_DOOR_PART_2X2;
    public static final BooleanProperty OPENED = BlockStateProperties.OPEN;
    private static final VoxelShape NORTH_CLOSE = Block.box(0,0,15,16,16,16);
    private static final VoxelShape SOUTH_CLOSE = Block.box(0,0,0,16,16,1);
    private static final VoxelShape EAST_CLOSE = Block.box(0,0,0,1,16,16);
    private static final VoxelShape WEST_CLOSE = Block.box(15,0,0,16,16,16);
    private static final VoxelShape NORTH_OPEN = Block.box(15,0,0,16,16,16);
    private static final VoxelShape SOUTH_OPEN = Block.box(0,0,0,1,16,16);
    private static final VoxelShape EAST_OPEN = Block.box(0,0,15,16,16,16);
    private static final VoxelShape WEST_OPEN = Block.box(0,0,0,16,16,1);


    public DualDoor2x2(){
        this(Properties.of(Material.WOOD, MaterialColor.WOOD).strength(5.0f)
                .sound(SoundType.WOOD));
    }

    public DualDoor2x2(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(DUALPART2X2, DualDoorPart2x2.CO_11));
    }

    public void neighborChanged(BlockState blockState, Level world, BlockPos blockPos, Block block, BlockPos blockPos1, boolean b) {
    }


    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        if(!blockState.getValue(OPENED)) {
            level.playSound((Player)null, blockPos, SoundEvents.WOODEN_DOOR_OPEN, SoundSource.BLOCKS, 2.0F, 1.0F);
        }else {
            level.playSound((Player)null, blockPos, SoundEvents.WOODEN_DOOR_CLOSE, SoundSource.BLOCKS, 2.0F, 1.0F);
        }
        HashMap<String, BlockPos> positions = getDualDoor2X2Positions(blockPos,blockState);

        level.setBlock(positions.get("co_11"), BlockInit.DUAL_DOOR_2X2.get().defaultBlockState().setValue(DUALPART2X2, DualDoorPart2x2.CO_11).setValue(FACING,blockState.getValue(FACING)).setValue(OPENED,!blockState.getValue(OPENED)), 3);
        level.setBlock(positions.get("co_12"), BlockInit.DUAL_DOOR_2X2_PARTS.get().defaultBlockState().setValue(DUALPART2X2, DualDoorPart2x2.CO_12).setValue(FACING,blockState.getValue(FACING)).setValue(OPENED,!blockState.getValue(OPENED)), 3);
        level.setBlock(positions.get("co_21"), BlockInit.DUAL_DOOR_2X2_PARTS.get().defaultBlockState().setValue(DUALPART2X2, DualDoorPart2x2.CO_21).setValue(FACING,blockState.getValue(FACING)).setValue(OPENED,!blockState.getValue(OPENED)), 3);
        level.setBlock(positions.get("co_22"), BlockInit.DUAL_DOOR_2X2_PARTS.get().defaultBlockState().setValue(DUALPART2X2, DualDoorPart2x2.CO_22).setValue(FACING,blockState.getValue(FACING)).setValue(OPENED,!blockState.getValue(OPENED)), 3);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }


    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter iBlockReader, BlockPos blockPos, CollisionContext iSelectionContext) {
        Direction direction = blockState.getValue(FACING);
        if(!blockState.getValue(OPENED)){
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
        }
        else{
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
    }

    @Override
    public RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext blockItemUseContext) {
        Direction direction = blockItemUseContext.getClickedFace();
        BlockPos blockpos = blockItemUseContext.getClickedPos();
        Direction.Axis direction$axis = direction.getAxis();
        BlockState blockstate;
        BlockPos checkPos;
        if (direction$axis == Direction.Axis.Y) {
            blockstate = this.defaultBlockState().setValue(FACING, blockItemUseContext.getHorizontalDirection()).setValue(DUALPART2X2, DualDoorPart2x2.CO_11).setValue(OPENED,false);
            checkPos=blockpos;
        } else {
            blockstate = this.defaultBlockState().setValue(FACING, direction.getOpposite()).setValue(DUALPART2X2, DualDoorPart2x2.CO_11).setValue(OPENED,false);
            checkPos=blockItemUseContext.getClickedPos();
        }
        if(blockstate.canSurvive(blockItemUseContext.getLevel(),checkPos)){
            return blockstate;
        }
        return null;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader iWorldReader, BlockPos blockPos) {
        HashMap<String,BlockPos> positions=getDualDoor2X2Positions(blockPos,blockState);
        return FaceAttachedHorizontalDirectionalBlock.canAttach(iWorldReader, blockPos, Direction.DOWN)&&FaceAttachedHorizontalDirectionalBlock.canAttach(iWorldReader, positions.get("co_21"), Direction.DOWN)&&iWorldReader.isEmptyBlock(positions.get("co_12"))&&iWorldReader.isEmptyBlock(positions.get("co_22"));
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        HashMap<String,BlockPos> positions=getDualDoor2X2Positions(pos,state);
        worldIn.setBlock(positions.get("co_12"), BlockInit.DUAL_DOOR_2X2_PARTS.get().defaultBlockState().setValue(DUALPART2X2, DualDoorPart2x2.CO_12).setValue(FACING,state.getValue(FACING)).setValue(OPENED,false), 3);
        worldIn.setBlock(positions.get("co_21"), BlockInit.DUAL_DOOR_2X2_PARTS.get().defaultBlockState().setValue(DUALPART2X2, DualDoorPart2x2.CO_21).setValue(FACING,state.getValue(FACING)).setValue(OPENED,false), 3);
        worldIn.setBlock(positions.get("co_22"), BlockInit.DUAL_DOOR_2X2_PARTS.get().defaultBlockState().setValue(DUALPART2X2, DualDoorPart2x2.CO_22).setValue(FACING,state.getValue(FACING)).setValue(OPENED,false), 3);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(worldIn, pos, state, player);
        HashMap<String,BlockPos> positions=getDualDoor2X2Positions(pos,state);
        worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 35);
        worldIn.setBlock(positions.get("co_12"), Blocks.AIR.defaultBlockState(), 35);
        worldIn.setBlock(positions.get("co_21"), Blocks.AIR.defaultBlockState(), 35);
        worldIn.setBlock(positions.get("co_22"), Blocks.AIR.defaultBlockState(), 35);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        super.createBlockStateDefinition(p_206840_1_);
        p_206840_1_.add(FACING, DUALPART2X2,OPENED);
    }

    @Override
    public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
        return false;
    }

}
