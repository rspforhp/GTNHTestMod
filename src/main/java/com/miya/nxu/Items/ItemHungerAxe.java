package com.miya.nxu.Items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;

import com.miya.nxu.Config;

public class ItemHungerAxe extends ItemAxe {

    public ItemHungerAxe() {
        super(ToolMaterial.EMERALD);
        setTextureName("nxu:hunger_axe");
        setUnlocalizedName("hunger_axe");
        setMaxDamage(0);
        this.damageVsEntity = Config.HungerAxe.DamageAgainstUndead;
    }

    public static void spawnParticles(Entity e) {
        if (!Config.HungerAxe.SpawnParticlesOnInteraction) return;
        int ci = Potion.potionTypes[Potion.heal.getId()].getLiquidColor();
        double d0 = (double) (ci >> 16 & 255) / 255.0D;
        double d1 = (double) (ci >> 8 & 255) / 255.0D;
        double d2 = (double) (ci >> 0 & 255) / 255.0D;
        for (int i = 0; i < 5; i++) {

            e.worldObj.spawnParticle(
                "mobSpell",
                e.posX + (e.rand.nextDouble() - 0.5D) * (double) e.width,
                e.posY + e.rand.nextDouble() * (double) e.height - (double) e.yOffset,
                e.posZ + (e.rand.nextDouble() - 0.5D) * (double) e.width,
                d0,
                d1,
                d2);
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer attacker, Entity e) {
        if (!(e instanceof EntityLivingBase target)) return false;
        if (target instanceof EntityZombie z && z.isVillager()) {
            attacker.addExhaustion(3 * 4);
            spawnParticles(target);
            if (!attacker.worldObj.isRemote) z.convertToVillager();
            return true;
        }
        if (!target.isEntityUndead()) {

            float amountToHeal = Math
                .min(Config.HungerAxe.MaxHealthTransfer, target.getMaxHealth() - target.getHealth());
            if (amountToHeal == 0) if (Config.HungerAxe.UseHungerWithoutInteraction) attacker.addExhaustion(3 * 4);
            else {
                if (Config.HungerAxe.StealHpFromAttacker) if (attacker.getHealth() >= amountToHeal + 1)
                    attacker.setHealth(attacker.getHealth() - amountToHeal);
                else return true;
                target.setHealth(target.getHealth() + (amountToHeal + 1));
                attacker.addExhaustion(amountToHeal * 4);
            }
            spawnParticles(target);
            return true;
        }
        attacker.addExhaustion(3 * 4);
        spawnParticles(target);
        return false;
    }

    @Override
    public void onUpdate(ItemStack s, World w, Entity e, int slot, boolean selected) {
        super.onUpdate(s, w, e, slot, selected);
        if (e instanceof EntityPlayer p && selected) {
            if (w.getTotalWorldTime() % (2 * 20) == 0) {
                FoodStats fs = p.getFoodStats();
                fs.addStats(Config.HungerAxe.FoodGain, Config.HungerAxe.SaturationGain);
            }
        }
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
