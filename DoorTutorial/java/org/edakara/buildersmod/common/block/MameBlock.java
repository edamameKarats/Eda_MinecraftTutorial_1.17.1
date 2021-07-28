package org.edakara.buildersmod.common.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public class MameBlock extends Block {
    public MameBlock() {
        super(BlockBehaviour.Properties
                .of(Material.METAL, MaterialColor.METAL)
                .strength(5f, 6f)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(2)
                .sound(SoundType.METAL));
    }
}
