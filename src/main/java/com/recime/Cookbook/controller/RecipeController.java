package com.recime.Cookbook.controller;

import com.recime.Cookbook.dto.RecipeDTO;
import com.recime.Cookbook.mapper.RecipeMapper;
import com.recime.Cookbook.model.Recipe;
import com.recime.Cookbook.service.RecipeService;
import lombok.RequiredArgsConstructor;
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
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RecipeDTO> create(@RequestBody RecipeDTO dto) {
        Recipe recipe = RecipeMapper.toEntity(dto);
        Recipe saved = recipeService.saveRecipe(recipe);
        return ResponseEntity.ok(RecipeMapper.toDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!recipeService.exists(id)) {
            return ResponseEntity.notFound().build();
        }
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}
