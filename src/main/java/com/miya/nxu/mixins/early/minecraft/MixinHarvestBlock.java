package com.miya.nxu.mixins.early.minecraft;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.miya.nxu.Config;
import com.miya.nxu.Items.ItemDestructionPickaxe;
import com.miya.nxu.Items.ItemErosionShovel;
import com.miya.nxu.Items.ItemHungerAxe;

@Mixin(value = Block.class, remap = false)
public class MixinHarvestBlock {

    @Inject(method = "harvestBlock", at = @At("HEAD"), cancellable = true)
    public void $voidBlock(World worldIn, EntityPlayer player, int x, int y, int z, int meta, CallbackInfo ci) {
        ItemStack heldItem = player.getHeldItem();
        if (heldItem == null) return;
        if ((Config.HungerAxe.VoidMinedBlock && heldItem.getItem() instanceof ItemHungerAxe)
            || (Config.ErosionShovel.VoidMinedBlock && heldItem.getItem() instanceof ItemErosionShovel)
            || (Config.DestructionPickaxe.VoidMinedBlock && heldItem.getItem() instanceof ItemDestructionPickaxe)) {
            player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock((Block) (Object) this)], 1);
            player.addExhaustion(0.025F);
            ci.cancel();
        }
    }
}
