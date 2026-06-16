package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.structure.JungleRuinStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStructureTypes {


    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, AncientTimes.MODID);

    public static final RegistryObject<StructureType<JungleRuinStructure>> JUNGLE_RUIN_STRUCTURE = STRUCTURE_TYPES.register("jungle_ruin", () -> () -> JungleRuinStructure.CODEC);
}
