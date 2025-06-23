package com.recime.Cookbook.service;

import com.recime.Cookbook.model.Recipe;
import com.recime.Cookbook.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes(){
        return recipeRepository.findAll();
    }

    public Optional<Recipe> getRecipeById(Long recipeId){
        return recipeRepository.findById(recipeId);
    }

    public void deleteRecipe(Long recipeId){
        recipeRepository.deleteById(recipeId);
    }

    public Recipe saveRecipe(Recipe recipe){
        return recipeRepository.save(recipe);
    }

    public boolean exists(Long id) {
        return recipeRepository.existsById(id);
    }
}
