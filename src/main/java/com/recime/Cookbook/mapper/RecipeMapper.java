package com.recime.Cookbook.mapper;

import com.recime.Cookbook.dto.RecipeDTO;
import com.recime.Cookbook.model.Recipe;

import java.util.List;
import java.util.stream.Collectors;

public abstract class RecipeMapper {

    public static RecipeDTO toDTO(Recipe recipe) {
        return RecipeDTO.builder()
                .id(recipe.getId())
                .title(recipe.getTitle())
                .description(recipe.getDescription())
                .instruction(recipe.getInstruction())
                .vegetarian(recipe.isVegetarian())
                .servings(recipe.getServings())
                .ingredient(recipe.getIngredient())
                .build();
    }

    public static Recipe toEntity(RecipeDTO dto) {
        Recipe recipe = new Recipe();
        recipe.setTitle(dto.getTitle());
        recipe.setDescription(dto.getDescription());
        recipe.setInstruction(dto.getInstruction());
        recipe.setVegetarian(dto.isVegetarian());
        recipe.setServings(dto.getServings());
        recipe.setIngredient(dto.getIngredient());
        return recipe;
    }
}