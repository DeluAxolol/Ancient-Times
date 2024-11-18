package com.delu.ancienttimes.datagen.server;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.tags.ModTags;
import com.delu.ancienttimes.registries.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, AncientTimes.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        tag(ModTags.Blocks.TRIOCLANTUS_DIGGABLES).add(Blocks.DIRT, Blocks.GRASS, Blocks.COARSE_DIRT, Blocks.SAND, Blocks.MUD, Blocks.MUD);
        tag(BlockTags.LOGS).add(ModBlocks.MEAL_LOG.get());
        tag(BlockTags.LEAVES).add(ModBlocks.MEAL_LEAVES.get());
        tag(BlockTags.SAPLINGS).add(ModBlocks.MEAL_SAPLING.get());
        tag(BlockTags.STAIRS).add(ModBlocks.MEAL_STAIRS.get());
        tag(BlockTags.SLABS).add(ModBlocks.MEAL_SLAB.get());
        axe(
                ModBlocks.MEAL_TRAPDOOR,
                ModBlocks.MEAL_PLANKS,
                ModBlocks.MEAL_DOOR,
                ModBlocks.MEAL_LOG,
                ModBlocks.STRIPPED_MEAL_LOG,
                ModBlocks.MEAL_STAIRS,
                ModBlocks.MEAL_SLAB
        );
    }

    public void pickaxe(Supplier<? extends Block>... blocks) {
        IntrinsicTagAppender<Block> tagAppender = tag(BlockTags.MINEABLE_WITH_PICKAXE);
        Arrays.stream(blocks).map(Supplier::get).forEach(tagAppender::add);
    }

    public void shovel(Supplier<? extends Block>... blocks) {
        IntrinsicTagAppender<Block> tagAppender = tag(BlockTags.MINEABLE_WITH_SHOVEL);
        Arrays.stream(blocks).map(Supplier::get).forEach(tagAppender::add);
    }

    public void axe(Supplier<? extends Block>... blocks) {
        IntrinsicTagAppender<Block> tagAppender = tag(BlockTags.MINEABLE_WITH_AXE);
        Arrays.stream(blocks).map(Supplier::get).forEach(tagAppender::add);
    }

    public void hoe(Supplier<? extends Block>... blocks) {
        IntrinsicTagAppender<Block> tagAppender = tag(BlockTags.MINEABLE_WITH_HOE);
        Arrays.stream(blocks).map(Supplier::get).forEach(tagAppender::add);
    }

    public void stone(Supplier<? extends Block>... blocks) {
        IntrinsicTagAppender<Block> tagAppender = tag(BlockTags.NEEDS_STONE_TOOL);
        Arrays.stream(blocks).map(Supplier::get).forEach(tagAppender::add);
    }

    public void iron(Supplier<? extends Block>... blocks) {
        IntrinsicTagAppender<Block> tagAppender = tag(BlockTags.NEEDS_IRON_TOOL);
        Arrays.stream(blocks).map(Supplier::get).forEach(tagAppender::add);
    }

    public void diamond(Supplier<? extends Block>... blocks) {
        IntrinsicTagAppender<Block> tagAppender = tag(BlockTags.NEEDS_DIAMOND_TOOL);
        Arrays.stream(blocks).map(Supplier::get).forEach(tagAppender::add);
    }
}
