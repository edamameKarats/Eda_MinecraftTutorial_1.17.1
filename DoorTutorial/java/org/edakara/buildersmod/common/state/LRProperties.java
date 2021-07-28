package org.edakara.buildersmod.common.state;

import net.minecraft.util.StringRepresentable;

public enum LRProperties implements StringRepresentable {
    LEFT,
    RIGHT;

    public String toString() { return this.getSerializedName(); }

    public String getSerializedName() { return this.name().toLowerCase(); }

}
