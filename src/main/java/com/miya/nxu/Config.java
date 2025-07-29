package com.miya.nxu;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    //public static String greeting = "Hello World";

    //God bless java's inner classes being the way they are lmao
    public class HungerAxe
    {
        public static Boolean StealHpFromAttacker=true;
        public static Float DamageAgainstUndead=6+3*4f;
        public static Boolean SpawnParticlesOnInteraction=true;
        public static Integer MaxHealthTransfer=3;
        public static Integer FoodGain=1;
        public static Float SaturationGain=0.25f;
    }


    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        HungerAxe.StealHpFromAttacker=configuration.getBoolean("StealHpFromAttacker",Configuration.CATEGORY_GENERAL, HungerAxe.StealHpFromAttacker,"Whether to steal hp from the attacker to heal the target.");
        HungerAxe.SpawnParticlesOnInteraction=configuration.getBoolean("SpawnParticlesOnInteraction",Configuration.CATEGORY_GENERAL, HungerAxe.SpawnParticlesOnInteraction,"Whether to spawn particles on all axe interactions or not.");
        HungerAxe.DamageAgainstUndead=configuration.getFloat("DamageAgainstUndead",Configuration.CATEGORY_GENERAL, HungerAxe.DamageAgainstUndead,0,100,"How much damage the axe does(it can only damage undead mobs).");
        HungerAxe.MaxHealthTransfer=configuration.getInt("MaxHealthTransfer",Configuration.CATEGORY_GENERAL, HungerAxe.MaxHealthTransfer,0,100,"How much health at max can be transfered to the entity.");

        HungerAxe.FoodGain=configuration.getInt("FoodGain",Configuration.CATEGORY_GENERAL, HungerAxe.FoodGain,0,20,"How much food is added every 2 seconds.");
        HungerAxe.SaturationGain=configuration.getFloat("SaturationGain",Configuration.CATEGORY_GENERAL, HungerAxe.SaturationGain,0f,1f,"How much saturation (% of food) is added every 2 seconds.");


        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
