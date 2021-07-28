package org.edakara.buildersmod.common.state;

import net.minecraft.util.StringRepresentable;

public enum DoorPart1x2 implements StringRepresentable {
    CO_11,
    CO_12;

    public String toString() { return this.getSerializedName(); }

    public String getSerializedName() { return this.name().toLowerCase(); }

}
