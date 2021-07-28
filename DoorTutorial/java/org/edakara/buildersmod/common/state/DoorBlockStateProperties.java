package org.edakara.buildersmod.common.state;

import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;

public class DoorBlockStateProperties extends BlockStateProperties {
    public static final EnumProperty<DoorPart1x2> DOOR_PART_1X2 = EnumProperty.create("door_part_1x2", DoorPart1x2.class);
    public static final EnumProperty<DualDoorPart2x2> DUAL_DOOR_PART_2X2 = EnumProperty.create("dual_door_part_2x2", DualDoorPart2x2.class);
}