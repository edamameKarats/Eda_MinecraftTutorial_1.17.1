package org.edakara.buildersmod.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.edakara.buildersmod.common.state.DoorBlockStateProperties;
import org.edakara.buildersmod.common.state.DualDoorPart2x2;
import org.edakara.buildersmod.common.state.LRProperties;

import java.util.HashMap;

public class StaticFunctions {
    public static HashMap<String, BlockPos> getDualDoor2X2Positions(BlockPos pos, BlockState state) {
        return StaticFunctions.getDualDoor2X2Positions(pos,state, LRProperties.RIGHT);
    }

    public static HashMap<String, BlockPos> getDualDoor2X2Positions(BlockPos pos, BlockState state, LRProperties hinge){
        HashMap<String, BlockPos> positions=new HashMap<String, BlockPos>();
        BlockPos stdPos;
        switch (state.getValue(DoorBlockStateProperties.DUAL_DOOR_PART_2X2)){
            case CO_12:
                stdPos=pos.below();
                break;
            case CO_21:
                switch(hinge){
                    case RIGHT:
                        switch (state.getValue(BlockStateProperties.FACING)) {
                            case EAST:
                                stdPos=pos.relative(Direction.SOUTH);
                                break;
                            case WEST:
                                stdPos=pos.relative(Direction.NORTH);
                                break;
                            case SOUTH:
                                stdPos=pos.relative(Direction.WEST);
                                break;
                            default:
                                stdPos=pos.relative(Direction.EAST);
                        }
                        break;
                    default:
                        switch (state.getValue(BlockStateProperties.FACING)) {
                            case EAST:
                                stdPos=pos.relative(Direction.NORTH);
                                break;
                            case WEST:
                                stdPos=pos.relative(Direction.SOUTH);
                                break;
                            case SOUTH:
                                stdPos=pos.relative(Direction.EAST);
                                break;
                            default:
                                stdPos=pos.relative(Direction.WEST);
                        }
                }
                break;
            case CO_22:
                switch(hinge){
                    case RIGHT:
                        switch (state.getValue(BlockStateProperties.FACING)) {
                            case EAST:
                                stdPos=pos.relative(Direction.SOUTH).below();
                                break;
                            case WEST:
                                stdPos=pos.relative(Direction.NORTH).below();
                                break;
                            case SOUTH:
                                stdPos=pos.relative(Direction.WEST).below();
                                break;
                            default:
                                stdPos=pos.relative(Direction.EAST).below();
                        }
                        break;
                    default:
                        switch (state.getValue(BlockStateProperties.FACING)) {
                            case EAST:
                                stdPos=pos.relative(Direction.NORTH).below();
                                break;
                            case WEST:
                                stdPos=pos.relative(Direction.SOUTH).below();
                                break;
                            case SOUTH:
                                stdPos=pos.relative(Direction.EAST).below();
                                break;
                            default:
                                stdPos=pos.relative(Direction.WEST).below();
                        }
                }
                break;

            default:
                stdPos=pos;
        }
        switch (state.getValue(BlockStateProperties.FACING)){
            case EAST:
                positions.put("co_11",stdPos);
                positions.put("co_21",stdPos.relative(Direction.NORTH));
                positions.put("co_12",stdPos.above());
                positions.put("co_22",stdPos.relative(Direction.NORTH).above());
                break;
            case WEST:
                positions.put("co_11",stdPos);
                positions.put("co_21",stdPos.relative(Direction.SOUTH));
                positions.put("co_12",stdPos.above());
                positions.put("co_22",stdPos.relative(Direction.SOUTH).above());
                break;
            case SOUTH:
                positions.put("co_11",stdPos);
                positions.put("co_21",stdPos.relative(Direction.EAST));
                positions.put("co_12",stdPos.above());
                positions.put("co_22",stdPos.relative(Direction.EAST).above());
                break;
            default:
                positions.put("co_11",stdPos);
                positions.put("co_21",stdPos.relative(Direction.WEST));
                positions.put("co_12",stdPos.above());
                positions.put("co_22",stdPos.relative(Direction.WEST).above());
        }
        return positions;
    }
}
