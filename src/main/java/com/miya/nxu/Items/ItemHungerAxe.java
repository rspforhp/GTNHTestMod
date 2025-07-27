package com.miya.nxu.Items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.FoodStats;
import net.minecraft.world.World;

import java.util.Random;

public class ItemHungerAxe  extends ItemAxe {
    public ItemHungerAxe() {
        super(ToolMaterial.EMERALD);
        setTextureName("nxu:hunger_axe");
        setUnlocalizedName("hunger_axe");
        setMaxDamage(0);
        //For testing effect id 17 is hunger

        //one full bar per 3 seconds
        //so half a bar per 1.5 seconds
        //saturation is at half a rate
        //so full bar per 6 seconds
        //or half a bar per 3 seconds

        //on hit heals attacked for 2 hearts, and removes 1.5 hearts and hunger from player
        //cant use if has not enough hp

        //cures villagers without penalty on hit

        //on hit for undead, uses no hp, attacks for 6(regular) + 1.5hears*4

        //also spawn red particles for most of those interactions
    }

    public void spawnParticles(Entity e)
    {
        //TODO: don't think it works, and im not sure how particles are meant to work in 1.7.10
        for (int i = 0; i < 5; i++) {
            Random r=itemRand;
            double rangeMin=-0.5d;
            double rangeMax=0.5d;
            double randomValue1 = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            double randomValue2 = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            double randomValue3 = rangeMin + (rangeMax - rangeMin) * r.nextDouble();
            e.worldObj.spawnParticle("effect", e.posX,e.posY,e.posZ,randomValue1,randomValue2,randomValue3 );
        }
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer attacker, Entity e) {
        //TODO: IDK WHY IT STILL ATTACKS EVEN THO I RETURN FALSE
        super.onLeftClickEntity(stack, attacker, e);
        if(!(e instanceof EntityLivingBase  target))
            return true;
        if(e.worldObj.isRemote)return false;
        if(target instanceof EntityZombie z && z.isVillager())
        {
            //Cure
            spawnParticles(z);
            z.convertToVillager();
            return false;
        }
        if(!target.isEntityUndead()) {
            //TODO: config value for taking damage as suggested
            if(attacker.getHealth()>=4)
            {
                //Idk if its right to use heal to take damage
                attacker.setHealth(attacker.getHealth()-3);
                target.setHealth(target.getHealth()+4);
                attacker.getFoodStats().addStats(-3,0);
            }

        }
        else {
            attacker.setHealth(attacker.getHealth()-3*4);
            return true;
        }


        return false;
    }



    @Override
    public void onUpdate(ItemStack s, World w, Entity e, int slot, boolean selected) {
        super.onUpdate(s, w, e, slot, selected);
        if(e instanceof EntityPlayer p&& selected)
        {
            //I think this is close enough
            //TODO: introduce a config value for this
            if(w.getTotalWorldTime()%(2*20)==0)
            {
                FoodStats fs=p.getFoodStats();
                fs.addStats(1, 0.25f);
            }
        }
    }

    //Unbreakable
    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }
    //
}
