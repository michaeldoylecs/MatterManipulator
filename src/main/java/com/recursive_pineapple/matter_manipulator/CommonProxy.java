package com.recursive_pineapple.matter_manipulator;

import com.recursive_pineapple.matter_manipulator.common.building.BlockSpec;
import com.recursive_pineapple.matter_manipulator.common.compat.BlockPropertyRegistry;
import com.recursive_pineapple.matter_manipulator.common.entities.EntityItemLarge;
import com.recursive_pineapple.matter_manipulator.common.items.MMItems;
import com.recursive_pineapple.matter_manipulator.common.networking.Messages;
import com.recursive_pineapple.matter_manipulator.common.recipes.ManipulatorRecipes;
import com.recursive_pineapple.matter_manipulator.common.utils.Mods;
import com.recursive_pineapple.matter_manipulator.server.BlockStateCommand;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.entity.player.EntityPlayer;

public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        MMMod.LOG.info("Loading Matter Manipulator version " + Tags.VERSION);

        GlobalMMConfig.init();
        MMItems.registerItems();
        Messages.init();
    }

    public void init(FMLInitializationEvent event) {
        EntityItemLarge.registerCommon();
        
        if (Mods.GregTech.isModLoaded()) {
            MMItems.registerMultis();
        }
    }

    public void postInit(FMLPostInitializationEvent event) {
        ManipulatorRecipes.addRecipes();
        BlockPropertyRegistry.init();
    }

    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new BlockStateCommand());
    }

    public EntityPlayer getThePlayer() {
        return null;
    }
}
