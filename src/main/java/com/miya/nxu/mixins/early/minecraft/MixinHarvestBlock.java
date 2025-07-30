package com.miya.nxu.mixins.early.minecraft;


import com.miya.nxu.Config;
import com.miya.nxu.Items.ItemHungerAxe;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Block.class, remap = false)
public class MixinHarvestBlock{

    @Inject(method = "harvestBlock", at = @At("HEAD"), cancellable = true)
    public void $voidBlock(World worldIn, EntityPlayer player, int x, int y, int z, int meta, CallbackInfo ci) {
        ItemStack heldItem=player.getHeldItem();
        if(Config.HungerAxe.VoidMinedBlock&&heldItem!=null&&heldItem.getItem() instanceof ItemHungerAxe)
        {
            player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock((Block)(Object)this)], 1);
            player.addExhaustion(0.025F);
            ci.cancel();
        }
    }
}
