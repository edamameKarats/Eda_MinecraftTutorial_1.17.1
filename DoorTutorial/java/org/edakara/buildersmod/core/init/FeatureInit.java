package org.edakara.buildersmod.core.init;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.event.world.BiomeLoadingEvent;

public class FeatureInit {
    public static void addOres(final BiomeLoadingEvent event) {
        addOre(event, OreConfiguration.Predicates.NATURAL_STONE,
                BlockInit.MAME_ORE.get().defaultBlockState(), 4, 0, 60, 50);
    }

    public static void addOre(final BiomeLoadingEvent event, RuleTest rule, BlockState state, int veinSize,
                              int minHeight, int maxHeight, int amount) {
        event.getGeneration().addFeature(GenerationStep.Decoration.UNDERGROUND_ORES,
                Feature.ORE.configured(new OreConfiguration(rule, state, veinSize))
                        .rangeUniform(VerticalAnchor.absolute(minHeight), VerticalAnchor.absolute(maxHeight))
                        .squared().count(amount));
    }
}
