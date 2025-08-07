package com.miya.nxu.Items;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;

public class ItemDestructionPickaxe extends ItemPickaxe {

    public ItemDestructionPickaxe() {
        super(ToolMaterial.EMERALD);
        setTextureName("nxu:destruction_pickaxe");
        setUnlocalizedName("destruction_pickaxe");
        setMaxDamage(0);
        // efficiencyOnProperMaterial*=5;
    }

    public static ArrayList<Block> StoneBlocks = new ArrayList<>();
    public static ArrayList<Block> NonStoneBlocks = new ArrayList<>();

    // getDigSpeed
    // should mine slower than a fist for uneffective materials
    // im not sure if its meant to ONLY mine stone?
    @Override
    public float func_150893_a(ItemStack itemstack, Block block) {
        float r = super.func_150893_a(itemstack, block);
        if (r != efficiencyOnProperMaterial) r = 0.1f;
        if (block.getMaterial() == Material.rock) r *= 5;
        return r;
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
