package com.miya.nxu;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {

    // public static String greeting = "Hello World";

    // God bless java's inner classes being the way they are lmao
    public class HungerAxe {

        public static Boolean StealHpFromAttacker = true;
        public static Float DamageAgainstUndead = 6 + 3 * 4f;
        public static Boolean SpawnParticlesOnInteraction = true;
        public static Integer MaxHealthTransfer = 3;
        public static Integer FoodGain = 1;
        public static Float SaturationGain = 0.25f;
        public static Boolean UseHungerWithoutInteraction = true;
        public static Boolean VoidMinedBlock = false;
    }

    public class ErosionShovel {

        public static Boolean VoidMinedBlock = true;
    }

    public class DestructionPickaxe {

        public static Boolean VoidMinedBlock = true;
    }

    public static void synchronizeConfiguration(File configFile) {
        Configuration configuration = new Configuration(configFile);

        configuration.addCustomCategoryComment("HungerAxe", "Values for hunger axe item");
        HungerAxe.VoidMinedBlock = configuration.getBoolean(
            "VoidMinedBlock",
            "HungerAxe",
            HungerAxe.VoidMinedBlock,
            "Void the mined block, like the rest of the unstable tools.");

        HungerAxe.StealHpFromAttacker = configuration.getBoolean(
            "StealHpFromAttacker",
            "HungerAxe",
            HungerAxe.StealHpFromAttacker,
            "Whether to steal hp from the attacker to heal the target.");
        HungerAxe.SpawnParticlesOnInteraction = configuration.getBoolean(
            "SpawnParticlesOnInteraction",
            "HungerAxe",
            HungerAxe.SpawnParticlesOnInteraction,
            "Whether to spawn particles on all axe interactions or not.");
        HungerAxe.DamageAgainstUndead = configuration.getFloat(
            "DamageAgainstUndead",
            "HungerAxe",
            HungerAxe.DamageAgainstUndead,
            0,
            100,
            "How much damage the axe does(it can only damage undead mobs).");
        HungerAxe.MaxHealthTransfer = configuration.getInt(
            "MaxHealthTransfer",
            "HungerAxe",
            HungerAxe.MaxHealthTransfer,
            0,
            100,
            "How much health at max can be transfered to the entity.");
        HungerAxe.FoodGain = configuration
            .getInt("FoodGain", "HungerAxe", HungerAxe.FoodGain, 0, 20, "How much food is added every 2 seconds.");
        HungerAxe.SaturationGain = configuration.getFloat(
            "SaturationGain",
            "HungerAxe",
            HungerAxe.SaturationGain,
            0f,
            1f,
            "How much saturation (% of food) is added every 2 seconds.");
        HungerAxe.UseHungerWithoutInteraction = configuration.getBoolean(
            "UseHungerWithoutInteraction",
            "HungerAxe",
            HungerAxe.UseHungerWithoutInteraction,
            "Use hunger even if the target didn't get healed.");

        configuration.addCustomCategoryComment("ErosionShovel", "Values for erosion shovel item");
        ErosionShovel.VoidMinedBlock = configuration.getBoolean(
            "VoidMinedBlock",
            "ErosionShovel",
            ErosionShovel.VoidMinedBlock,
            "Void the mined block, like the rest of the unstable tools.");

        configuration.addCustomCategoryComment("DestructionPickaxe", "Values for destruction pickaxe item");
        DestructionPickaxe.VoidMinedBlock = configuration.getBoolean(
            "VoidMinedBlock",
            "DestructionPickaxe",
            DestructionPickaxe.VoidMinedBlock,
            "Void the mined block, like the rest of the unstable tools.");

        if (configuration.hasChanged()) {
            configuration.save();
        }
    }
}
