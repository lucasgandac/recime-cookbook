package com.recime.Cookbook.repository;

import com.recime.Cookbook.model.Recipe;
import com.recime.Cookbook.repository.criteria.RecipeRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long>, RecipeRepositoryCustom {
}