package com.miya.nxu.Items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.registry.GameRegistry;

// Structure from Et Futurum (Requiem)
public enum ModItems {

    // spotless:off
    // make sure to leave a trailing comma


    HUNGER_AXE(true, new ItemHungerAxe(), "hunger_axe"),
    EROSION_SHOVEL(true, new ItemErosionShovel(), "erosion_shovel"),
    DESTRUCTION_PICKAXE(true, new ItemDestructionPickaxe(), "destruction_pickaxe"),


    ; // leave trailing semicolon
    // spotless:on

    public static final ModItems[] VALUES = values();

    public static void init() {
        for (ModItems item : VALUES) {
            if (item.isEnabled()) {
                GameRegistry.registerItem(item.get(), item.name);
            }
        }
        // What happens when it's turned off, but gets turned on later? does the item just disappear from inventories
        // and stuff?
    }

    final private boolean isEnabled;
    final private Item theItem;
    private final String name;

    ModItems(boolean enabled, Item item, String name) {
        isEnabled = enabled;
        theItem = item;
        this.name = name;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public Item get() {
        return theItem;
    }

    public ItemStack newItemStack() {
        return newItemStack(1);
    }

    public ItemStack newItemStack(int count) {
        return newItemStack(count, 0);
    }

    public ItemStack newItemStack(int count, int meta) {
        return new ItemStack(this.get(), count, meta);
    }
}
