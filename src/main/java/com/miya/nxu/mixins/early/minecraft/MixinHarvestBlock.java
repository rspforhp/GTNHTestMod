package com.miya.nxu.mixins.early.minecraft;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.miya.nxu.Config;
import com.miya.nxu.Items.ItemDestructionPickaxe;
import com.miya.nxu.Items.ItemErosionShovel;
import com.miya.nxu.Items.ItemHungerAxe;
import com.miya.nxu.Items.ItemPrecisionShears;

@Mixin(value = Block.class, remap = false)
public class MixinHarvestBlock {

    @Shadow
    protected ItemStack createStackedBlock(int meta) {
        return null;
    }

    @Shadow
    protected void dropBlockAsItem(World worldIn, int x, int y, int z, ItemStack itemIn) {}

    @Inject(method = "harvestBlock", at = @At("HEAD"), cancellable = true)
    public void $voidBlock(World worldIn, EntityPlayer player, int x, int y, int z, int meta, CallbackInfo ci) {
        Block b = (Block) (Object) this;
        ItemStack heldItem = player.getHeldItem();
        if (heldItem == null) return;
        if ((Config.HungerAxe.VoidMinedBlock && heldItem.getItem() instanceof ItemHungerAxe)
            || (Config.ErosionShovel.VoidMinedBlock && heldItem.getItem() instanceof ItemErosionShovel)
            || (Config.DestructionPickaxe.VoidMinedBlock && heldItem.getItem() instanceof ItemDestructionPickaxe)) {
            player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(b)], 1);
            player.addExhaustion(0.025F);
            ci.cancel();
        }

        if (heldItem.getItem() instanceof ItemPrecisionShears) {
            // Prob can be made clearer, without replacing, but idk mixins that well
            player.addStat(StatList.mineBlockStatArray[Block.getIdFromBlock(b)], 1);
            player.addExhaustion(0.025F);
            if (b.canSilkHarvest(worldIn, player, x, y, z, meta) && EnchantmentHelper.getSilkTouchModifier(player)) {
                ArrayList<ItemStack> items = new ArrayList<ItemStack>();
                ItemStack itemstack = this.createStackedBlock(meta);
                if (itemstack != null) {
                    items.add(itemstack);
                }
                ForgeEventFactory.fireBlockHarvesting(items, worldIn, b, x, y, z, meta, 0, 1.0f, true, player);
                for (ItemStack is : items) {
                    if (!player.inventory.addItemStackToInventory(is))
                        this.dropBlockAsItem(worldIn, (int) player.posX, (int) player.posY, (int) player.posZ, is);
                }
            } else {
                // b.harvesters.set(player);
                int i1 = EnchantmentHelper.getFortuneModifier(player);
                ArrayList<ItemStack> items = b.getDrops(worldIn, x, y, z, meta, i1);
                for (ItemStack item : items) {
                    if (!player.inventory.addItemStackToInventory(item))
                        this.dropBlockAsItem(worldIn, (int) player.posX, (int) player.posY, (int) player.posZ, item);
                }

                // b.harvesters.set(null);
            }
            /*
             * TODO: how to tell client to make particles here?
             * int color=6821499;
             * double d0 = (double) (color >> 16 & 255) / 255.0D;
             * double d1 = (double) (color >> 8 & 255) / 255.0D;
             * double d2 = (double) (color >> 0 & 255) / 255.0D;
             * for (int i = 0; i < 5; i++) {
             * worldIn.spawnParticle("mobSpell", x,y,z,d0,d1,d2 );
             * }
             */
            ci.cancel();
        }

    }
}
