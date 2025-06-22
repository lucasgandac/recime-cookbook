package com.recime.Cookbook.service;

import com.recime.Cookbook.model.Ingredient;
import com.recime.Cookbook.repository.IngredientRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientService {
    private final IngredientRepo ingredientRepo;

    public IngredientService(IngredientRepo ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    public Ingredient findOrSaveByName(String ingredientName){
        return ingredientRepo.findByName(ingredientName).orElseGet(() ->
                ingredientRepo.save(new Ingredient(null, ingredientName))
        );
    }

    public Optional<Ingredient> findByName(String ingredientName){
        return ingredientRepo.findByName(ingredientName);
    }
}
