package com.delu.ancienttimes.registries;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.block.ChiselableBlockEntity;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ModBlocks {

    public static void registerModBlocks() {
        // Principal Blocks
        registerBlockWithItem("mard_flower", MARD_FLOWER, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("ravenhead_sprouts", RAVENHEAD_SPROUTS, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("ravenheads_thornbush_block", RAVENHEADS_THORNBUSH_BLOCK, ModCreativeTabs.ANCIENT_TIMES_TAB);

        // Meal Blocks
        registerBlockWithItem("meal_log", MEAL_LOG, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("stripped_meal_log", STRIPPED_MEAL_LOG, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("meal_planks", MEAL_PLANKS, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("meal_leaves", MEAL_LEAVES, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("meal_trapdoor", MEAL_TRAPDOOR, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("meal_door", MEAL_DOOR, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("meal_sapling", MEAL_SAPLING, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("meal_slab", MEAL_SLAB, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("meal_stairs", MEAL_STAIRS, ModCreativeTabs.ANCIENT_TIMES_TAB);

        // Signs
        registerBlockWithItem("meal_sign", MEAL_SIGN, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("meal_wall_sign", MEAL_WALL_SIGN, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("meal_hanging_sign", MEAL_HANGING_SIGN, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("meal_wall_hanging_sign", MEAL_WALL_HANGING_SIGN, ModCreativeTabs.ANCIENT_TIMES_TAB);

        // Suspicious Blocks
        registerBlockWithItem("sus_red_sand", SUS_RED_SAND, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("sus_andesite", SUS_ANDESITE, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("sus_clay", SUS_CLAY, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("sus_dirt", SUS_DIRT, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("sus_mud", SUS_MUD, ModCreativeTabs.ANCIENT_TIMES_TAB);
        registerBlockWithItem("sus_snow", SUS_SNOW, ModCreativeTabs.ANCIENT_TIMES_TAB);
    }

    public static final MardFlower MARD_FLOWER = new MardFlower(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.GREEN)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.GRASS)
                    .offset(AbstractBlock.OffsetType.XZ)
                    .pistonBehavior(PistonBehavior.DESTROY)
    );

    public static final RavenheadSprouts RAVENHEAD_SPROUTS = new RavenheadSprouts(
            AbstractBlock.Settings.copy(Blocks.WHEAT)
                    .mapColor(MapColor.GREEN)
                    .noCollision()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.GRASS)
                    .offset(AbstractBlock.OffsetType.XZ)
                    .pistonBehavior(PistonBehavior.DESTROY)
    );

    public static final Block RAVENHEADS_THORNBUSH_BLOCK = new Block(
            AbstractBlock.Settings.copy(Blocks.WHEAT)
                    .strength(0.5f)
                    .mapColor(MapColor.GREEN)
                    .sounds(BlockSoundGroup.GRASS)
                    .pistonBehavior(PistonBehavior.DESTROY)
    ) {
        private static final VoxelShape COLLISION_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 15.0, 15.0);
        private static final VoxelShape OUTLINE_SHAPE = Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);

        @Override
        public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
            DamageSources damageSources = world.getDamageSources();
            entity.damage(damageSources.cactus(), 1.0F);
            super.onEntityCollision(state, world, pos, entity);
        }

        @Override
        public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
            return COLLISION_SHAPE;
        }

        @Override
        public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
            return OUTLINE_SHAPE;
        }
    };

    // Meal Blocks
    public static final PillarBlock MEAL_LOG = new PillarBlock(logSettings(MapColor.SPRUCE_BROWN, MapColor.PODZOL));
    public static final PillarBlock STRIPPED_MEAL_LOG = new PillarBlock(logSettings(MapColor.DIRT_BROWN, MapColor.DIRT_BROWN));
    public static final Block MEAL_PLANKS = new Block(planksSettings(MapColor.SPRUCE_BROWN));
    public static final LeavesBlock MEAL_LEAVES = leaves(BlockSoundGroup.GRASS);
    public static final TrapdoorBlock MEAL_TRAPDOOR = new TrapdoorBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.SPRUCE_BROWN)
                    .instrument(Instrument.BASS)
                    .strength(3.0f)
                    .nonOpaque()
                    .burnable(),
            BlockSetType.OAK
    );
    public static final DoorBlock MEAL_DOOR = new DoorBlock(
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.SPRUCE_BROWN)
                    .instrument(Instrument.BASS)
                    .strength(3.0f)
                    .nonOpaque()
                    .burnable()
                    .pistonBehavior(PistonBehavior.DESTROY),
            BlockSetType.OAK
    );
    public static final SaplingBlock MEAL_SAPLING = new MealSapling(
            new MealTreeGrower(),
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.GREEN)
                    .noCollision()
                    .ticksRandomly()
                    .breakInstantly()
                    .sounds(BlockSoundGroup.GRASS)
                    .pistonBehavior(PistonBehavior.DESTROY)
    );
    public static final SlabBlock MEAL_SLAB = new SlabBlock(AbstractBlock.Settings.copy(MEAL_PLANKS));
    public static final StairsBlock MEAL_STAIRS = new StairsBlock(
            MEAL_PLANKS.getDefaultState(),
            AbstractBlock.Settings.copy(MEAL_PLANKS)
    );

    // Signs
    public static final ModStandingSignBlock MEAL_SIGN = new ModStandingSignBlock(
            AbstractBlock.Settings.copy(Blocks.OAK_SIGN),
            ModWoodTypes.MEAL
    );
    public static final ModWallSignBlock MEAL_WALL_SIGN = new ModWallSignBlock(
            AbstractBlock.Settings.copy(Blocks.OAK_WALL_SIGN),
            ModWoodTypes.MEAL
    );
    public static final ModHangingSignBlock MEAL_HANGING_SIGN = new ModHangingSignBlock(
            AbstractBlock.Settings.copy(Blocks.OAK_HANGING_SIGN),
            ModWoodTypes.MEAL
    );
    public static final ModWallHangingSignBlock MEAL_WALL_HANGING_SIGN = new ModWallHangingSignBlock(
            AbstractBlock.Settings.copy(Blocks.OAK_WALL_HANGING_SIGN),
            ModWoodTypes.MEAL
    );

    // Suspicious Blocks
    public static final BrushableBlock SUS_RED_SAND = new BrushableBlock(
            Blocks.RED_SAND,
            AbstractBlock.Settings.copy(Blocks.SUSPICIOUS_SAND).sounds(BlockSoundGroup.SAND),
            SoundEvents.ITEM_BRUSH_BRUSHING_SAND,
            SoundEvents.ITEM_BRUSH_BRUSHING_SAND_COMPLETE
    );

    public static final BrushableBlockWithoutFall SUS_ANDESITE = new BrushableBlockWithoutFall(
            Blocks.STONE,
            AbstractBlock.Settings.copy(Blocks.STONE),
            SoundEvents.BLOCK_STONE_BREAK,
            SoundEvents.BLOCK_STONE_BREAK
    ) {
        @Override
        public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
            return new ChiselableBlockEntity(pos, state);
        }
    };

    public static final BrushableBlockWithoutFall SUS_CLAY = new BrushableBlockWithoutFall(
            Blocks.CLAY,
            AbstractBlock.Settings.copy(Blocks.CLAY),
            SoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL,
            SoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL_COMPLETE
    );

    public static final BrushableBlockWithoutFall SUS_DIRT = new BrushableBlockWithoutFall(
            Blocks.DIRT,
            AbstractBlock.Settings.copy(Blocks.DIRT),
            SoundEvents.ITEM_BRUSH_BRUSHING_SAND,
            SoundEvents.ITEM_BRUSH_BRUSHING_SAND_COMPLETE
    );

    public static final BrushableBlockWithoutFall SUS_MUD = new BrushableBlockWithoutFall(
            Blocks.MUD,
            AbstractBlock.Settings.copy(Blocks.MUD),
            SoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL,
            SoundEvents.ITEM_BRUSH_BRUSHING_GRAVEL_COMPLETE
    );

    public static final BrushableBlockWithoutFall SUS_SNOW = new BrushableBlockWithoutFall(
            Blocks.SNOW_BLOCK,
            AbstractBlock.Settings.copy(Blocks.SNOW_BLOCK),
            SoundEvents.ITEM_BRUSH_BRUSHING_SAND,
            SoundEvents.ITEM_BRUSH_BRUSHING_SAND_COMPLETE
    );

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(AncientTimes.MOD_ID, name), block);
    }

    private static Block registerBlockWithItem(String name, Block block, RegistryKey<ItemGroup> tab) {
        Block registeredBlock = registerBlock(name, block);
        registerBlockItem(name, registeredBlock, tab);
        return registeredBlock;
    }

    private static Item registerBlockItem(String name, Block block, RegistryKey<ItemGroup> tab) {
        return Registry.register(Registries.ITEM, new Identifier(AncientTimes.MOD_ID, name),
                new BlockItem(block, new Item.Settings().arch$tab(tab)));
    }

    private static AbstractBlock.Settings logSettings(MapColor topColor, MapColor sideColor) {
        return AbstractBlock.Settings.create()
                .mapColor(state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? topColor : sideColor)
                .instrument(Instrument.BASS)
                .strength(2.0F)
                .sounds(BlockSoundGroup.WOOD)
                .burnable();
    }

    private static AbstractBlock.Settings planksSettings(MapColor color) {
        return AbstractBlock.Settings.create()
                .mapColor(color)
                .instrument(Instrument.BASS)
                .strength(2.0f, 3.0f)
                .sounds(BlockSoundGroup.WOOD)
                .burnable();
    }

    private static LeavesBlock leaves(BlockSoundGroup soundGroup) {
        return new LeavesBlock(
                AbstractBlock.Settings.copy(Blocks.OAK_LEAVES)
                        .mapColor(MapColor.GREEN)
                        .strength(0.2F)
                        .ticksRandomly()
                        .sounds(soundGroup)
                        .nonOpaque()
                        .allowsSpawning(ModBlocks::ocelotOrParrotSpawn)
                        .suffocates(ModBlocks::never)
                        .blockVision(ModBlocks::never)
                        .burnable()
                        .pistonBehavior(PistonBehavior.DESTROY)
        );
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return false;
    }

    private static boolean never(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    private static Boolean ocelotOrParrotSpawn(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }
}