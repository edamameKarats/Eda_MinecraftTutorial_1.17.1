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
import org.edakara.buildersmod.common.state.DoorPart1x2;

public class Door1x2_Parts extends Block{
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

    public Door1x2_Parts(){
        this(Properties.of(Material.WOOD, MaterialColor.WOOD).requiresCorrectToolForDrops().strength(5.0F));
    }

    public Door1x2_Parts(Properties props) {
        super(props);
        this.defaultBlockState().setValue(FACING, Direction.NORTH).setValue(PART1X2, DoorPart1x2.CO_12);
    }

    public void neighborChanged(BlockState p_220069_1_, Level p_220069_2_, BlockPos p_220069_3_, Block p_220069_4_, BlockPos p_220069_5_, boolean p_220069_6_) {
    }

    @Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult blockHitResult) {
        BlockPos rootPos=blockPos.below();
        BlockState rootState=level.getBlockState(rootPos);
        return level.getBlockState(rootPos).getBlock().use(rootState,level,rootPos,player,hand,blockHitResult);
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
    public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
        BlockPos rootPos=pos.below();
        BlockState rootState=worldIn.getBlockState(rootPos);
        worldIn.getBlockState(rootPos).getBlock().playerWillDestroy(worldIn,rootPos,rootState,player);
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
        p_206840_1_.add(FACING,PART1X2,OPENED);
    }


}
