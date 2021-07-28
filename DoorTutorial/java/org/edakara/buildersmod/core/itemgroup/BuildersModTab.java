package org.edakara.buildersmod.core.itemgroup;


import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.edakara.buildersmod.core.init.ItemInit;

public class BuildersModTab extends CreativeModeTab {
    public static final BuildersModTab BUILDERS_MOD = new BuildersModTab(CreativeModeTab.TABS.length,
            "builders_mod");

    public BuildersModTab(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ItemInit.MAME_INGOT.get());
    }

}
