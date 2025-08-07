package com.miya.nxu.Items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemErosionShovel extends ItemSpade {

    public ItemErosionShovel() {
        super(ToolMaterial.EMERALD);
        setTextureName("nxu:erosion_shovel");
        setUnlocalizedName("erosion_shovel");
        setMaxDamage(0);
    }

    // getDigSpeed
    // should mine slower than a fist for uneffective materials
    @Override
    public float func_150893_a(ItemStack itemstack, Block block) {
        float r = super.func_150893_a(itemstack, block);
        if (r != efficiencyOnProperMaterial) r = 0.1f;
        return r;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, int x, int y, int z,
        EntityLivingBase p) {
        int curY = y;
        if (p instanceof EntityPlayer player) while (true) {
            curY++;
            Block bAbove = worldIn.getBlock(x, curY, z);
            if (bAbove instanceof BlockFalling f) {
                // Are there falling blocks who dont inherit this?
                bAbove.removedByPlayer(worldIn, player, x, curY, z, true);
                bAbove.harvestBlock(worldIn, player, x, curY, z, 0);
            } else break;
        }

        return super.onBlockDestroyed(stack, worldIn, blockIn, x, y, z, p);
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
