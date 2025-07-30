package com.miya.nxu.Items;

import com.miya.nxu.Config;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;

public class ItemPrecisionShears extends ItemShears {

    public ItemPrecisionShears() {
        super();
        setTextureName("nxu:precision_shears");
        setUnlocalizedName("precision_shears");
        setMaxDamage(0);
    }


    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_) {
        return super.onBlockDestroyed(stack, worldIn, blockIn, p_150894_4_, p_150894_5_, p_150894_6_, p_150894_7_);
    }

    //Should be able to mine what stone tools can too
    @Override
    public boolean canHarvestBlock(Block b, ItemStack itemStack) {
        return super.canHarvestBlock(b, itemStack) || Items.stone_pickaxe.canHarvestBlock(b,null);
    }

    // Unbreakable
    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_) {
        return false;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }
    //
}
