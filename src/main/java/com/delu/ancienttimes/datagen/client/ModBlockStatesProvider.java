package com.delu.ancienttimes.datagen.client;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.registries.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStatesProvider extends BlockStateProvider {

    public ModBlockStatesProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, AncientTimes.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        makeMardFlowerModel(ModBlocks.MARD_FLOWER.get());
    }

    protected void makeMardFlowerModel(Block block) {
        getVariantBuilder(block).forAllStates(state -> {
            int age = state.getValue(BlockStateProperties.AGE_3);
            String modelName = ForgeRegistries.BLOCKS.getKey(block).getPath();


            return ConfiguredModel.builder().modelFile(models().withExistingParent(modelName + "_stage_" + age, modLoc("block/mard_flower_prefab")).texture("crop", "block/" + modelName + "_stage_" + age).renderType(mcLoc("cutout"))).build();
        });
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
