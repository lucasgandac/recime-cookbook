package com.recime.Cookbook.service;

import com.recime.Cookbook.model.Recipe;
import com.recime.Cookbook.repository.RecipeRepository;
import com.recime.Cookbook.repository.criteria.params.RecipeParams;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class )

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    @Test
    void testGetAllRecipes() {
        List<Recipe> mockList = List.of(new Recipe(), new Recipe());
        when(recipeRepository.findAll()).thenReturn(mockList);

        List<Recipe> result = recipeService.getAllRecipes();
        assertEquals(2, result.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void testGetRecipeById_found() {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        Optional<Recipe> result = recipeService.getRecipeById(1L);
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    void testGetRecipeById_notFound() {
        when(recipeRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Recipe> result = recipeService.getRecipeById(99L);
        assertFalse(result.isPresent());
    }

    @Test
    void testDeleteRecipe() {
        recipeService.deleteRecipe(5L);
        verify(recipeRepository, times(1)).deleteById(5L);
    }

    @Test
    void testSaveRecipe_shouldLowercaseIngredients() {
        Recipe recipe = new Recipe();
        recipe.setIngredient(List.of("Tomato", "Onion"));

        Recipe expectedSaved = new Recipe();
        expectedSaved.setIngredient(List.of("tomato", "onion"));

        when(recipeRepository.save(any(Recipe.class))).thenReturn(expectedSaved);

        Recipe saved = recipeService.saveRecipe(recipe);
        assertEquals(List.of("tomato", "onion"), saved.getIngredient());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    void testExists() {
        when(recipeRepository.existsById(1L)).thenReturn(true);

        assertTrue(recipeService.exists(1L));
        verify(recipeRepository, times(1)).existsById(1L);
    }

    @Test
    void testGetFilteredRecipes() {
        RecipeParams params = new RecipeParams();
        List<Recipe> mockFiltered = List.of(new Recipe());

        when(recipeRepository.getRecipesWithFilter(params)).thenReturn(mockFiltered);

        List<Recipe> result = recipeService.getFilteredRecipes(params);
        assertEquals(1, result.size());
        verify(recipeRepository, times(1)).getRecipesWithFilter(params);
    }
}
