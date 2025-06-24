package com.recime.Cookbook.controller;

import com.recime.Cookbook.dto.RecipeDTO;
import com.recime.Cookbook.exception.RecipeCreationException;
import com.recime.Cookbook.exception.RecipeNotFoundException;
import com.recime.Cookbook.mapper.RecipeMapper;
import com.recime.Cookbook.model.Recipe;
import com.recime.Cookbook.repository.criteria.params.RecipeParams;
import com.recime.Cookbook.service.RecipeService;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.hibernate.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping()
    public List<RecipeDTO> getAll() {
        return recipeService.getAllRecipes().stream()
                .map(RecipeMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeDTO> getById(@PathVariable Long id) {
        return recipeService.getRecipeById(id)
                .map(RecipeMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe with id " + id.toString() + " not found"));
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> create(@RequestBody RecipeDTO dto) {
        try {
            Recipe recipe = RecipeMapper.toEntity(dto);
            Recipe saved = recipeService.saveRecipe(recipe);
            return ResponseEntity.ok(RecipeMapper.toDTO(saved));
        }
        catch (Exception e){
            throw new RecipeCreationException("Recipe could not be created: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!recipeService.exists(id)) {
            throw new RecipeNotFoundException("Recipe with id " + id.toString() + " not found");
        }
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<RecipeDTO>> searchRecipes(
            @ParameterObject RecipeParams recipeParams
    ) {
        List<Recipe> filtered = recipeService.getFilteredRecipes(recipeParams);
        List<RecipeDTO> result = filtered.stream()
                .map(RecipeMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }
}
