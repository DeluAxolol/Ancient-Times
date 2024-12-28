package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AncientTimes.MODID);

    public static final RegistryObject<BlockEntityType<BrushableBlockEntity>> BRUSHABLE_BLOCK = BLOCK_ENTITY_TYPES.register("brushable_block", () -> BlockEntityType.Builder.of(BrushableBlockEntity::new, ModBlocks.SUS_RED_SAND.get(), ModBlocks.SUS_ANDESITE.get(), ModBlocks.SUS_CLAY.get(), ModBlocks.SUS_DIRT.get(), ModBlocks.SUS_MUD.get(), ModBlocks.SUS_SNOW.get()).build(null));

}
