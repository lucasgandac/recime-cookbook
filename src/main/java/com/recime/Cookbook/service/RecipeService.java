package com.recime.Cookbook.service;

import com.recime.Cookbook.model.Ingredient;
import com.recime.Cookbook.model.Recipe;
import com.recime.Cookbook.repository.RecipeRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private final RecipeRepo recipeRepo;
    private final IngredientService ingredientService;

    public RecipeService(RecipeRepo recipeRepo, IngredientService ingredientService) {
        this.recipeRepo = recipeRepo;
        this.ingredientService = ingredientService;
    }

    public List<Recipe> getAllRecipes(){
        return recipeRepo.findAll();
    }

    public Optional<Recipe> getRecipeById(Long recipeId){
        return recipeRepo.findById(recipeId);
    }

    public void deleteRecipe(Long recipeId){
        recipeRepo.deleteById(recipeId);
    }

    public Recipe saveRecipe(Recipe recipe){
        List<Ingredient> processedIngredients = recipe.getIngredients().stream()
                        .map(ingredient -> ingredientService.findOrSaveByName(ingredient.getName()))
                .collect(Collectors.toList());

        recipe.setIngredients(processedIngredients);
        return recipeRepo.save(recipe);
    }

}
