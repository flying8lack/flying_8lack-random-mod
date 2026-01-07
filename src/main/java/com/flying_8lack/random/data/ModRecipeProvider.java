package com.flying_8lack.random.data;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.DIAMOND)
//                .define('X', Items.SUGAR)
//                .pattern("X X")
//                .unlockedBy("has_sugar", has(Items.SUGAR))
//                .save(recipeOutput);
    }
}
