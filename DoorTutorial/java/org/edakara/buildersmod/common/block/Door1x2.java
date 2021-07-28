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
import org.edakara.buildersmod.common.state.DoorPart1x2;
import org.edakara.buildersmod.core.init.BlockInit;

import javax.annotation.Nullable;

public class Door1x2 extends Block {
    public static final DirectionProperty FACING = BlockStateProperties.FACING;
    public static final EnumProperty<DoorPart1x2> PART1X2 = DoorBlockStateProperties.DOOR_PART_1X2;
    public static final BooleanProperty OPENED = BlockStateProperties.OPEN;
    private static final VoxelShape NORTH_CLOSE = Block.box(0,0,15,16,16,16);
    private static final VoxelShape SOUTH_CLOSE = Block.box(0,0,0,16,16,1);
    private static final VoxelShape EAST_CLOSE = Block.box(0,0,0,1,16,16);
    private static final VoxelShape WEST_CLOSE = Block.box(15,0,0,16,16,16);
    private static final VoxelShape NORTH_OPEN = Block.box(15,0,0,16,16,16);
    private static final VoxelShape SOUTH_OPEN = Block.box(0,0,0,1,16,16);
    private static final VoxelShape EAST_OPEN = Block.box(0,0,15,16,16,16);
    private static final VoxelShape WEST_OPEN = Block.box(0,0,0,16,16,1);


    public Door1x2(){
        this(Properties.of(Material.WOOD, MaterialColor.WOOD).strength(5.0f)
                .sound(SoundType.WOOD));
    }

    public Door1x2(Properties props) {
        super(props);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(PART1X2, DoorPart1x2.CO_11));
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
        level.setBlock(blockPos, BlockInit.DOOR_1X2.get().defaultBlockState().setValue(PART1X2, DoorPart1x2.CO_11).setValue(FACING,blockState.getValue(FACING)).setValue(OPENED,!blockState.getValue(OPENED)), 3);
        level.setBlock(blockPos.above(), BlockInit.DOOR_1X2_PARTS.get().defaultBlockState().setValue(PART1X2, DoorPart1x2.CO_12).setValue(FACING,blockState.getValue(FACING)).setValue(OPENED,!blockState.getValue(OPENED)), 3);
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
            blockstate = this.defaultBlockState().setValue(FACING, blockItemUseContext.getHorizontalDirection()).setValue(PART1X2, DoorPart1x2.CO_11).setValue(OPENED,false);
            checkPos=blockpos;
        } else {
            blockstate = this.defaultBlockState().setValue(FACING, direction.getOpposite()).setValue(PART1X2, DoorPart1x2.CO_11).setValue(OPENED,false);
            checkPos=blockItemUseContext.getClickedPos();
        }
        if(blockstate.canSurvive(blockItemUseContext.getLevel(),checkPos)){
            return blockstate;
        }
        return null;
    }

    @Override
    public boolean canSurvive(BlockState blockState, LevelReader iWorldReader, BlockPos blockPos) {
        return FaceAttachedHorizontalDirectionalBlock.canAttach(iWorldReader, blockPos, Direction.DOWN)&&iWorldReader.isEmptyBlock(blockPos.above());
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        worldIn.setBlock(pos.above(), BlockInit.DOOR_1X2_PARTS.get().defaultBlockState().setValue(PART1X2, DoorPart1x2.CO_12).setValue(FACING,state.getValue(FACING)).setValue(OPENED,false), 3);
    }

    @Override
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(worldIn, pos, state, player);
        worldIn.setBlock(pos, Blocks.AIR.defaultBlockState(), 35);
        worldIn.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 35);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState p_149656_1_) {
        return PushReaction.DESTROY;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_206840_1_) {
        super.createBlockStateDefinition(p_206840_1_);
        p_206840_1_.add(FACING,PART1X2,OPENED);
    }

    @Override
    public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
        return false;
    }

}
