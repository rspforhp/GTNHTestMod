package com.miya.nxu.mixins;

import com.gtnewhorizon.gtnhmixins.builders.IMixins;
import com.gtnewhorizon.gtnhmixins.builders.MixinBuilder;

import javax.annotation.Nonnull;

public enum Mixins implements IMixins {

    // spotless:off

    VOID_MINED_BLOCK(new MixinBuilder("Void mined block for some tools")
        .setPhase(Phase.EARLY)
        .addCommonMixins("minecraft.MixinHarvestBlock")
    )

    ;
    // spotless:on


    private final MixinBuilder builder;

    Mixins(MixinBuilder builder) {
        this.builder = builder;
    }

    @Nonnull
    @Override
    public MixinBuilder getBuilder() {
        return this.builder;
    }
}
