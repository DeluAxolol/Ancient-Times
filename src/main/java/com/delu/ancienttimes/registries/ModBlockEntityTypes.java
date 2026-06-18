package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.block.ChiselableBlockEntity;
import com.delu.ancienttimes.common.entity.ModHangingSignBlockEntity;
import com.delu.ancienttimes.common.entity.ModSignBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BrushableBlockEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, AncientTimes.MODID);

    public static final RegistryObject<BlockEntityType<BrushableBlockEntity>> BRUSHABLE_BLOCK = BLOCK_ENTITY_TYPES.register("brushable_block", () -> BlockEntityType.Builder.of(BrushableBlockEntity::new, ModBlocks.SUSPICIOUS_RED_SAND.get(), ModBlocks.SUSPICIOUS_CLAY.get(), ModBlocks.SUSPICIOUS_DIRT.get(), ModBlocks.SUSPICIOUS_MUD.get(), ModBlocks.SUSPICIOUS_SNOW.get()).build(null));
    public static final RegistryObject<BlockEntityType<ChiselableBlockEntity>> CHISELABLE_BLOCK = BLOCK_ENTITY_TYPES.register("chiselable_block", () -> BlockEntityType.Builder.of(ChiselableBlockEntity::new, ModBlocks.SUSPICIOUS_STONE.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModSignBlockEntity>> MOD_SIGN =
            BLOCK_ENTITY_TYPES.register("mod_sign", () ->
                    BlockEntityType.Builder.of(ModSignBlockEntity::new,
                            ModBlocks.MEAL_SIGN.get(), ModBlocks.MEAL_WALL_SIGN.get()).build(null));
    public static final RegistryObject<BlockEntityType<ModHangingSignBlockEntity>> MOD_HANGING_SIGN =
            BLOCK_ENTITY_TYPES.register("mod_hanging_sign", () ->
                    BlockEntityType.Builder.of(ModHangingSignBlockEntity::new,
                            ModBlocks.MEAL_HANGING_SIGN.get(), ModBlocks.MEAL_WALL_HANGING_SIGN.get()).build(null));

}
