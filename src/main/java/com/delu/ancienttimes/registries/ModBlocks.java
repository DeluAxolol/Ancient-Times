package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.block.MardFlower;
import com.delu.ancienttimes.common.block.ScalemouflisEggBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, AncientTimes.MODID);

    public static final RegistryObject<MardFlower> MARD_FLOWER = BLOCKS.register("mard_flower",
            () -> new MardFlower(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XZ).pushReaction(PushReaction.DESTROY)));


    public static final RegistryObject<RotatedPillarBlock> MEAL_LOG = registerWithTab("meal_log", () -> log(MapColor.WOOD, MapColor.PODZOL), ModCreativeTabs.ANCIENT_TIMES_TAB);
    public static final RegistryObject<RotatedPillarBlock> STRIPPED_MEAL_LOG = registerWithTab("stripped_meal_log", () -> log(MapColor.DIRT, MapColor.DIRT), ModCreativeTabs.ANCIENT_TIMES_TAB);
    public static final RegistryObject<Block> MEAL_PLANKS = registerWithTab("meal_planks", ModBlocks::planks, ModCreativeTabs.ANCIENT_TIMES_TAB);
    public static final RegistryObject<Block> MEAL_LEAVES = registerWithTab("meal_leaves", () -> leaves(SoundType.GRASS), ModCreativeTabs.ANCIENT_TIMES_TAB);
    public static final RegistryObject<TrapDoorBlock> MEAL_TRAPDOOR = registerWithTab("meal_trapdoor", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn(ModBlocks::never).ignitedByLava(), BlockSetType.OAK), ModCreativeTabs.ANCIENT_TIMES_TAB);
    public static final RegistryObject<DoorBlock> MEAL_DOOR = registerWithTab("meal_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(MEAL_PLANKS.get().defaultMapColor()).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY), BlockSetType.OAK), ModCreativeTabs.ANCIENT_TIMES_TAB);
    public static final RegistryObject<SaplingBlock> MEAL_SAPLING = registerWithTab("meal_sapling", () -> new SaplingBlock(new OakTreeGrower(), BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)), ModCreativeTabs.ANCIENT_TIMES_TAB);
    public static final RegistryObject<SlabBlock> MEAL_SLAB = registerWithTab("meal_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(ModBlocks.MEAL_PLANKS.get())), ModCreativeTabs.ANCIENT_TIMES_TAB);
    public static final RegistryObject<StairBlock> MEAL_STAIRS = registerWithTab("meal_stairs", () -> new StairBlock(() -> ModBlocks.MEAL_PLANKS.get().defaultBlockState() ,BlockBehaviour.Properties.copy(ModBlocks.MEAL_PLANKS.get())), ModCreativeTabs.ANCIENT_TIMES_TAB);




    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier) {
        return register(name, blockSupplier, Item.Properties::new);
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Supplier<Item.Properties> properties) {
        return register(name, blockSupplier, b -> new BlockItem(b, properties.get()));
    }

    public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Function<Block, Item> blockItemFunction) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ModItems.ITEMS.register(name, () -> blockItemFunction.apply(block.get()));
        return block;
    }

    public static <T extends Block> RegistryObject<T> registerWithTab(String name, Supplier<T> blockSupplier, Supplier<CreativeModeTab> tab) {
        return registerWithTab(name, blockSupplier, Item.Properties::new, tab);
    }

    public static <T extends Block> RegistryObject<T> registerWithTab(String name, Supplier<T> blockSupplier, Supplier<Item.Properties> properties, Supplier<CreativeModeTab> tab) {
        return registerWithTab(name, blockSupplier, b -> new BlockItem(b, properties.get()), tab);
    }

    public static <T extends Block> RegistryObject<T> registerWithTab(String name, Supplier<T> blockSupplier, Function<Block, Item> blockItemFunction, Supplier<CreativeModeTab> tab) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ModItems.registerWithCreativeModeTab(name, tab, () -> blockItemFunction.apply(block.get()));
        return block;
    }

    public static <T extends Block> RegistryObject<T> registerWithTab(String name, Supplier<T> blockSupplier, ResourceKey<CreativeModeTab> tab) {
        return registerWithTab(name, blockSupplier, Item.Properties::new, tab);
    }

    public static <T extends Block> RegistryObject<T> registerWithTab(String name, Supplier<T> blockSupplier, Supplier<Item.Properties> properties, ResourceKey<CreativeModeTab> tab) {
        return registerWithTab(name, blockSupplier, b -> new BlockItem(b, properties.get()), tab);
    }

    public static <T extends Block> RegistryObject<T> registerWithTab(String name, Supplier<T> blockSupplier, Function<Block, Item> blockItemFunction, ResourceKey<CreativeModeTab> tab) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ModItems.registerWithCreativeModeTab(name, tab, () -> blockItemFunction.apply(block.get()));
        return block;
    }


    public static RotatedPillarBlock log(MapColor pTopMapColor, MapColor pSideMapColor) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor((p_152624_) -> p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? pTopMapColor : pSideMapColor).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    }


    public static Block planks(){
        return planks(MapColor.WOOD);
    }
    public static Block planks(MapColor color){
        return  new Block(BlockBehaviour.Properties.of().mapColor(color).instrument(NoteBlockInstrument.BASS).strength(2.0F, 3.0F).sound(SoundType.WOOD).ignitedByLava());
    }

    private static LeavesBlock leaves(SoundType pType) {
        return new LeavesBlock(BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).strength(0.2F).randomTicks().sound(pType).noOcclusion().isValidSpawn(ModBlocks::ocelotOrParrot).isSuffocating(ModBlocks::never).isViewBlocking(ModBlocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(ModBlocks::never));
    }

    private static boolean always(BlockState p_50775_, BlockGetter p_50776_, BlockPos p_50777_) {
        return true;
    }


    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos, EntityType<?> type) {
        return false;
    }
    private static boolean never(BlockState p_50806_, BlockGetter p_50807_, BlockPos p_50808_) {
        return false;
    }

    private static boolean ocelotOrParrot(BlockState p_50822_, BlockGetter p_50823_, BlockPos p_50824_, EntityType<?> p_50825_) {
        return p_50825_ == EntityType.OCELOT || p_50825_ == EntityType.PARROT;
    }
}


   