package com.flying_8lack.random.data;

import com.flying_8lack.random.main.ModBlock;
import com.flying_8lack.random.main.ModItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlock.FIG_BLOCK)
                .define('X', ModItem.FIG_FOOD)
                .pattern("XXX")
                .pattern("XXX")
                .pattern("XXX")
                .unlockedBy("has_fig", has(ModItem.FIG_FOOD))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlock.H_ELEVATOR)
                .define('F', ModBlock.FIG_BLOCK)
                .define('G', ModItem.SOFT_GUM)
                .define('X', ModItem.SILLY_GLASS_SHARD)
                .pattern("FGF")
                .pattern("GXG")
                .pattern("FFF")
                .unlockedBy("has_fig_block", has(ModBlock.FIG_BLOCK))
                .unlockedBy("has_soft_gum", has(ModItem.SOFT_GUM))
                .unlockedBy("has_silly_glass_shard", has(ModItem.SILLY_GLASS_SHARD))
                .save(recipeOutput);



        SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModItem.HARD_GUM), RecipeCategory.MISC,
                ModItem.SOFT_GUM, 2.0f, 50)
                .unlockedBy("has_hard_gum", has(ModItem.SOFT_GUM))
                .save(recipeOutput);


    }
}
