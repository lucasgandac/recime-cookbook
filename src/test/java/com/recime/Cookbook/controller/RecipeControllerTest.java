package com.recime.Cookbook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.recime.Cookbook.dto.RecipeDTO;
import com.recime.Cookbook.exception.RecipeCreationException;
import com.recime.Cookbook.exception.RecipeNotFoundException;
import com.recime.Cookbook.model.Recipe;
import com.recime.Cookbook.repository.criteria.params.RecipeParams;
import com.recime.Cookbook.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    private RecipeService recipeService;
    private RecipeController controller;
    private Recipe recipe;
    private RecipeDTO recipeDTO;

    @BeforeEach
    void setUp() {
        recipeService = mock(RecipeService.class);
        controller = new RecipeController(recipeService);

        recipe = new Recipe(1L, "Pasta", "Desc", "Cook it", true, 2, List.of("pasta", "tomato"));
        recipeDTO = new RecipeDTO(1L, "Pasta", "Desc", "Cook it", true, 2, List.of("pasta", "tomato"));
    }

    @Test
    void testGetRecipeById_found() {
        when(recipeService.getRecipeById(1L)).thenReturn(Optional.of(recipe));
        ResponseEntity<RecipeDTO> result = controller.getById(1L);
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals("Pasta", result.getBody().getTitle());
    }

    @Test
    void testGetRecipeById_notFound() {
        when(recipeService.getRecipeById(99L)).thenReturn(Optional.empty());
        assertThrows(RecipeNotFoundException.class, () -> controller.getById(99L));
    }

    @Test
    void testCreateRecipe_success() {
        when(recipeService.saveRecipe(any())).thenReturn(recipe);
        ResponseEntity<RecipeDTO> result = controller.create(recipeDTO);
        assertNotNull(result);
        assertNotNull(result.getBody());
        assertEquals("Pasta", result.getBody().getTitle());
    }

    @Test
    void testCreateRecipe_error() {
        when(recipeService.saveRecipe(any())).thenThrow(new RuntimeException("DB error"));
        assertThrows(RecipeCreationException.class, () -> controller.create(recipeDTO));
    }

    @Test
    void testDeleteRecipe_found() {
        when(recipeService.exists(1L)).thenReturn(true);
        doNothing().when(recipeService).deleteRecipe(1L);
        assertDoesNotThrow(() -> controller.delete(1L));
    }

    @Test
    void testDeleteRecipe_notFound() {
        when(recipeService.exists(99L)).thenReturn(false);
        assertThrows(RecipeNotFoundException.class, () -> controller.delete(99L));
    }

    @Test
    void testGetAllRecipes() {
        when(recipeService.getAllRecipes()).thenReturn(List.of(recipe));
        List<RecipeDTO> results = controller.getAll();
        assertEquals(1, results.size());
        assertEquals("Pasta", results.get(0).getTitle());
    }

    @Test
    void testSearchRecipes() {
        RecipeParams params = new RecipeParams();
        params.setVegetarian(true);
        params.setContentSearch("Pasta");

        when(recipeService.getFilteredRecipes(params)).thenReturn(List.of(recipe));

        var result = controller.searchRecipes(params);
        assertEquals(1, result.getBody().size());
        assertEquals("Pasta", result.getBody().get(0).getTitle());
    }
}
