package com.delu.ancienttimes.datagen.server;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.registries.ModBlocks;
import com.delu.ancienttimes.registries.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> recipeGenerator) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, Items.RED_DYE).requires(ModItems.MARD_FLOWER.get()).unlockedBy("has_item", has(ModItems.MARD_FLOWER.get())).save(recipeGenerator, AncientTimes.modLoc("mard_flower_red_dye"));
        doorBuilder(ModBlocks.MEAL_DOOR.get(), Ingredient.of(ModBlocks.MEAL_PLANKS.get())).unlockedBy("has_item", has(ModBlocks.MEAL_PLANKS.get())).save(recipeGenerator);
        trapdoorBuilder(ModBlocks.MEAL_TRAPDOOR.get(), Ingredient.of(ModBlocks.MEAL_PLANKS.get())).unlockedBy("has_item", has(ModBlocks.MEAL_PLANKS.get())).save(recipeGenerator);
        planksFromLog(recipeGenerator, ModBlocks.MEAL_PLANKS.get(), ModBlocks.MEAL_LOG.get(), 4);

        slabBuilder(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MEAL_SLAB.get(), Ingredient.of(ModBlocks.MEAL_PLANKS.get())).unlockedBy("has_item", has(ModBlocks.MEAL_PLANKS.get())).save(recipeGenerator);
        stairBuilder(ModBlocks.MEAL_STAIRS.get(), Ingredient.of(ModBlocks.MEAL_PLANKS.get())).unlockedBy("has_item", has(ModBlocks.MEAL_PLANKS.get())).save(recipeGenerator);
    }


    protected static void planksFromLog(Consumer<FinishedRecipe> pFinishedRecipeConsumer, ItemLike pPlanks, ItemLike pLogs, int pResultCount) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.BUILDING_BLOCKS, pPlanks, pResultCount).requires(pLogs).group("planks").unlockedBy("has_log", has(pLogs)).save(pFinishedRecipeConsumer);
    }


}
