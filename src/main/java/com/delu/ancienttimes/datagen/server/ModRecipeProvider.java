package com.delu.ancienttimes.datagen.server;

import com.delu.ancienttimes.AncientTimes;
import com.delu.ancienttimes.registries.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput p_248933_) {
        super(p_248933_);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> recipeGenerator) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, Items.RED_DYE).requires(ModItems.MARD_FLOWER.get()).unlockedBy("has_item", has(ModItems.MARD_FLOWER.get())).save(recipeGenerator, AncientTimes.modLoc("mard_flower_red_dye"));
    }
}
