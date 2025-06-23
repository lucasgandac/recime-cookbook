package com.recime.Cookbook.repository.criteria;

import com.recime.Cookbook.dto.RecipeDTO;
import com.recime.Cookbook.model.Recipe;
import com.recime.Cookbook.repository.criteria.params.RecipeParams;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepositoryCustom{
    List<Recipe> getRecipesWithFilter(RecipeParams params);
}