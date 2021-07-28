package org.edakara.buildersmod.core.init;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.fmllegacy.RegistryObject;
import org.edakara.buildersmod.BuildersMod;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.edakara.buildersmod.common.block.*;

public class BlockInit {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS,
			BuildersMod.MOD_ID);

	public static final RegistryObject<Block> MAME_BLOCK = BLOCKS
			.register("mame_block",
					() -> new MameBlock());
	public static final RegistryObject<Block> MAME_ORE = BLOCKS
			.register("mame_ore",
					() -> new MameOre());
	public static final RegistryObject<Block> HOUSE_WOOD_CHAIR = BLOCKS
			.register("house_wood_chair",
					() -> new HouseWoodChair());
	public static final RegistryObject<Block> DOOR_1X2 = BLOCKS
			.register("door_1x2",
					() -> new Door1x2());
	public static final RegistryObject<Block> DOOR_1X2_PARTS = BLOCKS
			.register("door_1x2_parts",
					() -> new Door1x2_Parts());
	public static final RegistryObject<Block> DUAL_DOOR_2X2 = BLOCKS
			.register("dual_door_2x2",
					() -> new DualDoor2x2());
	public static final RegistryObject<Block> DUAL_DOOR_2X2_PARTS = BLOCKS
			.register("dual_door_2x2_parts",
					() -> new DualDoor2x2_Parts());
}
