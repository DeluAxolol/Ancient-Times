package com.delu.ancienttimes.datagen.client;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.common.block.RavenheadSprouts;
import com.delu.ancienttimes.common.util.ResourceLocationUtils;
import com.delu.ancienttimes.registries.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;

public class ModBlockStatesProvider extends BlockStateProvider {

    public ModBlockStatesProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, AncientTimes.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        makeMardFlowerModel(ModBlocks.MARD_FLOWER.get());
        axisWithItem(ModBlocks.MEAL_LOG.get());
        axisWithItem(ModBlocks.STRIPPED_MEAL_LOG.get());
        sapling(ModBlocks.MEAL_SAPLING.get());
        simpleBlockWithItem(ModBlocks.MEAL_PLANKS.get());
        simpleBlockWithItem(ModBlocks.MEAL_LEAVES.get());
        makeDoor(ModBlocks.MEAL_DOOR.get());
        makeTrapdoor(ModBlocks.MEAL_TRAPDOOR.get());
        stairsFromPlanks(ModBlocks.MEAL_STAIRS.get(), ModBlocks.MEAL_PLANKS.get());
        slabFromPlanks(ModBlocks.MEAL_SLAB.get(), ModBlocks.MEAL_PLANKS.get());
        makeRavenHeadSproutsModel((CropBlock) ModBlocks.RAVENHEAD_SPROUTS.get(), "ravenheadsprouts_stage", "ravenheadsprouts_stage");

    }

    public void stairsFromPlanks(StairBlock stair, Block planks){
        stairsBlock(stair, blockTexture(planks));
        simpleBlockItem(stair, existing(stair));
    }

    public void slabFromPlanks(SlabBlock slab, Block planks){
        slabBlock(slab, blockTexture(planks), blockTexture(planks));
        simpleBlockItem(slab, existing(slab));
    }


    public void makeTrapdoor(TrapDoorBlock trapdoor){
        ResourceLocation name = blockTexture(trapdoor);
        trapdoorBlockWithRenderType(trapdoor, name, true, mcLoc("cutout"));
        simpleBlockItem(trapdoor, models().getExistingFile(ResourceLocationUtils.extend(key(trapdoor), "_bottom")));
    }
    public void makeDoor(DoorBlock door){
        ResourceLocation name = blockTexture(door);
        doorBlock(door, ResourceLocationUtils.extend(name, "_bot"), ResourceLocationUtils.extend(name, "_top"));
        itemModels().basicItem(door.asItem());
    }

    public void simpleBlockWithItem(Block block) {
        simpleBlockWithItem(block, cubeAll(block));
    }

    protected void sapling(Block sapling){
        ResourceLocation saplingTexture = blockTexture(sapling);
        simpleBlock(sapling, models().cross(key(sapling).getPath(), saplingTexture).renderType(mcLoc("cutout")));
        basicItem(sapling);
    }

    protected void axisWithItem(RotatedPillarBlock block){
        ResourceLocation name = blockTexture(block);
        axisBlock(block, ResourceLocationUtils.extend(name, "_side"), ResourceLocationUtils.extend(name, "_top"));
        simpleBlockItem(block, existing(block));
    }

    protected void makeMardFlowerModel(Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            int age = state.getValue(BlockStateProperties.AGE_3);
            String modelName = ForgeRegistries.BLOCKS.getKey(block).getPath();


            return ConfiguredModel.builder().modelFile(models().withExistingParent(modelName + "_stage_" + age, modLoc("block/mard_flower_prefab")).texture("crop", "block/" + modelName + "_stage_" + age).renderType(mcLoc("cutout"))).build();
        });
    }

    public void makeRavenHeadSproutsModel(CropBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> ravenHeadSproutsStates(state, block, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] ravenHeadSproutsStates(BlockState state, CropBlock block, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((RavenheadSprouts) block).getAgeProperty()),
                new ResourceLocation(AncientTimes.MODID, "block/" + textureName + state.getValue(((RavenheadSprouts) block).getAgeProperty()))).renderType("cutout"));

        return models;
    }

    public void basicItem(Block item)
    {
        ResourceLocation key = key(item);
        itemModels().getBuilder(key.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocationUtils.prepend(key, "block/"));
    }

    public ModelFile existing(Block block) {
        return models().getExistingFile(ForgeRegistries.BLOCKS.getKey(block));
    }

    public ResourceLocation blockTextureLoc(String name) {
        return new ResourceLocation(AncientTimes.MODID, ModelProvider.BLOCK_FOLDER + "/" + name);
    }

    protected ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    protected String name(Block block) {
        return key(block).getPath();
    }

}
