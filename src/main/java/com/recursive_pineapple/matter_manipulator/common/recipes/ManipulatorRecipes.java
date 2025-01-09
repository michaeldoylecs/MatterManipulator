package com.recursive_pineapple.matter_manipulator.common.recipes;

import static com.recursive_pineapple.matter_manipulator.common.utils.MMValues.L;
import static gregtech.api.enums.Mods.AE2FluidCraft;
import static gregtech.api.enums.Mods.IndustrialCraft2;
import static gregtech.api.util.GTModHandler.getModItem;
import static gregtech.api.util.GTRecipeBuilder.*;
import static gregtech.api.util.GTRecipeConstants.*;

import net.minecraft.item.ItemStack;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.enums.TierEU;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;

import appeng.api.AEApi;

import com.recursive_pineapple.matter_manipulator.asm.Optional;
import com.recursive_pineapple.matter_manipulator.common.items.MMItemList;
import com.recursive_pineapple.matter_manipulator.common.utils.Mods;
import com.recursive_pineapple.matter_manipulator.common.utils.Mods.Names;

import bartworks.system.material.WerkstoffLoader;
import gtPlusPlus.core.item.ModItems;
import tectech.recipe.TTRecipeAdder;
import tectech.thing.CustomItemList;

public class ManipulatorRecipes {

    private ManipulatorRecipes() {}

    private static boolean ae, gt, th, eio, gs;

    public static void addRecipes() {
        ae = Mods.AppliedEnergistics2.isModLoaded();
        gt = Mods.GregTech.isModLoaded();
        th = Mods.Thaumcraft.isModLoaded();
        eio = Mods.EnderIO.isModLoaded();
        gs = Mods.GraviSuite.isModLoaded();

        if (gt) {
            addMK0Recipes();
            addMK1Recipes();
            addMK2Recipes();
            addMK3Recipe();
            addUplinkRecipes();
        }
    }

    @Optional(Names.GREG_TECH)
    private static void addMK0Recipes() {
        // Power core MK0
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.StainlessSteel, 8),
                getModItem(IndustrialCraft2.ID, "itemBatCrystal", 1), // energy crystal
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorHV, 12),
                new Object[] {
                    OrePrefixes.circuit.get(Materials.HV), 2
                },
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 16)
            )
            .fluidInputs(Materials.SolderingAlloy.getMolten(L * 4))
            .itemOutputs(MMItemList.PowerCore0.get(1))
            .eut((int) TierEU.RECIPE_HV)
            .duration(20 * SECONDS)
            .addTo(RecipeMaps.assemblerRecipes);

        // Computer core MK0
        if (eio) GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.StainlessSteel, 8),
                new Object[] {
                    OrePrefixes.circuit.get(Materials.IV), 2
                },
                getModItem(Mods.EnderIO.ID, "blockEndermanSkull", 1, 2), // tormented enderman skull
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorHV, 12),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 16)
            )
            .fluidInputs(Materials.SolderingAlloy.getMolten(L * 8))
            .itemOutputs(MMItemList.ComputerCore0.get(1))
            .eut((int) TierEU.RECIPE_HV)
            .duration(20 * SECONDS)
            .addTo(RecipeMaps.circuitAssemblerRecipes);

        // Teleporter core MK0
        if (th) GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.StainlessSteel, 1),
                ItemList.Emitter_HV.get(2),
                getModItem(Mods.Thaumcraft.ID, "ItemResource", 1, 15), // primal charm
                ItemList.QuantumEye.get(2),
                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Thaumium, 8),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.Thaumium, 16)
            )
            .fluidInputs(FluidRegistry.getFluidStack("ender", 2000))
            .itemOutputs(MMItemList.TeleporterCore0.get(1))
            .eut((int) TierEU.RECIPE_HV)
            .duration(20 * SECONDS)
            .addTo(RecipeMaps.assemblerRecipes);

        // Frame MK0
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 16)
            )
            .itemOutputs(MMItemList.Frame0.get(1))
            .eut((int) TierEU.RECIPE_HV)
            .duration(20 * SECONDS)
            .addTo(RecipeMaps.benderRecipes);

        // Lens MK0
        if (th) GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTOreDictUnificator.get(OrePrefixes.ring, Materials.StainlessSteel, 4),
                getModItem(Mods.Thaumcraft.ID, "FocusTrade", 1), // equal trade focus
                ItemList.Field_Generator_MV.get(1),
                ItemList.Electric_Piston_HV.get(2),
                ItemList.Electric_Motor_HV.get(2)
            )
            .fluidInputs(Materials.SolderingAlloy.getMolten(L * 4))
            .itemOutputs(MMItemList.Lens0.get(1))
            .eut((int) TierEU.RECIPE_HV)
            .duration(20 * SECONDS)
            .addTo(RecipeMaps.assemblerRecipes);

        // Manipulator MK0
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                MMItemList.Lens0.get(1),
                MMItemList.TeleporterCore0.get(1),
                MMItemList.ComputerCore0.get(1),
                MMItemList.PowerCore0.get(1),
                MMItemList.Frame0.get(1)
            )
            .fluidInputs(Materials.SolderingAlloy.getMolten(L * 8))
            .itemOutputs(MMItemList.MK0.get(1))
            .eut((int) TierEU.RECIPE_HV)
            .duration(30 * SECONDS)
            .addTo(RecipeMaps.assemblerRecipes);

    }

    @Optional(Names.GREG_TECH)
    private static void addMK1Recipes() {
        Fluid solderIndalloy = FluidRegistry.getFluid("molten.indalloy140") != null ?
            FluidRegistry.getFluid("molten.indalloy140") :
            FluidRegistry.getFluid("molten.solderingalloy");

        // Power core MK1
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 8),
                ItemList.Energy_LapotronicOrb.get(1),
                ItemList.Circuit_Chip_PIC.get(4),
                GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorIV, 6),
                new Object[] {
                    OrePrefixes.circuit.get(Materials.IV), 2
                },
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 16)
            )
            .fluidInputs(new FluidStack(solderIndalloy, (int) (L * 8)))
            .itemOutputs(MMItemList.PowerCore1.get(1))
            .eut((int) TierEU.RECIPE_IV)
            .duration(20 * SECONDS)
            .addTo(RecipeMaps.assemblerRecipes);

        // Computer core MK1
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.TungstenSteel, 1),
                new Object[] {
                    OrePrefixes.circuit.get(Materials.ZPM), 2
                },
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorIV, 12),
                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 8),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 16)
            )
            .fluidInputs(new FluidStack(solderIndalloy, (int) (L * 16)))
            .itemOutputs(MMItemList.ComputerCore1.get(1))
            .eut((int) TierEU.RECIPE_IV)
            .duration(20 * SECONDS)
            .addTo(RecipeMaps.circuitAssemblerRecipes);

        // Teleporter core MK1
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Enderium, 1),
                getModItem(IndustrialCraft2.ID, "blockMachine2", 1, 0), // teleporter
                ItemList.Emitter_IV.get(2),
                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.TungstenSteel, 8),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 16)
            )
            .fluidInputs(new FluidStack(solderIndalloy, (int) (L * 16)))
            .itemOutputs(MMItemList.TeleporterCore1.get(1))
            .eut((int) TierEU.RECIPE_IV)
            .duration(20 * SECONDS)
            .addTo(RecipeMaps.assemblerRecipes);

        // Frame MK1
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 32)
            )
            .itemOutputs(MMItemList.Frame1.get(1))
            .eut((int) TierEU.RECIPE_IV)
            .duration(20 * SECONDS)
            .addTo(RecipeMaps.benderRecipes);

        // Lens MK1
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                GTOreDictUnificator.get(OrePrefixes.lens, Materials.NetherStar, 2),
                GTOreDictUnificator.get(OrePrefixes.ring, Materials.TungstenSteel, 4),
                ItemList.Field_Generator_IV.get(1),
                ItemList.Electric_Piston_IV.get(2),
                ItemList.Electric_Motor_IV.get(2)
            )
            .fluidInputs(new FluidStack(solderIndalloy, (int) (L * 4)))
            .itemOutputs(MMItemList.Lens1.get(1))
            .eut((int) TierEU.RECIPE_IV)
            .duration(20 * SECONDS)
            .addTo(RecipeMaps.assemblerRecipes);

        // Manipulator MK1
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                MMItemList.Lens1.get(1),
                MMItemList.TeleporterCore1.get(1),
                MMItemList.ComputerCore1.get(1),
                MMItemList.PowerCore1.get(1),
                MMItemList.Frame1.get(1),
                MMItemList.AEDownlink.get(1)
            )
            .fluidInputs(new FluidStack(solderIndalloy, (int) (L * 16)))
            .itemOutputs(MMItemList.MK1.get(1))
            .eut((int) TierEU.RECIPE_IV)
            .duration(30 * SECONDS)
            .addTo(RecipeMaps.assemblerRecipes);
    }

    @Optional(Names.GREG_TECH)
    private static void addMK2Recipes() {
        Fluid solderIndalloy = FluidRegistry.getFluid("molten.indalloy140") != null ?
            FluidRegistry.getFluid("molten.indalloy140") :
            FluidRegistry.getFluid("molten.solderingalloy");

        // Power core MK2
        GTValues.RA.stdBuilder()
            .metadata(RESEARCH_ITEM, ItemList.Circuit_Chip_HPIC.get(2))
            .metadata(RESEARCH_TIME, 40 * MINUTES)
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.HSSS, 8),
                ItemList.Energy_LapotronicOrb2.get(1),
                ItemList.Circuit_Chip_HPIC.get(4),
                GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorLuV, 6),
                new Object[] {
                    OrePrefixes.circuit.get(Materials.LuV), 2
                },
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.HSSS, 16)
            )
            .fluidInputs(
                new FluidStack(solderIndalloy, (int) (L * 8)),
                new FluidStack(FluidRegistry.getFluid("ic2coolant"), 16000)
            )
            .itemOutputs(MMItemList.PowerCore2.get(1))
            .duration(1 * MINUTES)
            .eut((int) TierEU.RECIPE_LuV)
            .addTo(AssemblyLine);

        // Computer core MK2
        GTValues.RA.stdBuilder()
            .metadata(RESEARCH_ITEM, GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.HSSS, 1))
            .metadata(RESEARCH_TIME, 40 * MINUTES)
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.HSSS, 1),
                new Object[] {
                    OrePrefixes.circuit.get(Materials.UV), 2
                },
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorLuV, 18),
                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.HSSS, 8),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.HSSS, 16)
            )
            .fluidInputs(
                new FluidStack(solderIndalloy, (int) (L * 16)),
                new FluidStack(FluidRegistry.getFluid("ic2coolant"), 32000)
            )
            .itemOutputs(MMItemList.ComputerCore2.get(1))
            .duration(1 * MINUTES)
            .eut((int) TierEU.RECIPE_LuV)
            .addTo(AssemblyLine);

        // Teleporter core MK2
        if (gs) GTValues.RA.stdBuilder()
            .metadata(RESEARCH_ITEM, GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Quantium, 1))
            .metadata(RESEARCH_TIME, 40 * MINUTES)
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.Quantium, 1),
                ItemList.Emitter_LuV.get(2),
                ItemList.Field_Generator_LuV.get(1),
                ItemList.QuantumStar.get(4),
                getModItem(Mods.GraviSuite.ID, "itemSimpleItem", 4, 3), // gravitation engine
                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.HSSS, 8),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.HSSS, 16)
            )
            .fluidInputs(
                new FluidStack(solderIndalloy, (int) (L * 16)),
                Materials.Quantium.getMolten(L * 16),
                Materials.Duranium.getMolten(L * 8),
                new FluidStack(FluidRegistry.getFluid("ic2coolant"), 32000)
            )
            .itemOutputs(MMItemList.TeleporterCore2.get(1))
            .duration(1 * MINUTES)
            .eut((int) TierEU.RECIPE_LuV)
            .addTo(AssemblyLine);

        // Frame MK2
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.HSSS, 48)
            )
            .itemOutputs(MMItemList.Frame2.get(1))
            .eut((int) TierEU.RECIPE_LuV)
            .duration(30 * SECONDS)
            .addTo(RecipeMaps.benderRecipes);

        // Lens MK2
        GTValues.RA.stdBuilder()
            .metadata(RESEARCH_ITEM, WerkstoffLoader.RedZircon.get(OrePrefixes.lens, 1))
            .metadata(RESEARCH_TIME, 40 * MINUTES)
            .itemInputs(
                WerkstoffLoader.RedZircon.get(OrePrefixes.lens, 2),
                GTOreDictUnificator.get(OrePrefixes.ring, Materials.HSSS, 4),
                ItemList.Field_Generator_LuV.get(1),
                ItemList.Electric_Motor_LuV.get(2),
                ItemList.Electric_Piston_LuV.get(2)
            )
            .fluidInputs(new FluidStack(solderIndalloy, (int) (L * 4)))
            .itemOutputs(MMItemList.Lens2.get(1))
            .duration(1 * MINUTES)
            .eut((int) TierEU.RECIPE_LuV)
            .addTo(AssemblyLine);

        // Manipulator MK2
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                MMItemList.Lens2.get(1),
                MMItemList.TeleporterCore2.get(1),
                MMItemList.ComputerCore2.get(1),
                MMItemList.PowerCore2.get(1),
                MMItemList.Frame2.get(1),
                MMItemList.AEDownlink.get(1)
            )
            .fluidInputs(new FluidStack(solderIndalloy, (int) (L * 16)))
            .itemOutputs(MMItemList.MK2.get(1))
            .eut((int) TierEU.RECIPE_LuV)
            .duration(30 * SECONDS)
            .addTo(RecipeMaps.assemblerRecipes);
    }

    @Optional(Names.GREG_TECH)
    private static void addMK3Recipe() {
        Fluid solderIndalloy = FluidRegistry.getFluid("molten.indalloy140") != null ?
            FluidRegistry.getFluid("molten.indalloy140") :
            FluidRegistry.getFluid("molten.solderingalloy");

        // Power core MK3
        GTValues.RA.stdBuilder()
            .metadata(RESEARCH_ITEM, ItemList.Circuit_Chip_UHPIC.get(2))
            .metadata(RESEARCH_TIME, 60 * MINUTES)
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.NaquadahAlloy, 8),
                ItemList.Energy_Module.get(1),
                ItemList.Circuit_Chip_UHPIC.get(8),
                GTOreDictUnificator.get(OrePrefixes.wireGt02, Materials.SuperconductorZPM, 12),
                new Object[] {
                    OrePrefixes.circuit.get(Materials.ZPM), 2
                },
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.NaquadahAlloy, 16)
            )
            .fluidInputs(new FluidStack(solderIndalloy, (int) (L * 16)), Materials.SuperCoolant.getFluid(32000))
            .itemOutputs(MMItemList.PowerCore3.get(1))
            .duration(1 * MINUTES)
            .eut((int) TierEU.RECIPE_ZPM)
            .addTo(AssemblyLine);

        // Computer core MK3
        GTValues.RA.stdBuilder()
            .metadata(RESEARCH_ITEM, GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.NaquadahAlloy, 1))
            .metadata(RESEARCH_TIME, 60 * MINUTES)
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.NaquadahAlloy, 1),
                new Object[] {
                    OrePrefixes.circuit.get(Materials.UV), 2
                },
                GTOreDictUnificator.get(OrePrefixes.wireGt01, Materials.SuperconductorZPM, 30),
                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.NaquadahAlloy, 8),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.NaquadahAlloy, 16)
            )
            .fluidInputs(new FluidStack(solderIndalloy, (int) (L * 32)), Materials.SuperCoolant.getFluid(64000))
            .itemOutputs(MMItemList.ComputerCore3.get(1))
            .duration(1 * MINUTES)
            .eut((int) TierEU.RECIPE_ZPM)
            .addTo(AssemblyLine);

        // Teleporter core MK3
        if (gs) GTValues.RA.stdBuilder()
            .metadata(RESEARCH_ITEM, GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.MysteriousCrystal, 1))
            .metadata(RESEARCH_TIME, 60 * MINUTES)
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.frameGt, Materials.MysteriousCrystal, 1),
                ItemList.Emitter_ZPM.get(2),
                ItemList.Field_Generator_ZPM.get(1),
                ItemList.Gravistar.get(4),
                new ItemStack(ModItems.itemStandarParticleBase, 16, 0), // gravitons
                getModItem(Mods.GraviSuite.ID, "itemSimpleItem", 16, 3), // gravitation engine
                GTOreDictUnificator.get(OrePrefixes.itemCasing, Materials.Trinium, 8),
                GTOreDictUnificator.get(OrePrefixes.screw, Materials.Trinium, 16)
            )
            .fluidInputs(
                new FluidStack(solderIndalloy, (int) (L * 16)),
                Materials.MysteriousCrystal.getMolten(L * 16),
                Materials.Tritanium.getMolten(L * 16),
                Materials.SuperCoolant.getFluid(32000)
            )
            .itemOutputs(MMItemList.TeleporterCore3.get(1))
            .duration(1 * MINUTES)
            .eut((int) TierEU.RECIPE_ZPM)
            .addTo(AssemblyLine);

        // Frame MK3
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(10),
                GTOreDictUnificator.get(OrePrefixes.plate, Materials.NaquadahAlloy, 64)
            )
            .itemOutputs(MMItemList.Frame3.get(1))
            .eut((int) TierEU.RECIPE_ZPM)
            .duration(30 * SECONDS)
            .addTo(RecipeMaps.benderRecipes);

        // Lens MK3
        GTValues.RA.stdBuilder()
            .metadata(RESEARCH_ITEM, WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.lens, 1))
            .metadata(RESEARCH_TIME, 60 * MINUTES)
            .itemInputs(
                WerkstoffLoader.MagnetoResonaticDust.get(OrePrefixes.lens, 2),
                GTOreDictUnificator.get(OrePrefixes.ring, Materials.NaquadahAlloy, 4),
                ItemList.Field_Generator_ZPM.get(1),
                ItemList.Electric_Piston_ZPM.get(2),
                ItemList.Electric_Motor_ZPM.get(2)
            )
            .fluidInputs(new FluidStack(solderIndalloy, (int) (L * 16)))
            .itemOutputs(MMItemList.Lens3.get(1))
            .duration(1 * MINUTES)
            .eut((int) TierEU.RECIPE_ZPM)
            .addTo(AssemblyLine);

        // ME Downlink
        if (ae) GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                AEApi.instance()
                    .definitions()
                    .blocks()
                    .wireless()
                    .maybeStack(1)
                    .get(),
                AEApi.instance()
                    .definitions()
                    .blocks()
                    .energyCell()
                    .maybeStack(1)
                    .get(),
                AEApi.instance()
                    .definitions()
                    .materials()
                    .cell256kPart()
                    .maybeStack(1)
                    .get(),
                getModItem(AE2FluidCraft.ID, "fluid_interface", 1),
                ItemList.Conveyor_Module_IV.get(2),
                ItemList.Electric_Pump_IV.get(2),
                new Object[] {
                    OrePrefixes.circuit.get(Materials.IV), 1
                }
            )
            .fluidInputs(new FluidStack(solderIndalloy, (int) (L * 8)))
            .itemOutputs(MMItemList.AEDownlink.get(1))
            .eut((int) TierEU.RECIPE_IV)
            .duration(20 * SECONDS)
            .addTo(RecipeMaps.assemblerRecipes);

        // Quantum Downlink
        if (ae) GTValues.RA.stdBuilder()
            .metadata(
                RESEARCH_ITEM,
                AEApi.instance()
                    .definitions()
                    .blocks()
                    .quantumRing()
                    .maybeStack(1)
                    .get()
            )
            .metadata(RESEARCH_TIME, 120 * MINUTES)
            .itemInputs(
                AEApi.instance()
                    .definitions()
                    .blocks()
                    .quantumRing()
                    .maybeStack(8)
                    .get(),
                AEApi.instance()
                    .definitions()
                    .blocks()
                    .quantumLink()
                    .maybeStack(1)
                    .get(),
                AEApi.instance()
                    .definitions()
                    .blocks()
                    .controller()
                    .maybeStack(1)
                    .get(),
                AEApi.instance()
                    .definitions()
                    .blocks()
                    .energyCellDense()
                    .maybeStack(1)
                    .get(),
                AEApi.instance()
                    .definitions()
                    .materials()
                    .cell4096kPart()
                    .maybeStack(1)
                    .get(),
                getModItem(AE2FluidCraft.ID, "fluid_interface", 1),
                ItemList.Conveyor_Module_ZPM.get(2),
                ItemList.Electric_Pump_ZPM.get(2),
                new Object[] {
                    OrePrefixes.circuit.get(Materials.UV), 4
                }
            )
            .fluidInputs(new FluidStack(solderIndalloy, (int) (L * 32)))
            .itemOutputs(MMItemList.QuantumDownlink.get(1))
            .duration(1 * MINUTES)
            .eut((int) TierEU.RECIPE_ZPM)
            .addTo(AssemblyLine);

        // Manipulator MK3
        GTValues.RA.stdBuilder()
            .itemInputs(
                GTUtility.getIntegratedCircuit(5),
                MMItemList.Lens3.get(1),
                MMItemList.TeleporterCore3.get(1),
                MMItemList.ComputerCore3.get(1),
                MMItemList.PowerCore3.get(1),
                MMItemList.Frame3.get(1),
                MMItemList.AEDownlink.get(1),
                MMItemList.QuantumDownlink.get(1)
            )
            .fluidInputs(new FluidStack(solderIndalloy, (int) (L * 16)))
            .itemOutputs(MMItemList.MK3.get(1))
            .eut((int) TierEU.RECIPE_ZPM)
            .duration(30 * SECONDS)
            .addTo(RecipeMaps.assemblerRecipes);
    }

    @SuppressWarnings("deprecation")
    @Optional(Names.GREG_TECH)
    private static void addUplinkRecipes() {
        Fluid solderIndalloy = FluidRegistry.getFluid("molten.indalloy140") != null ?
            FluidRegistry.getFluid("molten.indalloy140") :
            FluidRegistry.getFluid("molten.solderingalloy");

        // Quantum Uplink ME Connector Hatch
        if (ae) TTRecipeAdder.addResearchableAssemblylineRecipe(
            AEApi.instance()
                .definitions()
                .parts()
                .patternTerminal()
                .maybeStack(1)
                .get(),
            80_000,
            32,
            (int) TierEU.RECIPE_UV,
            2,
            new Object[] {
                CustomItemList.dataIn_Hatch.get(1),
                AEApi.instance()
                    .definitions()
                    .materials()
                    .cell16384kPart()
                    .maybeStack(1)
                    .get(),
                getModItem(AE2FluidCraft.ID, "fluid_interface", 1),
                AEApi.instance()
                    .definitions()
                    .parts()
                    .patternTerminal()
                    .maybeStack(1)
                    .get(),
                ItemList.Robot_Arm_UV.get(1),
                AEApi.instance()
                    .definitions()
                    .materials()
                    .blankPattern()
                    .maybeStack(64)
                    .get(),
                new Object[] {
                    OrePrefixes.circuit.get(Materials.UV), 4
                }
            },
            new FluidStack[] {
                new FluidStack(solderIndalloy, (int) (L * 32)), Materials.Naquadria.getMolten(L * 16),
            },
            MMItemList.UplinkHatch.get(1),
            60 * SECONDS,
            (int) TierEU.RECIPE_UV
        );

        // Matter Manipulator Quantum Uplink
        if (ae) TTRecipeAdder.addResearchableAssemblylineRecipe(
            AEApi.instance()
                .definitions()
                .blocks()
                .quantumLink()
                .maybeStack(1)
                .get(),
            160_000,
            32,
            (int) TierEU.RECIPE_UV,
            4,
            new Object[] {
                CustomItemList.Machine_Multi_DataBank.get(1),
                AEApi.instance()
                    .definitions()
                    .blocks()
                    .controller()
                    .maybeStack(4)
                    .get(),
                AEApi.instance()
                    .definitions()
                    .blocks()
                    .quantumRing()
                    .maybeStack(8)
                    .get(),
                AEApi.instance()
                    .definitions()
                    .blocks()
                    .quantumLink()
                    .maybeStack(1)
                    .get(),
                AEApi.instance()
                    .definitions()
                    .blocks()
                    .iOPort()
                    .maybeStack(1)
                    .get(),
                AEApi.instance()
                    .definitions()
                    .materials()
                    .cardSuperSpeed()
                    .maybeStack(2)
                    .get(),
                CustomItemList.dataOut_Hatch.get(1),
                CustomItemList.DATApipe.get(32),
                new Object[] {
                    OrePrefixes.circuit.get(Materials.UHV), 2
                }
            },
            new FluidStack[] {
                new FluidStack(solderIndalloy, (int) (L * 64)), Materials.Naquadria.getMolten(L * 32),
            },
            MMItemList.UplinkController.get(1),
            60 * SECONDS,
            (int) TierEU.RECIPE_UV
        );
    }
}
