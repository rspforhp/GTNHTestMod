package com.miya.nxu.mixins;

import javax.annotation.Nonnull;

import com.gtnewhorizon.gtnhmixins.builders.ITargetMod;
import com.gtnewhorizon.gtnhmixins.builders.TargetModBuilder;

public enum TargetMod implements ITargetMod {

    // spotless:off
    COFHCORE("cofh.asm.LoadingPlugin", "CoFHCore"),
    OPTIFINE("optifine.OptiFineForgeTweaker", "Optifine");
    // spotless:on
    private final TargetModBuilder builder;

    TargetMod(String coreModClass, String modId) {
        this.builder = new TargetModBuilder().setCoreModClass(coreModClass)
            .setModId(modId);
    }

    @Nonnull
    @Override
    public TargetModBuilder getBuilder() {
        return builder;
    }
}
