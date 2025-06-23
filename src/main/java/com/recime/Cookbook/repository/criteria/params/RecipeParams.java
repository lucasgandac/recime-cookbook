package com.recime.Cookbook.repository.criteria.params;

import lombok.Data;

import java.util.List;

@Data
public class RecipeParams {
    private Boolean vegetarian;
    private Integer servings;
    private List<String> includeIngredients;
    private List<String> excludeIngredients;
    private String contentSearch;
}
